package components;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

public class ImageView extends Region {
    private javafx.scene.image.ImageView imageView;
    private Group maskView;
    private javafx.scene.image.ImageView maskImageView;
    private Circle mask;

    public boolean isLoupeVisible() {
        return loupeVisible;
    }

    public void setLoupeVisible(boolean loupeVisible) {
        this.loupeVisible = loupeVisible;
        mask.setVisible(loupeVisible);
    }

    private boolean loupeVisible = false;

    public ImageView(Image image, double scale) {
        imageView = new javafx.scene.image.ImageView(image);
        maskView = new Group();
        maskImageView = new javafx.scene.image.ImageView(image);
        maskImageView.setSmooth(false);
        maskImageView.setScaleX(scale);
        maskImageView.setScaleY(scale);
        maskView.getChildren().add(maskImageView);

        // Cursor
        // maskView.setCursor(Cursor.NONE);

        // Mask
        mask = new Circle(0,0,100);
        mask.setVisible(loupeVisible);
        maskView.setClip(mask);

        //adding to the root's children
        getChildren().add(imageView);
        getChildren().add(maskView);

        setOnMouseMoved(e -> {
            if (!loupeVisible)
                return;

            // Move zoomed image around underneath cursor to align properly
            maskImageView.setX(maskImageView.getLayoutBounds().getWidth()/scale - e.getX());
            maskImageView.setY(maskImageView.getLayoutBounds().getHeight()/scale - e.getY());
            // Move actual mask
            mask.setCenterX(e.getX());
            mask.setCenterY(e.getY());
        });
    }

    public ImageView(Image image, double x, double y, double width, double height) {
        this(image);

        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    public ImageView(Image image) {
        this(image, 2.0);
    }

    public void crop(double top, double left, double bottom, double right) {

    }
}
