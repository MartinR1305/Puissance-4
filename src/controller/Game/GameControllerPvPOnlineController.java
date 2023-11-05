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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Grid;
import model.Player;
import model.Results;
import model.ValueSquare;

public class GameControllerPvPOnlineController extends GameController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	private ClientTCP clientTCP;
	private static boolean areTwoPlayersConnected, isConnected, isPlaying, isGameFinished;

	private static ValueSquare numPlayer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// We actualize the client attribute with the one of the main
		setClientTCP(Main.getClientTCP());

		areTwoPlayersConnected = true;
		isConnected = true;
		isGameFinished = false;

		this.backToHome();
		this.updateIsPlaying();
	}

	/**
	 * Method that allows to start a PvP Local game
	 * 
	 * @param p1, first player of the game
	 * @param p2, second player of the game
	 */
	public void startGamePvPOnline(Player p) {
		player1 = p;
		player2 = null;
		grid = new Grid();

		// We put all circles in the matrix
		matrixCircles = new Circle[][] { { c00, c01, c02, c03, c04, c05 }, { c10, c11, c12, c13, c14, c15 },
				{ c20, c21, c22, c23, c24, c25 }, { c30, c31, c32, c33, c34, c35 }, { c40, c41, c42, c43, c44, c45 },
				{ c50, c51, c52, c53, c54, c55 }, { c60, c61, c62, c63, c64, c65 } };

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

		addCoinGamePvPLocal();
	}

	/**
	 * Method that allows to add a coin in the game for the player
	 */
	public void addCoinGamePvPLocal() {

		if (!grid.getGrid().get(columnAddCoin).isColumnFull()) {
			grid.addCoinGrid(columnAddCoin, numPlayer);

			// Send the column played to the server
			clientTCP.getWriter().println(columnAddCoin);

			setColorsGrid(grid);

			if (numPlayer.equals(ValueSquare.P1)) {
				if (grid.isGridFull()) {
					drawGamePvPOnline();
					isGameFinished = true;
				}

				else if (grid.isJ1win()) {
					setColorsWinningCircles(grid, 1);
					winGamePvPOnline();
					isGameFinished = true;
				}
			}

			else if (numPlayer.equals(ValueSquare.P2)) {
				if (grid.isGridFull()) {
					drawGamePvPOnline();
					isGameFinished = true;
				}

				else if (grid.isJ2win()) {
					setColorsWinningCircles(grid, 2);
					winGamePvPOnline();
					isGameFinished = true;
				}
			}
			isPlaying = false;
		}
	}

	/**
	 * Method that allows to add a coin in the game for other player
	 * 
	 * @param nbColumn
	 */
	public void otherPlayerPlayed(String nbColumn) {

		if (numPlayer.equals(ValueSquare.P1)) {
			grid.addCoinGrid(Integer.valueOf(nbColumn), ValueSquare.P2);
		}

		else if (numPlayer.equals(ValueSquare.P2)) {
			grid.addCoinGrid(Integer.valueOf(nbColumn), ValueSquare.P1);
		}

		setColorsGrid(grid);

		if (numPlayer.equals(ValueSquare.P1)) {
			if (grid.isGridFull()) {
				drawGamePvPOnline();
				isGameFinished = true;
			}

			else if (grid.isJ2win()) {
				setColorsWinningCircles(grid, 2);
				defeatGamePvPOnline();
				isGameFinished = true;
			}
		}

		else if (numPlayer.equals(ValueSquare.P2)) {
			if (grid.isGridFull()) {
				drawGamePvPOnline();
				isGameFinished = true;
			}

			else if (grid.isJ1win()) {
				setColorsWinningCircles(grid, 1);
				defeatGamePvPOnline();
				isGameFinished = true;
			}
		}
		
		if (!isGameFinished) {
			isPlaying = true;
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

		// Display message
		gameFinish.setText("You won the game !");
		gameFinish.setVisible(true);
		playerPlaying.setVisible(false);

		// Add the draw on players's data
		player1.addMatch("Player Online", Results.VICTORY);

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that display a message, set data for a draw
	 */
	public void drawGamePvPOnline() {
		//super.disableAllButtons();

//		// Display message
//		gameFinish.setText("Game is over ! Nobody won ... It is a draw !");
//		gameFinish.setVisible(true);
//		playerPlaying.setVisible(false);
//
//		// Add the draw on players's data
//		player1.addMatch("Player Online", Results.DRAW);
//
//		// We serialize
//		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that display a message, set data for a defeat
	 */
	public void defeatGamePvPOnline() {
		//super.disableAllButtons();

//		// Display message
//		gameFinish.setText("You lost the game !");
//		gameFinish.setVisible(true);
//		playerPlaying.setVisible(false);
//
//		// Add the draw on players's data
//		player1.addMatch("Player Online", Results.DEFEAT);
//
//		// We serialize
//		Serialization.serializePlayer(Main.getPlayersData().getValue());
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
		if (isPlayerPlaying.equals(true)) {
			isPlaying = true;
			numPlayer = ValueSquare.P1;
		}

		else if (isPlayerPlaying.equals(false)) {
			isPlaying = false;
			numPlayer = ValueSquare.P2;
		}
	}

	/**
	 * Method that allows to update the boolean for know if the player's turn server
	 */
	public void updateIsPlaying() {
		Thread thread = new Thread(() -> {
			while (isConnected) {
				try {
					Thread.sleep(500);
					Platform.runLater(() -> {
						if (isPlaying) {
							playerPlaying.setText("Your Turn !");
							ableAllButtons();
						} else if (!isPlaying) {
							playerPlaying.setText("Opponent Turn !");
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
	 * Method that allows to switch to the game if two players are connected to the
	 * server
	 */
	public void backToHome() {
		Thread thread = new Thread(() -> {
			while (areTwoPlayersConnected) {
				try {
					Thread.sleep(500);
					Platform.runLater(() -> {
						if (!areTwoPlayersConnected && isConnected) {
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

	// -------------------------------------------------------------------- EXIT
	// WITH DISCONEXION
	// ----------------------------------------------------------------------------------------------------------------
	// //

	/**
	 * Method that allows to switch the scene to the Home with the disconnection
	 * between the player and the server
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void switchToHomeWithDecoServer(ActionEvent event) throws IOException {
		isConnected = false;
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
