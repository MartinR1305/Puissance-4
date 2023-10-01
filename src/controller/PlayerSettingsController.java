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

public class PlayerSettingsController extends ForAllControllers{
	
	@FXML
	Label playerSettings;
	
	@FXML
	Button createPlayer, viewPlayer, viewRanking, editPlayer, deletePlayer, back;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	// Controller that allows to switch the scene for create a player
	public void switchToCreatePlayer(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(".." + File.separator + "view" + File.separator + "CreatePlayer.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Controller that allows to switch the scene for view a player
	public void switchToChoiceViewPlayer(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(".." + File.separator + "view" + File.separator + "ChoiceViewPlayer.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Controller that allows to switch the scene for see the players ranking
	public void switchToViewRanking(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(".." + File.separator + "view" + File.separator + "RankingPlayers.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Controller that allows to switch the scene for edit a player
	public void switchToChoiceEditPlayer(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(".." + File.separator + "view" + File.separator + "ChoiceEditPlayer.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Controller that allows to switch the scene for delete a player
	public void switchToChoiceDeletePlayer(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(".." + File.separator + "view" + File.separator + "ChoiceDeletePlayer.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
