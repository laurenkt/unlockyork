package components;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.POI;

import javax.swing.event.DocumentEvent;
import java.util.ArrayList;
import java.util.List;

public class MapView extends ScrollPane {

    public class POIEvent extends Event {
        private POI poi;

        public POIEvent(POI poi) {
            super(EventType.ROOT);
            this.poi = poi;
        }

        public POI getPOI() {
            return this.poi;
        }
    }

    private double scaleValue = 0.7;
    private double zoomIntensity = 0.02;
    private List<POIView> poiViews = new ArrayList<>();
    private ImageView mapView;
    private Region target;
    private Node zoomNode;
    private StackPane stack;
    private Bounds boundsInScene;
    private Timeline timeline = new Timeline();
    private Timeline activePointTimeline = new Timeline();
    private int level = 0;
    private Point2D anchorPoint = null;

    private DoubleProperty xCenter = new SimpleDoubleProperty(0);
    private DoubleProperty yCenter = new SimpleDoubleProperty(0);

    private EventHandler<? super POIEvent> onPoiClicked;

    private long lastAnchorTime = 0;

    private List<Image> tiles = new ArrayList<>();

    public MapView(List<POI> POIs) {
        super();

        tiles.add(new Image(getClass().getResource("/tiles/16.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/17.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/18.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/19.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/20.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/21.png").toExternalForm()));

        mapView = new ImageView();
        mapView.setImage(tiles.get(level));

        for(POI poi : POIs) {
            // poi == POIs.get(i)
            poiViews.add(new POIView(poi));
        }

        for(int i = 0; i > POIs.size(); i++) {
            poiViews.add(new POIView(POIs.get(i)));
        }

        stack = new StackPane();
        stack.setAlignment(Pos.TOP_LEFT);
        stack.getChildren().add(mapView);
        stack.getChildren().addAll(poiViews);

        for(POIView SubPOI : poiViews) {
            stack.getChildren().addAll(SubPOI.getSubPOIViews());
        }

        HBox hBox = new HBox();
        hBox.getChildren().add(stack);

        this.target = hBox;

        this.zoomNode = new Group(target);
        setContent(outerNode(zoomNode));

        setPannable(true);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setFitToHeight(true); //center
        setFitToWidth(true); //center

        // Ensure target scales on both directions together
        target.scaleYProperty().bind(target.scaleXProperty());
        target.scaleXProperty().addListener((obs, old, val) -> {
            for(POIView poi : poiViews) {
                poi.setScaleX(0.3 / val.doubleValue());

                for(POIView subPoi : poi.getSubPOIViews()) {
                    subPoi.setScaleX(0.3 / val.doubleValue());
                }
            }
            this.setLevel((int)(4*val.doubleValue() - 1));
        });

        setScaleValue(scaleValue);

        stack.setOnMouseClicked(e -> {
            final double threshold = 50;

            double x = e.getX();
            double y = e.getY();

            for (POI poi : POIs) {
                if (Math.abs(poi.getX() - x) < threshold &&
                        Math.abs(poi.getY() - y) < threshold) {
                    onPoiClicked.handle(new POIEvent(poi));
                    setPointActive(poi);
                    centerPoint(poi.getX(), poi.getY());
                    return;
                }
            }

            // Else
            setPointActive(null);
            onPoiClicked.handle(new POIEvent(null));
        });

        layout();
        setHvalue(0.5);
        setVvalue(0.5);
        /*
        hvalueProperty().addListener((obs, old, val) -> {
            System.out.printf("hVal: %f\r\n", val);
            System.out.flush();
        });*/

        final Timeline xTimeline = new Timeline();
        target.scaleXProperty().addListener((obs, old, val) -> {
            layout();
            double mapWidth = target.getBoundsInParent().getWidth();
            double viewportWidth = getViewportBounds().getWidth();
            double xPercent = xCenter.getValue() / target.getWidth();
            double xTargetPos = xPercent * mapWidth - viewportWidth/2 + viewportWidth/4;
            double hMax = mapWidth - getViewportBounds().getWidth();
            double xVal = xTargetPos / hMax;

            setHvalue(xVal);

            /*
            xTimeline.stop();
            xTimeline.getKeyFrames().clear();
            xTimeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(250), new KeyValue(hvalueProperty(), xVal, Interpolator.EASE_BOTH))
            );
            xTimeline.play();*/
        });

        final Timeline yTimeline = new Timeline();
        target.scaleYProperty().addListener((obs, old, val) -> {
            layout();
            double mapHeight = target.getBoundsInParent().getHeight();
            double viewportHeight = getViewportBounds().getHeight();
            double yPercent = yCenter.getValue() / target.getHeight();
            double yTargetPos = yPercent * mapHeight - viewportHeight/2 + viewportHeight/4;
            double vMax = mapHeight - getViewportBounds().getHeight();
            double yVal = yTargetPos / vMax;

            setVvalue(yVal);

            /*
            yTimeline.stop();
            yTimeline.getKeyFrames().clear();
            yTimeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(250), new KeyValue(vvalueProperty(), yVal, Interpolator.EASE_BOTH))
            );
            yTimeline.play();*/
        });
    }

