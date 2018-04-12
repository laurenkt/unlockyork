import components.ImageView;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.bytedeco.javacv.*;
import org.omg.IOP.Codec;

import org.bytedeco.javacpp.*;

import java.io.Console;
import java.io.File;

import static org.bytedeco.javacpp.avcodec.*;
import static org.bytedeco.javacpp.avformat.*;
import static org.bytedeco.javacpp.avutil.*;
import static org.bytedeco.javacpp.swscale.*;

public class TestModules extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        int width = 1000;
        int height = 500;

        Group root = new Group();
        Scene scene = new Scene(root, width, height, Color.BLACK);

        ImageView imageView = new ImageView(new Image(getClass().getResource("test_image.jpg").toExternalForm(), width, height, false, false));
        imageView.setLoupeVisible(true);
        root.getChildren().add(imageView);

        primaryStage.setScene(scene);
        primaryStage.show();


        //FFmpegLogCallback.set();
        //av_log_set_level(AV_LOG_DEBUG);
        trim_video(5, 10, "./local_file.mp4", "./new_file.mp4");
    }

    /**
     * Approach using JavaCV
     * @param from_seconds
     * @param end_seconds
     * @param in_filename
     * @param out_filename
     */
    void trim_video(long from_seconds, long end_seconds, String in_filename, String out_filename) {
        long from_seconds_us = from_seconds * 1000 * 1000;
        long end_seconds_us = end_seconds * 1000 * 1000;

        FrameGrabber grabber = new FFmpegFrameGrabber(in_filename);
        try {
            grabber.start();
            grabber.setTimestamp(from_seconds_us); // Write from specific moment

            File out = new File(out_filename); // Set destination to write
            FrameRecorder recorder = new FFmpegFrameRecorder(out, grabber.getImageWidth(), grabber.getImageHeight());

            recorder.setFormat(grabber.getFormat());
            recorder.setFrameRate(grabber.getFrameRate());
            recorder.setSampleRate(grabber.getSampleRate());
            recorder.setAspectRatio(grabber.getAspectRatio());
            // Seems to be the first format that works on my machine --
            // There must be a way to enumerate compatible sample formats
            recorder.setSampleFormat(AV_SAMPLE_FMT_FLTP);

            recorder.setAudioCodec(grabber.getAudioCodec());
            recorder.setAudioBitrate(grabber.getAudioBitrate());
            recorder.setAudioChannels(grabber.getAudioChannels());

            recorder.setVideoCodec(grabber.getVideoCodec());
            recorder.setVideoBitrate(grabber.getVideoBitrate());

            recorder.start();

            Frame frame;
            Long timestamp;
            Long fullLength = end_seconds_us - from_seconds_us;
            double percent = 0d, oldPercent = 0d;

            while ((frame = grabber.grabFrame()) != null && (timestamp = grabber.getTimestamp()) <= end_seconds_us) {
                recorder.setTimestamp(grabber.getTimestamp() - from_seconds_us);
                recorder.record(frame);
            }

            grabber.close();
            recorder.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Approach using JavaCPP-ffmpeg
     * @param from_seconds
     * @param end_seconds
     * @param in_filename
     * @param out_filename
     */
    void cut_video(double from_seconds, double end_seconds, String in_filename, String out_filename) {
        AVOutputFormat outputFormat     = new AVOutputFormat(null);
        AVFormatContext inputFormatCtx  = new AVFormatContext(null);
        AVFormatContext outputFormatCtx = new AVFormatContext(null);
        AVPacket pkt = new AVPacket();
        int ret = 0, i = 0;

        av_register_all();

        try {
            if ((ret = avformat_open_input(inputFormatCtx, in_filename, null, null)) < 0) {
                throw new Exception("Could not open input file");
            }

            if ((ret = avformat_find_stream_info(inputFormatCtx, (PointerPointer) null)) < 0) {
                throw new Exception ("Failed to retrieve input stream information");
            }

            av_dump_format(inputFormatCtx, 0, in_filename, 0);

            avformat_alloc_output_context2(outputFormatCtx, null, null, out_filename);
            if (outputFormatCtx.isNull()) {
                ret = AVERROR_UNKNOWN;
                throw new Exception("Could not create output context");
            }

            outputFormat = outputFormatCtx.oformat();

            for (i = 0; i < inputFormatCtx.nb_streams(); i++) {
                AVStream in_stream = inputFormatCtx.streams(i);
                AVStream out_stream = avformat_new_stream(outputFormatCtx, in_stream.codec().codec());
                if (out_stream.isNull()) {
                    ret = AVERROR_UNKNOWN;
                    throw new Exception("Failed allocating output stream");
                }

                ret = avcodec_copy_context(out_stream.codec(), in_stream.codec());
                if (ret < 0) {
                    throw new Exception("Failed to copy context from input to output stream codec context");
                }
                out_stream.codec().codec_tag(0);
                if ((outputFormatCtx.oformat().flags() & AVFMT_GLOBALHEADER) > 0)
                    out_stream.codec().flags(out_stream.codec().flags() | AV_CODEC_FLAG_GLOBAL_HEADER);
            }
            av_dump_format(outputFormatCtx, 0, out_filename, 1);

            if (!((outputFormat.flags() & AVFMT_NOFILE) > 0)) {
                ret = avio_open(outputFormatCtx.pb(), out_filename, AVIO_FLAG_WRITE);
                if (ret < 0) {
                    throw new Exception("Could not open output file");
                }
            }

            ret = avformat_write_header(outputFormatCtx, (PointerPointer)null);
            if (ret < 0) {
                throw new Exception("Error occurred when opening output file");
            }

            ret = av_seek_frame(inputFormatCtx, -1, ((long) from_seconds) * AV_TIME_BASE, AVSEEK_FLAG_ANY);
            if (ret < 0) {
                throw new Exception("Error seek");
            }

            long[] dts_start_from = new long[inputFormatCtx.nb_streams()];
            long[] pts_start_from = new long[inputFormatCtx.nb_streams()];

            while (true) {
                AVStream in_stream, out_stream;

                ret = av_read_frame(inputFormatCtx, pkt);
                if (ret < 0)
                    break;

                in_stream = inputFormatCtx.streams(pkt.stream_index());
                out_stream = outputFormatCtx.streams(pkt.stream_index());

                //log_packet(ifmt_ctx, & pkt, "in");

                if (av_q2d(in_stream.time_base()) * pkt.pts() > end_seconds) {
                    av_packet_unref(pkt);
                    break;
                }

                if (dts_start_from[pkt.stream_index()] == 0) {
                    dts_start_from[pkt.stream_index()] = pkt.dts();
                    System.out.printf("dts_start_from: %d\n", (dts_start_from[pkt.stream_index()]));
                }
                if (pts_start_from[pkt.stream_index()] == 0) {
                    pts_start_from[pkt.stream_index()] = pkt.pts();
                    System.out.printf("pts_start_from: %d\n", (pts_start_from[pkt.stream_index()]));
                }

                /* copy packet */
                pkt.pts(av_rescale_q_rnd(pkt.pts() - pts_start_from[pkt.stream_index()], in_stream.time_base(), out_stream.time_base(), AV_ROUND_NEAR_INF | AV_ROUND_PASS_MINMAX));
                pkt.dts(av_rescale_q_rnd(pkt.dts() - dts_start_from[pkt.stream_index()], in_stream.time_base(), out_stream.time_base(), AV_ROUND_NEAR_INF | AV_ROUND_PASS_MINMAX));
                if (pkt.pts() < 0) {
                    pkt.pts(0);
                }
                if (pkt.dts() < 0) {
                    pkt.dts(0);
                }
                pkt.duration(av_rescale_q((long) pkt.duration(), in_stream.time_base(), out_stream.time_base()));
                pkt.pos(-1);
                //log_packet(ofmt_ctx, & pkt, "out");
                //printf("\n");

                ret = av_interleaved_write_frame(outputFormatCtx, pkt);
                if (ret < 0) {
                    System.err.println("Error muxing packet\n");
                    break;
                }
                av_packet_unref(pkt);
            }

            av_write_trailer(outputFormatCtx);
        }
        catch (Exception e) {
            System.err.println(e);
        }
        finally {
            avformat_close_input(inputFormatCtx);

            /* close output */
            if (!outputFormatCtx.isNull() && !((outputFormat.flags() & AVFMT_NOFILE) != 0))
                avio_closep(outputFormatCtx.pb());
            avformat_free_context(outputFormatCtx);

            if (ret < 0 && ret != AVERROR_EOF) {
                System.err.println("Error occurred:");
            }
        }
    }
}
