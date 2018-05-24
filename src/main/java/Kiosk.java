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

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drag to pan the map");

        ScaleFactor();

        Image poiIcon = new Image(getClass().getClassLoader().getResource("poi.png").toExternalForm());
        Image mapLayout = new Image(getClass().getClassLoader().getResource("York16.png").toExternalForm());
        map = new MapView(mapLayout, poiIcon);

        presentation = new Presentation();
        XMLParser parser = new XMLParser();
        presentation = parser.parser("src/build/resources/main/example.pws", "src/build/resources/main/schema.xsd");
        InfoView Info = new InfoView();
        group = new Group();
        group = Info.DisplayPresentationView(presentation, SlideNum, ScaleWidthFactor, ScaleHeightFactor);

        HBox slide = new HBox();
        HBox Buttons = new HBox();

        Button forward = new Button("Forward");
        Button back = new Button("Back");

        HBox.setHgrow(forward, Priority.ALWAYS);
        HBox.setHgrow(back, Priority.ALWAYS);
        forward.setMaxWidth(Double.MAX_VALUE);
        back.setMaxWidth(Double.MAX_VALUE);

        slide.getChildren().add(group);
        Buttons.getChildren().addAll(back,forward);
        Buttons.setAlignment(Pos.CENTER);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = (screenSize.getWidth());
        double screenHeight = (screenSize.getHeight());

        BorderPane userView = new BorderPane();
        userView.setPrefWidth(screenWidth);
        userView.setPrefHeight(screenHeight);
        userView.setLeft(map);
        userView.setRight(slide);
        userView.setBottom(Buttons);

        userView.setAlignment(map, Pos.CENTER);
        userView.setAlignment(slide, Pos.CENTER);
        userView.setAlignment(Buttons, Pos.CENTER);

        Scene scene = new Scene(userView);

        map.prefHeightProperty().bind(userView.widthProperty().divide(2));
        map.prefWidthProperty().bind(userView.widthProperty().divide(2));

        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(SlideNum < presentation.getSlides().size()) {
                    SlideNum = SlideNum + 1;
                }

                if(SlideNum < 5){
                    SlideNum = 0;
                }
                newSlideForward = new HBox();

                group = Info.DisplayPresentationView(presentation,SlideNum, ScaleWidthFactor, ScaleHeightFactor);

                newSlideForward.getChildren().add(group);

                userView.setLeft(map);
                userView.setRight(newSlideForward);
                userView.setBottom(Buttons);

                userView.setAlignment(map, Pos.CENTER);
                userView.setAlignment(newSlideForward, Pos.CENTER);
                userView.setAlignment(Buttons, Pos.CENTER);
                System.out.println("SlideNum = " + SlideNum);
            }
        });

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(SlideNum > -1) {
                    SlideNum = SlideNum - 1;
                }

                if(SlideNum == -1){
                    SlideNum = 4;
                }

                newSlideBack = new HBox();

                group = Info.DisplayPresentationView(presentation,SlideNum, ScaleWidthFactor, ScaleHeightFactor);
                group.prefHeight(100);
                group.prefWidth(100);
                newSlideBack.getChildren().add(group);

                userView.setLeft(map);
                userView.setRight(newSlideBack);
                userView.setBottom(Buttons);

                userView.setAlignment(map, Pos.CENTER);
                userView.setAlignment(newSlideBack, Pos.CENTER);
                userView.setAlignment(Buttons, Pos.CENTER);

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
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public void ScaleFactor() {
        double DefaultScreenWidth = 1920;
        double DefaultScreenHeight = 1080;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = (screenSize.getWidth())/2;
        double screenHeight = (screenSize.getHeight())/2;

        ScaleWidthFactor = screenWidth / DefaultScreenWidth;
        ScaleHeightFactor = screenHeight / DefaultScreenHeight;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
