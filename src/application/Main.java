package application;

import java.io.File;
import java.io.IOException;
import java.util.List;

import Serialization.Serialization;
import controller.Game.ChoiceOnlineGameController;
import controller.Game.GameOnlineController;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Grid;
import model.Player;
import model.ValueSquare;

public class Main extends Application {

	private ChoiceOnlineGameController clientController;
	private GameOnlineController gameController;
	private static ClientTCP clientTCP;
	private static ObservableValue<List<Player>> playersData = new SimpleObjectProperty<List<Player>>();

	/**
	 * Method for do preparation and start the Connection to the server ( null at
	 * the beginning ) in a thread
	 * 
	 * @throws IOException
	 */
	public Main() throws IOException {
		// We create the client that we will use in the application
		clientController = new ChoiceOnlineGameController();
		gameController = new GameOnlineController();

		clientTCP = new ClientTCP("", 0, clientController, gameController);

		clientController.setClientTCP(clientTCP);
		gameController.setClientTCP(clientTCP);

		// We try to connect him to the server ( don't work because IP and port are null
		// for the moment )
		new Thread(() -> {
			try {
				clientTCP.connectToServer();
			} catch (IOException IOError) {
				IOError.printStackTrace();
			}
		}).start();
	}

	/**
	 * Method for start the application and open the FXML file
	 */
	@Override
	public void start(Stage primaryStage) {
		try {

//            List<Player> deserializedPlayers = new ArrayList<Player>();
//            Player p1 = new Player("Default", "1", "Player", 10);
//            Player p2 = new Player("Default", "2", "Player", 20);
//            deserializedPlayers.add(p1);
//            deserializedPlayers.add(p2);

			// Serialization
			List<Player> deserializedPlayers = Serialization.deserializePlayer();
			Serialization.serializePlayer(deserializedPlayers);
			((SimpleObjectProperty<List<Player>>) playersData).setValue(deserializedPlayers);

			// Here we start the application
			Parent root = FXMLLoader
					.load(getClass().getResource(".." + File.separator + "view" + File.separator + "PreHome.fxml"));
			Scene scene1 = new Scene(root);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene1);
			primaryStage.centerOnScreen();

			// Here we download the background music and play it
//            Media media = new Media(new File("src/ressources/sounds/PreHome.mp3").toURI().toString());
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.setVolume(0.1);
//            mediaPlayer.play();

			primaryStage.show();

			// Set on center the stage
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()) / 2);
			primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()) / 2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter for the client of the application
	 * 
	 * @return clientTCP : clientTCP of the application
	 */
	public static ClientTCP getClientTCP() {
		return clientTCP;
	}

	/**
	 * Getter for the player's list
	 * 
	 * @return playersData : List of Data's players
	 */
	public static ObservableValue<List<Player>> getPlayersData() {
		return playersData;
	}

	/**
	 * Method that allows to close the client when the FXML file is closed as well
	 * 
	 * @throws Exception
	 */
	@Override
	public void stop() throws Exception {
		System.out.println("Client is about to close");

		if (clientTCP != null) {
			clientTCP.close();
		}
	}

	/**
	 * main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}