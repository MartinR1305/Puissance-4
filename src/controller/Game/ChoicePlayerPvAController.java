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

public class ChoicePlayerPvAController extends ForAllControllers implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	Label playerChoice, errorMsg, labelAlgoLvl, timeLimite;

	@FXML
	Button back, play;

	@FXML
	ComboBox<Player> listPlayer;

	@FXML
	ComboBox<Integer> listLevel;

	@FXML
	ComboBox<String> timeLimiteChoice;

	/**
	 * Method that will be called when the FXML file is opened
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setComboBoxWithPlayers(listPlayer);
		setComboBoxWithLevels(listLevel);
		setComboBoxWithTimeLimits(timeLimiteChoice);
	}

	/**
	 * Method that allows to switch to a PvA game
	 * 
	 * @param event : The event that will activate the action
	 * @throws IOException
	 */
	public void switchToGamePvA(ActionEvent event) throws IOException {
		if (listPlayer.getValue() == null) {
			displayMessage(errorMsg);
		}

		else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					".." + File.separator + ".." + File.separator + "view" + File.separator + "GamePvALocal.fxml"));
			root = loader.load();

			GamePvALocalController gamePvALocalController = loader.getController();

			int timeLimit = 0;
			if (timeLimiteChoice.getValue() == "No Limits") {
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

			gamePvALocalController.startGamePvALocal(listPlayer.getValue(), listLevel.getValue(), timeLimit);

			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			setCenterStage(stage);
		}
	}
}
