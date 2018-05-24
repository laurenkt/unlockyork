import components.MapView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import components.SlideView;
import models.Presentation;

import java.awt.*;

public class Kiosk extends Application {

    private MapView map;
    private int slideNum = 0;
    private Presentation presentation;
    private double ScaleWidthFactor;
    private double ScaleHeightFactor;
    private SlideView[] slides;
    private StackPane userView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Unlock York");

        ScaleFactor();

        Image poiIcon = new Image(getClass().getClassLoader().getResource("poi.png").toExternalForm());
        Image mapLayout = new Image(getClass().getClassLoader().getResource("York16.png").toExternalForm());
        map = new MapView(mapLayout, poiIcon);

        XMLParser parser = new XMLParser();
        presentation = parser.parser("src/build/resources/main/example.pws", "src/build/resources/main/schema.xsd");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = (screenSize.getWidth());
        double screenHeight = (screenSize.getHeight());

        HBox Buttons = new HBox();
        Button forward = new Button("Next");
        Button back = new Button("Previous");
        HBox.setHgrow(forward, Priority.ALWAYS);
        HBox.setHgrow(back, Priority.ALWAYS);
        forward.setMaxWidth(Double.MAX_VALUE);
        back.setMaxWidth(Double.MAX_VALUE);
        Buttons.getChildren().addAll(back, forward);
        //Buttons.setAlignment(Pos.BOTTOM_CENTER);
        Buttons.setMaxHeight(50);
        Buttons.setTranslateY(screenHeight/2);

        userView = new StackPane();
        userView.setPrefWidth(screenWidth);
        userView.setPrefHeight(screenHeight);
        userView.getChildren().addAll(map, Buttons);

        Scene scene = new Scene(userView);

        //map.prefHeightProperty().bind(userView.widthProperty().divide(2));
        //map.prefWidthProperty().bind(userView.widthProperty().divide(2));

        slides = presentation.getSlides().stream()
                .map(slide -> new SlideView(slide, ScaleWidthFactor, ScaleHeightFactor))
                .toArray(size -> new SlideView[size]);

        this.setSlideNum(0);

        forward.setOnAction(event -> {
            if(slideNum < presentation.getSlides().size() - 1) {
                slideNum = slideNum + 1;
            }
            else {
                slideNum = 0;
            }

            this.setSlideNum(slideNum);
        });

        back.setOnAction(event -> {
            if (slideNum > 0) {
                slideNum = slideNum - 1;
            }
            else {
                slideNum = presentation.getSlides().size() - 1;
            }

            this.setSlideNum(slideNum);
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

    public void setSlideNum(int slideNum) {
        this.slideNum = slideNum;
        // Remove existing slide
        userView.getChildren().removeIf(node -> node instanceof SlideView);
        // Add new one
        userView.getChildren().add(slides[slideNum]);
        userView.setAlignment(slides[slideNum], Pos.TOP_LEFT);
        System.out.println("slideNum = " + slideNum);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
