import components.IconButton;
import components.MapView;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import components.SlideView;
import models.Presentation;

import java.awt.*;

public class Kiosk extends Application {

    private MapView map;
    private int slideNum = 0;
    private Presentation presentation;
    private double scaleWidthFactor = 1;
    private double scaleHeightFactor = 1;
    private SlideView[] slides;
    private StackPane userView;
    private Pane slidePane = new Pane();
    private Pane backgroundPane = new Pane();
    private Slider scaleSlider = new Slider(0.45, 1.15, 1);

    @Override
    public void start(Stage primaryStage) {
        final double margin = 50;
        final double offset = .55;

        primaryStage.setTitle("Unlock York");

        map = new MapView();

        try {
            presentation = XMLParser.parse(
                    "src/build/resources/main/example.pws",
                    "src/build/resources/main/schema.xsd"
            );
        }
        catch (Exception e) {
            // Couldn't load data - exit
            // @TODO show a user friendly error message here explaining the problem (e.g. missing PWS, invalid PWS)
            System.err.println("Error parsing PWS document");
            System.err.println(e);
            System.exit(1);
        }

        Button forward = new IconButton("/icons/right.png");
        Button back = new IconButton("/icons/left.png");
        Button home = new IconButton("/icons/map_centre.png");
        home.setTranslateX(margin);

        scaleSlider.setOrientation(Orientation.VERTICAL);
        scaleSlider.setTranslateX(margin/2);
        scaleSlider.setMaxHeight(200);

        userView = new StackPane();
        userView.getStylesheets().add(getClass().getResource("/css/Kiosk.css").toExternalForm());
        userView.setAlignment(Pos.TOP_LEFT);
        userView.getChildren().addAll(
                map,
                backgroundPane,
                slidePane,
                scaleSlider,
                back,
                forward,
                home
        );

        double minWidth = presentation.getMaxX2();
        double minHeight = presentation.getMaxY2();

        Scale scale = new Scale();
        scale.setPivotX(0);
        scale.setPivotY(0);
        scale.setPivotZ(0);
        slidePane.getTransforms().add(scale);
        slidePane.setPickOnBounds(false);
        slidePane.setTranslateY(margin);

        backgroundPane.setMouseTransparent(true);
        backgroundPane.setStyle(("-fx-background-color: rgba(255,255,255,0.7)"));

        Scene scene = new Scene(userView);
        primaryStage.widthProperty().addListener((obs, old, val) -> {
            forward.setTranslateX(val.doubleValue() - margin*2);
            back.setTranslateX(val.doubleValue() / 2 + margin*2);
            backgroundPane.setTranslateX(val.doubleValue()*offset);
            slidePane.setTranslateX(val.doubleValue()*offset + margin);
            scaleWidthFactor = (val.doubleValue()*(1-offset) - 2*margin) / (minWidth);
            scale.setX(Math.min(scaleWidthFactor, scaleHeightFactor));
            scale.setY(Math.min(scaleWidthFactor, scaleHeightFactor));
        });
        primaryStage.heightProperty().addListener((obs, old, val) -> {
            forward.setTranslateY(val.doubleValue() - margin*2);
            back.setTranslateY(val.doubleValue() - margin*2);
            home.setTranslateY(val.doubleValue() - home.getHeight() - margin/2);
            scaleSlider.setTranslateY(val.doubleValue() - scaleSlider.getHeight() - margin/2);
            scaleHeightFactor = (val.doubleValue() - 2*margin) / (minHeight);
            scale.setX(Math.min(scaleWidthFactor, scaleHeightFactor));
            scale.setY(Math.min(scaleWidthFactor, scaleHeightFactor));
        });

        //map.prefHeightProperty().bind(userView.widthProperty().divide(2));
        //map.prefWidthProperty().bind(userView.widthProperty().divide(2));

        slides = presentation.getSlides().stream()
                .map(slide -> new SlideView(slide))
                .toArray(size -> new SlideView[size]);

        this.setSlideNum(0);

        forward.setOnAction(e -> this.onNext(e));
        back.setOnAction(e -> this.onPrevious(e));
        scene.setOnMouseClicked(e -> this.onClick(e));

        // Volume
        map.scaleProperty().bindBidirectional(scaleSlider.valueProperty());

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public void onNext(Event event) {
        if(slideNum < presentation.getSlides().size() - 1) {
            slideNum = slideNum + 1;
        }
        else {
            slideNum = 0;
        }

        this.setSlideNum(slideNum);
    }

    public void onPrevious(Event event) {
        if (slideNum > 0) {
            slideNum = slideNum - 1;
        }
        else {
            slideNum = presentation.getSlides().size() - 1;
        }

        this.setSlideNum(slideNum);
    }

    public void onClick(MouseEvent event) {
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

    public void setSlideNum(int slideNum) {
        this.slideNum = slideNum;
        // Remove existing slide
        slidePane.getChildren().clear();
        // Add new one
        slidePane.getChildren().add(slides[slideNum]);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
