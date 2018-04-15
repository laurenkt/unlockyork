import components.PictureView;
import components.MovieView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.util.Duration;

import java.nio.file.Paths;


public class TestModules extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        int width = 1000;
        int height = 700;

        Group root = new Group();
        Scene scene = new Scene(root, width, height, Color.BLACK);

        PictureView pictureView = new PictureView(
                new Image(getClass().getClassLoader().getResource("test_image.jpg").toExternalForm(), width, height, false, false),
                100, 500, 200, 200
        );
        pictureView.setLoupeEnabled(true);
        root.getChildren().add(pictureView);

        pictureView.setStyle("-fx-opacity:0.0");
        DoubleProperty opacity = new SimpleDoubleProperty();
        opacity.addListener((ov, prev, val) -> pictureView.setStyle("-fx-opacity:" + val));
        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.millis(   0), new KeyValue(opacity, new Double(0.0))),
            new KeyFrame(Duration.millis(5000), new KeyValue(opacity, new Double(0.0))),
            new KeyFrame(Duration.millis(5500), new KeyValue(opacity, new Double(1.0)))
        );
        timeline.play();


        PictureView gifView = new PictureView(
                new Image(Paths.get("test_gif.gif").toUri().toString(), width, height, false, false),
                500, 100, 400,300
        );
        gifView.setLoupeEnabled(true);
        root.getChildren().add(gifView);

        MovieView movieView = new MovieView(
                "./local_file.mp4",
                10, 10, 480,360
        );
        root.getChildren().add(movieView);

        primaryStage.setScene(scene);
        primaryStage.show();

        /*
        scene.setOnMouseClicked(e -> {
            pictureView.crop(20, 20, 100, 150);
        });*/
    }
}
