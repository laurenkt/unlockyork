package components;

import events.POIEvent;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
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
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import models.POI;
import java.util.ArrayList;
import java.util.List;

public class MapView extends ScrollPane {

    public void centerAtYouAreHere() {
        this.centerPoint(youAreHereLocation.getX(), youAreHereLocation.getY());
    }

    private double scaleValue = 0.7;
    private double zoomIntensity = 0.01;
    private List<POIView> poiViews = new ArrayList<>();
    private ImageView mapView;
    private Region target;
    private Node zoomNode;
    private StackPane stack;
    private boolean leftAligned = false;
    private Timeline timeline = new Timeline();
    private int level = 0;
    private DoubleProperty xCenter = new SimpleDoubleProperty(0);
    private DoubleProperty yCenter = new SimpleDoubleProperty(0);
    private DoubleProperty scaleProperty = new SimpleDoubleProperty(1);
    private List<Image> tiles = new ArrayList<>();
    private Image youAreHereIcon = new Image(getClass().getResource("/icons/map_me.png").toExternalForm());
    private ImageView youAreHere = new ImageView(youAreHereIcon);
    private Point2D youAreHereLocation;
    private EventHandler<? super POIEvent> onPoiClicked;

    //sets kiosk location
    public void setYouAreHere(double latitude, double longitude) {
        youAreHereLocation = POI.latLongToPoint(latitude, longitude);
        youAreHere.setTranslateX(youAreHereLocation.getX());
        youAreHere.setTranslateY(youAreHereLocation.getY());
        stack.getChildren().add(youAreHere);
    }

    public void setLeftAligned(boolean isLeftAligned) {
        leftAligned = isLeftAligned;
    }

    public MapView(List<POI> POIs) {
        super();

        // adds all the map tiles to an array list
        tiles.add(new Image(getClass().getResource("/tiles/16.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/17.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/18.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/19.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/20.png").toExternalForm()));
        tiles.add(new Image(getClass().getResource("/tiles/21.png").toExternalForm()));

        mapView = new ImageView();
        mapView.setImage(tiles.get(level));

        // Creates the POI objects from all the POI's in the PWS file
        for(POI poi : POIs) {
            poiViews.add(new POIView(poi));
        }

        // sets up the initial map view
        stack = new StackPane();
        stack.setAlignment(Pos.TOP_LEFT);
        stack.getChildren().add(mapView);
        stack.getChildren().addAll(poiViews);
        for(POIView poiView : poiViews) {
            stack.getChildren().addAll(poiView.getSubPOIViews());
        }


        HBox hBox = new HBox();
        hBox.getChildren().add(stack);

        this.target = hBox;

        this.zoomNode = new Group(target);
        setContent(outerNode(zoomNode));

        setPannable(true);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scaleProperty.bindBidirectional(target.scaleXProperty());
        scaleProperty.bindBidirectional(target.scaleYProperty());

        //scales POI icons
        scaleProperty.addListener((obs, old, val) -> {
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
            setPointActive(null);
            if (onPoiClicked != null) {
                onPoiClicked.handle(new POIEvent(null));
            }
        });

        layout();
        setHvalue(0.5);
        setVvalue(0.5);

        // Animate 'You Are Here' icon
        final Timeline youAreHereTimeline = new Timeline();
        final Translate youAreHereTransform = new Translate(0, 0);
        youAreHere.getTransforms().add(youAreHereTransform);
        youAreHereTimeline.setCycleCount(Timeline.INDEFINITE);
        youAreHereTimeline.setAutoReverse(true);
        youAreHereTimeline.getKeyFrames().addAll(
            new KeyFrame(Duration.millis(0), new KeyValue(youAreHereTransform.yProperty(), 0, Interpolator.EASE_BOTH)),
            new KeyFrame(Duration.millis(500), new KeyValue(youAreHereTransform.yProperty(), -20, Interpolator.EASE_BOTH))
        );
        youAreHereTimeline.play();

        // When the view scales, update scroll positions to counteract movement
        target.scaleYProperty().addListener((obs, old, val) -> {
            layout();
            setVvalue(getScrollYForTarget(yCenter.getValue()));
        });

        target.scaleXProperty().addListener((obs, old, val) -> {
            layout();
            setHvalue(getScrollXForTarget(xCenter.getValue()));
        });
    }


