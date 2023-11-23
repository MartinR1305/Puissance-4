package controller.Player;

import Serialization.Serialization;
import application.Main;
import controller.ForAllControllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Player;

public class CreatePlayerController extends ForAllControllers {

	@FXML
	private Label errorMsg, successMsg, createPlayer;

	@FXML
	private Button back, confirm;

	@FXML
	private TextField lastName, firstName, userName, age;

	/**
	 * 
	 * Method that allows to create a player by filling in the fields
	 */
	public void CreatePlayer() {
		// Here we check if the age is an integer
		if (isInteger(age.getText())) {
			// Here we check that all the fields are filled and that the age is positive
			if (!lastName.getText().isEmpty() && !firstName.getText().isEmpty() && !userName.getText().isEmpty()
					&& !age.getText().isEmpty() && Integer.valueOf(age.getText()) >= 0) {
				if (!isNbIn(lastName.getText()) && !isNbIn(firstName.getText())) {
					// We create the player
					Player player = new Player(userName.getText(), lastName.getText(), firstName.getText(),
							Integer.valueOf(age.getText()));
					// We add the player to data and serialize it
					Main.getPlayersData().getValue().add(player);
					Serialization.serializePlayer(Main.getPlayersData().getValue());

					successMsg.setVisible(true);

					// We disable the button confirm and back
					confirm.setDisable(true);
					confirm.setVisible(false);
					back.setDisable(true);
					back.setVisible(false);

					// We switch to player settings after 2.5 seconds
					switchToFileWithDelay("PlayerSettings.fxml", successMsg);
				} else {
					displayMessage(errorMsg);
				}
			} else {
				displayMessage(errorMsg);
			}
		} else {
			displayMessage(errorMsg);
		}
	}

	/**
	 * Method that allows to know if there are number(s) in a String
	 * 
	 * @param string
	 * @return
	 */
	public boolean isNbIn(String string) {

		// Parcours de chaque caractère de la chaîne
		for (char c : string.toCharArray()) {
			// Vérifie si le caractère est un chiffre
			if (Character.isDigit(c)) {
				return true;
			}
		}

		return false;
	}
}