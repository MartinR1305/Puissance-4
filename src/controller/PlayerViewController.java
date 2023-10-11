package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Player;

public class PlayerViewController extends ForAllControllers {

	@FXML
	Label firstName;
	Label lastName;
	Label userName;
	Label age;
	
	@FXML
	Button back;
	
	public void viewPlayer(Player p) {
		firstName.setText(p.getFirstName());
		lastName.setText(p.getLastName());
		userName.setText(p.getUserName());
		age.setText(Integer.toString(p.getAge()));
	}

}
