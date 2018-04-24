import components.MapView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Kiosk extends Application {

    private MapView map;
    private double changeInX = 0;
    private double changeInY = 0;
    private double XPosPressed = 0;
    private double YPosPressed = 0;
    private double XPosReleased = 0;
    private double YPosReleased = 0;

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

        BorderPane poiInfo = new BorderPane();
        HBox UserButtons = new HBox();
        ScrollPane Info = new ScrollPane();

        Text poiMinister = new Text("York Minister");
        Text info = new Text("Welcome to the york minister");

        Button back = new Button("Back");
        Button audioTour = new Button("Audio Tour");
        Button Video = new Button("Video");
        Button SubMap = new Button("Sub-map");
        Button Presentataion = new Button("Presentation");

        HBox.setHgrow(back, Priority.ALWAYS);
        HBox.setHgrow(audioTour, Priority.ALWAYS);
        HBox.setHgrow(Video, Priority.ALWAYS);
        HBox.setHgrow(SubMap, Priority.ALWAYS);
        HBox.setHgrow(Presentataion, Priority.ALWAYS);
        back.setMaxWidth(Double.MAX_VALUE);
        audioTour.setMaxWidth(Double.MAX_VALUE);
        Video.setMaxWidth(Double.MAX_VALUE);
        SubMap.setMaxWidth(Double.MAX_VALUE);
        Presentataion.setMaxWidth(Double.MAX_VALUE);

        UserButtons.getChildren().addAll(back,audioTour,Video,SubMap,Presentataion);
        UserButtons.setAlignment(Pos.CENTER);

        Image YorkMinister = new Image(getClass().getClassLoader().getResource("YorkMinister.jpg").toExternalForm());
        ImageView YorkMinisterImage = new ImageView(YorkMinister);
        //String videoPath = new String(getClass().getClassLoader().getResource("unlockyork.mp4)").toExternalForm());

        //Media media = new Media(videoPath);
        //MediaPlayer mediaPlayer = new MediaPlayer(media);

        MediaPlayer videoPlayer = new MediaPlayer( new Media(getClass().getResource("unlockyork.mp4").toExternalForm()));
        MediaPlayer audioPlayer = new MediaPlayer( new Media(getClass().getResource("TrialAudio.mp4").toExternalForm()));

        poiInfo.setTop(poiMinister);
        poiInfo.setAlignment(poiMinister, Pos.CENTER);
        poiInfo.setCenter(info);
        poiInfo.setAlignment(info, Pos.CENTER);
        poiInfo.setBottom(UserButtons);
        poiInfo.setAlignment(UserButtons, Pos.CENTER);

        Scene scene2 = new Scene(poiInfo);

        Scene scene = new Scene(map);
        back.setOnAction(e -> primaryStage.setScene(scene));
        Video.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MediaView mediaView = new MediaView(videoPlayer);
                poiInfo.setCenter(mediaView);
                videoPlayer.play();
                }
        });
        audioTour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                poiInfo.setCenter(YorkMinisterImage);
                audioPlayer.play();
            }
        });
        SubMap.setOnAction(e -> poiInfo.setCenter(YorkMinisterImage));
        Presentataion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PresentationView view = new PresentationView();
                try {
                    view.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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
                    primaryStage.setScene(scene2);
                    primaryStage.setFullScreen(true);
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
