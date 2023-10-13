package controller.Player;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.stage.Stage;
import model.Player;

public class ChoicePlayerViewController extends ForAllControllers implements Initializable{
	
	private Stage stage;
    private Scene scene;
    private Parent root;
	    
	    
	@FXML
	Button back, confirm;
	
	
	@FXML
	ComboBox<Player> listPlayer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		setComboBoxWithPlayers(listPlayer);
	}
	
	public void switchToViewPlayer(ActionEvent event) throws IOException {
		 FXMLLoader loader = new FXMLLoader(
                 getClass().getResource(".." + File.separator + ".." + File.separator + "view" + File.separator + "PlayerView.fxml"));
         root = loader.load();
         PlayerViewController playerViewController = loader.getController();
         playerViewController.viewPlayer(listPlayer.getValue());
         stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
	}
}
