package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.SwingUtilities;

import application.Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Player;

public class ForAllControllers {

	private Stage stage;
	private Scene scene;
	private Parent root;

	/**
	 * Method that allows to display a message with a thread and to hide if after 3
	 * seconds
	 * 
	 * @param label : The label that we want to display
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
	 * Controller that allows to switch the scene for view a player
	 * 
	 * @param event : The event that will activate the action
	 * @throws IOException
	 */
	public void switchToChoiceViewPlayer(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				".." + File.separator + ".." + File.separator + "view" + File.separator + "ChoicePlayerView.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		setCenterStage(stage);
	}

	/**
	 * Controller that allows to switch the scene for edit a player
	 * 
	 * @param event : The event that will activate the action
	 * @throws IOException
	 */
	public void switchToChoiceEditPlayer(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				".." + File.separator + ".." + File.separator + "view" + File.separator + "ChoicePlayerEdit.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		setCenterStage(stage);
	}

	/**
	 * Method that allows to switch the scene to the Home
	 * 
	 * @param event : The event that will activate the action
	 * @throws IOException
	 */
	public void switchToHome(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass()
				.getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "Home.fxml"));

		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		setCenterStage(stage);
	}

	/**
	 * Method that allows to switch the scene to the Player Settings
	 * 
	 * @param event : The event that will activate the action
	 * @throws IOException
	 */
	public void switchToPlayerSettings(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				".." + File.separator + ".." + File.separator + "view" + File.separator + "PlayerSettings.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		setCenterStage(stage);
	}

	/**
	 * Method that allows to switch the scene to nameFile after 5s
	 * 
	 * @param nameFile    : File where we want to go
	 * @param labelOnPage : Label on the page we currently are
	 */
	public void switchToFileWithDelay(String nameFile, Label labelOnPage) {
		Thread thread = new Thread(() -> {
			try {
				Thread.sleep(5000);
				Platform.runLater(() -> {
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource(
								".." + File.separator + ".." + File.separator + "view" + File.separator + nameFile));
						Parent root = loader.load();
						Stage stage = (Stage) labelOnPage.getScene().getWindow();
						Scene scene = new Scene(root);
						stage.setScene(scene);
						stage.show();
						setCenterStage(stage);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread.start();
	}

	/**
	 * Method that allows to know if the String is a integer
	 * 
	 * @param str : The string that we want to check if it's a integer or not
	 * @return boolean : Answer if the String is a integer
	 */
	public boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Method that allows to set the stage on the center of the screen
	 * 
	 * @param stage : The current stage
	 */
	public void setCenterStage(Stage stage) {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	/**
	 * Method that allows to set a combo box with the list of all the players
	 * 
	 * @param comboBoxJoueur : The ComboBox that we want to set with all the players
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
					setText(joueur.getFirstName() + " '" + joueur.getUserName().toUpperCase() + "' "
							+ joueur.getLastName());
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
					return joueur.getFirstName() + " '" + joueur.getUserName().toUpperCase() + "' "
							+ joueur.getLastName();
				}
			}

			@Override
			public Player fromString(String string) {
				return null;
			}
		});
	}

	/**
	 * Method that allows to set a combo box with the list of levels
	 * 
	 * @param comboBoxLevel : The ComboBox that we want to set with all the levels
	 */
	public void setComboBoxWithLevels(ComboBox<Integer> comboBoxLevel) {
		ObservableList<Integer> levelList = FXCollections.observableArrayList();

		for (int i = 2; i < 12; i++) {
			levelList.add(i);
		}

		// Set the drop-down menu
		comboBoxLevel.setCellFactory(param -> new ListCell<Integer>() {
			@Override
			protected void updateItem(Integer level, boolean empty) {
				super.updateItem(level, empty);

				if (empty || level == null) {
					setText(null);
				} else {
					setText(level.toString());
				}
			}
		});

		comboBoxLevel.setItems(levelList);

		// We set display of level once selected
		comboBoxLevel.setConverter(new StringConverter<Integer>() {
			@Override
			public String toString(Integer level) {
				if (level == null) {
					return null;
				} else {
					return level.toString();
				}
			}

			@Override
			public Integer fromString(String string) {
				return null;
			}
		});
	}

	public void setComboBoxWithTimeLimits(ComboBox<String> comboBoxLevel) {
		ObservableList<String> timeLimits = FXCollections.observableArrayList();

		timeLimits.add("No Limits");
		timeLimits.add("10 Seconds");
		timeLimits.add("20 Seconds");
		timeLimits.add("30 Seconds");
		timeLimits.add("45 Seconds");
		timeLimits.add("60 Seconds");

		// Set the drop-down menu
		comboBoxLevel.setCellFactory(param -> new ListCell<String>() {
			@Override
			protected void updateItem(String timeLimit, boolean empty) {
				super.updateItem(timeLimit, empty);

				if (empty || timeLimit == null) {
					setText(null);
				} else {
					setText(timeLimit.toString());
				}
			}
		});

		comboBoxLevel.setItems(timeLimits);

		// We set display of level once selected
		comboBoxLevel.setConverter(new StringConverter<String>() {
			@Override
			public String toString(String timeLimit) {
				if (timeLimit == null) {
					return null;
				} else {
					return timeLimit.toString();
				}
			}

			@Override
			public String fromString(String string) {
				return null;
			}
		});
	}
}
