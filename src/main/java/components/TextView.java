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

/**
 * Wrapper for the JavaFX TextView which contains extra functionality:
 *
 * - Animation using `startTime` and `duration` parameters
 * - Flow multiple texts
 *
 * @author Unlock (lt696@york.ac.uk)
 */
public class TextView extends Region {
    private TextFlow textFlowNode;

    /**
     * Create a TextView from a text file
     * @param x
     * @param y
     * @param width
     * @param height
     * @param path
     *      path to the plain text file (e.g. .txt file)
     * @throws IOException
     *      exception will be thrown (and must be handled) if the file cannot be found
     */
    public TextView(double x, double y, double width, double height, Path path) throws IOException {
        this(x, y, width, height, null, null, 0, Double.POSITIVE_INFINITY, path);
    }

    /**
     * Create a TextView with a string
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text
     *      string in the text view to display
     */
    public TextView(double x, double y, double width, double height, String text) {
        this(x, y, width, height, null, null, 0, Double.POSITIVE_INFINITY, new Text(text));
    }

    /**
     * Create a TextView from a text file, with transition
     * @param x
     * @param y
     * @param width
     * @param height
     * @param font
     * @param color
     * @param startTime
     *      time before the element appears (in milliseconds)
     * @param duration
     *      time before the element disappears (in milliseconds)
     * @param path
     *      path to the plain text file (e.g. .txt file)
     * @throws IOException
     */
    public TextView(double x, double y, double width, double height, Font font, Color color, double startTime, double duration, Path path) throws IOException {
        this(x, y, width, height, font, color, startTime, duration, String.join("\n", Files.readAllLines(path)));
    }

    /**
     * Create a TextView from a string, with transition
     * @param x
     * @param y
     * @param width
     * @param height
     * @param font
     * @param color
     * @param startTime
     *      time before the element appears (in milliseconds)
     * @param duration
     *      time before the element disappears (in milliseconds)
     * @param text
     */
    public TextView(double x, double y, double width, double height, Font font, Color color, double startTime, double duration, String text) {
        this(x, y, width, height, font, color, startTime, duration, new Text(text));
    }

    /**
     * Create a TextView from multiple text nodes (variable length arguments).
     * This allows different formatting for sections of text within the block (e.g. if they are created with
     * different fonts).
     * Example usage like:
     *      group.getChildren().add(new TextView(100, 200, 400, 300, null, Color.BLACK, 1000, 3000,
     *          new Text('Some'),
     *          new Text('Example'),
     *          new Text('Text'));
     * @param x
     * @param y
     * @param width
     * @param height
     * @param font
     * @param color
     * @param startTime
     *      time before the element appears (in milliseconds)
     * @param duration
     *      time before the element disappears (in milliseconds)
     * @param textNodes
     *      any number of text nodes, separated by commas, or an array of text nodes
     */
    public TextView(double x, double y, double width, double height, Font font, Color color, double startTime, double duration, Text ... textNodes) {
        textFlowNode = new TextFlow(textNodes);

        // Set layout params
        setHeight(height);
        setWidth(width);
        setLayoutX(x);
        setLayoutY(y);
        setVisible(false); // Start invisible because it will animate in (this may happen immediately)

        char lastCharOfNode = '\0';
        for (Text textNode : textNodes) {
            // For any node that is not the first node, if the node doesn't start with a space
            // or the previous node didn't end with one, one must be inserted so that the text flows correctly with
            // white space between formatting blocks
            if (lastCharOfNode != '\0') {
                if (lastCharOfNode != ' ' && textNode.getText().charAt(0) != ' ') {
                    textNode.setText(" " + textNode.getText());
                }
            }
            // Update the last char
            lastCharOfNode = textNode.getText().charAt(textNode.getText().length() - 1);

            // Overwrite formatting if any is set
            if (font != null)
                textNode.setFont(font);

            if (color != null)
                textNode.setFill(color);
        }

        // Animation, can use different properties here, but visibility is the default
        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(startTime), e -> setVisible(true)),
                new KeyFrame(Duration.millis(startTime + duration), e -> setVisible(false))
        );
        timeline.play();

        // Add to the region
        getChildren().add(textFlowNode);
    }

    // Must override these because the TextFlow doesn't necessarily fit to the container
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
}
