package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.SwingUtilities;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Player;

public class ForAllControllers {

	private Stage stage;
	private Scene scene;
	private Parent root;

	/**
	 * Method that allows to display a message with a thread and to hide if after 3 seconds
	 * @param label, the label that we want to display
	 */
	public void displayMessage(Label label) {
		// Create a new thread to manage the wait
		Thread waitThread = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(3000);
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							// Make label invisible after pause
							label.setVisible(false);
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		label.setVisible(true);
		waitThread.start();
	}
	
	/**
	 * Method that allows to set a combo box with the list of all the players
	 * @param comboBoxJoueur, the box that we want to set with all the players
	 */
	public void setComboBoxWithPlayers(ComboBox<Player> comboBoxJoueur) {
		List<Player> listJoueurs = Main.getPlayersData().getValue();
		ObservableList<Player> joueursList = FXCollections.observableArrayList();

		// Here we add all the player
		for (Player joueur : listJoueurs) {
			joueursList.add(joueur);
		}

		// Set the drop-down menu
		comboBoxJoueur.setCellFactory(param -> new ListCell<Player>() {
			@Override
			protected void updateItem(Player joueur, boolean empty) {
				super.updateItem(joueur, empty);

				if (empty || joueur == null) {
					setText(null);
				} else {
					setText(joueur.getFirstName() + " '" + joueur.getUserName() + "' " + joueur.getLastName());
				}
			}
		});

		comboBoxJoueur.setItems(joueursList);

		// We set display of player names once selected
		comboBoxJoueur.setConverter(new StringConverter<Player>() {
			@Override
			public String toString(Player joueur) {
				if (joueur == null) {
					return null;
				} else {
					return joueur.getFirstName() + " '" + joueur.getUserName() + "' " + joueur.getLastName();
				}
			}

			@Override
			public Player fromString(String string) {
				return null;
			}
		});
	}

	/**
	 * Controller that allows to switch the scene to the Home
	 * @param event
	 * @throws IOException
	 */
	public void switchToHome(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(".." + File.separator + "view" + File.separator + "Home.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Controller that allows to switch the scene to the Player Settings
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void switchToPlayerSettings(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(".." + File.separator + "view" + File.separator + "PlayerSettings.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Method that allows to know if a String can be a integer
	 * @param str, the string that we want to check if it's a integer or not
	 * @return 1 if the string is a integer, 0 otherwise
	 */
	public boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
