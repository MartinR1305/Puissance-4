package controller.Player;

import java.io.File;
import java.io.IOException;

import controller.ForAllControllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PlayerSettingsController extends ForAllControllers {

	@FXML
	Label playerSettings;

	@FXML
	Button createPlayer, viewPlayer, viewRanking, editPlayer, deletePlayer, back;

	private Stage stage;
	private Scene scene;
	private Parent root;

	/**
	 * Controller that allows to switch the scene for create a player
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void switchToCreatePlayer(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "CreatePlayer.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		setCenterStage(stage);
	}

	/**
	 * Controller that allows to switch the scene for view a player
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void switchToChoiceViewPlayer(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "ChoicePlayerView.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		setCenterStage(stage);
	}

	/**
	 * Controller that allows to switch the scene for see the players ranking
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void switchToViewRanking(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "RankingPlayers.fxml"));
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
	 * @param event
	 * @throws IOException
	 */
	public void switchToChoiceEditPlayer(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "ChoicePlayerEdit.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		setCenterStage(stage);
	}

	/**
	 * Controller that allows to switch the scene for delete a player
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void switchToChoiceDeletePlayer(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "ChoiceDeletePlayer.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		setCenterStage(stage);
	}
}
