package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class ChoicePlayersPvPController extends ForAllControllers implements Initializable{
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    Label player1, player2, errorMsg, errorMsg2;
    
    @FXML
    Button back, play;
    
    @FXML
    ComboBox<Player> listPlayer1, listPlayer2;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setComboBoxWithPlayers(listPlayer1);
        setComboBoxWithPlayers(listPlayer2);
    }
    
    
    /**
     * Method that allows to switch to a local game PvP
     * @param event
     * @throws IOException
     */
    public void switchToGamePvP(ActionEvent event) throws IOException {
        if(listPlayer1.getValue() == null || listPlayer2.getValue() == null) {
            displayMessage(errorMsg);
        }
        
        else if(listPlayer1.getValue() == listPlayer2.getValue()) {
            displayMessage(errorMsg2);
        }
        
        else {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(".." + File.separator + "view" + File.separator + "Game.fxml"));
            root = loader.load();
            GameController gameController = loader.getController();
            gameController.startGame(listPlayer1.getValue(), listPlayer2.getValue());
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        }
    }
}