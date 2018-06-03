package components;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import models.POI;
import java.util.ArrayList;
import java.util.List;

public class MapView extends ScrollPane {

    public void centerAtYouAreHere() {
        this.centerPoint(youAreHereLocation.getX(), youAreHereLocation.getY());
    }

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
    private double zoomIntensity = 0.01;
    private List<POIView> poiViews = new ArrayList<>();
    private ImageView mapView;
    private Region target;
    private Node zoomNode;
    private StackPane stack;
    private Timeline timeline = new Timeline();
    private int level = 0;
    private DoubleProperty xCenter = new SimpleDoubleProperty(0);
    private DoubleProperty yCenter = new SimpleDoubleProperty(0);
    private EventHandler<? super POIEvent> onPoiClicked;
    private List<Image> tiles = new ArrayList<>();
    private Image youAreHereIcon = new Image(getClass().getResource("/icons/map_me.png").toExternalForm());
    private ImageView youAreHere = new ImageView(youAreHereIcon);
    private Point2D youAreHereLocation;

    public void setYouAreHere(double latitude, double longitude) {
        youAreHereLocation = POI.latLongToPoint(latitude, longitude);
        youAreHere.setTranslateX(youAreHereLocation.getX());
        youAreHere.setTranslateY(youAreHereLocation.getY());
        stack.getChildren().add(youAreHere);
    }

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

        for(POIView subPOI : poiViews) {
            stack.getChildren().addAll(subPOI.getSubPOIViews());
        }

        HBox hBox = new HBox();
        hBox.getChildren().add(stack);

        this.target = hBox;

        this.zoomNode = new Group(target);
        setContent(outerNode(zoomNode));

        setPannable(true);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        hvalueProperty().addListener((obs, old, val) -> {
            System.out.printf("hVal %f\r\n", val.doubleValue());
        });

        // Ensure target scales on both directions together
        target.scaleYProperty().bind(target.scaleXProperty());
        target.scaleXProperty().addListener((obs, old, val) -> {
            for(POIView poi : poiViews) {
                poi.setScaleX(0.3 / val.doubleValue());

                for(POIView subPoi : poi.getSubPOIViews()) {
                    subPoi.setScaleX(0.3 / val.doubleValue());
                }
            }
            youAreHere.setScaleX(0.4 / val.doubleValue());
            youAreHere.setScaleY(0.4 / val.doubleValue());
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
                for (POI subPoi : poi.getSubPOI()) {
                    if (Math.abs(subPoi.getX() - x) < threshold &&
                            Math.abs(subPoi.getY() - y) < threshold) {
                        onPoiClicked.handle(new POIEvent(subPoi));
                        setPointActive(poi);
                        return;
                    }
                }
            }

            // Else
            setPointActive(null);
            onPoiClicked.handle(new POIEvent(null));
        });

        layout();
        setHvalue(0.5);
        setVvalue(0.5);

        target.scaleYProperty().addListener((obs, old, val) -> {
            layout();

            double mapHeight = target.getBoundsInParent().getHeight();
            double viewportHeight = getViewportBounds().getHeight();
            double yPercent = yCenter.getValue() / target.getHeight();
            double yTargetPos = yPercent * mapHeight - viewportHeight/2;
            double vMax = mapHeight - getViewportBounds().getHeight();
            double yVal = yTargetPos / vMax;

            setVvalue(yVal);
        });

        target.scaleXProperty().addListener((obs, old, val) -> {
            layout();

            double mapWidth = target.getBoundsInParent().getWidth();
            double viewportWidth = getViewportBounds().getWidth();
            double xPercent = xCenter.getValue() / target.getWidth();
            double xTargetPos = xPercent * mapWidth - viewportWidth/2 + viewportWidth/4;
            double hMax = mapWidth - getViewportBounds().getWidth();
            double xVal = xTargetPos / hMax;

            setHvalue(xVal);
        });
    }

    public void centerPoint(double x, double y) {
        layout();

        xCenter.setValue(x);
        yCenter.setValue(y);

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
                new KeyFrame(Duration.millis(250), new KeyValue(target.scaleXProperty(), target.scaleXProperty().getValue())),
                new KeyFrame(Duration.millis(750), new KeyValue(target.scaleXProperty(), 1.25, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(249), new KeyValue(hvalueProperty(), xVal, Interpolator.EASE_IN)),
                new KeyFrame(Duration.millis(249), new KeyValue(vvalueProperty(), yVal, Interpolator.EASE_IN))
        );
        timeline.play();
    }

    private POIView activePoiView = null;
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
    }

    public DoubleProperty scaleProperty() {
        return target.scaleXProperty();
    }

    private Node outerNode(Node node) {
        Node outerNode = centeredNode(node);
        outerNode.setOnScroll(e -> {
            e.consume();
            onScroll(e.getDeltaY(), new Point2D(e.getX(), e.getY()));
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
                new KeyFrame(Duration.millis(50), new KeyValue(target.scaleXProperty(), scaleValue, Interpolator.LINEAR))
        );
        timeline.play();
    }

    private long debounce = 0;
    private void onScroll(double wheelDelta, Point2D mousePoint) {
        double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

        // convert target coordinates to zoomTarget coordinates
        Point2D posInZoomTarget = target.parentToLocal(zoomNode.parentToLocal(mousePoint));

        if (System.currentTimeMillis() - debounce > 500) {
            debounce = System.currentTimeMillis();
            xCenter.setValue(posInZoomTarget.getX());
            yCenter.setValue(posInZoomTarget.getY());
        }

        // Bounded scale value
        setScaleValue(
                Math.min(1.5, Math.max(0.25, scaleValue * zoomFactor))
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
