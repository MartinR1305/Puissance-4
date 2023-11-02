package controller.Game;

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

public class ChoicePvPorAvAController extends ForAllControllers{
	
    @FXML
    private Label avaGame, pvpGame;

    @FXML
    private Button back, goAvAGame, goPvPGame;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	/**
     * Controller that allows to switch the scene to the choice a player for a PvP game in TCP
     * @param event
     * @throws IOException
     */
    public void switchToChoicePlayerPvP(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "ChoicePlayerPvP.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        setCenterStage(stage);
    }
    
	/**
     * Controller that allows to switch the scene to the choice of the level of the algorithm for a AvA game in TCP
     * @param event
     * @throws IOException
     */
//    public void switchToChoiceLvlAlgo(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(
//                getClass().getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "ChoicePvPorAvA.fxml"));
//        root = loader.load();
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//        setCenterStage(stage);
//    }

}
