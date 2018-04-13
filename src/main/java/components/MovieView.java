package components;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.nio.file.Paths;

public class MovieView extends Region {
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private BorderPane borderPane;
    private HBox toolbar;

    private Button playPauseButton = new Button("Pause");
    private Button muteButton = new Button("Mute");
    private Slider seekSlider = new Slider(0, 1, 0);
    private Slider rateSlider = new Slider(0.5, 2.0, 1);
    private Slider volumeSlider = new Slider(0, 1, 0.8);

    public MovieView(String path, double x, double y, double width, double height) {
        mediaPlayer = new MediaPlayer(new Media(Paths.get(path).toUri().toString()));
        mediaPlayer.autoPlayProperty().setValue(true);

        mediaView = new MediaView(mediaPlayer);
        toolbar = addToolBar();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(mediaView);
        borderPane.setBottom(toolbar);

        borderPane.setStyle("-fx-background-color: Black");

        toolbar.getChildren().addAll(
                playPauseButton,
                seekSlider,
                rateSlider,
                muteButton,
                volumeSlider
        );

        setLayoutX(x);
        setLayoutY(y);
        setWidth(width);
        setMaxWidth(width);
        setHeight(height);
        setMaxHeight(height);

        getChildren().add(borderPane);

        setOnMouseClicked(e -> {
            mediaPlayer.seek(new Duration(0));
        });

        playPauseButton.setOnMouseClicked(e -> {
            if (playPauseButton.getText() == "Play") {
                mediaPlayer.play();
                playPauseButton.setText("Pause");
            }
            else {
                mediaPlayer.pause();
                playPauseButton.setText("Play");
            }
        });

        muteButton.setOnMouseClicked(e -> {
            if (muteButton.getText() == "Mute") {
                mediaPlayer.setMute(true);
                muteButton.setText("Unmute");
            }
            else {
                mediaPlayer.setMute(false);
                muteButton.setText("Mute");
            }
        });

        seekSlider.setShowTickLabels(true);
        seekSlider.setShowTickMarks(true);
        seekSlider.setMajorTickUnit(60);
        seekSlider.setMinorTickCount(0);
        seekSlider.maxProperty().bind(
                Bindings.createDoubleBinding(
                    () -> mediaPlayer.getTotalDuration() == null ?
                            0 :
                            mediaPlayer.getTotalDuration().toSeconds(),
                    mediaPlayer.totalDurationProperty()
                )
        );
        toolbar.setHgrow(seekSlider, Priority.ALWAYS);
        seekSlider.setMaxWidth(Double.POSITIVE_INFINITY);

        volumeSlider.setMaxWidth(50);
        rateSlider.setMaxWidth(50);
        mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> ov, Duration old_val, Duration new_val) -> {
            seekSlider.setValue(new_val.toSeconds());
        });
        /*
        seekSlider.setOnMouseClicked(e -> {
            mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
        });
        /*
        seekSlider.valueProperty().addListener((ov, prev_val, new_val) -> {
            mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(new_val.doubleValue()));
        });*/

        volumeSlider.valueProperty().addListener((ov, prev_val, new_val) -> {
            mediaPlayer.setVolume(new_val.doubleValue());
        });

        rateSlider.setShowTickLabels(true);
        rateSlider.setShowTickMarks(true);
        rateSlider.setMajorTickUnit(0.25);
        rateSlider.setMinorTickCount(0);
        rateSlider.setBlockIncrement(0.25);
        rateSlider.setSnapToTicks(true);
        rateSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                if (value == 0.5)  return "0.5";
                if (value == 0.75) return "0.75";
                if (value == 1.0)  return "1.0";
                if (value == 1.5)  return "1.5";
                if (value == 2.0)  return "2.0";
                return "";
            }

            @Override
            public Double fromString(String string) {
                throw new UnsupportedOperationException();
            }
        });
        rateSlider.valueProperty().addListener((ov, prev_val, new_val) -> {
            mediaPlayer.setRate(new_val.doubleValue());
        });

    }

    private HBox addToolBar() {
        HBox toolBar = new HBox();
        toolBar.setPadding(new Insets(20));
        toolBar.setAlignment(Pos.CENTER);
        toolBar.alignmentProperty().isBound();
        toolBar.setSpacing(5);
        toolBar.setStyle("-fx-background-color: Black");

        return toolBar;
    }
}
