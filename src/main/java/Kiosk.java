import components.MapView;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import javafx.util.Duration;
import models.InfoView;
import models.Presentation;
import models.Slide;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Kiosk extends Application {

    private MapView map;
    private int SlideNum = 0;
    private Presentation presentation;
    private Group group;
    private HBox newSlideForward;
    private HBox newSlideBack;
    private double ScaleWidthFactor;
    private double ScaleHeightFactor;

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

        ScaleFactor();

        System.out.println(getClass().getClassLoader().getResource("York16.png"));

        Image poiIcon = new Image(getClass().getClassLoader().getResource("poi.png").toExternalForm());
        Image mapLayout = new Image(getClass().getClassLoader().getResource("York16.png").toExternalForm());
        map = new MapView(mapLayout, poiIcon);

        BorderPane userView = new BorderPane();
        //userView.setLeft(map);

        presentation = new Presentation();

        XMLParser parser = new XMLParser();
        presentation = parser.parser("src/build/resources/main/example.pws", "src/build/resources/main/schema.xsd");
        InfoView Info = new InfoView();
        group = new Group();
        group = Info.DisplayPresentationView(presentation, SlideNum, ScaleWidthFactor, ScaleHeightFactor);

        HBox slide = new HBox();
        HBox Buttons = new HBox();
        BorderPane presentationDisplay = new BorderPane();

        Button forward = new Button("Forward");
        Button back = new Button("Back");

        HBox.setHgrow(forward, Priority.ALWAYS);
        HBox.setHgrow(back, Priority.ALWAYS);
        forward.setMaxWidth(Double.MAX_VALUE);
        back.setMaxWidth(Double.MAX_VALUE);

        slide.getChildren().add(group);
        Buttons.getChildren().addAll(back,forward);
        Buttons.setAlignment(Pos.CENTER);

        presentationDisplay.setCenter(slide);
        presentationDisplay.setBottom(Buttons);

        presentationDisplay.setAlignment(slide, Pos.CENTER);
        presentationDisplay.setAlignment(Buttons, Pos.CENTER);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        Scene scene = new Scene(presentationDisplay, width, height);

        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(SlideNum < presentation.getSlides().size()) {
                    SlideNum = SlideNum + 1;
                }
                newSlideForward = new HBox();

                group = Info.DisplayPresentationView(presentation,SlideNum, ScaleWidthFactor, ScaleHeightFactor);
                Boolean resizable = group.isResizable();
                System.out.println("resizable = " + resizable);


                newSlideForward.getChildren().add(group);

                presentationDisplay.setCenter(newSlideForward);
                presentationDisplay.setBottom(Buttons);

                presentationDisplay.setAlignment(newSlideForward, Pos.CENTER);
                presentationDisplay.setAlignment(Buttons, Pos.CENTER);
                System.out.println("SlideNum = " + SlideNum);
            }
        });

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(SlideNum > 0) {
                    SlideNum = SlideNum - 1;
                }
                newSlideBack = new HBox();

                group = Info.DisplayPresentationView(presentation,SlideNum, ScaleWidthFactor, ScaleHeightFactor);
                group.prefHeight(100);
                group.prefWidth(100);
                newSlideBack.getChildren().add(group);

                presentationDisplay.setCenter(newSlideBack);
                presentationDisplay.setBottom(Buttons);

                presentationDisplay.setAlignment(newSlideBack, Pos.CENTER);
                presentationDisplay.setAlignment(Buttons, Pos.CENTER);

                System.out.println("SlideNum = " + SlideNum);
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
                    //primaryStage.setScene(scene2);
                    //primaryStage.setFullScreen(true);
                }
            }
        });

        primaryStage.setScene(scene);
        //primaryStage.setFullScreen(true);
        primaryStage.show();

        /*
        Scene scene = new Scene(root,700, 700);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();*/
        //player.play();
    }

    public void ScaleFactor() {
        double DefaultScreenWidth = 1920;
        double DefaultScreenHeight = 1080;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        ScaleWidthFactor = screenWidth / DefaultScreenWidth;
        ScaleHeightFactor = screenHeight / DefaultScreenHeight;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
