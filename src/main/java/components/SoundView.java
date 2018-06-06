package components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * A sound module used to play back audio files. A JavaFX Pane is generated
 * containing GUI elements used to control the media playback. Time markers are
 * supported in the form of an integer representing the number of seconds into
 * the media playback it should be positioned.
 * <p>
 * GUI elements can be toggle visible or invisible. All elements of GUI are
 * customisable using CSS.
 * <p>
 * Supported audio formats: MP3, AIFF, WAV, MPEG-4 with ACC
 * 
 * @author Ben King
 * @version 1.0
 */
public class SoundView extends Region {
	
	// internal variables
	private Pane rootPane = new Pane();
	private MediaPlayer player;
	private MediaView mediaView = new MediaView(player);
	private double mediaDuration;
	private List<Integer> markers;
	private String markerColour = "#000000";
	private boolean isVisible;

	// gui elements
	private ToggleButton playPauseButton = new ToggleButton("Pause");
	private ToggleButton muteButton = new ToggleButton("Mute");
	private List<Button> markerButtons = new ArrayList<Button>();
	private Button backwardButton = new Button("Backward");
	private Button forwardButton = new Button("Forward");
	private Pane seekingPane = new Pane();
	private Pane visiblePane = new Pane();
	private Slider volumeSlider = new Slider();
	private Slider seekingSlider = new Slider();
	private Text timeIndicator = new Text();

	// gui styles
	private String playSelectedStyle = "";
	private String pauseSelectedStyle = "";
	private String muteSelectedStyle = "";
	private String unmuteSelectedStyle = "";

