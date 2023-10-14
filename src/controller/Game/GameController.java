package controller.Game;

import controller.ForAllControllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Grid;
import model.Player;
import model.ValueSquare;

public class GameController extends ForAllControllers{

	protected Player player1, player2;
	protected Grid grid;
	protected int turnPlayer;
	protected int columnAddCoin;

	@FXML
	protected Circle[][] matrixCircles;

	@FXML
	Circle c00, c01, c02, c03, c04, c05, c10, c11, c12, c13, c14, c15, c20, c21, c22, c23, c24, c25, c30, c31, c32, c33,
			c34, c35, c40, c41, c42, c43, c44, c45, c50, c51, c52, c53, c54, c55, c60, c61, c62, c63, c64, c65;

	@FXML
	Label playerPlaying, questionToExit, gameFinish;

	@FXML
	Button yes, no, exit, C0, C1, C2, C3, C4, C5, C6;
	
	
	// --------------------------------------------------------------------------------------------- METHODS FOR ALL GAMES ------------------------------------------------------------------------------------------------ //

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
	
	/**
	 * Method that allows to disable all buttons of the game
	 */
	public void disableAllButtons() {
		C0.setDisable(true);
		C1.setDisable(true);
		C2.setDisable(true);
		C3.setDisable(true);
		C4.setDisable(true);
		C5.setDisable(true);
		C6.setDisable(true);
	}
	
	/**
	 * Method that allows to set able all buttons of the game
	 */
	public void ableAllButtons() {
		C0.setDisable(false);
		C1.setDisable(false);
		C2.setDisable(false);
		C3.setDisable(false);
		C4.setDisable(false);
		C5.setDisable(false);
		C6.setDisable(false);
	}
	
	
	// ---------------------------------------------------------------------------- DISPLAY MESSAGE CONFIRMATION ----------------------------------------------------------------------------------------------------------- //

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