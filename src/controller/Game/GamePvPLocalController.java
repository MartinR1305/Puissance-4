package controller.Game;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import Serialization.Serialization;
import application.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Grid;
import model.Player;
import model.Results;
import model.ValueSquare;

public class GamePvPLocalController extends GameController implements Initializable {

	private int timeLimit;
	private static Timeline timeline;
	
	private Stage stage;
	private Scene scene;
	private Parent root;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (timeLimit != -1) {
			timeline = new Timeline();
			timeline.setCycleCount(Timeline.INDEFINITE);
		}
	}

	/**
	 * Method that allows to start a PvP Local game
	 * 
	 * @param p1, first player of the game
	 * @param p2, second player of the game
	 */
	public void startGamePvPLocal(Player p1, Player p2, int timeLimit) {

		player1 = p1;
		player2 = p2;
		this.timeLimit = timeLimit;
		grid = new Grid();

		// We put all circles in the matrix
		matrixCircles = new Circle[][] { { c00, c01, c02, c03, c04, c05 }, { c10, c11, c12, c13, c14, c15 },
				{ c20, c21, c22, c23, c24, c25 }, { c30, c31, c32, c33, c34, c35 }, { c40, c41, c42, c43, c44, c45 },
				{ c50, c51, c52, c53, c54, c55 }, { c60, c61, c62, c63, c64, c65 } };

		// We choose randomly the player who will start the game
		Random random = new Random();
		turnPlayer = random.nextInt(2) + 1;

		if (turnPlayer == 1) {
			playerPlaying.setText("It's " + player1.getUserName() + "'s Turn");
		}

		else {
			playerPlaying.setText("It's " + player2.getUserName() + "'s Turn");
		}

		setColorsGrid(grid);

		if (timeLimit != -1) {
			timeline.stop();
			startCountdown(valueTime, timeLimit);
		} else {
			hideCount(true);
		}
	}

	/**
	 * Method for managing the action of clicking on "0" to "6" buttons
	 * 
	 * @param event
	 */
	@FXML
	private void indexColumnSetWithButton(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
		String buttonText = clickedButton.getText();

		// Update the columnAddCoin variable according to the button text
		columnAddCoin = Integer.parseInt(buttonText);

		addCoinGamePvPLocal(columnAddCoin);
	}

	/**
	 * Method that allows to add a coin in the game by a player
	 */
	public void addCoinGamePvPLocal(int columnToAdd) {

		// If the player 1 played
		if (turnPlayer == 1) {
			if (!grid.getGrid().get(columnToAdd).isColumnFull()) {
				grid.addCoinGrid(columnToAdd, ValueSquare.P1);
				setColorsGrid(grid);

				if (grid.isGridFull()) {
					drawGamePvPLocal();
				}

				else if (grid.isJ1win()) {
					setColorsWinningCircles(grid, 1);
					winGamePvPLocal(player1, player2);
				}

				// We continue the game
				else {
					playerPlaying.setText("It's " + player2.getUserName() + "'s Turn");
					turnPlayer++;

					if (timeLimit != -1) {
						timeline.stop();
						startCountdown(valueTime, timeLimit);
					}
				}
			}
		}

		// If the player 2 played
		else {
			if (!grid.getGrid().get(columnToAdd).isColumnFull()) {
				grid.addCoinGrid(columnToAdd, ValueSquare.P2);
				setColorsGrid(grid);

				if (grid.isGridFull()) {
					drawGamePvPLocal();
				}

				else if (grid.isJ2win()) {
					setColorsWinningCircles(grid, 2);
					winGamePvPLocal(player2, player1);
				}

				// We continue the game
				else {
					playerPlaying.setText("It's " + player1.getUserName() + "'s Turn");
					turnPlayer--;

					if (timeLimit != -1) {
						timeline.stop();
						startCountdown(valueTime, timeLimit);
					}
				}
			}
		}
	}

	/**
	 * Method that display a message, set data for a draw and then back to the
	 * previous scene
	 */
	public void drawGamePvPLocal() {
		super.disableAllButtons();

		// Display message
		gameFinish.setText("Game is over ! Nobody won ... It is a draw !");
		gameFinish.setVisible(true);
		playerPlaying.setVisible(false);

		// Add the draw on players's data
		player1.addMatch(player2.getUserName(), Results.DRAW, grid.countCoin());
		player2.addMatch(player1.getUserName(), Results.DRAW, grid.countCoin());

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that display a message, set data for a victory and then back to the
	 * previous scene
	 * 
	 * @param playerWin,   player who won the game
	 * @param playerLoose, player who lost the game
	 */
	public void winGamePvPLocal(Player playerWin, Player playerLoose) {
		super.disableAllButtons();

		// Display message
		gameFinish.setText("Game is over .. " + playerWin.getUserName() + " won the game !");
		gameFinish.setVisible(true);
		playerPlaying.setVisible(false);

		// Add the draw on players's data
		playerWin.addMatch(playerLoose.getUserName(), Results.VICTORY, grid.countCoin());
		playerLoose.addMatch(playerWin.getUserName(), Results.DEFEAT, grid.countCoin());

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that allows players to do an other game with same settings
	 */
	public void playAgain() {
		startGamePvPLocal(player1, player2, timeLimit);
		enableAllButtons();
		gameFinish.setVisible(false);
		playerPlaying.setVisible(true);
	}

	/**
	 * Method that allows to start a count down for a player's turn
	 * 
	 * @param label
	 * @param timeLimit
	 */
	public void startCountdown(Label label, int timeLimit) {
		// Using an array to store the value of seconds effectively final
		final int[] seconds = { timeLimit };

		// Clear previous key frames
		timeline.getKeyFrames().clear();

		// Creating a key frame that will trigger every second
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Update the label text with the current value of seconds
				label.setText(Integer.toString(seconds[0]) + " s");

				// Decrement seconds
				seconds[0]--;

				// Check if the count down is complete
				if (seconds[0] == 0) {
					// Stop the time line when the count down is complete
					timeline.stop();

					// We check that's the column is not full
					do {
						if (timeLimit != 1) {
							Random random = new Random();
							columnAddCoin = random.nextInt(6);
						}
					} while (grid.getGrid().get(columnAddCoin).isColumnFull());

					addCoinGamePvPLocal(columnAddCoin);
				}

				else if (seconds[0] > 5) {
					label.setStyle("-fx-text-fill: white");
				}

				else if (seconds[0] < 5) {
					label.setStyle("-fx-text-fill: red");
				}
			}
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
	
	/**
	 * Method that allows to switch the scene to the Home after stop the timer
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void switchToHomeBis(ActionEvent event) throws IOException {
		timeline.stop();
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "Home.fxml"));
		
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		setCenterStage(stage);
	}
}