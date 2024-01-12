package controller.Player;

import Serialization.Serialization;
import application.Main;
import controller.ForAllControllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Player;

public class PlayerEditController extends ForAllControllers{
	
   private Player playerToEdit;
   
	@FXML
    private Button back;

    @FXML
    private Button confirm;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField userName;

    @FXML
    private TextField age;

    @FXML
    private Label errorMsg;
    
    @FXML
    private Label successMsg;

    /**
     * Method that edits the information of a player.
     * @param p : The player to edit.
     */
    public void editPlayer(Player player) {
    	playerToEdit = player;
    	
    	firstName.setText(playerToEdit.getFirstName());
    	lastName.setText(playerToEdit.getLastName());
    	userName.setText(playerToEdit.getUserName());   
    	age.setText(Integer.toString(playerToEdit.getAge()));
    	
    	
    }
    
    /**
     * Method that edit the player by clicking on confirm button
     */
    public void confirm() {
        
    	// Get the updated information from the text fields
        String newFirstName = firstName.getText();
        String newLastName = lastName.getText();
        String newUserName = userName.getText();
        String ageText = age.getText();
       	
        // Validate and parse the new age
        int newAge;
        try {
        	newAge = Integer.parseInt(ageText);
            if (newAge < 0) {
                // Age should not be negative
                //errorMsg.setText("Age cannot be negative");
                displayMessage(errorMsg);
                return;
            }
        } catch (NumberFormatException e) {
            // Age should be a valid integer
            //errorMsg.setText("Invalid age format");
            displayMessage(errorMsg);
            return;
        }
        
        // Check if any of the fields are empty
        if (newFirstName.isEmpty() || newLastName.isEmpty() || newUserName.isEmpty() || ageText.isEmpty()) {
        	//errorMsg.setText("errorMsg.setText(\"Invalid age format");
            displayMessage(errorMsg);
        }else {
        	
        	// Update the player's attributes
            playerToEdit.setFirstName(newFirstName);
            playerToEdit.setLastName(newLastName);
            playerToEdit.setUserName(newUserName);
            playerToEdit.setAge(newAge);
            
            displayMessage(successMsg);
            
            Serialization.serializePlayer(Main.getPlayersData().getValue());
            
            confirm.setVisible(false);
            confirm.setDisable(true);
            switchToFileWithDelay("PlayerSettings.fxml", successMsg);
        }
    }
}