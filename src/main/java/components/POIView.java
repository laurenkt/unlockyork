package components;

import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import models.POI;

import java.util.ArrayList;
import java.util.List;

public class POIView extends Region {

    //main POI
    final private Image poiIcon = new Image(getClass().getResource("/icons/map_poi.png").toExternalForm());
    final private Image activePoiIcon = new Image(getClass().getResource("/icons/map_poi.png").toExternalForm());
    //sub POI
    final private Image subPoiIcon = new Image(getClass().getResource("/icons/map_spoi.png").toExternalForm());
    final private Image activeSubPoiIcon = new Image(getClass().getResource("/icons/map_spoi_active.png").toExternalForm());
    //shop
    final private Image shopIcon = new Image(getClass().getResource("/icons/map_shop.png").toExternalForm());
    final private Image activeShopIcon = new Image(getClass().getResource("/icons/map_shop.png").toExternalForm());
    //cafe
    final private Image cafeIcon = new Image(getClass().getResource("/icons/map_cafe.png").toExternalForm());
    final private Image activeCafeIcon = new Image(getClass().getResource("/icons/map_cafe.png").toExternalForm());
    //pub
    final private Image pubIcon = new Image(getClass().getResource("/icons/map_bar.png").toExternalForm());
    final private Image activePubIcon = new Image(getClass().getResource("/icons/map_bar.png").toExternalForm());
    //hotel
    final private Image hotelIcon = new Image(getClass().getResource("/icons/map_hotel.png").toExternalForm());
    final private Image activeHotelIcon = new Image(getClass().getResource("/icons/map_hotel.png").toExternalForm());
    //restaurant
    final private Image restaurantIcon = new Image(getClass().getResource("/icons/map_dining.png").toExternalForm());
    final private Image activeRestaurantIcon = new Image(getClass().getResource("/icons/map_dining.png").toExternalForm());

    POI poi;
    List<POIView> subPOIViews = new ArrayList<>();
    ImageView icon = new ImageView();
    Text name = new Text();

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
        icon.setImage(getImageForType(poi.getType(), false));

        this.setMaxHeight(100);
        this.setMaxWidth(100);
        this.setTranslateX((this.poi.getX())-50);
        this.setTranslateY((this.poi.getY())-50);
        this.getChildren().addAll(icon, name);

        for(POI subPOI : poi.getSubPOI()) {
            POIView subPoiView = new POIView(subPOI);
            subPOIViews.add(subPoiView);
            subPoiView.setVisible(false);
        }

    }

    private Image getImageForType(String type, boolean isActive) {
        type = type.toLowerCase(); // Normalize
        if (!isActive) {
            if ("spoi".equals(type))       return subPoiIcon;
            if ("shop".equals(type))       return shopIcon;
            if ("cafe".equals(type))       return cafeIcon;
            if ("pub".equals(type))        return pubIcon;
            if ("hotel".equals(type))      return hotelIcon;
            if ("restaurant".equals(type)) return restaurantIcon;

            return poiIcon;
        }

        // Active
        if ("spoi".equals(type))       return activeSubPoiIcon;
        if ("shop".equals(type))       return activeShopIcon;
        if ("cafe".equals(type))       return activeCafeIcon;
        if ("pub".equals(type))        return activePubIcon;
        if ("hotel".equals(type))      return activeHotelIcon;
        if ("restaurant".equals(type)) return activeRestaurantIcon;

        return activePoiIcon;
    }

    public void setActive(boolean isActive) {
        icon.setImage(getImageForType(poi.getType(), isActive));

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
