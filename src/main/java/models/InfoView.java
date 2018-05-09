package models;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import models.Presentation;
import models.Slide;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

import static javafx.scene.paint.CycleMethod.NO_CYCLE;
import static javafx.scene.paint.CycleMethod.REPEAT;

public class InfoView {

    public static Group DisplayPresentationView(Presentation presentation, int slideNum) {
    Group pres = new Group();

    pres = displaySlide(presentation.getSlides().get(slideNum));


    return pres;
    }

    public static Group displaySlide(models.Slide slide)
    {
        javafx.scene.Group slideElements = new javafx.scene.Group();
        ObservableList list = slideElements.getChildren();

        for (int t = 0; t < slide .getText().size(); t++) {
            list.add(displayText(slide.getText().get(t)));
        }

        for (int v = 0; v < slide.getVideo().size(); v++) {
            list.add(displayVideo(slide.getVideo().get(v)));
        }

        for (int a = 0; a < slide.getAudio().size(); a++) {
            list.add(displayAudio(slide.getAudio().get(a)));
        }

        for (int im = 0; im < slide.getImage().size(); im++) {
            list.add(displayImage(slide.getImage().get(im)));
        }

        for (int s = 0; s < slide.getShape().size(); s++) {

            if (slide.getShape().get(s).getShape().equals("rectangle")) {
                list.add(displayRectangle(slide.getShape().get(s)));

            } else if (slide.getShape().get(s).getShape().equals("ellipse")) {
                list.add(displayEllipse(slide.getShape().get(s)));

            } else if (slide.getShape().get(s).getShape().equals("line")) {
                list.add(displayLine(slide.getShape().get(s)));
            }
        }

        return slideElements;
    }


    public static ImageView displayImage(models.Image xmlImage)
    {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(xmlImage.getPath());
        } catch (FileNotFoundException e) {
            inputStream = InfoView.class.getResourceAsStream("not_found.png");
        }

        Image image = new Image(inputStream);

        ImageView imageView = new ImageView(image);

        imageView.setX(xmlImage.getPosition().getxTopLeft());
        imageView.setY(xmlImage.getPosition().getyTopLeft());
        imageView.setFitHeight(xmlImage.getPosition().getyBottomRight() - xmlImage.getPosition().getyTopLeft());
        imageView.setFitWidth(xmlImage.getPosition().getxBottomRight() - xmlImage.getPosition().getxTopLeft());

