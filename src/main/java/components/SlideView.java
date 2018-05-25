package components;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import models.Shape;
import models.Slide;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SlideView extends Region {
    public SlideView(Slide slide, double scaleHeightFactor, double scaleWidthFactor) {
        // Clicking on empty areas in the region should pass through to anything behind
        // clicking on children nodes should process these events
        setPickOnBounds(false);

        List list = getChildren();

        //list.add(displaySlideBackground(slide, scaleHeightFactor, scaleWidthFactor));

        for (int t = 0; t < slide.getText().size(); t++) {
            list.add(displayText(slide.getText().get(t), scaleHeightFactor, scaleWidthFactor));
        }

        for (int v = 0; v < slide.getVideo().size(); v++) {
            list.add(displayVideo(slide.getVideo().get(v), scaleHeightFactor, scaleWidthFactor));
        }

        for (int a = 0; a < slide.getAudio().size(); a++) {
            list.add(displayAudio(slide.getAudio().get(a), scaleHeightFactor, scaleWidthFactor));
        }

        for (int im = 0; im < slide.getImage().size(); im++) {
            list.add(displayImage(slide.getImage().get(im), scaleHeightFactor, scaleWidthFactor));
        }

        for (int s = 0; s < slide.getShape().size(); s++) {
            if (slide.getShape().get(s).getShape().equals("rectangle")) {
                list.add(displayRectangle(slide.getShape().get(s), scaleHeightFactor, scaleWidthFactor));

            } else if (slide.getShape().get(s).getShape().equals("ellipse")) {
                list.add(displayEllipse(slide.getShape().get(s), scaleHeightFactor, scaleWidthFactor));

            } else if (slide.getShape().get(s).getShape().equals("line")) {
                list.add(displayLine(slide.getShape().get(s), scaleHeightFactor, scaleWidthFactor));
            }
        }
    }

    public Rectangle displaySlideBackground(Slide slide, double scaleHeightFactor, double scaleWidthFactor)
    {
        Rectangle background = new Rectangle();

        background.setX(0);
        background.setY(0);
        background.setHeight(1080 * scaleHeightFactor);
        background.setWidth(1920 * scaleWidthFactor);
        background.setFill(Color.web(slide.getColour().getFill()));

        return background;
    }

    public Node displayImage(models.Image xmlImage, double scaleHeightFactor, double scaleWidthFactor)
    {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(xmlImage.getPath());
        } catch (FileNotFoundException e) {
            System.err.println("Image could not be found: " + xmlImage.getPath());
            inputStream = SlideView.class.getResourceAsStream("/not_found.png");
        }

        Image image = new Image(inputStream);

        return new PictureView(
            image,
            xmlImage.getPosition().getxTopLeft() * scaleWidthFactor,
            xmlImage.getPosition().getyTopLeft() * scaleWidthFactor,
            (xmlImage.getPosition().getxBottomRight() - xmlImage.getPosition().getxTopLeft()) * scaleWidthFactor,
            (xmlImage.getPosition().getyBottomRight() - xmlImage.getPosition().getyTopLeft()) * scaleHeightFactor
        );
    }

    public Stop[] gradientHandler(Shape xmlShape)
    {
        String colourOneString = "#";
        String colourTwoString = "#";

        for(int i = 10; i < 16; i++)
        {
            colourOneString += xmlShape.getColour().getFill().charAt(i);
        }

        for(int i = 18; i < 24; i++)
        {
            colourTwoString += xmlShape.getColour().getFill().charAt(i);
        }

        System.out.println("GRADIENT colourONE: " + colourOneString + " colourTWO: " + colourTwoString);

        Stop[] stops = { new Stop(0, Color.web(colourOneString)), new Stop(1, Color.web(colourTwoString))};

        return stops;
    }

    public Node displayRectangle(models.Shape xmlShape, double scaleHeightFactor, double scaleWidthFactor)
    {
        Rectangle rectangle = new Rectangle();

        rectangle.setX(xmlShape.getPosition().getxTopLeft() * scaleWidthFactor);
        rectangle.setY(xmlShape.getPosition().getyTopLeft() * scaleHeightFactor);
        rectangle.setHeight((xmlShape.getPosition().getyBottomRight() - xmlShape.getPosition().getyTopLeft()) * scaleHeightFactor);
        rectangle.setWidth((xmlShape.getPosition().getxBottomRight() - xmlShape.getPosition().getxTopLeft()) * scaleWidthFactor);

        rectangle.setStrokeWidth(xmlShape.getStroke());
        rectangle.setStroke(Color.web(xmlShape.getColour().getColor()));


        if(xmlShape.getColour().getFill().charAt(0) == 'g')
        {
            LinearGradient linearGradient = new LinearGradient(rectangle.getX(), rectangle.getY(), rectangle.getWidth() + rectangle.getX(), rectangle.getHeight() + rectangle.getY(), false,CycleMethod.REPEAT, gradientHandler(xmlShape));
            rectangle.setFill(linearGradient);
        }
        else
        {
            rectangle.setFill(Color.web(xmlShape.getColour().getFill()));
        }

        return rectangle;
    }

    public Node displayEllipse(models.Shape xmlShape, double scaleHeightFactor, double scaleWidthFactor)
    {
        Ellipse ellipse = new Ellipse();

        ellipse.setCenterX((((xmlShape.getPosition().getxBottomRight() - xmlShape.getPosition().getxTopLeft()) / 2) + xmlShape.getPosition().getxTopLeft()) * scaleWidthFactor);
        ellipse.setCenterY((((xmlShape.getPosition().getyBottomRight() - xmlShape.getPosition().getyTopLeft()) / 2 ) + xmlShape.getPosition().getyTopLeft()) * scaleHeightFactor);

        ellipse.setRadiusX(((xmlShape.getPosition().getxBottomRight() - xmlShape.getPosition().getxTopLeft()) / 2) * scaleWidthFactor);
        ellipse.setRadiusY(((xmlShape.getPosition().getyBottomRight() - xmlShape.getPosition().getyTopLeft()) / 2 ) * scaleHeightFactor);

        ellipse.setStrokeWidth(xmlShape.getStroke());
        ellipse.setStroke(Color.web(xmlShape.getColour().getColor()));

        if(xmlShape.getColour().getFill().charAt(0) == 'g')
        {

            RadialGradient radialGradient = new RadialGradient(0, 0, ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getRadiusY(), false, CycleMethod.REPEAT, gradientHandler(xmlShape));
            ellipse.setFill(radialGradient);

        }
        else
        {
            ellipse.setFill(Color.web(xmlShape.getColour().getFill()));
        }

        return ellipse;

    }

    //need to add in actual color and fill plus handle gradients
    public Node displayLine(models.Shape xmlShape, double scaleHeightFactor, double scaleWidthFactor)
    {
        Line line = new Line();

        line.setStartX(xmlShape.getPosition().getxTopLeft() * scaleWidthFactor);
        line.setStartY(xmlShape.getPosition().getyTopLeft() * scaleHeightFactor);
        line.setEndX(xmlShape.getPosition().getxBottomRight() * scaleWidthFactor);
        line.setEndY(xmlShape.getPosition().getyBottomRight() * scaleHeightFactor);

        line.setStrokeWidth(xmlShape.getStroke());

        line.setFill(Color.web(xmlShape.getColour().getFill()));

        line.setStroke(Color.web(xmlShape.getColour().getColor()));

        return line;
    }

    //tested and working, no player controls at the moment, set to autoplay for now
    public Node displayVideo(models.Video xmlVideo, double scaleHeightFactor, double scaleWidthFactor)
    {
        return new MovieView(
            xmlVideo.getPath(),
            xmlVideo.getPosition().x1 * scaleWidthFactor,
            xmlVideo.getPosition().y1 * scaleHeightFactor,
            xmlVideo.getPosition().getWidth() * scaleWidthFactor,
            xmlVideo.getPosition().getHeight() * scaleHeightFactor
        );
    }

    //tested and working, no player controls at the moment, set to autoplay for now
    public Node displayAudio(models.Audio xmlAudio, double scaleHeightFactor, double scaleWidthFactor)
    {
        SoundView soundView = new SoundView(new Media(Paths.get(xmlAudio.getPath()).toUri().toString()), false, false, new ArrayList<Integer>());
        soundView.setLayoutX(xmlAudio.getPosition().x1 * scaleWidthFactor);
        soundView.setLayoutY(xmlAudio.getPosition().y1 * scaleHeightFactor);
        return soundView;
    }

    //prints on top of each other for separate text elements, format not always correct
    public Node displayText(models.Text xmlText, double scaleHeightFactor, double scaleWidthFactor)
    {
        return new TextView(
            xmlText.getPosition().x1 * scaleWidthFactor,
            xmlText.getPosition().y1 * scaleHeightFactor,
            500,
            500,
            null,
            null,
            0,
            Double.POSITIVE_INFINITY,
            xmlText.getContent().stream().map(textContent -> {
                Text text =  new Text(textContent.getContent());
                text.setFont(textContent.getFont().getFont());
                text.setUnderline(textContent.getFont().isUnderline());
                text.setFill(textContent.getColour().getColorPaint());
                return text;
            }).toArray(size -> new Text[size])
        );
    }

}
