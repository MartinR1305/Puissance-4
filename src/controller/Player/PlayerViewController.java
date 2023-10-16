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
import model.Player;

public class PlayerViewController extends ForAllControllers {
	
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
	
	public void viewPlayer(Player p) {
		firstName.setText(p.getFirstName());
		lastName.setText(p.getLastName());
		userName.setText(p.getUserName());
		age.setText(Integer.toString(p.getAge()));
	}
	
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
}
