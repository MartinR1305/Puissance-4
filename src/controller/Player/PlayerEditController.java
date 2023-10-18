package controller.Player;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Player;

public class PlayerEditController {
	
	private Stage stage;
    private Scene scene;
    private Parent root;

	@FXML
	Label firstName;
	
	@FXML
	Label lastName;
	
	@FXML
	Label userName;
	
	@FXML
	Label age;
	
	@FXML
	Button back;
	
	public void editPlayer(Player p) {
		firstName.setText(p.getFirstName());
		lastName.setText(p.getLastName());
		userName.setText(p.getUserName());
		age.setText(Integer.toString(p.getAge()));
	}

}
