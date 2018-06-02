package components;

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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import models.POI;

import java.util.ArrayList;
import java.util.List;

public class POIView extends Region {

    final private Image poiIcon = new Image(getClass().getResource("/icons/map_poi.png").toExternalForm());
    final private Image subPoiIcon = new Image(getClass().getResource("/icons/map_spoi.png").toExternalForm());
    final private Image shopIcon = new Image(getClass().getResource("/icons/map_shop.png").toExternalForm());
    final private Image cafeIcon = new Image(getClass().getResource("/icons/map_cafe.png").toExternalForm());
    final private Image pubIcon = new Image(getClass().getResource("/icons/map_bar.png").toExternalForm());
    final private Image hotelIcon = new Image(getClass().getResource("/icons/map_hotel.png").toExternalForm());
    final private Image restaurantIcon = new Image(getClass().getResource("/icons/map_dining.png").toExternalForm());

    private ImageView icon = new ImageView();
    private ≠Text name = new Text();
    private POI poi;
    private List<POIView> subPOIViews = new ArrayList<>();
    private ColorAdjust colorAdjust = new ColorAdjust();
    private DropShadow dropShadow = new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 5, 0.9, 0, 0);
    private Timeline timeline = new Timeline();

    public POIView(POI poi) {
        this.poi = poi;

        name.setText(poi.getName());
        name.setFont(new Font(30));
        name.setStrokeWidth(10);
        name.setTextOrigin(VPos.BOTTOM);
        name.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
        name.setTextAlignment(TextAlignment.CENTER);
       // name.getLayoutBounds().getWidth()
        name.setX(-((name.getLayoutBounds().getWidth()-100) / 2));


        //name.setWrappingWidth(100);

        icon.setFitHeight(100);
        icon.setFitWidth(100);
        icon.setImage(getImageForType(poi.getType()));

        this.setMaxHeight(100);
        this.setMaxWidth(100);
        this.setTranslateX((this.poi.getX())-50);
        this.setTranslateY((this.poi.getY())-50);
        this.getChildren().addAll(icon, name);
        setPickOnBounds(true);

        icon.setEffect(colorAdjust);
        setActive(false);

        for(POI subPOI : poi.getSubPOI()) {
            POIView subPoiView = new POIView(subPOI);
            subPOIViews.add(subPoiView);
            subPoiView.setVisible(false);
        }

        // Maintain aspect ratio: anything done to scale X should do to scale Y
        scaleYProperty().bind(scaleXProperty());

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(0), new KeyValue(scaleXProperty(), 1, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(500 + (Math.random()-0.5)*100), new KeyValue(scaleXProperty(), 1.1, Interpolator.EASE_BOTH))
        );
        timeline.play();

        this.setOnMouseEntered(e -> {
            timeline.stop();
            timeline.getKeyFrames().clear();
            timeline.setCycleCount(1);
            timeline.setAutoReverse(false);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(100), new KeyValue(scaleXProperty(), 1.5, Interpolator.EASE_BOTH))
            );
            timeline.play();
        });
        this.setOnMouseExited(e -> {
            timeline.stop();
            timeline.getKeyFrames().clear();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(true);
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.millis(0), new KeyValue(scaleXProperty(), 1, Interpolator.EASE_BOTH)),
                    new KeyFrame(Duration.millis(500 + (Math.random()-0.5)*100), new KeyValue(scaleXProperty(), 1.1, Interpolator.EASE_BOTH))
            );
            timeline.play();
        });
    }

    private Image getImageForType(String type) {
        type = type.toLowerCase(); // Normalize

        if ("spoi".equals(type))       return subPoiIcon;
        if ("shop".equals(type))       return shopIcon;
        if ("cafe".equals(type))       return cafeIcon;
        if ("pub".equals(type))        return pubIcon;
        if ("hotel".equals(type))      return hotelIcon;
        if ("restaurant".equals(type)) return restaurantIcon;

        return poiIcon;
    }

    public void setActive(boolean isActive) {
        icon.setImage(getImageForType(poi.getType()));

        if (isActive) {
            colorAdjust.setInput(dropShadow);
            colorAdjust.setBrightness(-0.15);
            colorAdjust.setHue(-0.15);
            colorAdjust.setSaturation(0.95);
            timeline.stop();
        }
        else {
            colorAdjust.setInput(null);
            colorAdjust.setBrightness(-1);
            timeline.play();
        }

        for(POIView subPOI : subPOIViews) {
            subPOI.setVisible(isActive);
        }
    }

    public List<components.POIView> getSubPOIViews() {
        return subPOIViews;
    }

    public POI getPOI() {
        return poi;
    }
}
