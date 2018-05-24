import org.bytedeco.javacpp.*;

import java.io.FileNotFoundException;

import static org.bytedeco.javacpp.avcodec.*;
import static org.bytedeco.javacpp.avformat.*;
import static org.bytedeco.javacpp.avutil.*;

/**
 *
 * @author Unlock (lt696@york.ac.uk)
 */
public abstract class VideoTool {

    /**
     *
     * @param path
     * @param from_seconds
     * @param end_seconds
     * @return
     * @throws Exception
     */
    public static String trim(String path, double from_seconds, double end_seconds) throws Exception {
        return trim(path, from_seconds, end_seconds, false);
    }

    /**
     * Approach using JavaCPP-ffmpeg
     * @param path
     * @param from_seconds
     * @param end_seconds
     */
    public static String trim(String path, double from_seconds, double end_seconds, boolean log) throws Exception {
        av_log_set_level(log ? AV_LOG_DEBUG : AV_LOG_ERROR);

        AVOutputFormat outputFormat = new AVOutputFormat(null);
        AVFormatContext inputFormatCtx = new AVFormatContext(null);
        AVFormatContext outputFormatCtx = new AVFormatContext(null);
        AVPacket pkt = new AVPacket();
        int ret = 0;
        String out_filename = "" + Double.toString(from_seconds) + "to" + Double.toString(end_seconds) + "_" + path;

        av_register_all();

        try {
            if ((ret = avformat_open_input(inputFormatCtx, path, null, null)) < 0) {
                throw new FileNotFoundException("Could not open input file");
            }

            if ((ret = avformat_find_stream_info(inputFormatCtx, (PointerPointer) null)) < 0) {
                throw new Exception("Failed to retrieve input stream information");
            }

            if (log)
                av_dump_format(inputFormatCtx, 0, path, 0);

            avformat_alloc_output_context2(outputFormatCtx, null, null, out_filename);
            if (outputFormatCtx.isNull()) {
                ret = AVERROR_UNKNOWN;
                throw new Exception("Could not create output context");
            }

            outputFormat = outputFormatCtx.oformat();

            for (int i = 0; i < inputFormatCtx.nb_streams(); i++) {
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

            if (log)
                av_dump_format(outputFormatCtx, 0, out_filename, 1);

            if (!((outputFormat.flags() & AVFMT_NOFILE) > 0)) {
                AVIOContext pb = new AVIOContext(null);
                ret = avio_open(pb, out_filename, AVIO_FLAG_WRITE);
                outputFormatCtx.pb(pb);
                if (ret < 0) {
                    throw new Exception("Could not open output file");
                }
            }

            ret = avformat_write_header(outputFormatCtx, (PointerPointer) null);
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
                }
                if (pts_start_from[pkt.stream_index()] == 0) {
                    pts_start_from[pkt.stream_index()] = pkt.pts();
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

                ret = av_interleaved_write_frame(outputFormatCtx, pkt);
                if (ret < 0) {
                    System.err.println("Error muxing packet\n");
                    break;
                }
                av_packet_unref(pkt);
            }

            av_write_trailer(outputFormatCtx);
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        } finally {
            avformat_close_input(inputFormatCtx);

            if (!outputFormatCtx.isNull() && !((outputFormat.flags() & AVFMT_NOFILE) != 0))
                avio_closep(outputFormatCtx.pb());

            avformat_free_context(outputFormatCtx);

            if (ret < 0 && ret != AVERROR_EOF) {
                System.err.println("Error occurred:");
            }
        }

        return out_filename;
    }
}
