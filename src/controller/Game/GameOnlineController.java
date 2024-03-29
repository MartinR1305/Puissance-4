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
	private boolean isPlayerPlaying;

	private static boolean isAlgoPlaying;
	private static Algorithm algo;
	private static ValueSquare numPlayer;

	private ClientTCP clientTCP;
	private static boolean areTwoPlayersConnected, isConnected, isPlaying, isGameFinished, isWonTheGame, isDraw;

	/**
	 * Method that will be called when the FXML file is opened
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// We actualize the client attribute with the one of the main
		setClientTCP(Main.getClientTCP());

		// Initialization of booleans
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
	 * Setter of clientTCP
	 * 
	 * @param clientTCP : The new value for the clientTCP
	 */
	public void setClientTCP(ClientTCP clientTCP) {
		this.clientTCP = clientTCP;
	}

	/**
	 * Method that allows to start a game with a player playing
	 * 
	 * @param p : Player of the game
	 */
	public void startGamePlayer(Player p) {
		// Initialization of attributes
		player1 = p;
		player2 = null;
		grid = new Grid();
		isPlayerPlaying = true;
		isAlgoPlaying = false;

		// We put all circles in the matrix
		matrixCircles = new Circle[][] { { c00, c01, c02, c03, c04, c05 }, { c10, c11, c12, c13, c14, c15 },
				{ c20, c21, c22, c23, c24, c25 }, { c30, c31, c32, c33, c34, c35 }, { c40, c41, c42, c43, c44, c45 },
				{ c50, c51, c52, c53, c54, c55 }, { c60, c61, c62, c63, c64, c65 } };

		setColorsGrid(grid);
	}

	/**
	 * Method that allows to start a game with a algorithm playing
	 * 
	 * @param level      : Level of the algorithm
	 * @param isStarting : If the algorithm starts or no the game
	 */
	public void startGameAlgorithm(int level, boolean isStarting) {
		// Initialization of attributes
		player1 = null;
		player2 = null;
		grid = new Grid();
		isPlayerPlaying = false;
		isAlgoPlaying = true;

		// A algorithm will play so the player can not add a coin so we disable all
		// buttons
		disableAllButtons();

		// We put all circles in the matrix
		matrixCircles = new Circle[][] { { c00, c01, c02, c03, c04, c05 }, { c10, c11, c12, c13, c14, c15 },
				{ c20, c21, c22, c23, c24, c25 }, { c30, c31, c32, c33, c34, c35 }, { c40, c41, c42, c43, c44, c45 },
				{ c50, c51, c52, c53, c54, c55 }, { c60, c61, c62, c63, c64, c65 } };

		setColorsGrid(grid);

		// If the algorithm starts the game
		if (isStarting) {
			// Initialization of others attributes
			numPlayer = ValueSquare.P1;
			algo = new Algorithm(level, ValueSquare.P2, ValueSquare.P1, 2, 3, 10000);

			isPlaying = true;

			// Algorithm is searching the best column to play
			int columnAlgo = algo.algoMinMax(grid, true, true);
			grid.addCoinGrid(columnAlgo, ValueSquare.P1);

			isPlaying = false;

			// Send the column played to the server
			clientTCP.getWriter().println(columnAlgo);

			setColorsGrid(grid);

		} else {
			// Initialization of others attributes
			numPlayer = ValueSquare.P2;
			algo = new Algorithm(level, ValueSquare.P1, ValueSquare.P2, 2, 3, 10000);
		}
	}

	/**
	 * Method for managing the action of clicking on "0" to "6" buttons
	 * 
	 * @param event : The event that will activate the action
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
			grid.addCoinGrid(columnAddCoin, numPlayer);

			// Send the column played to the server
			clientTCP.getWriter().println(columnAddCoin);

			setColorsGrid(grid);

			// If the player is P1
			if (numPlayer.equals(ValueSquare.P1)) {
				checkingWinGame(1);
			}

			// If the player is P2
			else if (numPlayer.equals(ValueSquare.P2)) {
				checkingWinGame(2);
			}
			checkingDrawGame();

			// We give the turn to the other player
			isPlaying = false;
		}
	}

	/**
	 * Method that allows to add a coin in the game for other player
	 * 
	 * @param nbColumn : The column where the other player played
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

		// If the other player is P1
		if (numPlayer.equals(ValueSquare.P1)) {
			grid.addCoinGrid(Integer.valueOf(nbColumn), ValueSquare.P2);
			checkingDefeatGame(2);
		}

		// If the other player is P2
		else if (numPlayer.equals(ValueSquare.P2)) {
			grid.addCoinGrid(Integer.valueOf(nbColumn), ValueSquare.P1);
			checkingDefeatGame(1);
		}

		checkingDrawGame();
		setColorsGrid(grid);

		if (!isGameFinished) {
			// We give the turn to the player
			isPlaying = true;
		}

		if (isAlgoPlaying && isPlaying) {
			// We add the coin
			int columnAlgo = algo.algoMinMax(grid, true, true);
			grid.addCoinGrid(columnAlgo, numPlayer);

			// Send the column played to the server
			clientTCP.getWriter().println(columnAlgo);

			setColorsGrid(grid);

			// If the player is P1
			if (numPlayer.equals(ValueSquare.P1)) {
				checkingWinGame(1);
			}

			// If the player is P2
			else if (numPlayer.equals(ValueSquare.P2)) {

				checkingWinGame(2);
			}
			checkingDrawGame();

			// We give the turn to the other algorithm
			isPlaying = false;
		}
	}

	/**
	 * Method that display a message, set data for a victory
	 * 
	 * @param playerWin   : Player who won the game
	 * @param playerLoose : Player who lost the game
	 */
	public void winGameOnline() {
		super.disableAllButtons();

		// Add the draw on players's data
		player1.addMatch("Player Online", Results.VICTORY, grid.countCoin());

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that display a message, set data for a draw
	 */
	public void drawGameOnline() {
		super.disableAllButtons();

		// Add the draw on players's data
		player1.addMatch("Player Online", Results.DRAW, grid.countCoin());

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that display a message, set data for a defeat
	 */
	public void defeatGameOnline() {
		super.disableAllButtons();

		// Add the draw on players's data
		player1.addMatch("Player Online", Results.DEFEAT, grid.countCoin());

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that allows to actualize the boolean for know if two players are
	 * connected or not
	 * 
	 * @param is2ndClientConnected : The new value of is2ndClientConnected
	 */
	public void actualize2PlayersBoolean(Boolean is2ndClientConnected) {
		if (is2ndClientConnected.equals(true)) {
			areTwoPlayersConnected = true;
		} else if (is2ndClientConnected.equals(false)) {
			areTwoPlayersConnected = false;
			// We disconnect the application from the server via give him a null socket
			clientTCP.changeIP_Port("", "0");
		}
	}

	/**
	 * Method that allows to actualize the boolean for know if the player starts and
	 * assign the number of player
	 * 
	 * @param isPlayerPlaying : The new value of isPlayerPlaying
	 */
	public void actualizePlayerStarting(Boolean isPlayerPlaying) {
		// The player will start the game
		if (isPlayerPlaying.equals(true)) {
			isPlaying = true;
			numPlayer = ValueSquare.P1;
		} else {
			isPlaying = false;
			numPlayer = ValueSquare.P2;
		}
	}

	/**
	 * Method that allows to update the label for know if the player's turn
	 * 
	 * @param playerPlaying : Label that we want to update
	 */
	public void updateIsPlaying(Label playerPlaying) {
		Thread thread = new Thread(() -> {

			// We do the thread until the game is finished
			while (!isGameFinished) {
				try {
					Thread.sleep(100);
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
	 * 
	 * @param playerPlaying : Label for the player playing
	 * @param gameFinish    : Label for the finish text
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
	 * @param event : The event that will activate the action
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

	/**
	 * Method that allows to check if the player won the game or not and set up it
	 * if it's the case
	 * 
	 * @param playerToCheck : Player that we want to check if he won the game
	 */
	public void checkingWinGame(int playerToCheck) {
		if (playerToCheck == 1) {
			if (grid.isJ1win()) {
				// We configure the controller for a winning game
				setColorsWinningCircles(grid, playerToCheck);
				isGameFinished = true;
				isWonTheGame = true;

				if (isPlayerPlaying) {
					winGameOnline();
				}
			}
		} else {
			if (grid.isJ2win()) {
				// We configure the controller for a winning game
				setColorsWinningCircles(grid, playerToCheck);
				isGameFinished = true;
				isWonTheGame = true;

				if (isPlayerPlaying) {
					winGameOnline();
				}
			}
		}
	}

	/**
	 * Method that allows to check if it's a draw or not and set up it if it's the
	 * case
	 *
	 */
	public void checkingDrawGame() {
		// We check if the grid is full
		if (grid.isGridFull()) {
			// We configure the controller for a tie game
			isGameFinished = true;
			isDraw = true;

			if (isPlayerPlaying) {
				drawGameOnline();
			}
		}
	}

	/**
	 * Method that allows to check if the player lost the game or not and set up it
	 * if it's the case
	 * 
	 * @param playerToCheck : Player that we want to check if he lost the game
	 */
	public void checkingDefeatGame(int playerToCheck) {
		if (playerToCheck == 1) {
			// We check if the other player won the game
			if (grid.isJ1win()) {
				// We configure the controller for a defeat game
				setColorsWinningCircles(grid, 1);
				isGameFinished = true;
				isWonTheGame = false;

				if (isPlayerPlaying) {
					defeatGameOnline();
				}
			}
		} else {
			// We check if the other player won the game
			if (grid.isJ2win()) {
				// We configure the controller for a defeat game
				setColorsWinningCircles(grid, 2);
				isGameFinished = true;
				isWonTheGame = false;

				if (isPlayerPlaying) {
					defeatGameOnline();
				}
			}
		}
	}
}