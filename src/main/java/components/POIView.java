package components;

import events.POIEvent;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.VPos;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import models.POI;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class POIView extends Region {
    //audio for POI
    final static private AudioClip appearClip = new AudioClip(POIView.class.getResource("/sounds/dart.aiff").toExternalForm());
    final static private AudioClip activeClip = new AudioClip(POIView.class.getResource("/sounds/click.aiff").toExternalForm());
    //icons for all different types of POI, sPOI, sponsors
    final private Image poiIcon = new Image(getClass().getResource("/icons/map_poi.png").toExternalForm());
    final private Image subPoiIcon = new Image(getClass().getResource("/icons/map_spoi.png").toExternalForm());
    final private Image shopIcon = new Image(getClass().getResource("/icons/map_shop.png").toExternalForm());
    final private Image cafeIcon = new Image(getClass().getResource("/icons/map_cafe.png").toExternalForm());
    final private Image pubIcon = new Image(getClass().getResource("/icons/map_bar.png").toExternalForm());
    final private Image hotelIcon = new Image(getClass().getResource("/icons/map_hotel.png").toExternalForm());
    final private Image restaurantIcon = new Image(getClass().getResource("/icons/map_dining.png").toExternalForm());
    final private Image gardenIcon = new Image(getClass().getResource("/icons/map_garden.png").toExternalForm());
    final private Image activityIcon = new Image(getClass().getResource("/icons/map_activity.png").toExternalForm());

    private ImageView icon = new ImageView();
    private Text name = new Text();
    private POI poi;
    private List<POIView> subPOIViews = new ArrayList<>();
    private ColorAdjust colorAdjust = new ColorAdjust();
    private DropShadow dropShadow = new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 5, 0.9, 0, 0);
    private Timeline timeline = new Timeline();
    private boolean isActive;
    private EventHandler<? super POIEvent> onClicked;

    public DoubleProperty opacityProperty = new SimpleDoubleProperty(1);

    public POIView(POI poi) {
        this.poi = poi;

        //sets name of POI above icon
        name.setText(poi.getName());
        name.setFont(new Font(40));
        name.getStyleClass().add("unlock--poi-label");
        name.setTextOrigin(VPos.BOTTOM);
        name.setTextAlignment(TextAlignment.CENTER);
        name.setTranslateX(-((name.getLayoutBounds().getWidth()-100) / 2)); //makes sure text is centered above POI icon
        icon.setImage(getImageForType(poi.getType()));

        //sets region for POI
        setMaxHeight(100);
        setMaxWidth(100);
        setTranslateX((this.poi.getX())-50);
        setTranslateY((this.poi.getY())-50);
        getChildren().addAll(icon, name);
        setPickOnBounds(false);
        name.setMouseTransparent(true);

        icon.setEffect(colorAdjust);
        setActive(false);

        opacityProperty.addListener((obs, old, val) -> setStyle("-fx-opacity: "+(Math.round(val.doubleValue() * 100.0)/100.0)));
        //adds all sub POI to this POI
        for(POI subPOI : poi.getSubPOI()) {
            POIView subPoiView = new POIView(subPOI);
            subPOIViews.add(subPoiView);
            subPoiView.setVisible(false);
        }

        // Maintain aspect ratio: anything done to scale X should do to scale Y
        scaleYProperty().bind(scaleXProperty());
        icon.scaleYProperty().bind(icon.scaleXProperty());

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(0), new KeyValue(icon.scaleXProperty(), 1, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(500 + (Math.random()-0.5)*100), new KeyValue(icon.scaleXProperty(), 1.1, Interpolator.EASE_BOTH))
        );
        timeline.play();

        //when mouse is over POI
        icon.setPickOnBounds(true);
        icon.setOnMouseEntered(e -> {
            timeline.stop();
            timeline.getKeyFrames().clear();
            timeline.setCycleCount(1);
            timeline.setAutoReverse(false);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(100), new KeyValue(icon.scaleXProperty(), 1.5, Interpolator.EASE_BOTH))
            );
            timeline.play();
        });
        //when mouse leaves POI region
        icon.setOnMouseExited(e -> {
            timeline.stop();
            timeline.getKeyFrames().clear();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(true);
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.millis(0), new KeyValue(icon.scaleXProperty(), 1, Interpolator.EASE_BOTH)),
                    new KeyFrame(Duration.millis(500 + (Math.random()-0.5)*100), new KeyValue(icon.scaleXProperty(), 1.1, Interpolator.EASE_BOTH))
            );
            timeline.play();
        });
    }

    //when the POI is clicked on
    public void setOnClicked(EventHandler<? super POIEvent> handler) {
        for(POIView subPoiView : subPOIViews) { //show sPOI
            subPoiView.setOnClicked(handler);
        }
        icon.setOnMouseClicked(e -> { //change icon to show its been clicked
            handler.handle(new POIEvent(poi));
            e.consume();
        });
    }
    //returns the correct icon for the type of POI
    private Image getImageForType(String type) {
        type = type.toLowerCase(); // Normalize

        if ("spoi".equals(type))     return subPoiIcon;
        if ("shop".equals(type))     return shopIcon;
        if ("cafe".equals(type))     return cafeIcon;
        if ("pub".equals(type))      return pubIcon;
        if ("hotel".equals(type))    return hotelIcon;
        if ("dining".equals(type))   return restaurantIcon;
        if ("garden".equals(type))   return gardenIcon;
        if ("activity".equals(type)) return activityIcon;

        return poiIcon;
    }

    public void setActive(boolean isActive) {
        icon.setImage(getImageForType(poi.getType()));

        if (isActive) {//when a POI is selected
            colorAdjust.setInput(dropShadow);
            colorAdjust.setBrightness(-0.15);
            colorAdjust.setHue(-0.15);
            colorAdjust.setSaturation(0.95);
            timeline.stop();

            // If there's sPOIs, then hide the text label
            if (subPOIViews.size() > 0)
                name.setVisible(false);

            if (!this.isActive) {
                activeClip.play();
            }
        }
        else { //if the POI is not selected
            name.setVisible(true);
            colorAdjust.setInput(null);
            colorAdjust.setBrightness(-1);
            timeline.play();

            // Unset all sPoi active
            for (POIView subPoiView : subPOIViews) {
                subPoiView.setActive(false);
            }
        }

        //mouse scaling animation
        Timeline sPoiAppearTimeline = new Timeline();
        double delay = 500; // Initial delay + accumulator
        for(POIView subPOI : subPOIViews) {
            subPOI.setVisible(isActive);

            if (isActive && !this.isActive) {
                sPoiAppearTimeline.getKeyFrames().addAll(
                        new KeyFrame(Duration.millis(0), new KeyValue(subPOI.opacityProperty, 0)),
                        new KeyFrame(Duration.millis(delay), new KeyValue(subPOI.getIcon().translateYProperty(), -100)),
                        new KeyFrame(Duration.millis(delay), new KeyValue(subPOI.opacityProperty, 0)),
                        new KeyFrame(Duration.millis(delay += 200), new KeyValue(subPOI.opacityProperty, 1, Interpolator.EASE_BOTH)),
                        new KeyFrame(Duration.millis(delay), new KeyValue(subPOI.getIcon().translateYProperty(), 0, Interpolator.EASE_BOTH))
                );

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run () { POIView.appearClip.play(); }
                }, (long) delay);
            }
        }
        sPoiAppearTimeline.play();

        this.isActive = isActive;
    }

    private ImageView getIcon() {
        return icon;
    }

    public List<components.POIView> getSubPOIViews() {
        return subPOIViews;
    }

    public POI getPOI() {
        return poi;
    }
}
