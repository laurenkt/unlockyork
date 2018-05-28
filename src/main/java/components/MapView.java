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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MapView extends ScrollPane {
    private double scaleValue = 0.7;
    private double zoomIntensity = 0.02;
    private ImageView poi;
    private ImageView mapView;
    private Node target;
    private Node zoomNode;
    private Bounds boundsInScene;
    private Timeline timeline = new Timeline();
    private Timeline activePointTimeline = new Timeline();
    private int level = 0;
    private Scale scale = new Scale();
    private Point2D anchorPoint = null;

    private long lastAnchorTime = 0;

    private List<Image> tiles;
    private Image poiIcon;

    public MapView() {
        super();


        poiIcon = new Image(getClass().getResource("/icons/map_poi.png").toExternalForm());
        tiles = new ArrayList<>();
        tiles.add(new Image(getClass().getClassLoader().getResource("York20.png").toExternalForm()));
        tiles.add(new Image(getClass().getClassLoader().getResource("York18.png").toExternalForm()));
        tiles.add(new Image(getClass().getClassLoader().getResource("York17.png").toExternalForm()));
        tiles.add(new Image(getClass().getClassLoader().getResource("York16.png").toExternalForm()));

        mapView = new ImageView();
        mapView.setImage(tiles.get(level));

        poi = new ImageView();
        poi.setFitHeight(100);
        poi.setFitWidth(100);
        poi.setTranslateX(1877-50);
        poi.setTranslateY(1659-50);
        poi.setImage(poiIcon);

        StackPane stack = new StackPane();
        stack.setAlignment(Pos.TOP_LEFT);
        stack.getChildren().addAll(mapView, poi);

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
        scale.yProperty().bind(scale.xProperty());
        scale.xProperty().addListener((obs, old, val) -> {
            poi.setScaleX(0.3 / val.doubleValue());
            poi.setScaleY(0.3 / val.doubleValue());
            this.setLevel((int)(4*val.doubleValue() - 1));
        });
        target.getTransforms().add(scale);

        setScaleValue(scaleValue, 0, 0);

        activePointTimeline.setAutoReverse(true);
        activePointTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void setPointActive(boolean isActive) {
        activePointTimeline.getKeyFrames().clear();
        activePointTimeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(500), new KeyValue(poi.translateYProperty(), poi.getTranslateY() - 50, Interpolator.EASE_BOTH))
        );
        activePointTimeline.play();
    }

    public DoubleProperty scaleProperty() {
        return scale.xProperty();
    }

    public double getXPoiMin() {
        boundsInScene = poi.localToScene(poi.getBoundsInLocal());
        double xMin = boundsInScene.getMinX();
        return xMin;
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
                new KeyFrame(Duration.millis(200), new KeyValue(scale.xProperty(), scaleValue, Interpolator.LINEAR)),
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
