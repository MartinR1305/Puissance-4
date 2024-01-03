package controller.Game;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class ChoicePlayersPvPController extends ForAllControllers implements Initializable{
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    Label player1, player2, errorMsg, errorMsg2, versus, timeLimite;
    
    @FXML
    Button back, play;
    
    @FXML
    ComboBox<Player> listPlayer1, listPlayer2;
    
    @FXML 
    ComboBox<String> timeLimiteChoice;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setComboBoxWithPlayers(listPlayer1);
        setComboBoxWithPlayers(listPlayer2);
        setComboBoxWithTimeLimits(timeLimiteChoice);
    }
    
    
    /**
     * Method that allows to switch to a PvP local game
     * @param event
     * @throws IOException
     */
    public void switchToGamePvP(ActionEvent event) throws IOException {
        if(listPlayer1.getValue() == null || listPlayer2.getValue() == null || timeLimiteChoice.getValue() == null) {
            displayMessage(errorMsg);
        }
        
        else if(listPlayer1.getValue() == listPlayer2.getValue()) {
            displayMessage(errorMsg2);
        }
        
        else {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "GamePvPLocal.fxml"));
            root = loader.load();
            GamePvPLocalController gamePvPLocalController = loader.getController();
            
            int timeLimit = 0;
            if(timeLimiteChoice.getValue() == "No Limits") {
            	timeLimit = -1;
            } else {
                Pattern pattern = Pattern.compile("\\b(\\d+)\\b");
                Matcher matcher = pattern.matcher(timeLimiteChoice.getValue());

                if (matcher.find()) {
                    String numberString = matcher.group(1);
                    timeLimit = Integer.parseInt(numberString);
                } else {
                    System.out.println("ERROR");
                }
            }
            
            gamePvPLocalController.startGamePvPLocal(listPlayer1.getValue(), listPlayer2.getValue(), timeLimit);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            setCenterStage(stage);
        }
    }
}