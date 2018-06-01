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
    final private Image subPoiIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());
    final private Image activeSubPoiIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());
    //shop
    final private Image shopIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());
    final private Image activeShopIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());
    //cafe
    final private Image cafeIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());
    final private Image activeCafeIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());
    //pub
    final private Image pubIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());
    final private Image activePubIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());
    //hotel
    final private Image hotelIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());
    final private Image activeHotelIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());
    //restaurant
    final private Image restaurantIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());
    final private Image activeRestaurantIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());
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
        this.setImage(this.poiIcon);

        for(POI subPOI : poi.getSubPOI()) {
            POIView subPoiView = new POIView(subPOI);
            subPOIViews.add(subPoiView);
            subPoiView.setVisible(false);
        }
    }

    public void setActive(boolean isActive) {

        if(this.poi.getType().equals("POI")) {
            if (isActive) {
                this.setImage(activePoiIcon);
            } else {
                this.setImage(poiIcon);
            }
        }
        else if(this.poi.getType().equals("subPOI")) {
            if (isActive) {
                this.setImage(activeSubPoiIcon);
            } else {
                this.setImage(subPoiIcon);
            }
        }
        else if(this.poi.getType().equals("Shop")) {
            if (isActive) {
                this.setImage(activeShopIcon);
            } else {
                this.setImage(shopIcon);
            }
        }
        else if(this.poi.getType().equals("Cafe")) {
            if (isActive) {
                this.setImage(activeCafeIcon);
            } else {
                this.setImage(cafeIcon);
            }
        }
        else if(this.poi.getType().equals("Pub")) {
            if (isActive) {
                this.setImage(activePubIcon);
            } else {
                this.setImage(pubIcon);
            }
        }
        else if(this.poi.getType().equals("Hotel")) {
            if (isActive) {
                this.setImage(activeHotelIcon);
            } else {
                this.setImage(hotelIcon);
            }
        }
        else if(this.poi.getType().equals("Restaurant")) {
            if (isActive) {
                this.setImage(activeRestaurantIcon);
            } else {
                this.setImage(restaurantIcon);
            }
        }
        else {
            this.setImage(errorIcon);
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
