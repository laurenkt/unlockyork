package components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.POI;

public class POIView extends ImageView{

    ImageView POIView;
    javafx.scene.image.Image poiIcon;
    POI poi;

    public POIView(Image poiIcon, POI poi) {
        this.poiIcon = poiIcon;
        this.poi = poi;

        this.setFitHeight(100);
        this.setFitWidth(100);
        this.setTranslateX((this.poi.getX())-50);
        this.setTranslateY((this.poi.getY())-50);
        this.setImage(this.poiIcon);

        System.out.println("");
    }

    public ImageView getImageView() { return this; }
}
