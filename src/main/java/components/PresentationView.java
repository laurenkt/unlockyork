package components;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;

import models.*;



public class PresentationView extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Presentation presentation = new Presentation();

        XMLParser parser = new XMLParser();
        //causes issues with "no such file or directory"
        presentation = parser.parser("src/build/resources/main/example.pws", "src/build/resources/main/schema.xsd");

        //can force an index to select slide still needs work to fix
        for (int i = 0; i < (presentation.getSlides().size()); i++) {

            primaryStage.setTitle("Slide: " + i);
            primaryStage.setScene(new Scene(displaySlide(presentation.getSlides().get(i)), 1000, 1000));
            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    //loops through each slide element and adds to the list
    public static Group displaySlide(models.Slide slide)
    {
        Group slideElements = new Group();
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

    //need to add in actual colour and fill plus handle gradients
    public static ImageView displayImage(models.Image xmlImage)
    {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(xmlImage.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Image image = new Image(inputStream);

        ImageView imageView = new ImageView(image);

        imageView.setX(xmlImage.getPosition().getxTopLeft());
        imageView.setY(xmlImage.getPosition().getyTopLeft());
        imageView.setFitHeight(xmlImage.getPosition().getyBottomRight() - xmlImage.getPosition().getyTopLeft());
        imageView.setFitWidth(xmlImage.getPosition().getxBottomRight() - xmlImage.getPosition().getxTopLeft());

        return imageView;
    }

    //need to add in actual colour and fill plus handle gradients
    public static Rectangle displayRectangle(models.Shape xmlShape)
    {
        Rectangle rectangle = new Rectangle();

        rectangle.setX(xmlShape.getPosition().getxTopLeft());
        rectangle.setY(xmlShape.getPosition().getyTopLeft());
        rectangle.setHeight(xmlShape.getPosition().getyBottomRight() - xmlShape.getPosition().getyTopLeft());
        rectangle.setWidth(xmlShape.getPosition().getxBottomRight() - xmlShape.getPosition().getxTopLeft());

        rectangle.setStrokeWidth(xmlShape.getStroke());

        rectangle.setFill(Color.web(xmlShape.getColour().getColour()));
        return rectangle;
    }

    //need to add in actual colour and fill plus handle gradients
    public static Ellipse displayEllipse(models.Shape xmlShape)
    {
        Ellipse ellipse = new Ellipse();

        ellipse.setCenterX(((xmlShape.getPosition().getxBottomRight() - xmlShape.getPosition().getxTopLeft()) / 2) + xmlShape.getPosition().getxTopLeft());
        ellipse.setCenterY(((xmlShape.getPosition().getyBottomRight() - xmlShape.getPosition().getyTopLeft()) / 2 ) + xmlShape.getPosition().getyTopLeft());

        ellipse.setRadiusX((xmlShape.getPosition().getxBottomRight() - xmlShape.getPosition().getxTopLeft()) / 2);
        ellipse.setRadiusY((xmlShape.getPosition().getyBottomRight() - xmlShape.getPosition().getyTopLeft()) / 2 );

        ellipse.setStrokeWidth(xmlShape.getStroke());

        ellipse.setFill(Color.web(xmlShape.getColour().getColour()));

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

        line.setFill(Color.web(xmlShape.getColour().getColour()));

        line.setStroke(Color.web(xmlShape.getColour().getColour()));

        return line;
    }

    //tested and working, no player controls at the moment, set to autoplay for now
    public static MediaView displayVideo(models.Video xmlVideo)
    {
        MediaPlayer player = new MediaPlayer( new Media(Paths.get(xmlVideo.getPath()).toUri().toString()));
        MediaView mediaView = new MediaView(player);

        mediaView.setX(xmlVideo.getPosition().getxTopLeft());
        mediaView.setY(xmlVideo.getPosition().getyTopLeft());
        mediaView.setFitHeight(xmlVideo.getPosition().getyBottomRight() - xmlVideo.getPosition().getyTopLeft());
        mediaView.setFitWidth(xmlVideo.getPosition().getxBottomRight() - xmlVideo.getPosition().getxTopLeft());

        player.autoPlayProperty().setValue(true);
        return  mediaView;
    }

    //tested and working, no player controls at the moment, set to autoplay for now
    public static MediaView displayAudio(models.Audio xmlAudio)
    {
        MediaPlayer player = new MediaPlayer( new Media(Paths.get(xmlAudio.getPath()).toUri().toString()));
        MediaView mediaView = new MediaView(player);

        mediaView.setX(xmlAudio.getPosition().getxTopLeft());
        mediaView.setY(xmlAudio.getPosition().getyTopLeft());
        mediaView.setFitHeight(xmlAudio.getPosition().getyBottomRight() - xmlAudio.getPosition().getyTopLeft());
        mediaView.setFitWidth(xmlAudio.getPosition().getxBottomRight() - xmlAudio.getPosition().getxTopLeft());

        player.autoPlayProperty().setValue(true);
        return  mediaView;
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

            text.setText(xmlText.getContent().get(i).getContent());
            text.setUnderline(xmlText.getContent().get(i).getFont().isUnderline());
            Font font = new Font(xmlText.getContent().get(i).getFont().getFont(), xmlText.getContent().get(i).getFont().getTextSize());
            text.setFont(font);
            textList.add(text);
        }

        textFlow.setMaxWidth(500);
        textFlow.setVisible(true);
        return textFlow;
    }
}
