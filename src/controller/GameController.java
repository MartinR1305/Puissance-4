package controller;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import model.Player;

public class GameController extends ForAllControllers implements Initializable {

	private Player player1, player2;

	@FXML
	Label playerPlaying, questionToExit;

	@FXML
	Button yes, no, exit;

	@FXML
	Circle c00, c01, c02, c03, c04, c05, c10, c11, c12, c13, c14, c15, c20, c21, c22, c23, c24, c25, c30, c31, c32, c33, c34, c35, c40, c41, c42, c43, c44, c45, c50, c51, c52, c53, c54, c55, c60, c61, c62, c63, c64, c65;

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

		// We choose randomly the player who will start the game
		Random random = new Random();
		int randomInt = random.nextInt(2) + 1;
		
		if(randomInt == 1) {
			playerPlaying.setText("C'est à " + p1.getUserName() + " de jouer !");
		}
		
		else {
			playerPlaying.setText("C'est à " + p2.getUserName() + " de jouer !");
		}
	}

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