	public SoundView(Media media, boolean autoPlay, boolean inputIsVisible, List<Integer> inputMarkers) {
		// store input arguments
		this.markers = new ArrayList<Integer>(new LinkedHashSet<>(inputMarkers));
		this.isVisible = inputIsVisible;

		// create JavaFX MediaPlayer
		player = new MediaPlayer(media);
		rootPane.getChildren().add(mediaView);

		// set playPauseButton to match autoplay state
		player.setAutoPlay(autoPlay);
		if (!autoPlay) {
			playPauseButton.setSelected(true);
		}

		// wait til MediaPlayer is ready to collect media duration, and use to setup
		// seeking slider and time indicator
		player.setOnReady(new Runnable() {
			@Override
			public void run() {
				mediaDuration = media.getDuration().toSeconds();
				setupMarkerButtons();

				seekingSlider.setMax(Math.round(mediaDuration));
				seekingSlider.setMajorTickUnit(Math.round(mediaDuration / 2));
				timeIndicator.setText(
						"00:00 / " + (Math.round(mediaDuration) / 60) + ":" + (Math.round(mediaDuration) % 60));
			}
		});

		// update seekingSlider and timeIndicator whenever current time of MediaPlayer
		// changes
		player.currentTimeProperty()
				.addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
					seekingSlider.setValue(newValue.toSeconds());
					timeIndicator.setText((Math.round(newValue.toSeconds()) / 60) + ":"
							+ String.format("%02d", (Math.round(newValue.toSeconds()) % 60)) + " / "
							+ (Math.round(mediaDuration) / 60) + ":" + (Math.round(mediaDuration) % 60));
					;
				});

		// setup volumeSlider
		volumeSlider.setValue(100);
		volumeSlider.setShowTickMarks(true);
		volumeSlider.setShowTickLabels(true);
		volumeSlider.setMajorTickUnit(100);
		volumeSlider.setMinorTickCount(0);

		// setup seekingSlider
		seekingSlider.setShowTickMarks(true);
		seekingSlider.setMinorTickCount(1);
		seekingSlider.setPrefWidth(300);

		seekingPane.getChildren().addAll(seekingSlider);

		// setup HBox to store all GUI elements in, keeping layout consistent
		HBox controls = new HBox();
		controls.setPadding(new Insets(15, 12, 15, 12));
		controls.setSpacing(10);
		controls.getChildren().addAll(playPauseButton, muteButton, volumeSlider, backwardButton, forwardButton,
				seekingPane, timeIndicator);
		visiblePane.getChildren().add(controls);

		// if set visible set rootPane to contain all GUI elements
		if (isVisible) {
			rootPane.getChildren().add(visiblePane);
		}

		// play button listener
		playPauseButton.setOnAction(e -> {
			// set appearance to match settings
			if (playPauseButton.isSelected()) {
				playPauseButton.setText("Play");
				playPauseButton.setStyle(playSelectedStyle);
				player.pause();
			} else {
				playPauseButton.setText("Pause");
				playPauseButton.setStyle(pauseSelectedStyle);
				player.play();
			}
		});

		// mute button listener
		muteButton.setOnAction(e -> {
			// set appearance to match settings
			if (muteButton.isSelected()) {
				muteButton.setText("Unmute");
				muteButton.setStyle(unmuteSelectedStyle);
				player.setMute(true);
			} else {
				muteButton.setText("Mute");
				muteButton.setStyle(muteSelectedStyle);
				player.setMute(false);
			}
		});

		// volume slider listener
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				// set volume to new slider value
				player.setVolume(new_val.doubleValue() / 100);
			}
		});

		// seeking slider listeners
		seekingSlider.setOnMouseDragged((MouseEvent mouseEvent) -> {
			// seek to new slider position
			player.seek(Duration.seconds(seekingSlider.getValue()));
		});
		seekingSlider.setOnMousePressed((MouseEvent mouseEvent) -> {
			// seek to new slider position
			player.seek(Duration.seconds(seekingSlider.getValue()));
		});

		// backward button listener
		backwardButton.setOnAction(e -> {
			// order markers in descending order and jump to first that is behind the
			// current time
			List<Integer> temp = new ArrayList<Integer>(markers);
			Collections.sort(temp, Collections.reverseOrder());
			for (int i = 0; i < temp.size(); i++) {
				if (temp.get(i) <= player.getCurrentTime().toSeconds() - 1) {
					player.seek(Duration.seconds(temp.get(i)));
					return;
				}
			}
		});

		// forward button listener
		forwardButton.setOnAction(e -> {
			// order markers in ascending order and jump to first that is ahead of the
			// current time
			List<Integer> temp = new ArrayList<Integer>(markers);
			Collections.sort(temp);
			for (int i = 0; i < temp.size(); i++) {
				if (temp.get(i) >= player.getCurrentTime().toSeconds()) {
					player.seek(Duration.seconds(temp.get(i)));
					return;
				}
			}
		});

		getChildren().add(rootPane);
	}

	/**
	 * Generate JavaFX buttons used as markers on Seeking slider from markers
	 * arraylist
	 */
	private void setupMarkerButtons() {

		// create marker button for each marker
		for (int i = 0; i < markers.size(); i++) {

			Button markerButton = new Button();
			// calculate button position, apply appearance settings
			double temp = (seekingSlider.getWidth() / mediaDuration) * markers.get(i);
			markerButton.setLayoutX(temp - 3);
			markerButton.setLayoutY(10);
			markerButton.setMinSize(6, 8);
			markerButton.setMaxSize(6, 8);
			markerButton.setStyle("-fx-background-color: " + markerColour + "; -fx-border-color: transparent;");

			// create listener for button, identifies which marker button was pressed and
			// seeks to the time of the matching marker in the marker list
			markerButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					for (int y = 0; y < markerButtons.size(); y++) {
						if (markerButtons.get(y) == e.getSource()) {
							player.seek(Duration.seconds(markers.get(y)));
							return;
						}
					}
				}
			});

			markerButtons.add(markerButton);
			seekingPane.getChildren().add(markerButton);
		}
	}

	/**
	 * Set if the GUI of the media player is visible.
	 * <p>
	 * If false, the Pane of the sound module will be made empty to hide all
	 * elements
	 * 
	 * @param state
	 *            true = GUI is visible, false = GUI is hidden
	 */
	/*
	public void setVisible(boolean state) {
		if (state == isVisible) {
			// cancel if already in correct state
			return;
		} else if (state) {
			isVisible = true;
			rootPane.getChildren().add(visiblePane);
		} else if (!state) {
			isVisible = false;
			rootPane.getChildren().remove(visiblePane);
		}
	}
	*/

	/**
	 * Set CSS style of the Time Indicator
	 * 
	 * @param style
	 *            CSS style of Time Indicator style
	 */
	public void setTimeIndicatorStyle(String style) {
		timeIndicator.setStyle(style);
	}

	/**
	 * Set CSS style of Seeking slider
	 * 
	 * @param style
	 *            CSS string of Seeking slider style
	 */
	public void setSeekingSliderStyle(String style) {
		seekingSlider.setStyle(style);
	}

	/**
	 * Set CSS style of Volume slider
	 * 
	 * @param style
	 *            CSS string of Volume slider style
	 */
	public void setVolumeSliderStyle(String style) {
		volumeSlider.setStyle(style);
	}

	/**
	 * Set CSS style of Forwards marker button
	 * 
	 * @param style
	 *            CSS string of Forwards button style
	 */
	public void setForwardButtonStyle(String style) {
		forwardButton.setStyle(style);
	}

	/**
	 * Set CSS style of Backwards marker button
	 * 
	 * @param style
	 *            CSS string of Backwards button style
	 */
	public void setBackwardButtonStyle(String style) {
		backwardButton.setStyle(style);
	}

	/**
	 * Set CSS style of the Mut/Unmute button
	 * 
	 * @param muteStyle
	 *            CSS string of Mute button style
	 * @param unmuteStyle
	 *            CSS string of Unmute button style
	 */
	public void setMuteButtonStyle(String muteStyle, String unmuteStyle) {
		// store new style settings
		muteSelectedStyle = muteStyle;
		unmuteSelectedStyle = unmuteStyle;

		// apply new style settings
		if (muteButton.isSelected()) {
			muteButton.setStyle(unmuteSelectedStyle);
		} else {
			muteButton.setStyle(muteSelectedStyle);
		}
	}

	/**
	 * Set CSS style of the Play/Pause button
	 * 
	 * @param playStyle
	 *            CSS string of Play button style
	 * @param pauseStyle
	 *            CSS string of Pause button style
	 */
	public void setPlayPauseButtonStyle(String playStyle, String pauseStyle) {
		// store new style settings
		playSelectedStyle = playStyle;
		pauseSelectedStyle = pauseStyle;

		// apply new style settings
		if (playPauseButton.isSelected()) {
			playPauseButton.setStyle(playSelectedStyle);
			playPauseButton.setText("Play");
		} else {
			playPauseButton.setStyle(pauseSelectedStyle);
			playPauseButton.setText("Pause");
		}
	}

	/**
	 * Set CSS style of the sound module Pane
	 * 
	 * @param style
	 *            CSS string of style
	 */
	public void setPaneStyle(String style) {
		visiblePane.setStyle(style);
	}

	/**
	 * Set the list of markers for the media, based on the second that they happen
	 * 
	 * @param inputMarkers
	 *            Seconds where the markers should be placed
	 */
	public void setMarkers(List<Integer> inputMarkers) {
		// store new marker list
		this.markers = new ArrayList<Integer>(new LinkedHashSet<>(inputMarkers));

		// delete and remove all existing marker buttons
		markerButtons = new ArrayList<Button>();
		seekingPane.getChildren().clear();
		seekingPane.getChildren().add(seekingSlider);

		// create new marker buttons
		setupMarkerButtons();
	}

	/**
	 * Get the current list of markers within the sound module
	 * 
	 * @return List of markers within media
	 */
	public List<Integer> getMarkers() {
		return markers;
	}

	/**
	 * Resume playing media
	 */
	public void play() {
		player.play();

		// set playPauseButton to match
		playPauseButton.setSelected(false);
		playPauseButton.setText("Pause");
		playPauseButton.setStyle(pauseSelectedStyle);
	}

	/**
	 * Pause the media player
	 */
	public void pause() {
		player.pause();

		// set playPauseButton to match
		playPauseButton.setSelected(true);
		playPauseButton.setText("Play");
		playPauseButton.setStyle(playSelectedStyle);
	}

	/**
	 * Set whether the media player is muted or not
	 * 
	 * @param state
	 *            true = muted, false = unmuted
	 */
	public void mute(boolean state) {
		player.setMute(state);

		// set muteButton to match
		if (state) {
			muteButton.setSelected(true);
			muteButton.setText("Unmute");
			muteButton.setStyle(unmuteSelectedStyle);
		} else if (!state) {
			muteButton.setSelected(false);
			muteButton.setText("Mute");
			muteButton.setStyle(muteSelectedStyle);
		}
	}

	/**
	 * Set the percentage of the media playback volume
	 * 
	 * @param volume
	 *            Value within range 0-100
	 */
	public void setVolume(double volume) {
		player.setVolume(volume);
	}

	/**
	 * Move to a given time within the media
	 * 
	 * @param time
	 *            Time within media to move to
	 */
	public void seek(Duration time) {
		player.seek(time);
	}

	/**
	 * Returns the Pane containing the sound module JavaFX GUI elements
	 * 
	 * @return Pane containing GUI elements
	 */
	public Pane getPane() {
		return rootPane;
	}

	/**
	 * Set the hex colour of the markers on the seeking slider
	 * 
	 * @param colour
	 *            Hex code of desired colour
	 */
	public void setMarkerColour(String colour) {
		// store new colour
		markerColour = colour;

		// apply new colour to all marker buttons
		for (int i = 0; i < markerButtons.size(); i++) {
			markerButtons.get(i).setStyle("-fx-background-color: " + markerColour + "; -fx-border-color: transparent;");
		}
	}
}
