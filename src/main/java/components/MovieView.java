package components;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.nio.file.Paths;

/**
 * MovieView is a wrapper around the JavaFX video playback tools with some extra functionality
 * (sliding transport controls, media controls, full-screen mode).
 *
 * @author Unlock (lt696@york.ac.uk)
 */
public class MovieView extends Region {
    // The node that displays the media player
    private MediaView mediaView;
    // The component which deals with all the practical media commands (play/pause/seek/etc)
    private MediaPlayer mediaPlayer;
    // Component which all icons are mounted onto
    private HBox toolbar;
    // The floating 'mute icon' which remains after the toolbar leaves
    private ImageView muteIcon = new ImageView();
    // Slides the toolbar in and out
    private TranslateTransition toolbarTransition;
    // Reference to second window if full-screen is engaged
    private Stage fullscreenWindow;
    // What the fullscreen button does (is changed when in fullscreen mode)
    private EventHandler<ActionEvent> onFullScreenAction =
            e -> setFullScreen();

    /*
     * Toolbar icons
     */
    private Button playPauseButton = new Button();
    private Button muteButton = new Button();
    private Button rateButton = new Button();
    private Button fullscreenButton = new Button();
    private Slider seekSlider = new Slider(0, 1, 0);
    private Slider rateSlider = new Slider(0.5, 2.0, 1);
    private Slider volumeSlider = new Slider(0, 1, 0.8);

