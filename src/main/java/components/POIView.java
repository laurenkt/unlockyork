package components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.POI;

import java.util.ArrayList;
import java.util.List;

public class POIView extends ImageView {

    //main POI
    final private Image poiIcon = new Image(getClass().getResource("/icons/map_poi.png").toExternalForm());
    final private Image activePoiIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());
    //sub POI
    final private Image subPoiIcon = new Image(getClass().getResource("/icons/map_spoi.png").toExternalForm());
    final private Image activeSubPoiIcon = new Image(getClass().getResource("/icons/map_spoi.png").toExternalForm());
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
    //error
    final private Image errorIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());



    POI poi;
    List<POIView> subPOIViews = new ArrayList<>();

    public POIView(POI poi) {
        this.poi = poi;

        this.setFitHeight(100);
        this.setFitWidth(100);
        this.setTranslateX((this.poi.getX())-50);
        this.setTranslateY((this.poi.getY())-50);
        this.setImage(getImageForType(poi.getType(), false));

        for(POI subPOI : poi.getSubPOI()) {
            POIView subPoiView = new POIView(subPOI);
            subPOIViews.add(subPoiView);
            subPoiView.setVisible(false);
        }
    }

    private Image getImageForType(String type, boolean isActive) {
        System.out.println(poi.getId());
        System.out.println(type);

        type = type.toLowerCase(); // Normalize
        if (!isActive) {
            if ("shop".equals(type))       return shopIcon;
            if ("cafe".equals(type))       return cafeIcon;
            if ("pub".equals(type))        return pubIcon;
            if ("hotel".equals(type))      return hotelIcon;
            if ("restaurant".equals(type)) return restaurantIcon;

            return poiIcon;
        }

        // Active
        if ("shop".equals(type))       return activeShopIcon;
        if ("cafe".equals(type))       return activeCafeIcon;
        if ("pub".equals(type))        return activePubIcon;
        if ("hotel".equals(type))      return activeHotelIcon;
        if ("restaurant".equals(type)) return activeRestaurantIcon;

        return activePoiIcon;
    }

    public void setActive(boolean isActive) {
        setImage(getImageForType(poi.getType(), isActive));

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
