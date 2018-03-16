import components.MapView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Kiosk extends Application {

    private MapView map;
    public EventType testEvent;

    private List<String> getResourceFiles(String path ) throws IOException {
        List<String> filenames = new ArrayList<>();

        try(
                InputStream in = getResourceAsStream( path );
                BufferedReader br = new BufferedReader( new InputStreamReader( in ) ) ) {
            String resource;

            while( (resource = br.readLine()) != null ) {
                filenames.add( resource );
            }
        }

        return filenames;
    }

    private InputStream getResourceAsStream( String resource ) {
        final InputStream in
                = getContextClassLoader().getResourceAsStream( resource );

        return in == null ? getClass().getResourceAsStream( resource ) : in;
    }

    private ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    @Override
    public void start(Stage primaryStage) {
        /*
        StackPane root = new StackPane();

        MediaPlayer player = new MediaPlayer( new Media(getClass().getResource("unlockyork.mp4").toExternalForm()));
        MediaView mediaView = new MediaView(player);

        root.getChildren().add(mediaView);*/

        primaryStage.setTitle("Drag to pan the map");

        System.out.println(getClass().getClassLoader().getResource("York16.png"));

        Image poiIcon = new Image(getClass().getClassLoader().getResource("poi.png").toExternalForm());
        Image mapLayout = new Image(getClass().getClassLoader().getResource("York16.png").toExternalForm());
        map = new MapView(mapLayout, poiIcon);

        Scene scene = new Scene(map);

        mouseClick(scene);

        primaryStage.setScene(scene);
        primaryStage.show();

        /*
        Scene scene = new Scene(root,700, 700);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();*/
        //player.play();
    }

    public void mouseClick(Scene scene) {
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
                setTestEventType(event);

                //check list
                /*
                System.out.println(event.getEventType());
                System.out.println("mouse clicked at x = " + EventX + " y = " + EventY);
                System.out.println("xPoiMin = " + xPoiMin + ", xPoiMax = " + xPoiMax);
                System.out.println("yPoiMin = " + yPoiMin + " yPoiMax = " + yPoiMax);
                */

                // checks to see if the mouse click is within the x coordinate of the POI image
                if(EventX >= xPoiMin && EventX <= xPoiMax) {
                    xMinister = true;
                }
                // checks to see if the mouse click is within the Y coordinate of the POI image
                if(EventY >= yPoiMin && EventY <= xPoiMax) {
                    yMinister = true;
                }
                //If the mouse click is within the min and max, X and Y coordinates
                // the minister has been clicked on
                if(xMinister == true && yMinister == true) {
                    System.out.println("Clicked on the Minister");
                }
            }
        });
    }

    public void setTestEventType(MouseEvent event) {
        testEvent = event.getEventType();
    }

    public EventType getTestEventType() {
        return testEvent;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
