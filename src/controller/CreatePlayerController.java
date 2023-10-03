package controller;

import java.io.File;
import java.io.IOException;

import Serialization.Serialization;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import application.Main;
import model.Player;

public class CreatePlayerController extends ForAllControllers {

	@FXML
	private Label errorMsg, successMsg, createPlayer;

	@FXML
	private Button back, confirm;

	@FXML
	private TextField lastName, firstName, userName, age;

	// Method that allows to create a player by filling in the fields
	public void CreatePlayer() {
		
		// Here we check if the age is an integer
		if (isInteger(age.getText())) {
			
			// Here we check that all the fields are filled and that the age is positive
			if (!lastName.getText().isEmpty() && !firstName.getText().isEmpty() && !userName.getText().isEmpty()
					&& !age.getText().isEmpty() && Integer.valueOf(age.getText()) >= 0) {
				
				// We create the player
				Player player = new Player(userName.getText(), lastName.getText(), firstName.getText(),
						Integer.valueOf(age.getText()));
				
				// We add the player to data and serialize it
				Main.getPlayersData().getValue().add(player);
				Serialization.serializePlayer(Main.getPlayersData().getValue());

				successMsg.setVisible(true);

				// We disable the button confirm
				confirm.setDisable(true);

				// We switch to player settings after 2.5 seconds
				Thread thread = new Thread(() -> {
					try {
						Thread.sleep(2500);
						Platform.runLater(() -> {
							try {
								FXMLLoader loader = new FXMLLoader(getClass().getResource(
										".." + File.separator + "view" + File.separator + "PlayerSettings.fxml"));
								Parent root = loader.load();
								Stage stage = (Stage) successMsg.getScene().getWindow();
								Scene scene = new Scene(root);
								stage.setScene(scene);
								stage.show();
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});
				thread.start();
			} else {
				displayMessage(errorMsg);
			}
		} else {
			displayMessage(errorMsg);
		}
	}
}
