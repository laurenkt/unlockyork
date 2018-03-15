import components.MapView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Kiosk extends Application {

    @Override
    public void start(Stage primaryStage) {
        /*
        StackPane root = new StackPane();

        MediaPlayer player = new MediaPlayer( new Media(getClass().getResource("unlockyork.mp4").toExternalForm()));
        MediaView mediaView = new MediaView(player);

        root.getChildren().add(mediaView);*/


        primaryStage.setTitle("Drag to pan the map");

        MapView map = new MapView(new Image(getClass().getResource("York20.png").toExternalForm()));
        Scene scene = new Scene(map, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        /*
        Scene scene = new Scene(root,700, 700);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();*/
        //player.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
