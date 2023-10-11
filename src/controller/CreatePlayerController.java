package controller;

import Serialization.Serialization;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Player;

public class CreatePlayerController extends ForAllControllers {

    @FXML
    private Label errorMsg, successMsg, createPlayer;

    @FXML
    private Button back, confirm;

    @FXML
    private TextField lastName, firstName, userName, age;

    /**
     
Method that allows to create a player by filling in the fields*/
  public void CreatePlayer() {
      // Here we check if the age is an integer
      if (isInteger(age.getText())) {
          // Here we check that all the fields are filled and that the age is positive
          if (!lastName.getText().isEmpty() && !firstName.getText().isEmpty() && !userName.getText().isEmpty()&& !age.getText().isEmpty() && Integer.valueOf(age.getText()) >= 0) {
              // We create the player
              Player player = new Player(userName.getText(), lastName.getText(), firstName.getText(),
                      Integer.valueOf(age.getText()));
              // We add the player to data and serialize it
              Main.getPlayersData().getValue().add(player);
              Serialization.serializePlayer(Main.getPlayersData().getValue());

                successMsg.setVisible(true);

                // We disable the button confirm
                confirm.setDisable(true);
                confirm.setVisible(false);

                // We switch to player settings after 2.5 seconds
                switchToFileWithDelay("PlayerSettings.fxml", successMsg);
            } else {
                displayMessage(errorMsg);
            }
        } else {
            displayMessage(errorMsg);
        }
    }
}