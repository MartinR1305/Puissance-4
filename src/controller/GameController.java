package controller;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Grid;
import model.Player;
import model.ValueSquare;

public class GameController extends ForAllControllers implements Initializable {

	private Player player1, player2;
	private Grid grid;
	private int turnPlayer;
	private int columnAddCoin;

	@FXML
	private Circle[][] matrixCircles;

	@FXML
	Circle c00, c01, c02, c03, c04, c05, c10, c11, c12, c13, c14, c15, c20, c21, c22, c23, c24, c25, c30, c31, c32, c33,
			c34, c35, c40, c41, c42, c43, c44, c45, c50, c51, c52, c53, c54, c55, c60, c61, c62, c63, c64, c65;

	@FXML
	Label playerPlaying, questionToExit;

	@FXML
	Button yes, no, exit, C0, C1, C2, C3, C4, C5, C6;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	/**
	 * @param p1, first player of the game
	 * @param p2, second player of the game
	 */
	public void startGame(Player p1, Player p2) {
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
			playerPlaying.setText("C'est à " + player1.getUserName() + " de jouer !");
		}

		else {
			playerPlaying.setText("C'est à " + player2.getUserName() + " de jouer !");
		}

		setColorsGrid(grid);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Method that allows to set the colors for a case
	 * 
	 * @param grid,   the grid where the square is
	 * @param circle, the circle that the color will change according to the value
	 *                of the square
	 * @param column, column where the square is
	 * @param line,   line where the square is
	 */
	public void setColorCircle(Grid grid, Circle circle, int column, int line) {
		if (grid.getGrid().get(column).getColumn().get(line).getValue().equals(ValueSquare.P1)) {
			circle.setFill(Color.YELLOW);
		}

		else if (grid.getGrid().get(column).getColumn().get(line).getValue().equals(ValueSquare.P2)) {
			circle.setFill(Color.RED);
		}

		else {
			circle.setFill(Color.WHITE);
		}
	}

	/**
	 * Method that allows to set the colors for all the grid, we will call this
	 * method after all coin added
	 * 
	 * @param grid
	 */
	public void setColorsGrid(Grid grid) {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				setColorCircle(grid, matrixCircles[i][j], i, j);
			}
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------

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

		// Call the addCoinGame method here with the new column value
		addCoinGame();
	}

	/**
	 * Method that allows to add a coin in the game by a player
	 */
	public void addCoinGame() {

		// If the player 1 played
		if (turnPlayer == 1) {
			if (!grid.getGrid().get(columnAddCoin).isColumnFull()) {
				grid.addCoinGrid(columnAddCoin, ValueSquare.P1);
				setColorsGrid(grid);

				if (grid.isGridFull()) {
					// End game Draw
				}

				else if (grid.isJ1win()) {
					// End game Victory
				}

				// We continue the game
				else {
					playerPlaying.setText("C'est à " + player2.getUserName() + " de jouer !");
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
					// End game Draw
				}

				else if (grid.isJ2win()) {
					// End game Victory
				}

				// We continue the game
				else {
					playerPlaying.setText("C'est à " + player1.getUserName() + " de jouer !");
					turnPlayer--;
				}
			}
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Method that allows to display a message + 2 buttons for ask the user if he is
	 * sure to delete the player or not
	 */
	public void displayConfirmation() {
		questionToExit.setVisible(true);

		yes.setVisible(true);
		yes.setDisable(false);

		no.setVisible(true);
		no.setDisable(false);
	}

	/**
	 * Method that allows to hide the message and the buttons
	 */
	public void hideConfirmation() {
		questionToExit.setVisible(false);

		yes.setVisible(false);
		yes.setDisable(true);

		no.setVisible(false);
		no.setDisable(true);
	}
}