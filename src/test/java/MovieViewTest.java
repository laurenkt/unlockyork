import components.MovieView;
import de.saxsys.javafx.test.JfxRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

@RunWith(JfxRunner.class)
public class MovieViewTest {

    @Test
    public void positionArgumentsShouldBeUsed() {
        MovieView movieView = new MovieView("../local_file.mp4", 123, 55, 100, 100);
        assertEquals(123.0, movieView.getLayoutX());
        assertEquals(55.0, movieView.getLayoutY());
    }

    @Test
    public void boundsArgumentsShouldBeUsed() {
        MovieView movieView = new MovieView("../local_file.mp4", 0, 0, 400, 240);
        assertEquals(400.0, movieView.getWidth());
        assertEquals(240.0, movieView.getHeight());
    }

}
