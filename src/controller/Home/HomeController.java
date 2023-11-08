package controller.Home;

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

public class HomeController extends ForAllControllers{

	@FXML
	Label home;

	@FXML
	Button playSamePC, playSameNetwork, playVsIA, playerSettings;

	private Stage stage;
	private Scene scene;
	private Parent root;

	/**
     * Controller that allows to switch the scene to the choice of players for a local PvP game
     * @param event
     * @throws IOException
     */
    public void switchToChoicePlayersPvP(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "ChoicePlayersPvP.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        setCenterStage(stage);
    }
    
	/**
     * Controller that allows to switch the scene to the choice of player for a local PvA game
     * @param event
     * @throws IOException
     */
    public void switchToChoicePlayersPvA(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "ChoicePlayerPvA.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        setCenterStage(stage);
    }
    
	/**
     * Controller that allows to switch the scene to the choice of PvP or AvA game in TCP
     * @param event
     * @throws IOException
     */
    public void switchToChoiceOnline(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "ChoiceOnlineGame.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        setCenterStage(stage);
    }
}
