import components.MapView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import sun.applet.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class Kiosk extends Application {

    private MapView map;
    private double changeInX = 0;
    private double changeInY = 0;
    private double XPosPressed = 0;
    private double YPosPressed = 0;
    private double XPosReleased = 0;
    private double YPosReleased = 0;

    @Override
    public void start(Stage primaryStage) {
        /*
        StackPane root = new StackPane();

        MediaPlayer player = new MediaPlayer( new Media(getClass().getResource("unlockyork.mp4").toExternalForm()));
        MediaView mediaView = new MediaView(player);

        root.getChildren().add(mediaView);*/


        primaryStage.setTitle("Drag to pan the map");

        Image poiIcon = new Image(this.getClass().getClassLoader().getResource("poi.png").toExternalForm());
        System.out.println("load image from " + getClass().getResource("poi.png"));
        Image mapLayout = new Image(getClass().getResource("map.png").toExternalForm());

        map = new MapView(mapLayout,poiIcon);
        Scene scene = new Scene(map);

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double EventX = event.getX();
                double EventY = event.getY();
                double xPoiMin = map.getXPoiMin();
                double xPoiMax = map.getXPoiMax();
                double yPoiMin = map.getYPoiMin();
                double yPoiMax = map.getYPoiMax();
                Boolean xMinister = false;
                Boolean yMinister = false;

                System.out.println("mouse clicked at x = " + EventX + " y = " + EventY);
                System.out.println("xPoiMin = " + xPoiMin + ", xPoiMax = " + xPoiMax);
                System.out.println("yPoiMin = " + yPoiMin + " yPoiMax = " + yPoiMax);

                if(EventX >= xPoiMin && EventX <= xPoiMax) {
                    xMinister = true;
                }
                if(EventY >= yPoiMin && EventY <= xPoiMax) {
                    yMinister = true;
                }
                if(xMinister == true && yMinister == true) {
                    System.out.println("Clicked on the Minister");
                }
            }
        });

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