    private double getScrollXForTarget(double x) {
        double mapWidth = target.getBoundsInParent().getWidth(); // real-width after scaling
        double scaleFactor = target.getWidth() / mapWidth; // Difference in size between real and sclaed
        double viewportWidth = getViewportBounds().getWidth();
        double realViewportWidth = scaleFactor * viewportWidth;

        double availableWidth = target.getWidth() - realViewportWidth;
        x = x - realViewportWidth/2;

        if (leftAligned) x += realViewportWidth/4;

        return Math.min(1, Math.max(0, x/availableWidth));
    }

    private double getScrollYForTarget(double y) {
        double mapHeight = target.getBoundsInParent().getHeight(); // real-width after scaling
        double scaleFactor = target.getHeight() / mapHeight; // Difference in size between real and sclaed
        double viewportHeight = getViewportBounds().getHeight();
        double realViewportHeight = scaleFactor * viewportHeight;

        double availableHeight = target.getHeight() - realViewportHeight;
        y = y - realViewportHeight/2;

        return Math.min(1, Math.max(0, y/availableHeight));
    }

    // when either the home button or a POI/sponsor is pressed, centralise on that point.
    public void centerPoint(double x, double y) {
        layout();

        xCenter.setValue(x);
        yCenter.setValue(y);

        final double targetScale = 1.25;
        final Duration animationDuration = Duration.millis(1000);

        final Timeline timeline = new Timeline();
        // If it's at a different scale, zoom to it
        // The zoom will also adjust the scroll location towards 'xCenter'
        if (scaleProperty.getValue() != targetScale) {
            timeline.getKeyFrames().add(
                    new KeyFrame(animationDuration, new KeyValue(scaleProperty, targetScale, Interpolator.EASE_BOTH)));
        }
        // If it's at the same scale, then scroll to the location
        else {
            double xVal = getScrollXForTarget(x);
            double yVal = getScrollYForTarget(y);

            timeline.getKeyFrames().addAll(
                    new KeyFrame(animationDuration, new KeyValue(hvalueProperty(), xVal, Interpolator.EASE_BOTH)),
                    new KeyFrame(animationDuration, new KeyValue(vvalueProperty(), yVal, Interpolator.EASE_BOTH))
            );
        }
        timeline.play();
    }

    // determines whether a POI/sPOI is set visible on the map.
    private POIView activePoiView = null;
    public void setPointActive(POI poi) {
        for (POIView view : poiViews) {
            if (view.getPOI().equals(poi)) {
                if (activePoiView != view && activePoiView != null) {
                    activePoiView.setActive(false);
                }
                activePoiView = view;
                view.setActive(true);
                break;
            }
            else {
                for (POIView subView : view.getSubPOIViews()) {
                    if (subView.getPOI().equals(poi)) {
                        for (POIView otherViews : view.getSubPOIViews()) {
                            otherViews.setActive(false);
                        }
                        subView.setActive(true);
                        break;
                    }
                }
            }
        }

        if (poi == null) {
            // Set all inactive
            for (POIView view : poiViews) {
                view.setActive(false);
            }
        }
    }

    public DoubleProperty scaleProperty() {
        return scaleProperty;
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
                new KeyFrame(Duration.millis(100), new KeyValue(scaleProperty, scaleValue, Interpolator.LINEAR))
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
                Math.min(1.5, Math.max(0.35, scaleValue * zoomFactor))
        );
    }

    // if a POI is clicked, make it the centre point and set it visible on the map
    public void setOnPoiClicked(EventHandler<? super POIEvent> handler) {
        onPoiClicked = handler;
        for(POIView poiView : poiViews) {
            poiView.setOnClicked(e -> {
                handler.handle(e);
                setPointActive(e.getPOI());
                centerPoint(e.getPOI().getX(), e.getPOI().getY());
            });
        }
    }

    // sets the zoom level of the map
    private void setLevel(int level) {
        if (level != this.level) {
            this.level = level;
            mapView.setImage(tiles.get(Math.min(level, tiles.size() - 1)));
        }
    }

}
