package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
	        // Here we start the application
	        Parent root = FXMLLoader.load(getClass().getResource(".." + File.separator + "view" + File.separator + "PreHome.fxml"));
	        Scene scene1 = new Scene(root);
	        primaryStage.setScene(scene1);
	       
            // Here we download the background music and play it
            Media media = new Media(new File("src/sounds/foot2rue.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
	        
	        primaryStage.show();
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}