        return imageView;
    }

    public static Stop[] gradientHandler(Shape xmlShape)
    {
        String colourOneString = "#";
        String colourTwoString = "#";

        for(int i = 10; i < 16; i++)
        {
            colourOneString += xmlShape.colour.fill.charAt(i);
        }

        for(int i = 18; i < 24; i++)
        {
            colourTwoString += xmlShape.colour.fill.charAt(i);
        }

        System.out.println("GRADIENT colourONE: " + colourOneString + " colourTWO: " + colourTwoString);

        Stop[] stops = { new Stop(0, Color.web(colourOneString)), new Stop(1, Color.web(colourTwoString))};

        return stops;
    }

    public static Rectangle displayRectangle(models.Shape xmlShape)
    {
        Rectangle rectangle = new Rectangle();

        rectangle.setX(xmlShape.getPosition().getxTopLeft());
        rectangle.setY(xmlShape.getPosition().getyTopLeft());
        rectangle.setHeight(xmlShape.getPosition().getyBottomRight() - xmlShape.getPosition().getyTopLeft());
        rectangle.setWidth(xmlShape.getPosition().getxBottomRight() - xmlShape.getPosition().getxTopLeft());

        rectangle.setStrokeWidth(xmlShape.getStroke());
        rectangle.setStroke(Color.web(xmlShape.getColour().getColour()));


        if(xmlShape.colour.fill.charAt(0) == 'g')
        {
            LinearGradient linearGradient = new LinearGradient(rectangle.getX(), rectangle.getY(), rectangle.getWidth() + rectangle.getX(), rectangle.getHeight() + rectangle.getY(), false,CycleMethod.REPEAT, gradientHandler(xmlShape));
            rectangle.setFill(linearGradient);
        }
        else
        {
            rectangle.setFill(Color.web(xmlShape.getColour().fill));
        }

        return rectangle;
    }

    public static Ellipse displayEllipse(models.Shape xmlShape)
    {
        Ellipse ellipse = new Ellipse();

        ellipse.setCenterX(((xmlShape.getPosition().getxBottomRight() - xmlShape.getPosition().getxTopLeft()) / 2) + xmlShape.getPosition().getxTopLeft());
        ellipse.setCenterY(((xmlShape.getPosition().getyBottomRight() - xmlShape.getPosition().getyTopLeft()) / 2 ) + xmlShape.getPosition().getyTopLeft());

        ellipse.setRadiusX((xmlShape.getPosition().getxBottomRight() - xmlShape.getPosition().getxTopLeft()) / 2);
        ellipse.setRadiusY((xmlShape.getPosition().getyBottomRight() - xmlShape.getPosition().getyTopLeft()) / 2 );

        ellipse.setStrokeWidth(xmlShape.getStroke());
        ellipse.setStroke(Color.web(xmlShape.getColour().getColour()));

        if(xmlShape.colour.fill.charAt(0) == 'g')
        {

            RadialGradient radialGradient = new RadialGradient(0, 0, ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getRadiusY(), false, CycleMethod.REPEAT, gradientHandler(xmlShape));
            ellipse.setFill(radialGradient);

        }
        else
        {
            ellipse.setFill(Color.web(xmlShape.getColour().fill));
        }

        return ellipse;

    }

    //need to add in actual colour and fill plus handle gradients
    public static Line displayLine(models.Shape xmlShape)
    {
        Line line = new Line();

        line.setStartX(xmlShape.getPosition().getxTopLeft());
        line.setStartY(xmlShape.getPosition().getyTopLeft());
        line.setEndX(xmlShape.getPosition().getxBottomRight());
        line.setEndY(xmlShape.getPosition().getyBottomRight());

        line.setStrokeWidth(xmlShape.getStroke());

        line.setFill(Color.web(xmlShape.getColour().getFill()));

        line.setStroke(Color.web(xmlShape.getColour().getColour()));

        return line;
    }

    //tested and working, no player controls at the moment, set to autoplay for now
    public static Node displayVideo(models.Video xmlVideo)
    {
        try {
            MediaPlayer player = new MediaPlayer(new Media(Paths.get(xmlVideo.getPath()).toUri().toString()));
            MediaView mediaView = new MediaView(player);

            mediaView.setX(xmlVideo.getPosition().getxTopLeft());
            mediaView.setY(xmlVideo.getPosition().getyTopLeft());
            mediaView.setFitHeight(xmlVideo.getPosition().getyBottomRight() - xmlVideo.getPosition().getyTopLeft());
            mediaView.setFitWidth(xmlVideo.getPosition().getxBottomRight() - xmlVideo.getPosition().getxTopLeft());

            player.autoPlayProperty().setValue(true);
            return mediaView;
        }
        catch (Exception e) {
            InputStream inputStream = InfoView.class.getResourceAsStream("not_found.png");

            Image image = new Image(inputStream);

            ImageView imageView = new ImageView(image);

            imageView.setX(xmlVideo.getPosition().getxTopLeft());
            imageView.setY(xmlVideo.getPosition().getyTopLeft());
            imageView.setFitHeight(xmlVideo.getPosition().getyBottomRight() - xmlVideo.getPosition().getyTopLeft());
            imageView.setFitWidth(xmlVideo.getPosition().getxBottomRight() - xmlVideo.getPosition().getxTopLeft());

            return imageView;
        }
    }

    //tested and working, no player controls at the moment, set to autoplay for now
    public static Node displayAudio(models.Audio xmlAudio)
    {
        try {
            MediaPlayer player = new MediaPlayer(new Media(Paths.get(xmlAudio.getPath()).toUri().toString()));
            MediaView mediaView = new MediaView(player);

            mediaView.setX(xmlAudio.getPosition().getxTopLeft());
            mediaView.setY(xmlAudio.getPosition().getyTopLeft());
            mediaView.setFitHeight(xmlAudio.getPosition().getyBottomRight() - xmlAudio.getPosition().getyTopLeft());
            mediaView.setFitWidth(xmlAudio.getPosition().getxBottomRight() - xmlAudio.getPosition().getxTopLeft());

            player.autoPlayProperty().setValue(true);
            return mediaView;
        }
        catch (Exception e) {
            InputStream inputStream = InfoView.class.getResourceAsStream("not_found.png");

            Image image = new Image(inputStream);

            ImageView imageView = new ImageView(image);

            imageView.setX(xmlAudio.getPosition().getxTopLeft());
            imageView.setY(xmlAudio.getPosition().getyTopLeft());
            imageView.setFitHeight(xmlAudio.getPosition().getyBottomRight() - xmlAudio.getPosition().getyTopLeft());
            imageView.setFitWidth(xmlAudio.getPosition().getxBottomRight() - xmlAudio.getPosition().getxTopLeft());

            return imageView;
        }
    }

    //prints on top of each other for seperate text elements, format not always correct
    public static TextFlow displayText(models.Text xmlText)
    {
        TextFlow textFlow = new TextFlow();
        ObservableList textList = textFlow.getChildren();
        textFlow.setTextAlignment(TextAlignment.JUSTIFY);

        for(int i = 0; i < xmlText.getContent().size(); i++)
        {
            Text text = new Text();

            text.setText(
                    (i > 0 ? " " : "") +
                            xmlText.getContent().get(i).getContent()
            );
            text.setUnderline(xmlText.getContent().get(i).getFont().isUnderline());
            Font font = new Font(xmlText.getContent().get(i).getFont().getFont(), xmlText.getContent().get(i).getFont().getTextSize());
            text.setFont(font);
            textList.add(text);
        }

        textFlow.setLayoutX(xmlText.getPosition().getxTopLeft());
        textFlow.setLayoutY(xmlText.getPosition().getyTopLeft());
        textFlow.setMaxWidth(500);
        textFlow.setVisible(true);
        return textFlow;
    }

}
