import components.IconButton;
import components.MapView;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Slider scaleSlider = new Slider(0.35, 1.5, 1);
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

        try { // pulls all slides from the xml
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

        //logo at the top left
        ImageView logo = new ImageView(new Image(getClass().getResource("/logo.png").toExternalForm()));
        logo.setTranslateX(margin/2);
        logo.setTranslateY(margin/2);
        logo.setOpacity(0.2);
        logo.setMouseTransparent(true);

        //sets the icons for the user
        forward = new IconButton("/icons/right.png");
        back = new IconButton("/icons/left.png");
        home = new IconButton("/icons/map_centre.png");
        home.setTranslateX(margin*1.2);

        //set up zoom in control
        scaleSlider.setOrientation(Orientation.VERTICAL);
        scaleSlider.setTranslateX(margin/2);
        scaleSlider.setScaleX(1.5);
        scaleSlider.setScaleY(1.5);
        scaleSlider.setMaxHeight(100);

        //sets up the initial user interface
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
                home,
                logo
        );

        //stops the slide from showing on start up
        slidePane.setVisible(false);
        forward.setVisible(false);
        back.setVisible(false);

        //hold the size of the largest element, used for scaling
        double minWidth = presentation.getMaxX2();
        double minHeight = presentation.getMaxY2();

        //set up scale
        Scale scale = new Scale();
        scale.setPivotX(0);
        scale.setPivotY(0);
        scale.setPivotZ(0);
        //set up content pane
        slidePane.getTransforms().add(scale);
        slidePane.setPickOnBounds(false);
        slidePane.setTranslateY(margin);
        slidePane.setClip(new Rectangle(5000, 5000));

        backgroundPane.setMouseTransparent(true);
        backgroundPane.setStyle(("-fx-background-color: rgba(255,255,255,0.8)"));
        backgroundPane.visibleProperty().bind(slidePane.visibleProperty());

        Scene scene = new Scene(userView);
        //scales all elements onto the screen horizontally
        primaryStage.widthProperty().addListener((obs, old, val) -> {
            forward.setTranslateX(val.doubleValue() - margin*2);
            back.setTranslateX(val.doubleValue()*offset);
            backgroundPane.setTranslateX(val.doubleValue()*offset);
            slidePane.setTranslateX(val.doubleValue()*offset + margin);
            scaleWidthFactor = (val.doubleValue()*(1-offset) - 2*margin) / (minWidth);
            scale.setX(Math.min(scaleWidthFactor, scaleHeightFactor));
            scale.setY(Math.min(scaleWidthFactor, scaleHeightFactor));
        });
        //scales all elements onto the screen vertically
        primaryStage.heightProperty().addListener((obs, old, val) -> {
            forward.setTranslateY(val.doubleValue() - margin*2);
            back.setTranslateY(val.doubleValue() - margin*2);
            home.setTranslateY(val.doubleValue() - home.getHeight() - margin/2);
            scaleSlider.setTranslateY(val.doubleValue() - scaleSlider.getLayoutBounds().getHeight() - margin*0.95);
            scaleHeightFactor = (val.doubleValue() - 2*margin) / (minHeight);
            scale.setX(Math.min(scaleWidthFactor, scaleHeightFactor));
            scale.setY(Math.min(scaleWidthFactor, scaleHeightFactor));
        });

        //pulls all the slides found in the xml
        slides = presentation.getSlides().stream()
                .map(slide -> new SlideView(slide))
                .toArray(size -> new SlideView[size]);

        //action listeners
        forward.setOnAction(e -> this.onNext());
        back.setOnAction(e -> this.onPrevious());
        map.setOnPoiClicked(e -> this.onClickPoi(e.getPOI()));
        home.setOnAction(e -> map.centerAtYouAreHere());

        // Keyboard events
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.DOWN) {
                this.onNext();
                e.consume();
            }
            else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.UP) {
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

    // if the next button is pressed the slide numbers is incremented by one, this is to allow the next presentation slide to be
    // received
    public void onNext() {
        if (this.slideNum < poiSlideViews.size() - 1) {
            this.setSlideNum(this.slideNum + 1);
        }
    }

    // if the previous button is pressed the slide numbers is decreased by one, this is to allow the previous presentation slide to be
    // received
    public void onPrevious() {
        if (this.slideNum > 0) {
            this.setSlideNum(slideNum = this.slideNum - 1);
        }
    }

    // if the user clicks on a POI, display the slide pane and the starting information for that POI.
    public void onClickPoi(POI poi) {
        slidePane.setVisible(poi != null);
        map.setLeftAligned(slidePane.isVisible());
        //only show buttons when slide is shown
        poiSlideViews.clear(); //clears to ensure that it only contains slides for selected POI
        this.slideNum = 0;

        if (poi != null) {
            for (int i = 0; i < slides.length; i++) { //adds all slides for selected POI to POI slide array
                if (poi.getId().equals(slides[i].getSlide().getPoiId())) {
                    poiSlideViews.add(slides[i]);
                }
            }
            slidePane.getChildren().clear(); //clear old slide
            if (poiSlideViews.size() > 0) { //only if there are slides found for that POI
                slidePane.getChildren().add(poiSlideViews.get(0)); //shows first slide
                poiSlideViews.get(0).setTranslateX(0);
                poiSlideViews.get(0).setOpacity(1);
                back.setVisible(false); //as first slide, don't show back button
                forward.setVisible(poiSlideViews.size() > 1);
            }
        }
        else {
            forward.setVisible(false);
            back.setVisible(false);
        }
    }

    //adds sets the slide from a given index
    public void setSlideNum(int slideNum) {
        double direction = slideNum > this.slideNum ? 1 : -1;

        final Timeline timeline = new Timeline();
        final Duration swipeDuration = Duration.millis(300);
        timeline.getKeyFrames().clear();

        this.slideNum = slideNum;
        // Remove existing slide
        if (slidePane.getChildren().size() > 1) {
            Node node = slidePane.getChildren().remove(0);
            node.setTranslateX(0);
            node.setOpacity(1);
        }
        Node prev = slidePane.getChildren().get(0);
        if (prev != null) {
            timeline.getKeyFrames().addAll(
                    new KeyFrame(swipeDuration, new KeyValue(prev.translateXProperty(), direction * -backgroundPane.getWidth(), Interpolator.EASE_BOTH)),
                    new KeyFrame(swipeDuration, new KeyValue(prev.opacityProperty(), 0, Interpolator.EASE_BOTH))
            );
        }
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
