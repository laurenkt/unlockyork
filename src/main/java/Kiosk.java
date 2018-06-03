import components.IconButton;
import components.MapView;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import components.SlideView;
import javafx.util.Duration;
import models.POI;
import models.Presentation;

import java.util.ArrayList;
import java.util.List;

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
    private Slider scaleSlider = new Slider(0.25, 1.5, 1);
    private Button forward;
    private Button back;
    private Button home;
    private List<SlideView> poiSlideViews = new ArrayList();

    final static private AudioClip slideClip = new AudioClip(Kiosk.class.getResource("/sounds/swoosh.aiff").toExternalForm());

    @Override
    public void start(Stage primaryStage) throws Exception {
        final double margin = 50;
        final double offset = .5;

        // Load custom font
        javafx.scene.text.Font.loadFont(getClass().getResource("/fonts/OFLGoudyStM.otf").toExternalForm(), 10);

        primaryStage.setTitle("Unlock York");

        try {
            presentation = XMLParser.parse(
                    "src/build/resources/main/york.pws",
                    "src/build/resources/main/schema.xsd"
            );
        }
        catch (Exception e) {
            // Couldn't load data - exit
            // @TODO show a user friendly error message here explaining the problem (e.g. missing PWS, invalid PWS)
            System.err.println("Error parsing PWS document");
            System.err.println(e);
            throw e;
        }

        map = new MapView(presentation.getPOI());
        map.setYouAreHere(53.95582, -1.079939);

        forward = new IconButton("/icons/right.png");
        back = new IconButton("/icons/left.png");
        home = new IconButton("/icons/map_centre.png");
        home.setTranslateX(margin*1.2);

        scaleSlider.setOrientation(Orientation.VERTICAL);
        scaleSlider.setTranslateX(margin/2);
        scaleSlider.setScaleX(1.5);
        scaleSlider.setScaleY(1.5);
        scaleSlider.setMaxHeight(100);

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

        //stops the slide from showing on start up
        slidePane.setVisible(false);
        forward.setVisible(false);
        back.setVisible(false);

        double minWidth = presentation.getMaxX2();
        double minHeight = presentation.getMaxY2();

        Scale scale = new Scale();
        scale.setPivotX(0);
        scale.setPivotY(0);
        scale.setPivotZ(0);
        slidePane.getTransforms().add(scale);
        slidePane.setPickOnBounds(false);
        slidePane.setTranslateY(margin);
        slidePane.setClip(new Rectangle(5000, 5000));

        backgroundPane.setMouseTransparent(true);
        backgroundPane.setStyle(("-fx-background-color: rgba(255,255,255,0.8)"));
        backgroundPane.visibleProperty().bind(slidePane.visibleProperty());

        Scene scene = new Scene(userView);
        primaryStage.widthProperty().addListener((obs, old, val) -> {
            forward.setTranslateX(val.doubleValue() - margin*2);
            back.setTranslateX(val.doubleValue()*offset);
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
            scaleSlider.setTranslateY(val.doubleValue() - scaleSlider.getLayoutBounds().getHeight() - margin*0.95);
            scaleHeightFactor = (val.doubleValue() - 2*margin) / (minHeight);
            scale.setX(Math.min(scaleWidthFactor, scaleHeightFactor));
            scale.setY(Math.min(scaleWidthFactor, scaleHeightFactor));
        });

        slides = presentation.getSlides().stream()
                .map(slide -> new SlideView(slide))
                .toArray(size -> new SlideView[size]);

        forward.setOnAction(e -> this.onNext());
        back.setOnAction(e -> this.onPrevious());
        map.setOnPoiClicked(e -> this.onClickPoi(e.getPOI()));
        home.setOnAction(e -> map.centerAtYouAreHere());

        // Keyboard events
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.SPACE) {
                this.onNext();
                e.consume();
            }
            else if (e.getCode() == KeyCode.LEFT) {
                this.onPrevious();
                e.consume();
            }

        });

        // Volume
        map.scaleProperty().bindBidirectional(scaleSlider.valueProperty());

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public void onNext() {
        if (this.slideNum < poiSlideViews.size() - 1) {
            this.setSlideNum(this.slideNum + 1);
        }
    }

    public void onPrevious() {
        if (this.slideNum > 0) {
            this.setSlideNum(slideNum = this.slideNum - 1);
        }
    }

    public void onClickPoi(POI poi) {
        slidePane.setVisible(poi != null);
        map.setLeftAligned(slidePane.isVisible());
        //only show buttons when slide is shown
        poiSlideViews.clear();

        if (poi != null) {
            for (int i = 0; i < slides.length; i++) {
                if (poi.getId().equals(slides[i].getSlide().getPoiId())) {
                    poiSlideViews.add(slides[i]);
                }
            }
            slidePane.getChildren().clear();
            if (poiSlideViews.size() > 0) {
                slidePane.getChildren().add(poiSlideViews.get(0));
                back.setVisible(false);
                forward.setVisible(poiSlideViews.size() > 1);
            }
        }
        else {
            forward.setVisible(false);
            back.setVisible(false);
        }
    }

    public void setSlideNum(int slideNum) {
        double direction = slideNum > this.slideNum ? 1 : -1;

        final Timeline timeline = new Timeline();
        final Duration swipeDuration = Duration.millis(300);
        timeline.getKeyFrames().clear();

        this.slideNum = slideNum;
        // Remove existing slide
        if (slidePane.getChildren().size() > 1) {
            slidePane.getChildren().remove(0);
        }
        Node prev = slidePane.getChildren().get(0);
        if (prev != null) {
            timeline.getKeyFrames().addAll(
                    new KeyFrame(swipeDuration, new KeyValue(prev.translateXProperty(), direction * -backgroundPane.getWidth(), Interpolator.EASE_BOTH)),
                    new KeyFrame(swipeDuration, new KeyValue(prev.opacityProperty(), 0, Interpolator.EASE_BOTH))
            );
        }
        //slidePane.getChildren().clear();
        // Add new one
        Node next = poiSlideViews.get(slideNum);

        if (prev != next) {
            slidePane.getChildren().add(next);

            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.millis(0), new KeyValue(next.translateXProperty(), direction * backgroundPane.getWidth(), Interpolator.EASE_BOTH)),
                    new KeyFrame(Duration.millis(0), new KeyValue(next.opacityProperty(), 0, Interpolator.EASE_BOTH)),
                    new KeyFrame(swipeDuration, new KeyValue(next.translateXProperty(), 0, Interpolator.EASE_BOTH)),
                    new KeyFrame(swipeDuration, new KeyValue(next.opacityProperty(), 1, Interpolator.EASE_BOTH))
            );
        }

        timeline.play();
        slideClip.play();

        forward.setVisible(slideNum < poiSlideViews.size() - 1);
        back.setVisible(slideNum > 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
