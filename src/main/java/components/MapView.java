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
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MapView extends ScrollPane {
    private double scaleValue = 0.7;
    private double zoomIntensity = 0.02;
    private ImageView poi;
    private ImageView poi2;
    private ImageView mapView;
    private Region target;
    private Node zoomNode;
    private Bounds boundsInScene;
    private Timeline timeline = new Timeline();
    private Timeline activePointTimeline = new Timeline();
    private int level = 0;
    private Point2D anchorPoint = null;

    private long lastAnchorTime = 0;

    private List<Image> tiles = new ArrayList<>();
    private Image poiIcon = new Image(getClass().getResource("/icons/map_poi.png").toExternalForm());
    private Image activePoiIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());

    public MapView() {
        super();

        tiles.add(new Image(getClass().getResource("/tiles/16.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/17.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/18.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/19.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/20.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/21.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/22.png").toExternalForm()));

        mapView = new ImageView();
        mapView.setImage(tiles.get(level));

        poi = new ImageView();
        poi.setFitHeight(100);
        poi.setFitWidth(100);
        poi.setTranslateX(1877-50);
        poi.setTranslateY(1659-50);
        poi.setImage(poiIcon);

        poi2 = new ImageView();
        poi2.setFitHeight(100);
        poi2.setFitWidth(100);
        poi2.setTranslateX(1500-50);
        poi2.setTranslateY(1300-50);
        poi2.setImage(poiIcon);

        StackPane stack = new StackPane();
        stack.setAlignment(Pos.TOP_LEFT);
        stack.getChildren().addAll(mapView, poi, poi2);

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
            poi.setScaleX(0.3 / val.doubleValue());
            poi.setScaleY(0.3 / val.doubleValue());
            this.setLevel((int)(4*val.doubleValue() - 1));
        });

        setScaleValue(scaleValue, 0, 0);

        System.out.println("HMax");
        System.out.println(getHmax());

        setHvalue(0.5);
        setVvalue(0.5);
    }

    private boolean isActive = false;
    private double previousTranslateY = 0;
    public void setPointActive(boolean isActive) {
        activePointTimeline.stop();
        activePointTimeline.getKeyFrames().clear();
        if (isActive && !this.isActive) {
            previousTranslateY = poi.getTranslateY();
            poi.setImage(activePoiIcon);
            activePointTimeline.setAutoReverse(true);
            activePointTimeline.setCycleCount(Timeline.INDEFINITE);
            activePointTimeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.millis(0), new KeyValue(poi.translateYProperty(), previousTranslateY, Interpolator.EASE_BOTH)),
                    new KeyFrame(Duration.millis(500), new KeyValue(poi.translateYProperty(), previousTranslateY - 40, Interpolator.EASE_BOTH))
            );

            System.out.println(poi.getX());
            System.out.println(poi.getBoundsInLocal().getMinX());
            System.out.println(poi.getBoundsInParent().getMinX());
            System.out.println(0.5 * (poi.getBoundsInParent().getMinX() / target.getWidth()));

            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.millis(200), new KeyValue(target.scaleXProperty(), 1.25, Interpolator.LINEAR)),
                    new KeyFrame(Duration.millis(250), new KeyValue(hvalueProperty(), 0.53, Interpolator.EASE_BOTH)),
                    new KeyFrame(Duration.millis(250), new KeyValue(vvalueProperty(), 0.41, Interpolator.EASE_BOTH))
            );
            timeline.play();
        }
        else if (this.isActive) {
            poi.setImage(poiIcon);
            activePointTimeline.setAutoReverse(false);
            activePointTimeline.setCycleCount(1);
            activePointTimeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(100), new KeyValue(poi.translateYProperty(), previousTranslateY, Interpolator.EASE_BOTH))
            );
        }
        this.isActive = isActive;
        activePointTimeline.play();
    }

    public DoubleProperty scaleProperty() {
        return target.scaleXProperty();
    }

    public ArrayList<Bounds> getBoundsInScene() {

        ArrayList<Bounds> boundsInScene = new ArrayList<>();

        boundsInScene.add(0, poi.localToScene(poi.getBoundsInLocal()));
        boundsInScene.add(1, poi2.localToScene(poi2.getBoundsInLocal()));
        return boundsInScene;
    }

    public double getXPoiMax () {
        boundsInScene = poi.localToScene(poi.getBoundsInLocal());
        double xMax = boundsInScene.getMaxX();
        return xMax;
    }

    public double getYPoiMin() {
        boundsInScene = poi.localToScene(poi.getBoundsInLocal());
        double yMin = boundsInScene.getMinY();
        return yMin;
    }

    public double getYPoiMax() {
        boundsInScene = poi.localToScene(poi.getBoundsInLocal());
        double yMax = boundsInScene.getMaxY();
        return yMax;
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

    private void setScaleValue(double scaleValue, double hValue, double vValue) {
        this.scaleValue = scaleValue;

        // Basic interpolation
        // TODO: need to not redo this every scroll frame, just update the target
        // TODO: also need to make sure that the pivot point for the scale is always under the cursor
        timeline.stop();
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(200), new KeyValue(target.scaleXProperty(), scaleValue, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(200), new KeyValue(hvalueProperty(), hValue, Interpolator.LINEAR)),
                new KeyFrame(Duration.millis(200), new KeyValue(vvalueProperty(), vValue, Interpolator.LINEAR))
        );
        timeline.play();
    }

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

        // calculate adjustment of scroll position (pixels)
        Point2D adjustment = target.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        // convert back to [0, 1] range
        // (too large/small values are automatically corrected by ScrollPane)
        Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();

        // Bounded scale value
        setScaleValue(
                Math.min(1.15, Math.max(0.45, scaleValue * zoomFactor)),
                (valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()),
                (valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight())
        );
    }

    private void setLevel(int level) {
        if (level != this.level) {
            this.level = level;
            mapView.setImage(tiles.get(Math.min(level, tiles.size() - 1)));
        }
    }

}
