package controller.Player;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.ForAllControllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Player;

public class ChoicePlayerViewController extends ForAllControllers implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	Button back, confirm;

	@FXML
	Label errorMsg, question;

	@FXML
	ComboBox<Player> listPlayer;

	/**
	 * Method that will be called when the FXML file is opened
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		setComboBoxWithPlayers(listPlayer);
	}

	/**
	 * Method that allows to switch to the page for view a player
	 * 
	 * @param event : The event that will activate the action
	 * @throws IOException
	 */
	public void switchToViewPlayer(ActionEvent event) throws IOException {
		if (listPlayer.getValue() == null) {
			displayMessage(errorMsg);
		} else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					".." + File.separator + ".." + File.separator + "view" + File.separator + "PlayerView.fxml"));
			root = loader.load();
			PlayerViewController playerViewController = loader.getController();
			playerViewController.viewPlayer(listPlayer.getValue());
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			setCenterStage(stage);
		}
	}
}