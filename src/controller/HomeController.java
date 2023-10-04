package controller;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeController extends ForAllControllers{

	@FXML
	Label home;

	@FXML
	Button playSamePC, playSameNetwork, playVsIA, playerSettings;

	private Stage stage;
	private Scene scene;
	private Parent root;

	/**
	 * Controller that allows to switch the scene to the player settings
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
     * Controller that allows to switch the scene to the choice of players for a local PvP game
     * @param event
     * @throws IOException
     */
    public void switchToChoicePlayersPvP(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(".." + File.separator + "view" + File.separator + "ChoicePlayersPvP.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
