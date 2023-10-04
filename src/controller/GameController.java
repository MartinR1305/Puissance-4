package controller;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import model.Player;

public class GameController extends ForAllControllers implements Initializable {

	private Player player1, player2;

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
	}
}