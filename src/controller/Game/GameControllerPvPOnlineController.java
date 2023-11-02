package controller.Game;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.ClientTCP;
import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameControllerPvPOnlineController extends GameController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private ClientTCP clientTCP;
	private static boolean areTwoPlayersConnected;
	
	@FXML
	Label test;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// We actualize the client attribute with the one of the main
		setClientTCP(Main.getClientTCP());
		
	}
	
	/**
	 * Setter of clientTCP -> use for associating application and ClientTCP
	 * 
	 * @param clientTCP
	 */
	public void setClientTCP(ClientTCP clientTCP) {
		this.clientTCP = clientTCP;
	}
	
	/**
	 * Method that allows to actualize the boolean for know if two players are connected or not
	 * 
	 * @param state
	 */
	public void actualize2PlayersBoolean(Boolean areTwoPlayersConnected) {
		if (areTwoPlayersConnected.equals(true)) {
			this.areTwoPlayersConnected = true;
		}

		else if (areTwoPlayersConnected.equals(false)) {
			this.areTwoPlayersConnected = false;
		}
	}
	
	/**
	 * Method that allows to switch to the game is two players are connected to the
	 * server
	 */
	public void backToHome() {
		Thread thread = new Thread(() -> {
			while (areTwoPlayersConnected) {
				try {
					Thread.sleep(500);
					Platform.runLater(() -> {
						if (!areTwoPlayersConnected) {

							FXMLLoader loader = new FXMLLoader(getClass().getResource(".." + File.separator + ".."
									+ File.separator + "view" + File.separator + "Home.fxml"));
							try {
								root = loader.load();
							} catch (IOException e) {
								e.printStackTrace();
							}
							stage = (Stage) (test.getScene().getWindow());
							scene = new Scene(root);
							stage.setScene(scene);
							stage.show();
							setCenterStage(stage);

						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
}
