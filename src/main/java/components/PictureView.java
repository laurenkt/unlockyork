package components;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class PictureView extends Region {
    private javafx.scene.image.ImageView imageView;
    private javafx.scene.image.ImageView maskImageView;
    private Group maskView;
    private Circle mask;
    private boolean loupeVisible = false;
    private boolean mouseOver = false;

    public boolean isLoupeVisible() {
        return loupeVisible;
    }

    public void setLoupeVisible(boolean loupeVisible) {
        this.loupeVisible = loupeVisible;
    }


    public PictureView(Image image, double scale) {
        setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        imageView = new javafx.scene.image.ImageView(image);
        maskView = new Group();
        maskImageView = new javafx.scene.image.ImageView(image);
        maskImageView.setSmooth(false);
        maskImageView.setScaleX(scale);
        maskImageView.setScaleY(scale);
        maskView.getChildren().add(maskImageView);

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

            mask.setVisible(loupeVisible);

            // Move zoomed image around underneath cursor to align properly
            maskImageView.setX(maskImageView.getLayoutBounds().getWidth()/scale - e.getX());
            maskImageView.setY(maskImageView.getLayoutBounds().getHeight()/scale - e.getY());
            // Move actual mask
            mask.setCenterX(e.getX());
            mask.setCenterY(e.getY());
        });

        setOnMouseExited(e -> {
            mask.setVisible(false);
        });
    }

    public PictureView(Image image, double x, double y, double width, double height) {
        this(image);

        setLayoutX(x);
        setLayoutY(y);
        setWidth(width);
        setMaxWidth(width);
        setHeight(height);
        setMaxHeight(height);

        mask.setClip(new Rectangle(width, height));

        imageView.setFitWidth(width);
        imageView.setFitHeight(width);
        maskImageView.setFitWidth(width);
        maskImageView.setFitHeight(width);

    }

    public PictureView(Image image) {
        this(image, 2.0);
    }

    public void crop(double top, double left, double bottom, double right) {
        // Have to create the clipping mask twice because JavaFX complains the same
        // mask is used for two nodes
        setClip(new Rectangle(top, left, right - left, bottom - top));
        mask.setClip(new Rectangle(top, left, right - left, bottom - top));
    }
}
