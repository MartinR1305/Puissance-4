package controller.Game;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.ClientTCP;
import application.Main;
import controller.Home.HomeController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Player;

public class ChoicePlayerPvPController extends HomeController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	private ClientTCP clientTCP;
	private static boolean isConnected, areTwoPlayersConnected;

	@FXML
	private Label IP, port, choosePlayer, isConnectedToServer, msgError, msgError2;

	@FXML
	private Button back, connectTotTheServer;

	@FXML
	private TextField ipValue, portValue;

	@FXML
	private ComboBox<Player> listPlayer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// We actualize the client attribute with the one of the main
		setClientTCP(Main.getClientTCP());

		// We initialize data on the FXML file
		setComboBoxWithPlayers(listPlayer);
		isConnectedToServer.setText("Disconnected");

		// We start the updating of the state
		this.updateState();

		// We look if we can start a game
		this.switchToGame();
	}

	/**
	 * Method that allows to connect to a server by using two text fields
	 * 
	 * @throws IOException
	 */
	public void connectToServer() throws IOException {
		// We check that that the port and the IP are filled
		if (ipValue.getText() == "" || portValue.getText() == "") {
			displayMessage(msgError);

		} else {
			// We check that a player had been selected
			if (listPlayer.getValue() != null) {
				clientTCP.changeIP_Port(ipValue.getText(), portValue.getText());
			} else {
				displayMessage(msgError2);
			}
		}
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
	 * Method that allows to actualize the state of the connection between the
	 * client and the server
	 * 
	 * @param state
	 */
	public void actualizeState(String state) {
		if (state.equals("Connected")) {
			isConnected = true;
		}

		else if (state.equals("Disconnected")) {
			isConnected = false;
		}
	}

	/**
	 * Method that allows to actualize the boolean in order to know if two players
	 * are connected or not
	 * 
	 * @param state
	 */
	public void actualize2PlayersBoolean(Boolean areTwoClientsConnected) {
		if (areTwoClientsConnected.equals(true)) {
			areTwoPlayersConnected = true;
		}

		else if (areTwoClientsConnected.equals(false)) {
			areTwoPlayersConnected = false;
		}
	}

	/**
	 * Method that allows to update the state of the connection between the client
	 * and the server
	 */
	public void updateState() {
		Thread stateUpdateThread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(500);
					Platform.runLater(() -> {
						if (isConnected) {
							isConnectedToServer.setText("Connected");

							// We disable the button back and connect
							back.setDisable(true);
							back.setVisible(false);
							connectTotTheServer.setDisable(true);
							connectTotTheServer.setVisible(false);
						} else {
							isConnectedToServer.setText("Disconnected");

							// We activate the button back and connect
							back.setDisable(false);
							back.setVisible(true);
							connectTotTheServer.setDisable(false);
							connectTotTheServer.setVisible(true);
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		stateUpdateThread.setDaemon(true);
		stateUpdateThread.start();
	}

	/**
	 * Method that allows to switch to the game is two players are connected to the
	 * server
	 */
	public void switchToGame() {
		Thread thread = new Thread(() -> {
			while (!areTwoPlayersConnected) {
				try {
					Thread.sleep(500);
					Platform.runLater(() -> {
						if (areTwoPlayersConnected) {

							FXMLLoader loader = new FXMLLoader(getClass().getResource(".." + File.separator + ".."
									+ File.separator + "view" + File.separator + "GameControllerPvPOnline.fxml"));
							try {
								root = loader.load();
								GameControllerPvPOnlineController gameControllerPvPOnlineController = loader.getController();
					            gameControllerPvPOnlineController.startGamePvPOnline(listPlayer.getValue());
							} catch (IOException e) { 
								e.printStackTrace();
							}
							stage = (Stage) (back.getScene().getWindow());
							scene = new Scene(root);
							stage.setScene(scene);
							stage.show();
							setCenterStage(stage);

						} else {
							if (isConnected) {
								System.out.println("Waiting for the second player ... ");
							}
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
