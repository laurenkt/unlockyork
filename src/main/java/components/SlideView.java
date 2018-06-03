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
import models.SlideElement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SlideView extends Region {
    private Slide slide;

    public SlideView(Slide slide) {
        this.slide = slide;

        // Clicking on empty areas in the region should pass through to anything behind
        // clicking on children nodes should process these events
        setPickOnBounds(false);

        List list = getChildren();

        //list.add(displaySlideBackground(slide, scaleHeightFactor, scaleWidthFactor));
        for (SlideElement el : slide.getElements()) {
            list.add(renderElementNode(el));
        }

    }

    public Slide getSlide() {
        return slide;
    }

    public Node renderElementNode(SlideElement el) {
        // Resolve element type and dispatch to renderer
        if (el instanceof models.Text)
            return renderTextNode((models.Text)el);
        if (el instanceof models.Shape)
            return renderShapeNode((models.Shape)el);
        if (el instanceof models.Image)
            return renderImageNode((models.Image)el);
        if (el instanceof models.Audio)
            return renderAudioNode((models.Audio)el);
        if (el instanceof models.Video)
            return renderVideoNode((models.Video)el);

        // None found
        return null;
    }

    public Node renderShapeNode(models.Shape shape) {
        // Resolve shape type
        String shapeType = shape.getShape();

        // Dispatch to renderer
        if ("rectangle".equals(shapeType))
            return renderRectangleNode(shape);
        if ("ellipse".equals(shapeType))
            return renderEllipseNode(shape);
        if ("line".equals(shapeType))
            return renderLineNode(shape);

        // Nothing found
        return null;
    }

    public Rectangle displaySlideBackground(Slide slide)
    {
        Rectangle background = new Rectangle();

        background.setX(0);
        background.setY(0);
        background.setHeight(1080);
        background.setWidth(1920);
        background.setFill(Color.web(slide.getColor().getFill()));

        return background;
    }

    public Node renderImageNode(models.Image xmlImage)
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
            xmlImage.getPosition().getxTopLeft(),
            xmlImage.getPosition().getyTopLeft(),
            (xmlImage.getPosition().getxBottomRight() - xmlImage.getPosition().getxTopLeft()),
            (xmlImage.getPosition().getyBottomRight() - xmlImage.getPosition().getyTopLeft())
        );
    }

    public Stop[] gradientHandler(Shape xmlShape)
    {
        String colourOneString = "#";
        String colourTwoString = "#";

        for(int i = 10; i < 16; i++)
        {
            colourOneString += xmlShape.getColor().getFill().charAt(i);
        }

        for(int i = 18; i < 24; i++)
        {
            colourTwoString += xmlShape.getColor().getFill().charAt(i);
        }

        System.out.println("GRADIENT colourONE: " + colourOneString + " colourTWO: " + colourTwoString);

        Stop[] stops = { new Stop(0, Color.web(colourOneString)), new Stop(1, Color.web(colourTwoString))};

        return stops;
    }

    public Node renderRectangleNode(models.Shape xmlShape)
    {
        Rectangle rectangle = new Rectangle();

        rectangle.setTranslateX(xmlShape.getPosition().getxTopLeft());
        rectangle.setTranslateY(xmlShape.getPosition().getyTopLeft());
        rectangle.setHeight((xmlShape.getPosition().getyBottomRight() - xmlShape.getPosition().getyTopLeft()));
        rectangle.setWidth((xmlShape.getPosition().getxBottomRight() - xmlShape.getPosition().getxTopLeft()));
        rectangle.setStrokeWidth(xmlShape.getStroke());
        rectangle.setStroke(Color.web(xmlShape.getColor().getColor()));

        if(xmlShape.getColor().getFill().charAt(0) == 'g')
        {
            LinearGradient linearGradient = new LinearGradient(rectangle.getX(), rectangle.getY(), rectangle.getWidth() + rectangle.getX(), rectangle.getHeight() + rectangle.getY(), false,CycleMethod.REPEAT, gradientHandler(xmlShape));
            rectangle.setFill(linearGradient);
        }
        else
        {
            rectangle.setFill(Color.web(xmlShape.getColor().getFill()));
        }

        return rectangle;
    }

    public Node renderEllipseNode(models.Shape xmlShape)
    {
        Ellipse ellipse = new Ellipse();

        ellipse.setCenterX((((xmlShape.getPosition().getxBottomRight() - xmlShape.getPosition().getxTopLeft()) / 2) + xmlShape.getPosition().getxTopLeft()));
        ellipse.setCenterY((((xmlShape.getPosition().getyBottomRight() - xmlShape.getPosition().getyTopLeft()) / 2 ) + xmlShape.getPosition().getyTopLeft()));
        ellipse.setRadiusX(((xmlShape.getPosition().getxBottomRight() - xmlShape.getPosition().getxTopLeft()) / 2));
        ellipse.setRadiusY(((xmlShape.getPosition().getyBottomRight() - xmlShape.getPosition().getyTopLeft()) / 2));
        ellipse.setStrokeWidth(xmlShape.getStroke());
        ellipse.setStroke(Color.web(xmlShape.getColor().getColor()));

        if(xmlShape.getColor().getFill().charAt(0) == 'g')
        {
            RadialGradient radialGradient = new RadialGradient(0, 0, ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getRadiusY(), false, CycleMethod.REPEAT, gradientHandler(xmlShape));
            ellipse.setFill(radialGradient);
        }
        else
        {
            ellipse.setFill(Color.web(xmlShape.getColor().getFill()));
        }

        return ellipse;

    }

    //need to add in actual color and fill plus handle gradients
    public Node renderLineNode(models.Shape xmlShape)
    {
        Line line = new Line();

        line.setStartX(xmlShape.getPosition().getxTopLeft());
        line.setStartY(xmlShape.getPosition().getyTopLeft());
        line.setEndX(xmlShape.getPosition().getxBottomRight());
        line.setEndY(xmlShape.getPosition().getyBottomRight());
        line.setStrokeWidth(xmlShape.getStroke());
        line.setFill(Color.web(xmlShape.getColor().getFill()));
        line.setStroke(Color.web(xmlShape.getColor().getColor()));

        return line;
    }

    //tested and working, no player controls at the moment, set to autoplay for now
    public Node renderVideoNode(models.Video xmlVideo)
    {
        return new MovieView(
            xmlVideo.getPath(),
            xmlVideo.getPosition().x1,
            xmlVideo.getPosition().y1,
            xmlVideo.getPosition().getWidth(),
            xmlVideo.getPosition().getHeight()
        );
    }

    //tested and working, no player controls at the moment, set to autoplay for now
    public Node renderAudioNode(models.Audio xmlAudio)
    {
        SoundView soundView = new SoundView(new Media(Paths.get(xmlAudio.getPath()).toUri().toString()), false, false, new ArrayList<Integer>());
        soundView.setLayoutX(xmlAudio.getPosition().x1);
        soundView.setLayoutY(xmlAudio.getPosition().y1);
        return soundView;
    }

    //prints on top of each other for separate text elements, format not always correct
    public Node renderTextNode(models.Text xmlText)
    {
        double width = xmlText.getPosition().getWidth();
        double height = xmlText.getPosition().getHeight();

        if (width <= 0) width = 1000;
        if (height <= 0) height = 1000;

        return new TextView(
            xmlText.getPosition().x1,
            xmlText.getPosition().y1,
            width,
            height,
            null,
            null,
            0,
            Double.POSITIVE_INFINITY,
            xmlText.getContent().stream().map(textContent -> {
                Text text =  new Text(textContent.getContent());
                text.setFont(textContent.getFont().getFont());
                text.setUnderline(textContent.getFont().isUnderline());
                text.setFill(textContent.getColor().getColorPaint());
                return text;
            }).toArray(size -> new Text[size])
        );
    }

}
