package components;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.nio.file.Paths;

/**
 *
 *
 * @author Unlock (lt696@york.ac.uk)
 */
public class PictureView extends Region {
    private ImageView imageView;
    private ImageView maskImageView;
    private Group maskView;
    private Circle mask;
    private boolean loupeEnabled = false;
    private double scale;

    /**
     *
     * @param image
     * @param x
     * @param y
     * @param width
     * @param height
     * @param loupeScale
     */
    public PictureView(Image image, double x, double y, double width, double height, double loupeScale) {
        imageView = new ImageView(image);
        maskView = new Group();
        maskImageView = new ImageView(image);
        maskImageView.setSmooth(false);
        setLoupeScape(loupeScale);
        maskView.getChildren().add(maskImageView);

        // Mask
        mask = new Circle(0,0,100);
        mask.setVisible(loupeEnabled);
        maskView.setClip(mask);

        //adding to the root's children
        getChildren().add(imageView);
        getChildren().add(maskView);

        setLayoutX(x);
        setLayoutY(y);
        setWidth(width);
        setMaxWidth(width);
        setHeight(height);
        setMaxHeight(height);

        mask.setClip(new Rectangle(width, height));

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        maskImageView.setFitWidth(width);
        maskImageView.setFitHeight(height);


        // Display the loupe when the mouse moves across the image
        setOnMouseMoved(e -> {
            // Ensure the loupe is syncronised
            mask.setVisible(loupeEnabled);

            // Don't adjust the loupe unless it's enabled
            if (!loupeEnabled)
                return;

            // Move zoomed image around underneath cursor to align properly
            maskImageView.setX(maskImageView.getLayoutBounds().getWidth()/scale - e.getX());
            maskImageView.setY(maskImageView.getLayoutBounds().getHeight()/scale - e.getY());

            // Move actual mask
            mask.setCenterX(e.getX());
            mask.setCenterY(e.getY());
        });

        // Hide the loupe when the mouse leaves the region
        setOnMouseExited(e -> mask.setVisible(false));
    }

    /**
     *
     * @param image
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public PictureView(Image image, double x, double y, double width, double height) {
        this(image, x, y, width, height, 2.0);
    }

    /**
     *
     * @param path
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public PictureView(String path, double x, double y, double width, double height) {
        this(new Image(Paths.get(path).toUri().toString()), x, y, width, height);
    }

    /**
     *
     * @return
     */
    public boolean isLoupeEnabled() {
        return loupeEnabled;
    }

    /**
     *
     * @param loupeEnabled
     */
    public void setLoupeEnabled(boolean loupeEnabled) {
        this.loupeEnabled = loupeEnabled;
    }

    /**
     *
     * @param scale
     */
    public void setLoupeScape(double scale) {
        this.scale = scale;
        maskImageView.setScaleX(scale);
        maskImageView.setScaleY(scale);
    }

    /**
     *
     * @return
     */
    public double getLoupeScale() {
        return scale;
    }

    /**
     *
     * @param top
     * @param left
     * @param bottom
     * @param right
     */
    public void crop(double top, double left, double bottom, double right) {
        // Have to create the clipping mask twice because JavaFX complains the same
        // mask is used for two nodes
        setClip(new Rectangle(top, left, right - left, bottom - top));
        mask.setClip(new Rectangle(top, left, right - left, bottom - top));
    }

}
