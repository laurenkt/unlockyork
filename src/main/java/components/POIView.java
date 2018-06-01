package components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.POI;

import java.util.ArrayList;
import java.util.List;

public class POIView extends ImageView {

    final private Image poiIcon = new Image(getClass().getResource("/icons/map_poi.png").toExternalForm());
    final private Image activePoiIcon = new Image(getClass().getResource("/icons/map_poi_active.png").toExternalForm());

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
            subPOIViews.add(new POIView(subPOI));
        }
    }

    public void setActive(boolean isActive) {
        if (isActive) {
            this.setImage(activePoiIcon);
        }
        else {
            this.setImage(poiIcon);
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
