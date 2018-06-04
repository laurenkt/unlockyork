import components.PictureView;
import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.image.Image;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

@RunWith(JfxRunner.class)
public class PictureViewTest {

    Image testImage;

    @Before
    public void setUp() {
        testImage = new Image(getClass().getClassLoader().getResource("test_image.jpg").toExternalForm());
    }

    @Test
    public void positionArgumentsShouldBeUsed() {
        PictureView pictureView = new PictureView(testImage, 123, 55, 100, 100);
        assertEquals(123.0, pictureView.getLayoutX());
        assertEquals(55.0, pictureView.getLayoutY());
    }

    @Test
    public void boundsArgumentsShouldBeUsed() {
        PictureView pictureView = new PictureView(testImage, 0, 0, 400, 240);
        assertEquals(400.0, pictureView.getWidth());
        assertEquals(240.0, pictureView.getHeight());
    }

}
