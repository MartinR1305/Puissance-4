package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Serialization.Serialization;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Player;

public class Main extends Application {

	private static ObservableValue<List<Player>> playersData = new SimpleObjectProperty<List<Player>>();

	public static ObservableValue<List<Player>> getPlayersData() {
		return playersData;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
//			List<Player> deserializedPlayers = new ArrayList<Player>();
//			Player p1 = new Player("Oui", "c'est", "un test", 13);
//			deserializedPlayers.add(p1);

	        // Serialization 
			List<Player> deserializedPlayers = Serialization.deserializePlayer();;
	        Serialization.serializePlayer(deserializedPlayers);
	        ((SimpleObjectProperty<List<Player>>) playersData).setValue(deserializedPlayers);

			// Here we start the application
			Parent root = FXMLLoader
					.load(getClass().getResource(".." + File.separator + "view" + File.separator + "PreHome.fxml"));
			Scene scene1 = new Scene(root);
			primaryStage.setScene(scene1);

			// Here we download the background music and play it
			Media media = new Media(new File("src/sounds/PreHome.mp3").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setVolume(0.1);
			mediaPlayer.play();

			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}