    private void centerPoint(double x, double y) {
        layout();
        double mapWidth = target.getBoundsInParent().getWidth();
        double mapHeight = target.getBoundsInParent().getHeight();
        double viewportWidth = getViewportBounds().getWidth();
        double viewportHeight = getViewportBounds().getHeight();
        double xPercent = x / target.getWidth();
        double yPercent = y / target.getHeight();
        double xTargetPos = xPercent * mapWidth - viewportWidth/2 + viewportWidth/4;
        double yTargetPos = yPercent * mapHeight - viewportHeight/2;
        double hMax = mapWidth - getViewportBounds().getWidth();
        double vMax = mapHeight - getViewportBounds().getHeight();
        double xVal = xTargetPos / hMax;
        double yVal = yTargetPos / vMax;

        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                //new KeyFrame(Duration.millis(200), new KeyValue(target.scaleXProperty(), 1.25, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(250), new KeyValue(hvalueProperty(), xVal, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(250), new KeyValue(vvalueProperty(), yVal, Interpolator.EASE_BOTH))
        );
        timeline.play();
    }

    private POIView activePoiView = null;
    private double previousTranslateY = 0;
    public void setPointActive(POI poi) {
        if (activePoiView != null) {
            activePoiView.setActive(false);
        }

        for (POIView view : poiViews) {
            if (view.getPOI().equals(poi)) {
                activePoiView = view;
                view.setActive(true);
                break;
            }
        }
        /*
        activePointTimeline.stop();
        activePointTimeline.getKeyFrames().clear();
        if (poi != null && !activePoiView) {
            previousTranslateY = poiViews.get(0).getTranslateY();
            poiViews.get(0).setActive(true);
            activePointTimeline.setAutoReverse(true);
            activePointTimeline.setCycleCount(Timeline.INDEFINITE);
            activePointTimeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.millis(0), new KeyValue(poiViews.get(0).translateYProperty(), previousTranslateY, Interpolator.EASE_BOTH)),
                    new KeyFrame(Duration.millis(500), new KeyValue(poiViews.get(0).translateYProperty(), previousTranslateY - 40, Interpolator.EASE_BOTH))
            );

            System.out.println(poiViews.get(0).getX());
            System.out.println(poiViews.get(0).getBoundsInLocal().getMinX());
            System.out.println(poiViews.get(0).getBoundsInParent().getMinX());
            System.out.println(0.5 * (poiViews.get(0).getBoundsInParent().getMinX() / target.getWidth()));

        }
        else if (this.isActive) {
            poiViews.get(0).setActive(false);
            activePointTimeline.setAutoReverse(false);
            activePointTimeline.setCycleCount(1);
            activePointTimeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(100), new KeyValue(poiViews.get(0).translateYProperty(), previousTranslateY, Interpolator.EASE_BOTH))
            );
        }
        this.isActive = isActive;
        activePointTimeline.play();*/
    }

    public DoubleProperty scaleProperty() {
        return target.scaleXProperty();
    }

    private Node outerNode(Node node) {
        Node outerNode = centeredNode(node);
        outerNode.setOnScroll(e -> {
            e.consume();
            onScroll(e.getDeltaY(), new Point2D(e.getX(), e.getY()));
           // System.out.println(scaleValue);

        });
        return outerNode;
    }

    private Node centeredNode(Node node) {
        VBox vBox = new VBox(node);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private void setScaleValue(double scaleValue) {
        this.scaleValue = scaleValue;

        // Basic interpolation
        // TODO: need to not redo this every scroll frame, just update the target
        // TODO: also need to make sure that the pivot point for the scale is always under the cursor
        timeline.stop();
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(200), new KeyValue(target.scaleXProperty(), scaleValue, Interpolator.LINEAR))
        );
        timeline.play();
    }

    private long debounce = 0;
    private void onScroll(double wheelDelta, Point2D mousePoint) {
        double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

        Bounds innerBounds = zoomNode.getLayoutBounds();
        Bounds viewportBounds = getViewportBounds();

        // calculate pixel offsets from [0, 1] range
        double valX = this.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
        double valY = this.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

        layout(); // refresh ScrollPane scroll positions & target bounds

        // convert target coordinates to zoomTarget coordinates
        Point2D posInZoomTarget = target.parentToLocal(zoomNode.parentToLocal(mousePoint));


        if (System.currentTimeMillis() - debounce > 500) {
            debounce = System.currentTimeMillis();
            xCenter.setValue(posInZoomTarget.getX());
            yCenter.setValue(posInZoomTarget.getY());
        }

        // calculate adjustment of scroll position (pixels)
        Point2D adjustment = target.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        // convert back to [0, 1] range
        // (too large/small values are automatically corrected by ScrollPane)
        Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();

        // Bounded scale value
        setScaleValue(
                Math.min(1.5, Math.max(0.25, scaleValue * zoomFactor))
                //(valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()),
                //(valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight())
        );
    }

    public void setOnPoiClicked(EventHandler<? super POIEvent> handler) {
        this.onPoiClicked = handler;
    }

    private void setLevel(int level) {
        if (level != this.level) {
            this.level = level;
            mapView.setImage(tiles.get(Math.min(level, tiles.size() - 1)));
        }
    }

}
