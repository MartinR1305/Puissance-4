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
import javafx.application.Platform;
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
import model.Algorithm;
import model.Grid;
import model.Player;
import model.Results;
import model.ValueSquare;

public class GamePvALocalController extends GameController implements Initializable {

	Algorithm algo;
	private int lvlAlgo;
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
	 * Method that allows to start a PvA Local game
	 * 
	 * @param p1, player of the game
	 */
	public void startGamePvALocal(Player p, int level, int timeLimit) {

		player1 = p;
		player2 = null;
		lvlAlgo = level;
		this.timeLimit = timeLimit;
		algo = new Algorithm(level, ValueSquare.P1, ValueSquare.P2, 2, 3, 10000);
		grid = new Grid();

		// We put all circles in the matrix
		matrixCircles = new Circle[][] { { c00, c01, c02, c03, c04, c05 }, { c10, c11, c12, c13, c14, c15 },
				{ c20, c21, c22, c23, c24, c25 }, { c30, c31, c32, c33, c34, c35 }, { c40, c41, c42, c43, c44, c45 },
				{ c50, c51, c52, c53, c54, c55 }, { c60, c61, c62, c63, c64, c65 } };

		// We choose randomly the player who will start the game
		Random random = new Random();
		turnPlayer = random.nextInt(2) + 1;

		if (turnPlayer == 1) {
			playerPlaying.setText("It's Your Turn !");

			if (timeLimit != -1) {
				hideCount(false);
				timeline.stop();
				startCountdown(valueTime, timeLimit);
			} else {
				hideCount(true);
			}
		}

		else {
			playerPlaying.setText("The AI is thinking about a move !");
			hideCount(true);

			// We use Platform.runLater in order that the javaFX interface do not freeze
			// when we need to modify something on it
			Platform.runLater(() -> {
				long tempsDebut = System.currentTimeMillis();

				grid.addCoinGrid(algo.algoMinMax(grid), ValueSquare.P2);

				long tempsFin = System.currentTimeMillis();
				long dureeTotaleMillis = tempsFin - tempsDebut;
				double dureeSecondes = (dureeTotaleMillis / 1000.0) - 1;
				long minutes = (long) dureeSecondes / 60;
				double secondes = dureeSecondes % 60;

				System.out.printf("Algorithme took %d min %.3f sec to find the best move.%n", minutes, secondes);
				playerPlaying.setText("It's Your Turn !");

				hideCount(false);
				startCountdown(valueTime, timeLimit);
				setColorsGrid(grid);
			});
		}
		setColorsGrid(grid);
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

		addCoinGamePvALocal(columnAddCoin);

	}

	/**
	 * Method that allows to add a coin in the game by a player
	 */
	public void addCoinGamePvALocal(int columnToAdd) {

		// Player Turn
		if (!grid.getGrid().get(columnToAdd).isColumnFull()) {

			grid.addCoinGrid(columnToAdd, ValueSquare.P1);
			setColorsGrid(grid);

			if (grid.isGridFull()) {
				drawGamePvALocal();

			} else if (grid.isJ1win()) {
				setColorsWinningCircles(grid, 1);
				winGamePvALocal(player1);

			} else {
				// We continue the game & We start a thread for the algo's turn
				new Thread(() -> algorithmTurn()).start();
			}
		}
	}

	public void algorithmTurn() {

		// We use Platform.runLater in order that the javaFX interface do not freeze
		// when we need to modify something on it
		Platform.runLater(() -> {
			timeline.stop();
			hideCount(true);

			disableAllButtons();
			playerPlaying.setText("The AI is thinking about a move !");
		});

		// Algorithm turn
		long tempsDebut = System.currentTimeMillis();

		grid.addCoinGrid(algo.algoMinMax(grid), ValueSquare.P2);

		long tempsFin = System.currentTimeMillis();
		long dureeTotaleMillis = tempsFin - tempsDebut;
		double dureeSecondes = (dureeTotaleMillis / 1000.0) - 1;
		long minutes = (long) dureeSecondes / 60;
		double secondes = dureeSecondes % 60;

		System.out.printf("Algorithme took %d min %.3f sec to find the best move.%n", minutes, secondes);

		// We use Platform.runLater in order that the javaFX interface do not freeze
		// when we need to modify something on it
		Platform.runLater(() -> {
			setColorsGrid(grid);
			if (grid.isGridFull()) {
				drawGamePvALocal();

			} else if (grid.isJ2win()) {
				// We use Platform.runLater in order that the javaFX interface do not freeze
				// when we need to modify something on it
				Platform.runLater(() -> setColorsWinningCircles(grid, 2));

				looseGamePvALocal(player1);

			} else {
				// We continue the game
				playerPlaying.setText("Its Your Turn !");
				if (timeLimit != -1) {
					hideCount(false);
					valueTime.setText("");
					startCountdown(valueTime, timeLimit);
				}
				// We use Platform.runLater in order that the javaFX interface do not freeze
				// when we need to modify something on it
				Platform.runLater(() -> enableAllButtons());
			}
		});
	}

	/**
	 * Method that display a message, set data for a draw and then back to the
	 * previous scene
	 */
	public void drawGamePvALocal() {
		disableAllButtons();

		// Display message
		gameFinish.setText("Game is over ! Nobody won ... It is a draw !");
		gameFinish.setVisible(true);
		playerPlaying.setVisible(false);

		// Add the draw on players's data
		player1.addMatch(player2.getUserName(), Results.DRAW, grid.countCoin());

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that display a message, set data for a victory and then back to the
	 * previous scene
	 * 
	 * @param playerWin, player who won the game
	 */
	public void winGamePvALocal(Player playerWin) {
		disableAllButtons();

		// Display message
		gameFinish.setText("Game is over .. You won the game !");
		gameFinish.setVisible(true);
		playerPlaying.setVisible(false);

		// Add the draw on players's data
		playerWin.addMatch("Algorithm", Results.VICTORY, grid.countCoin());

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that display a message, set data for a defeat and then back to the
	 * previous scene
	 * 
	 * @param playerLoose, player who the lost the game
	 */
	public void looseGamePvALocal(Player playerLoose) {
		disableAllButtons();

		// Display message
		gameFinish.setText("Game is over .. You lost the game !");
		gameFinish.setVisible(true);
		playerPlaying.setVisible(false);

		// Add the draw on players's data
		playerLoose.addMatch("Algorithm", Results.DEFEAT, grid.countCoin());

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that allows players to do an other game with same settings
	 */
	public void playAgain() {
		startGamePvALocal(player1, lvlAlgo, timeLimit);
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
				if (seconds[0] == -2) {
					// Stop the time line when the count down is complete
					timeline.stop();

					// We check that's the column is not full
					do {
						if (timeLimit != 1) {
							Random random = new Random();
							columnAddCoin = random.nextInt(6);
						}
					} while (grid.getGrid().get(columnAddCoin).isColumnFull());

					new Thread(() -> addCoinGamePvALocal(columnAddCoin)).start();

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
		FXMLLoader loader = new FXMLLoader(getClass()
				.getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "Home.fxml"));

		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		setCenterStage(stage);
	}
}
