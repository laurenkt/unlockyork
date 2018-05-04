package components;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextView extends Region {
    private TextFlow textFlowNode;

    public TextView(double x, double y, double width, double height, Path path) throws IOException {
        this(x, y, width, height, String.join("\n", Files.readAllLines(path)));
    }

    public TextView(double x, double y, double width, double height, String text) {
        this(x, y, width, height, null, null, 0, Double.POSITIVE_INFINITY, new Text(text));
    }

    @Override
    public void setWidth(double width) {
       super.setWidth(width);
        textFlowNode.setMaxWidth(width);
    }

    @Override
    public void setHeight(double height) {
        super.setHeight(height);
        textFlowNode.setMaxHeight(height);
    }

    public TextView(double x, double y, double width, double height, Font font, Color color, double startTime, double duration, Path path) throws IOException {
        this(x, y, width, height, font, color, startTime, duration, String.join("\n", Files.readAllLines(path)));
    }

    public TextView(double x, double y, double width, double height, Font font, Color color, double startTime, double duration, String text) {
        this(x, y, width, height, font, color, startTime, duration, new Text(text));
    }

    public TextView(double x, double y, double width, double height, Font font, Color color, double startTime, double duration, Text ... textNodes) {
        textFlowNode = new TextFlow(textNodes);

        setHeight(height);
        setWidth(width);
        setLayoutX(x);
        setLayoutY(y);
        setVisible(false);

        for (Text textNode : textNodes) {
            if (font != null)
                textNode.setFont(font);

            if (color != null)
                textNode.setFill(color);
        }

        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(startTime), e -> setVisible(true)),
                new KeyFrame(Duration.millis(startTime + duration), e -> setVisible(false))
        );
        timeline.play();

        getChildren().add(textFlowNode);
    }
}
