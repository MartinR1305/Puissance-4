package controller.Home;


import java.util.ResourceBundle;

import controller.ForAllControllers;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PreHomeController extends ForAllControllers implements Initializable{
    @FXML
    private Button buttonClickToPlay;
    
    @FXML
    private ImageView imagePolytech;

    @FXML
    private Label connect, four, labelClickToPlay, saadMartin;

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        
        // We set the image
        Image image = new Image("ressources/images/Logo_Polytech.svg.png");
        imagePolytech.setImage(image);
        
        // We set the transition of a label
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), labelClickToPlay);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }
}