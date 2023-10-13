package controller.Player;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Serialization.Serialization;
import application.Main;
import controller.ForAllControllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.Player;

public class ChoiceDeletePlayerController extends ForAllControllers implements Initializable {

	@FXML
	Label questionDelete, questionToConfirm, successMsg, errorMsg;

	@FXML
	Button back, confirm, yes, no;

	@FXML
	ComboBox<Player> playersList;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// We actualize the box with the current players list
		setComboBoxWithPlayers(playersList);
	}

	/**
	 * Method that allows to delete the player choose in the box
	 */
	public void deletePlayer() {
		Player playerToDelete = null;

		// We search in data the player to delete
		List<Player> listeJoueur = Main.getPlayersData().getValue();
		for (Player joueur : listeJoueur) {
			if (joueur.getIdentifier().equals(playersList.getValue().getIdentifier())) {
				playerToDelete = joueur;
			}
		}

		// We remove him
		listeJoueur.remove(playerToDelete);

		// We serialize it
		Serialization.serializePlayer(Main.getPlayersData().getValue());

		displayMessage(successMsg);
		hideConfirmation();

		// We actualize the box
		setComboBoxWithPlayers(playersList);
		playersList.setValue(null);
	}

	/**
	 * Method that allows to display a message + 2 buttons for ask the user if he is
	 * sure to delete the player or not
	 */
	public void displayConfirmation() {

		// We check if a player has been selected in the box
		if (playersList.getValue() != null) {
			questionToConfirm.setVisible(true);

			yes.setVisible(true);
			yes.setDisable(false);

			no.setVisible(true);
			no.setDisable(false);
		}

		else {
			displayMessage(errorMsg);
		}
	}

	/**
	 * Method that allows to hide the message and the buttons
	 */
	public void hideConfirmation() {
		questionToConfirm.setVisible(false);

		yes.setVisible(false);
		yes.setDisable(true);

		no.setVisible(false);
		no.setDisable(true);
	}
}
