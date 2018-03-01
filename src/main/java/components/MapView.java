package components;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MapView extends StackPane {
    public MapView(Stage stage, Image tiles) {
        super();


        // set the image as the child
        getChildren().setAll(new ImageView(tiles));

        ScrollPane scroll = createScrollPane();
        Scene scene = new Scene(scroll);
        stage.setScene(scene);
        stage.show();
    }

    private ScrollPane createScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        // Make sure the content can be panned
        scrollPane.setPannable(true);
        // Hide the scroll bars
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // link it to this stackpane
        scrollPane.setContent(this);
        return scrollPane;
    }

}
