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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import model.Player;

public class ChoiceOnlineGameController extends HomeController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;

	private ClientTCP clientTCP;
	private static boolean isConnected, areTwoPlayersConnected;
	private boolean isPlayerRBSelected;
	private boolean isAlgoRBSelected;

	@FXML
	private Label IP, port, choosePlayer, isConnectedToServer, msgError, msgError2, playerChoice, algorithmChoice,
			chooseAlgoLvl, questionStartGame;

	@FXML
	private Button back, connectToTheServer, disconnectFromServer;

	@FXML
	private TextField ipValue, portValue;

	@FXML
	private ComboBox<Player> listPlayer;

	@FXML
	private ComboBox<Integer> algoLvl;

	@FXML
	private RadioButton playerRB, algoRB;
	
	@FXML
	private ToggleButton isStarting;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// We actualize the client attribute with the one of the main
		setClientTCP(Main.getClientTCP());

		isPlayerRBSelected = false;
		isAlgoRBSelected = false;

		// We initialize data on the FXML file
		setComboBoxWithPlayers(listPlayer);
		setComboBoxWithLevels(algoLvl);
		isConnectedToServer.setText("Disconnected");
		isStarting.setStyle("-fx-text-fill: red;");
		isStarting.setSelected(true);
		playerRB.setSelected(true);

		// We start the updating of the state
		this.updateState();

		// We look if we can start a game
		this.switchToGame();
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
	 * Method that allows to connect to a server by using two text fields
	 * 
	 * @throws IOException
	 */
	public void connectToServer() throws IOException {

		// We check that that the port and the IP are filled
		if (ipValue.getText() == "" || portValue.getText() == "") {
			displayMessage(msgError);

		} else {

			// Case where it will be a player who plays
			if (playerRB.isSelected()) {

				// We check that a player had been selected
				if (listPlayer.getValue() != null) {
					isPlayerRBSelected = true;
					clientTCP.changeIP_Port(ipValue.getText(), portValue.getText());
				} else {
					displayMessage(msgError2);
				}
			}

			// Case where it will be a algorithm who plays
			else if (algoRB.isSelected()) {

				// We check that a algorithm's level had been selected
				if (algoLvl.getValue() != null) {
					isAlgoRBSelected = true;
					clientTCP.changeIP_Port(ipValue.getText(), portValue.getText());
				} else {
					displayMessage(msgError2);
				}
			}
		}
	}

	public void disconnectFromServer() {
		isPlayerRBSelected = false;
		isAlgoRBSelected = false;
		clientTCP.changeIP_Port("", "0");
	}

	public void changeToPlayerChoice() {
		if (playerRB.isSelected() && algoRB.isSelected()) {
			// We select the algorithm choice and not select the player choice
			playerRB.setSelected(true);
			algoRB.setSelected(false);

			// We display player's choice
			choosePlayer.setVisible(true);
			choosePlayer.setDisable(false);
			listPlayer.setVisible(true);
			listPlayer.setDisable(false);

			// We hide algorithm's choice
			chooseAlgoLvl.setVisible(false);
			chooseAlgoLvl.setDisable(true);
			algoLvl.setVisible(false);
			algoLvl.setDisable(true);
			
			// We hide the choice for determinate if the player will start the game
			isStarting.setVisible(false);
			isStarting.setDisable(true);
			questionStartGame.setVisible(false);
			questionStartGame.setDisable(true);

			// We change the color of the label
			playerChoice.setStyle("-fx-text-fill: #ffff00;");
			algorithmChoice.setStyle("-fx-text-fill: white;");
		}

		playerRB.setSelected(true);
	}

	public void changeToAlgoChoice() {

		if (playerRB.isSelected() && algoRB.isSelected()) {
			// We select the algorithm choice and not select the player choice
			playerRB.setSelected(false);
			algoRB.setSelected(true);

			// We display algorithm's choice
			chooseAlgoLvl.setVisible(true);
			chooseAlgoLvl.setDisable(false);
			algoLvl.setVisible(true);
			algoLvl.setDisable(false);
			
			// We display the choice for determinate if the player will start the game
			isStarting.setVisible(true);
			isStarting.setDisable(false);
			questionStartGame.setVisible(true);
			questionStartGame.setDisable(false);

			// We hide player's choice
			choosePlayer.setVisible(false);
			choosePlayer.setDisable(true);
			listPlayer.setVisible(false);
			listPlayer.setDisable(true);

			// We change the color of the label
			algorithmChoice.setStyle("-fx-text-fill: #ffff00;");
			playerChoice.setStyle("-fx-text-fill: white;");
		}

		algoRB.setSelected(true);
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
	
	public void updateToggleButton() {
		if(isStarting.isSelected()) {
			isStarting.setText("NO");
			isStarting.setStyle("-fx-text-fill: red;");
		} else {
			isStarting.setText("YES");
			isStarting.setStyle("-fx-text-fill: green;");
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
							isConnectedToServer.setStyle("-fx-text-fill: #00ff2e;");

							// We disable the button back and connect
							back.setDisable(true);
							back.setVisible(false);
							connectToTheServer.setDisable(true);
							connectToTheServer.setVisible(false);
							disconnectFromServer.setDisable(false);
							disconnectFromServer.setVisible(true);
						} else {
							isConnectedToServer.setText("Disconnected");
							isConnectedToServer.setStyle("-fx-text-fill: red;");

							// We activate the button back and connect
							back.setDisable(false);
							back.setVisible(true);
							connectToTheServer.setDisable(false);
							connectToTheServer.setVisible(true);
							disconnectFromServer.setDisable(true);
							disconnectFromServer.setVisible(false);
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
								GameOnlineController gameControllerPvPOnlineController = loader.getController();
								
								if (isPlayerRBSelected) {
									gameControllerPvPOnlineController.startGamePlayer(listPlayer.getValue());
								}

								else if (isAlgoRBSelected) {
									gameControllerPvPOnlineController.startGameAlgorithm(algoLvl.getValue(), !isStarting.isSelected());
								}
								
							} catch (IOException e) {
								e.printStackTrace();
							}
							stage = (Stage) (back.getScene().getWindow());
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
