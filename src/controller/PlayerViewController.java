package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Player;

public class PlayerViewController extends ForAllControllers {

	@FXML
	Label firstName;
	
	@FXML
	Button back;
	
	public void viewPlayer(Player p) {
		firstName.setText(p.getFirstName());
	}

}
