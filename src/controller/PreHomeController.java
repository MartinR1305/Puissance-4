package controller;


import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class PreHomeController extends ForAllControllers implements Initializable{
	@FXML
	private Button buttonClickToPlay;

	@FXML
	private Label puiss4, labelClickToPlay, saadMartin;

	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), labelClickToPlay);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.0);
		fadeTransition.setCycleCount(Animation.INDEFINITE);
		fadeTransition.setAutoReverse(true);
		fadeTransition.play();
		
	}
	

}
