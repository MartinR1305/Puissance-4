package controller.Test;

import java.net.URL;
import java.util.ResourceBundle;

import controller.ForAllControllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Algorithm;
import model.Grid;
import model.ValueSquare;

public class TestAlgovsAlgoController extends ForAllControllers implements Initializable {
	@FXML
	private Label algo1, labelLvLA1, algo1alpha2, algo1alpha3, algo1alpha4, algo2, labelLvLA2, algo2alpha2, algo2alpha3, algo2alpha4, nbGame,
			victoriesA1, nbVictoriesA1, draws, nbDraws, victoriesA2, nbVictoriesA2, msgError;

	@FXML
	private Button back, play;

	@FXML
	private TextField valueLvLA1, valueA1A2, valueA1A3, valueA1A4, valueLvLA2, valueA2A2, valueA2A3, valueA2A4, valueNbGame;

	private Algorithm algorithm1, algorithm2;
	private Grid grid;
	int valueVictoriesA1;
	int valueDraws;
	int valueVictoriesA2;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nbVictoriesA1.setText("0");
		nbVictoriesA2.setText("0");
		nbDraws.setText("0");
		
		valueLvLA1.setText("4");
		valueLvLA2.setText("4");
	}

	public void playAvsA() {

		valueVictoriesA1 = 0;
		valueDraws = 0;
		valueVictoriesA2 = 0;

		// We check that text fields are not empty
		if (!valueLvLA1.getText().isEmpty() && !valueA1A2.getText().isEmpty() && !valueA1A3.getText().isEmpty() && !valueA1A4.getText().isEmpty() &&
				!valueLvLA2.getText().isEmpty() && !valueA2A2.getText().isEmpty() && !valueA2A3.getText().isEmpty() && !valueA2A4.getText().isEmpty()
				&& !valueNbGame.getText().isEmpty()) {

			// We check that text fields are not char
			if (isInteger(valueLvLA1.getText()) && isInteger(valueA1A2.getText()) && isInteger(valueA1A3.getText()) && isInteger(valueA1A4.getText())
					&& isInteger(valueLvLA2.getText()) && isInteger(valueA2A2.getText()) && isInteger(valueA2A3.getText())
					&& isInteger(valueA2A4.getText()) && isInteger(valueNbGame.getText())) {

				// We check if the game's number is positive and algorithm's level are acceptable as well
				if (Integer.valueOf(valueNbGame.getText()) > 0 && Integer.valueOf(valueLvLA1.getText()) > 0 && Integer.valueOf(valueLvLA2.getText()) > 0 && Integer.valueOf(valueLvLA1.getText()) < 11 && Integer.valueOf(valueLvLA2.getText()) < 11) {

					// Data recovery
					boolean isGameFinished = false;
					int nbGames = Integer.valueOf(valueNbGame.getText());
					
					int algo1alpha2 = Integer.valueOf(valueA1A2.getText());
					int algo1alpha3 = Integer.valueOf(valueA1A3.getText());
					int algo1alpha4 = Integer.valueOf(valueA1A4.getText());
					int algo2alpha2 = Integer.valueOf(valueA2A2.getText());
					int algo2alpha3 = Integer.valueOf(valueA2A3.getText());
					int algo2alpha4 = Integer.valueOf(valueA2A4.getText());
					
					int lvlA1 = Integer.valueOf(valueLvLA1.getText());
					int lvlA2 = Integer.valueOf(valueLvLA2.getText());

					// We initialize algorithms
					algorithm1 = new Algorithm(lvlA1, ValueSquare.P1, ValueSquare.P2, algo1alpha2, algo1alpha3,
							algo1alpha4);
					algorithm2 = new Algorithm(lvlA2, ValueSquare.P2, ValueSquare.P1, algo2alpha2, algo2alpha3,
							algo2alpha4);

					// We will simulate all games
					for (int game = 1; game < nbGames + 1; game++) {
						// We initialize a new grid
						grid = new Grid();
						isGameFinished = false;

						// We play the game
						while (!isGameFinished) {
							// We check that the grid is not full
							if (!grid.isGridFull()) {
								grid.addCoinGrid(algorithm1.algoMinMax(grid), ValueSquare.P1);
								// System.out.println(grid.toString());

								// If J1 won
								if (grid.isJ1win()) {
									valueVictoriesA1++;
									isGameFinished = true;
								}
								// We check that the grid is not full
								else if (!grid.isGridFull()) {
									grid.addCoinGrid(algorithm2.algoMinMax(grid), ValueSquare.P2);
									// System.out.println(grid.toString());

									// If J2 won
									if (grid.isJ2win()) {
										valueVictoriesA2++;
										isGameFinished = true;
									}
								} else {
									valueDraws++;
									isGameFinished = true;
								}
							} else {
								valueDraws++;
								isGameFinished = true;
							}
						}
					}
					
					nbVictoriesA1.setText(String.valueOf(valueVictoriesA1));
					nbVictoriesA2.setText(String.valueOf(valueDraws));
					nbDraws.setText(String.valueOf(valueVictoriesA2));

				} else {
					displayMessage(msgError);
				}
			} else {
				displayMessage(msgError);
			}

		} else {
			displayMessage(msgError);
		}
	}
}
