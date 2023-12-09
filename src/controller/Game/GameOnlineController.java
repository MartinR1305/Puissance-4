package controller.Game;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Serialization.Serialization;
import application.ClientTCP;
import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import model.Algorithm;
import model.Grid;
import model.Player;
import model.Results;
import model.ValueSquare;

public class GameOnlineController extends GameController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	private boolean isAlgoPlaying;
	private boolean isPlayerPlaying;
	private Algorithm algo;

	private ClientTCP clientTCP;
	private static boolean areTwoPlayersConnected, isConnected, isPlaying, isGameFinished, isWonTheGame, isDraw;

	private static ValueSquare numPlayer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// We actualize the client attribute with the one of the main
		setClientTCP(Main.getClientTCP());

		// Initialization of attributes
		areTwoPlayersConnected = true;
		isConnected = true;
		isGameFinished = false;
		isWonTheGame = false;
		isDraw = false;

		// Starting of thread methods
		this.backToHome();
		this.updateFinishLabel(playerPlaying, gameFinish);
		this.updateIsPlaying(playerPlaying);
	}

	/**
	 * Method that allows to start a PvP Local game
	 * 
	 * @param p1, first player of the game
	 * @param p2, second player of the game
	 */
	public void startGamePlayer(Player p) {
		// We set some attributes
		player1 = p;
		player2 = null;
		grid = new Grid();
		isPlayerPlaying = true;

		// We put all circles in the matrix
		matrixCircles = new Circle[][] { { c00, c01, c02, c03, c04, c05 }, { c10, c11, c12, c13, c14, c15 },
				{ c20, c21, c22, c23, c24, c25 }, { c30, c31, c32, c33, c34, c35 }, { c40, c41, c42, c43, c44, c45 },
				{ c50, c51, c52, c53, c54, c55 }, { c60, c61, c62, c63, c64, c65 } };

		setColorsGrid(grid);
	}

	public void startGameAlgorithm(int level, boolean isStarting) {
		// We set some attributes
		player1 = null;
		player2 = null;
		grid = new Grid();
		isPlayerPlaying = false;
		isAlgoPlaying = true;
		disableAllButtons();

		// We put all circles in the matrix
		matrixCircles = new Circle[][] { { c00, c01, c02, c03, c04, c05 }, { c10, c11, c12, c13, c14, c15 },
				{ c20, c21, c22, c23, c24, c25 }, { c30, c31, c32, c33, c34, c35 }, { c40, c41, c42, c43, c44, c45 },
				{ c50, c51, c52, c53, c54, c55 }, { c60, c61, c62, c63, c64, c65 } };

		setColorsGrid(grid);

		if (isStarting) {
			numPlayer = ValueSquare.P1;
			isPlaying = true;

			algo = new Algorithm(level, ValueSquare.P2, ValueSquare.P1, 2, 3, 10000);
			grid.addCoinGrid(algo.algoMinMax(grid), ValueSquare.P2);

			setColorsGrid(grid);
			isPlaying = false;

		} else {
			numPlayer = ValueSquare.P2;
			algo = new Algorithm(level, ValueSquare.P1, ValueSquare.P2, 2, 3, 10000);
		}
	}

	/**
	 * Setter of clientTCP -> use for associating application and ClientTCP
	 * 
	 * @param clientTCP
	 */
	public void setClientTCP(ClientTCP clientTCP) {
		this.clientTCP = clientTCP;
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

		// We add the coin in question
		addCoinGamePvPOnline();
	}

	/**
	 * Method that allows to add a coin in the game for the player
	 */
	public void addCoinGamePvPOnline() {

		// We check if the column clicked is not full
		if (!grid.getGrid().get(columnAddCoin).isColumnFull()) {

			// We add the coin
			grid.addCoinGrid(algo.algoMinMax(grid), numPlayer);

			// Send the column played to the server
			clientTCP.getWriter().println(columnAddCoin);

			setColorsGrid(grid);

			// If the player is P1
			if (numPlayer.equals(ValueSquare.P1)) {

				// We check if the grid is full
				if (grid.isGridFull()) {
					// We configure the controller for a tie game
					isGameFinished = true;
					isDraw = true;
				}

				// If the player won the game
				else if (grid.isJ1win()) {
					// We configure the controller for a winning game
					setColorsWinningCircles(grid, 1);
					isGameFinished = true;
					isWonTheGame = true;
				}
			}

			// If the player is P2
			else if (numPlayer.equals(ValueSquare.P2)) {

				// We check if the grid is full
				if (grid.isGridFull()) {
					// We configure the controller for a tie game
					isGameFinished = true;
					isDraw = true;
				}

				// If the player won the game
				else if (grid.isJ2win()) {
					// We configure the controller for a winning game
					setColorsWinningCircles(grid, 2);
					isGameFinished = true;
					isWonTheGame = true;
				}
			}
			// We give the turn to the other player
			isPlaying = false;
		}
	}

	/**
	 * Method that allows to add a coin in the game for other player
	 * 
	 * @param nbColumn
	 */
	public void otherPlayerPlayed(String nbColumn) {

		// Allocation of attributes
		C0 = new Button();
		C1 = new Button();
		C2 = new Button();
		C3 = new Button();
		C4 = new Button();
		C5 = new Button();
		C6 = new Button();
		gameFinish = new Label();
		playerPlaying = new Label();

		// We configure the updating of the label turn player
		Main.getClientTCP().getGameController().updateIsPlaying(playerPlaying);

		System.out.println(numPlayer);

		// If the other player is P1
		if (numPlayer.equals(ValueSquare.P1)) {

			addCoinAndCheckGrid(Integer.valueOf(nbColumn), ValueSquare.P2);
		}

		// If the other player is P2
		else if (numPlayer.equals(ValueSquare.P2)) {

			addCoinAndCheckGrid(Integer.valueOf(nbColumn), ValueSquare.P1);
		}

		setColorsGrid(grid);

		if (!isGameFinished) {
			// We give the turn to the player
			isPlaying = true;
		}

		if (isAlgoPlaying && !isGameFinished) {
			// We add the coin
			int columnAlgo = algo.algoMinMax(grid);
			grid.addCoinGrid(columnAlgo, numPlayer);

			// Send the column played to the server
			clientTCP.getWriter().println(columnAlgo);

			setColorsGrid(grid);

			// If the player is P1
			if (numPlayer.equals(ValueSquare.P1)) {

				// We check if the grid is full
				if (grid.isGridFull()) {
					// We configure the controller for a tie game
					isGameFinished = true;
					isDraw = true;
					drawGamePvPOnline();
				}

				// If the player won the game
				else if (grid.isJ1win()) {
					// We configure the controller for a winning game
					setColorsWinningCircles(grid, 1);
					isGameFinished = true;
					isWonTheGame = true;
					winGamePvPOnline();
				}
			}

			// If the player is P2
			else if (numPlayer.equals(ValueSquare.P2)) {

				// We check if the grid is full
				if (grid.isGridFull()) {
					// We configure the controller for a tie game
					isGameFinished = true;
					isDraw = true;
					drawGamePvPOnline();
				}

				// If the player won the game
				else if (grid.isJ2win()) {
					// We configure the controller for a winning game
					setColorsWinningCircles(grid, 2);
					isGameFinished = true;
					isWonTheGame = true;
					winGamePvPOnline();
				}
			}
		}
	}

	public void addCoinAndCheckGrid(int column, ValueSquare valueSquare) {
		// We add the coin
		grid.addCoinGrid(column, valueSquare);

		// We check if the grid is full
		if (grid.isGridFull()) {
			// We configure the controller for a tie game
			isGameFinished = true;
			isDraw = true;

			if (isPlayerPlaying) {
				drawGamePvPOnline();
			}
		}

		// We check if the other player won the game
		else if (grid.isJ2win()) {
			// We configure the controller for a defeat game
			setColorsWinningCircles(grid, 2);
			isGameFinished = true;
			isWonTheGame = false;

			if (isPlayerPlaying) {
				defeatGamePvPOnline();
			}
		}
	}

	/**
	 * Method that display a message, set data for a victory
	 * 
	 * @param playerWin,   player who won the game
	 * @param playerLoose, player who lost the game
	 */
	public void winGamePvPOnline() {
		super.disableAllButtons();

		// Add the draw on players's data
		player1.addMatch("Player Online", Results.VICTORY);

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that display a message, set data for a draw
	 */
	public void drawGamePvPOnline() {
		super.disableAllButtons();

		// Add the draw on players's data
		player1.addMatch("Player Online", Results.DRAW);

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that display a message, set data for a defeat
	 */
	public void defeatGamePvPOnline() {
		super.disableAllButtons();

		// Add the draw on players's data
		player1.addMatch("Player Online", Results.DEFEAT);

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that allows to actualize the boolean for know if two players are
	 * connected or not
	 * 
	 * @param state
	 */
	public void actualize2PlayersBoolean(Boolean is2ndClientConnected) {
		if (is2ndClientConnected.equals(true)) {
			areTwoPlayersConnected = true;
		}

		else if (is2ndClientConnected.equals(false)) {
			areTwoPlayersConnected = false;

			// We disconnect the application from the server via give him a null socket
			clientTCP.changeIP_Port("", "0");
		}
	}

	/**
	 * Method that allows to actualize the boolean for know if the player starts and
	 * assign the number of player
	 * 
	 * @param state
	 */
	public void actualizePlayerStarting(Boolean isPlayerPlaying) {

		// The player will start the game
		if (isPlayerPlaying.equals(true)) {
			isPlaying = true;
			numPlayer = ValueSquare.P1;
		}
		// The player won't start the game
		else if (isPlayerPlaying.equals(false)) {
			isPlaying = false;
			numPlayer = ValueSquare.P2;
		}
	}

	/**
	 * Method that allows to update the label for know if the player's turn
	 */
	public void updateIsPlaying(Label playerPlaying) {
		Thread thread = new Thread(() -> {

			// We do the thread until the game is finished
			while (!isGameFinished) {
				try {
					Thread.sleep(500);
					Platform.runLater(() -> {

						// We check if it's the player's turn
						if (isPlaying) {
							// We configure the text of the turn
							playerPlaying.setText("Your Turn !");

							// We able all buttons in order that the player can play
							if (isPlayerPlaying) {
								enableAllButtons();
							}
						}

						// If it's not the player's turn
						else if (!isPlaying) {
							// We configure the text of the turn
							playerPlaying.setText("Opponent Turn !");

							// We able all buttons in order that the player can not play
							disableAllButtons();
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * Method that allows to update the labels at the end of the game
	 */
	public void updateFinishLabel(Label playerPlaying, Label gameFinish) {
		Thread thread = new Thread(() -> {

			// We do the thread until the player is connected
			while (isConnected) {
				try {
					Thread.sleep(500);
					Platform.runLater(() -> {

						// If the game is the a draw
						if (isDraw) {
							// We configure the labels for a draw game
							gameFinish.setText("Game is over ! Nobody won ... It is a draw !");
							gameFinish.setVisible(true);
							playerPlaying.setVisible(false);
						}

						// If it's not ( Defeat or Victory or game not finished yet )
						else if (!isDraw) {

							// If it's a winning game
							if (isWonTheGame && isGameFinished) {
								// We configure the labels for a winning game
								gameFinish.setText("You won the game !");
								gameFinish.setVisible(true);
								playerPlaying.setVisible(false);

							}

							// If it's a defeat game
							else if (!isWonTheGame && isGameFinished) {
								// We configure the labels for a defeat game
								gameFinish.setText("You lost the game !");
								gameFinish.setVisible(true);
								playerPlaying.setVisible(false);
							}
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * Method that allows to switch to the game if two players are connected to the
	 * server
	 */
	public void backToHome() {
		Thread thread = new Thread(() -> {

			// We do the thread until the two players are connected
			while (areTwoPlayersConnected) {
				try {
					Thread.sleep(500);
					Platform.runLater(() -> {

						// If the other left the game
						if (!areTwoPlayersConnected && isConnected) {

							// We return the player to home
							FXMLLoader loader = new FXMLLoader(getClass().getResource(".." + File.separator + ".."
									+ File.separator + "view" + File.separator + "Home.fxml"));
							try {
								root = loader.load();
							} catch (IOException e) {
								e.printStackTrace();
							}
							stage = (Stage) (yes.getScene().getWindow());
							scene = new Scene(root);
							stage.setScene(scene);
							stage.show();
							setCenterStage(stage);

							// The player is therefore disconnected
							isConnected = false;
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * Method that allows to switch the scene to the Home with the disconnection
	 * between the player and the server
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void switchToHomeWithDecoServer(ActionEvent event) throws IOException {
		// We actualize booleans
		isConnected = false;
		areTwoPlayersConnected = false;

		// We disconnect the client from the server
		clientTCP.changeIP_Port("", "0");

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