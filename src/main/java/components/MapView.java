package components;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

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
    private int level = 0;

    private List<Image> tiles;
    private Image poiIcon;

    public MapView() {
        super();


        poiIcon = new Image(getClass().getClassLoader().getResource("poi.png").toExternalForm());
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
        poi.setTranslateX(-40);
        poi.setTranslateY(-610);
        poi.setImage(poiIcon);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(mapView,poi);

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

        updateScale();
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

    private void updateScale() {
        target.setScaleX(scaleValue);
        target.setScaleY(scaleValue);
    }

    private void onScroll(double wheelDelta, Point2D mousePoint) {
        double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

        Bounds innerBounds = zoomNode.getLayoutBounds();
        Bounds viewportBounds = getViewportBounds();

        // calculate pixel offsets from [0, 1] range
        double valX = getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
        double valY = getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

        // Bounded scale value
        scaleValue = Math.min(1.15, Math.max(0.45, scaleValue * zoomFactor));
        this.setLevel((int)(4*scaleValue - 1));

        updateScale();
        layout();// refresh ScrollPane scroll positions & target bounds
        
        // convert target coordinates to zoomTarget coordinates
        Point2D posInZoomTarget = target.parentToLocal(zoomNode.parentToLocal(mousePoint));

        // calculate adjustment of scroll position (pixels)
        Point2D adjustment = target.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        // convert back to [0, 1] range
        // (too large/small values are automatically corrected by ScrollPane)
        Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
        setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
        setVvalue((valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));


    }

    private void setLevel(int level) {
        if (level != this.level) {
            this.level = level;
            mapView.setImage(tiles.get(Math.min(level, tiles.size() - 1)));
        }
    }

}
