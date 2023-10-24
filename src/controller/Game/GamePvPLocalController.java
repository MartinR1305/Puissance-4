package controller.Game;

import java.util.Random;

import Serialization.Serialization;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import model.Grid;
import model.Player;
import model.Results;
import model.ValueSquare;

public class GamePvPLocalController extends GameController{
	
	/**
	 * Method that allows to start a PvP Local game
	 * @param p1, first player of the game
	 * @param p2, second player of the game
	 */
	public void startGamePvPLocal(Player p1, Player p2) {
	
		player1 = p1;
		player2 = p2;
		grid = new Grid();
		
		// We put all circles in the matrix
		matrixCircles = new Circle[][] { { c00, c01, c02, c03, c04, c05 }, { c10, c11, c12, c13, c14, c15 },
				{ c20, c21, c22, c23, c24, c25 }, { c30, c31, c32, c33, c34, c35 }, { c40, c41, c42, c43, c44, c45 },
				{ c50, c51, c52, c53, c54, c55 }, { c60, c61, c62, c63, c64, c65 } };

		// We choose randomly the player who will start the game
		Random random = new Random();
		turnPlayer = random.nextInt(2) + 1;

		if (turnPlayer == 1) {
			playerPlaying.setText("It's " + player1.getUserName() + " 's Turn");
		}

		else {
			playerPlaying.setText("It's " + player2.getUserName() + " 's Turn");
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

		addCoinGamePvPLocal();
	}
	
	/**
	 * Method that allows to add a coin in the game by a player
	 */
	public void addCoinGamePvPLocal() {

		// If the player 1 played
		if (turnPlayer == 1) {
			if (!grid.getGrid().get(columnAddCoin).isColumnFull()) {
				grid.addCoinGrid(columnAddCoin, ValueSquare.P1);
				setColorsGrid(grid);

				if (grid.isGridFull()) {
					drawGamePvPLocal();
				}

				else if (grid.isJ1win()) {
					setColorsWinningCircles(grid,1);
					winGamePvPLocal(player1, player2);
				}

				// We continue the game
				else {
					playerPlaying.setText("It's " + player2.getUserName() + " 's Turn");
					turnPlayer++;
				}
			}
		}

		// If the player 2 played
		else {
			if (!grid.getGrid().get(columnAddCoin).isColumnFull()) {
				grid.addCoinGrid(columnAddCoin, ValueSquare.P2);
				setColorsGrid(grid);

				if (grid.isGridFull()) {
					drawGamePvPLocal();
				}

				else if (grid.isJ2win()) {
					setColorsWinningCircles(grid,2);
					winGamePvPLocal(player2, player1);
				}

				// We continue the game
				else {
					playerPlaying.setText("It's " + player1.getUserName() + " 's Turn");
					turnPlayer--;
				}
			}
		}
	}
	
	
	/**
	 * Method that display a message, set data for a draw and then back to the previous scene
	 */
	public void drawGamePvPLocal() {
		disableAllButtons();
		
		// Display message
		gameFinish.setText("Game is over ! Nobody won ... It is a draw !");
		gameFinish.setVisible(true);
		playerPlaying.setVisible(false);
		
		// Add the draw on players's data
		player1.addMatch(player2.getUserName(), Results.DRAW);
		player2.addMatch(player1.getUserName(), Results.DRAW);
		
		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}
	
	/**
	 * Method that display a message, set data for a victory and then back to the previous scene
	 * @param playerWin, player who won the game
	 * @param playerLoose, player who lost the game
	 */
	public void winGamePvPLocal(Player playerWin, Player playerLoose) {
		disableAllButtons();
		
		// Display message
		gameFinish.setText("Game is over .. " + playerWin.getUserName() + " won the game !");
		gameFinish.setVisible(true);
		playerPlaying.setVisible(false);
		
		// Add the draw on players's data
		playerWin.addMatch(playerLoose.getUserName(), Results.VICTORY);
		playerLoose.addMatch(playerWin.getUserName(), Results.DEFEAT);
		
		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}
}
