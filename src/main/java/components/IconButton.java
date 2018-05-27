package components;

import javafx.scene.control.Button;

public class IconButton extends Button {
    public IconButton(String iconResourcePath) {
            getStyleClass().add("unlock--button");
            setStyle("-fx-background-image: url(" + iconResourcePath + ")");
    }
}