    /**
     * Constructor which takes a filepath
     * @param path
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public MovieView(String path, double x, double y, double width, double height) {
        this(new MediaPlayer(new Media(Paths.get(path).toUri().toString())), x, y, width, height);
    }

    /**
     * Constructor which takes an existing MediaPlayer instance
     * @param player
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public MovieView(MediaPlayer player, double x, double y, double width, double height) {
        // Add an overall class to namespace CSS to this module
        getStyleClass().add("unlock--movieview");

        mediaPlayer = player;
        player.setAutoPlay(true);

        // Inner nodes
        // Media
        mediaView = new MediaView(mediaPlayer);
        mediaView.setPreserveRatio(true);
        mediaView.setFitHeight(height);
        mediaView.setFitWidth(width);
        // Toolbar
        addToolBar();

        // Set layout bounds and positions
        setLayoutX(x);
        setLayoutY(y);
        setWidth(width);
        setMaxWidth(width);
        setHeight(height);
        setMaxHeight(height);
        setClip(new Rectangle(0, 0, width, height));

        // When the view is clicked, restart playback from beginning
        // Only do this on the mediaView to avoid problems with the toolbar
        mediaView.setOnMouseClicked(e -> {
            mediaPlayer.seek(new Duration(0));
            mediaPlayer.play();
        });

        // Need to set the listener to update the :playing pseudo-class
        mediaPlayer.statusProperty().addListener(obs ->
            updatePlayingState());

        // HBox used to align the video in the center
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(mediaView);

        // AnchorPane used to align the toolbar to the bottom
        AnchorPane pane = new AnchorPane();
        pane.getChildren().addAll(box, muteIcon, toolbar);

        // Toolbar anchors
        pane.setLeftAnchor(toolbar, 0.0);
        pane.setRightAnchor(toolbar, 0.0);
        pane.setBottomAnchor(toolbar, 0.0);

        // Mute icon anchor
        pane.setBottomAnchor(muteIcon, 10.0);

        // Video box anchors
        pane.setTopAnchor(box, 0.0);
        pane.setLeftAnchor(box, 0.0);
        pane.setRightAnchor(box, 0.0);
        pane.setBottomAnchor(box, 0.0);

        // Set the constraints and clipping on the pane to match the region
        pane.setMaxHeight(height);
        pane.setMinHeight(height);
        pane.setMinWidth(width);
        pane.setMaxWidth(width);
        pane.setClip(new Rectangle(0, 0, width, height));

        getChildren().add(pane);

        // Need to update all the states initially so when new windows are created
        // (full-screen) they have the correct icons
        updateMutedState();
        updatePlayingState();
        updatePlaybackRateState();
    }

    /**
     * Creates a full-screen window if one doesn't already exist.
     * Removes it if one does.
     */
    private void setFullScreen() {
        if (fullscreenWindow == null) {
            // The setFullScreen method doesn't seem to work universally, so
            // emulate it by fetching the screen bounds and placing the
            // undecorated window across that area
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            // The new movieView for the full-screen window uses the same mediaPlayer
            // instance as this one -- that way any changes to the media in one
            // window are preserved in the other (mute etc)
            MovieView movieView = new MovieView(
                    mediaPlayer,
                    0,
                    0,
                    primaryScreenBounds.getWidth(),
                    primaryScreenBounds.getHeight()
            );
            // Rebind the new windows fullscreen action to re-invoke this method on this window
            // (which will close the child window)
            movieView.setOnFullScreenAction(e -> setFullScreen());

            // Create the new window/scene
            fullscreenWindow = new Stage(StageStyle.UNDECORATED);
            Scene scene = new Scene(movieView, Color.BLACK);
            // Respond to ESC key to exit
            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE)
                    setFullScreen();
            });
            // Close window automatically when window is hidden
            fullscreenWindow.setOnHidden(e -> setFullScreen());

            // Set bounds to be full screen
            fullscreenWindow.setScene(scene);
            fullscreenWindow.setX(primaryScreenBounds.getMinX());
            fullscreenWindow.setY(primaryScreenBounds.getMinY());
            fullscreenWindow.setWidth(primaryScreenBounds.getWidth());
            fullscreenWindow.setHeight(primaryScreenBounds.getHeight());
            fullscreenWindow.setMaximized(true);

            fullscreenWindow.show();
        }
        else {
            fullscreenWindow.close();
            fullscreenWindow = null;
        }
    }

    /**
     * Updates the state of the :muted pseudo-class and corrects layout for mute icon
     */
    private void updateMutedState() {
        pseudoClassStateChanged(
                PseudoClass.getPseudoClass("muted"),
                mediaPlayer.isMute()
        );

        // Display a constant mute icon when muted so the user knows why there is no sound
        muteIcon.setVisible(mediaPlayer.isMute());
        // Align the icon properly with the actual mute button
        muteIcon.setLayoutX(muteButton.getLayoutX());
    }

    /**
     * Updates the state of the :playing pseudo-class
     */
    private void updatePlayingState() {
        pseudoClassStateChanged(
                PseudoClass.getPseudoClass("playing"),
                mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING
        );
    }

    /**
     * Updates the labels on the playback rate button.
     * If at any other speed than 1.0x, it will display that rate next to the button.
     */
    private void updatePlaybackRateState() {
        if (mediaPlayer.getRate() != 1.0) {
            // Spacing to give room for icon (slightly hacky but simplest solution,
            // may need to be adjusted if different font is used)
            rateButton.setText("       " + Double.toString(mediaPlayer.getRate()) + "x");
        }
        else {
            rateButton.setText("");
        }
    }

    /**
     * Create the toolbar if one doesn't exist already
     */
    private void addToolBar() {
        // Only create a toolbar if one doesn't already exist
        if (toolbar != null)
            return;

        // Toolbar to display controls on
        toolbar = new HBox();
        toolbar.setAlignment(Pos.CENTER);
        toolbar.alignmentProperty().isBound();
        toolbar.setSpacing(5);

        // Ensure the seek slider always fills the space
        toolbar.setHgrow(seekSlider, Priority.ALWAYS);
        seekSlider.setMaxWidth(Double.POSITIVE_INFINITY);
        volumeSlider.setMaxWidth(50);
        rateSlider.setMaxWidth(50);

        // Always-present-when-muted icon
        muteIcon.setImage(new Image(getClass().getClassLoader().getResource("icons/volume_off.png").toExternalForm(), 24.0, 24.0, true, false));
        muteIcon.setFitHeight(24.0);
        muteIcon.setFitHeight(24.0);

        // Add CSS classes
        toolbar.getStyleClass().add("controls");
        playPauseButton.getStyleClass().add("button--playpause");
        muteButton.getStyleClass().add("button--mute");
        fullscreenButton.getStyleClass().add("button--fullscreen");
        rateButton.getStyleClass().add("button--rate");
        muteIcon.getStyleClass().add("imageview--mute");

        /*
         * Ensure the toolbar responds properly to changes in the mediaPlayer
         */

        // Mute
        mediaPlayer.muteProperty().addListener(ob -> updateMutedState());

        // Max length
        mediaPlayer.totalDurationProperty().addListener((ov, prev, val) ->
            seekSlider.setMax(val.toSeconds()));
        if (mediaPlayer.getTotalDuration() != null)
            seekSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());

        // Current time
        mediaPlayer.currentTimeProperty().addListener((ov, prev, val) -> {
            if (!seekSlider.isValueChanging()) {
                seekSlider.setValue(val.toSeconds());
            }
        });

        // Volume
        mediaPlayer.volumeProperty().bindBidirectional(volumeSlider.valueProperty());

        // Playback rate
        mediaPlayer.rateProperty().addListener(obs ->
            updatePlaybackRateState());

        /*
         * Ensure the mediaPlayer responds correctly to the toolbar
         */

        // Toggle mute
        muteButton.setOnAction(e -> mediaPlayer.setMute(!mediaPlayer.isMute()));

        // Rate (speed) adjust
        ContextMenu rateMenu = new ContextMenu();
        rateMenu.getItems().addAll(
                createRateMenuItem(0.5),
                createRateMenuItem(0.75),
                createRateMenuItem(1.0),
                createRateMenuItem(1.5),
                createRateMenuItem(2.0)
        );
        rateButton.setOnMouseClicked(e -> rateMenu.show(rateButton, e.getScreenX(), e.getScreenY()));

        // Toggle playing
        playPauseButton.setOnAction(e -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)
                mediaPlayer.pause();
            else
                mediaPlayer.play();
        });

        // Adjust transport/seek slider
        seekSlider.valueProperty().addListener(ov -> {
            if (seekSlider.isValueChanging()) {
                mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
            }
        });
        seekSlider.setOnMousePressed(e ->
            mediaPlayer.seek(Duration.seconds(seekSlider.getValue())));

        // Go fullscreen
        fullscreenButton.setOnAction(e -> this.onFullScreenAction.handle(e));

        /*
         * Show/hide toolbar on movement
         */

        toolbarTransition = new TranslateTransition(Duration.millis(200), toolbar);
        toolbarTransition.setInterpolator(Interpolator.EASE_IN);

        setOnMouseEntered(e -> {
            toolbarTransition.setToY(0);
            toolbarTransition.play();
        });

        setOnMouseExited(e -> {
            toolbarTransition.setToY(toolbar.getHeight());
            toolbarTransition.play();
        });

        // Add icons to toolbar
        toolbar.getChildren().addAll(
                playPauseButton,
                seekSlider,
                rateButton,
                muteButton,
                volumeSlider,
                fullscreenButton
        );
    }

    /**
     * Convenience method for creating menu items for playback rate menu
     * @param rate
     * @return
     */
    private MenuItem createRateMenuItem(double rate) {
        MenuItem item = new MenuItem(Double.toString(rate) + "x");
        item.setOnAction(e -> mediaPlayer.setRate(rate));
        return item;
    }

    /**
     * Link with the stylesheet in /resources
     * @return
     */
    @Override public String getUserAgentStylesheet() {
        return getClass().getClassLoader().getResource("css/MovieView.css").toExternalForm();
    }

    /*
     * Accessors/mutators
     */

    /**
     *
     * @param onFullScreenAction
     */
    public void setOnFullScreenAction(EventHandler<ActionEvent> onFullScreenAction) {
        this.onFullScreenAction = onFullScreenAction;
    }

    /**
     *
     * @return
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

}
