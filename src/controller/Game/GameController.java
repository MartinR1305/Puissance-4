package controller.Game;

import java.util.ArrayList;
import java.util.List;

import controller.ForAllControllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Grid;
import model.Player;
import model.ValueSquare;

public class GameController extends ForAllControllers {

	protected static Player player1, player2;
	protected static Grid grid;
	protected int turnPlayer, columnAddCoin;

	@FXML
	protected static Circle[][] matrixCircles;

	@FXML
	protected Circle c00, c01, c02, c03, c04, c05, c10, c11, c12, c13, c14, c15, c20, c21, c22, c23, c24, c25, c30, c31, c32, c33,
			c34, c35, c40, c41, c42, c43, c44, c45, c50, c51, c52, c53, c54, c55, c60, c61, c62, c63, c64, c65;

	@FXML
	protected Label playerPlaying, questionToExit, gameFinish;

	@FXML
	protected Button yes, no, exit, C0, C1, C2, C3, C4, C5, C6;
	
	@FXML 
	protected Rectangle rectangleExit;

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
			circle.setFill(Color.web("#2979c9"));
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

	/**
	 * Method that allows to change the color for the 4 winning squares
	 * 
	 * @param nbPlayer
	 */
	public void setColorsWinningCircles(Grid grid, int nbPlayer) {
		List<List<Integer>> winningSquares = new ArrayList<List<Integer>>(4);
		winningSquares = grid.getWinningSquares(nbPlayer);
		for(int numSquare = 0 ; numSquare < 4 ; numSquare++){
			
			matrixCircles[winningSquares.get(numSquare).get(0)][winningSquares.get(numSquare).get(1)].setFill(Color.GREEN);
		}
	}

	/**
	 * Method that allows to disable all buttons of the game
	 */
	public void disableAllButtons() {
		this.C0.setDisable(true);
		this.C1.setDisable(true);
		this.C2.setDisable(true);
		this.C3.setDisable(true);
		this.C4.setDisable(true);
		this.C5.setDisable(true);
		this.C6.setDisable(true);
	}

	/**
	 * Method that allows to set able all buttons of the game
	 */
	public void ableAllButtons() {
		this.C0.setDisable(false);
		this.C1.setDisable(false);
		this.C2.setDisable(false);
		this.C3.setDisable(false);
		this.C4.setDisable(false);
		this.C5.setDisable(false);
		this.C6.setDisable(false);
	}

	// ----------------------------------------------------------------------------
	// DISPLAY MESSAGE CONFIRMATION
	// ----------------------------------------------------------------------------
	// //

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
		
		rectangleExit.setVisible(true);
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
		
		rectangleExit.setVisible(false);
	}
}