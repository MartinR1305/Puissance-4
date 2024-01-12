package controller.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	private Label algo1, labelLvLA1, algo1alpha2, algo1alpha3, algo1alpha4, algo2, labelLvLA2, algo2alpha2, algo2alpha3,
			algo2alpha4, nbGame, victoriesA1, nbVictoriesA1, draws, nbDraws, victoriesA2, nbVictoriesA2, msgError;

	@FXML
	private Button back, play, allResults;

	@FXML
	private TextField valueLvLA1, valueA1A2, valueA1A3, valueA1A4, valueLvLA2, valueA2A2, valueA2A3, valueA2A4,
			valueNbGame;

	private Algorithm algorithm1, algorithm2;
	private Grid grid;
	int valueVictoriesA1;
	int valueDraws;
	int valueVictoriesA2;

	/**
	 * Method that will be called when the FXML file is opened
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nbVictoriesA1.setText("0");
		nbVictoriesA2.setText("0");
		nbDraws.setText("0");

		valueLvLA1.setText("4");
		valueLvLA2.setText("4");
	}
	
	/**
	 * Method that allows to do a game between two algorithms and to get to results
	 * 
	 * @param algo1 : Algorithm who will start the game
	 * @param algo2 : Algorithm who won't start the game 
	 * @return value : 1 if the player 1 won, -1 if the player 2 won and 0 if it's a draw
	 */
	public int getResultGame(Algorithm algo1, Algorithm algo2) {
		// We initialize a new grid
		grid = new Grid();
		boolean isGameFinished = false;

		// We play the game
		while (!isGameFinished) {
			// We check that the grid is not full
			if (!grid.isGridFull()) {
				grid.addCoinGrid(algorithm1.algoMinMax(grid, false, false), ValueSquare.P1);
				// System.out.println(grid.toString());

				// If J1 won
				if (grid.isJ1win()) {
					isGameFinished = true;
					return 1;
				}
				// We check that the grid is not full
				else if (!grid.isGridFull()) {
					grid.addCoinGrid(algorithm2.algoMinMax(grid, false, false), ValueSquare.P2);
					// System.out.println(grid.toString());

					// If J2 won
					if (grid.isJ2win()) {
						isGameFinished = true;
						return -1;
					}
				}
			} else {
				isGameFinished = true;
				return 0;
			}
		}
		return 0;
	}

	/**
	 * Method that allows to simulate games 10 algorithms for our tests
	 * 
	 * @return listNbVictories : List of integers for each algorithm that says how many games it won
	 */
	public List<Integer> getAllResults() {
		int nbAlgo = 10; 

		List<Integer> listNbVictories = new ArrayList<>(Collections.nCopies(nbAlgo, 0));

		// We will simulate all games
		for (int alpha3A = 3; alpha3A < nbAlgo + 2; alpha3A++) {
			algorithm1 = new Algorithm(4, ValueSquare.P1, ValueSquare.P2, 2, alpha3A, 10000);
			for (int alpha3B = alpha3A + 1; alpha3B < nbAlgo + 3; alpha3B++) {
				algorithm2 = new Algorithm(4, ValueSquare.P1, ValueSquare.P2, 2, alpha3B, 10000);

				// We get the two results
				int resultGame1v2 = getResultGame(algorithm1, algorithm2);
				int resultGame2v1 = getResultGame(algorithm2, algorithm1);
				
				// Adding a victory for the winner | Match 1 vs 2 
				if(resultGame1v2 == 1) {
					System.out.println(alpha3A + " vs " + alpha3B + " : Winner is " + alpha3A);
					listNbVictories.set(alpha3A - 3, listNbVictories.get(alpha3A - 3) + 1);
				} else if (resultGame1v2 == -1) {
					System.out.println(alpha3A + " vs " + alpha3B + " : Winner is " + alpha3B);
					listNbVictories.set(alpha3B - 3, listNbVictories.get(alpha3B - 3) + 1);
				}
				
				// Adding a victory for the winner | Match 2 vs 1 
				if(resultGame2v1 == -1) {
					System.out.println(alpha3B + " vs " + alpha3A + " : Winner is " + alpha3A);
					listNbVictories.set(alpha3A - 3, listNbVictories.get(alpha3A - 3) + 1);
				} else if (resultGame2v1 == 1) {
					System.out.println(alpha3B + " vs " + alpha3A + " : Winner is " + alpha3B);
					listNbVictories.set(alpha3B - 3, listNbVictories.get(alpha3B - 3) + 1);
				}
			}
		}
		return listNbVictories;
	}
	
	/**
	 * Method that allows to display results of the games test
	 */
	public void displayResults() {
		List<Integer> results = getAllResults();

		for (int i = 0 ; i < results.size(); i++) {
			int alpha = i + 3;
			System.out.println("With a α3 value of : " + alpha + " - Algorith won " + results.get(i) + " games.");
		}
	}

	/**
	 * Method that allows to know results from games between two algorithms
	 */
	public void getResult() {

		valueVictoriesA1 = 0;
		valueDraws = 0;
		valueVictoriesA2 = 0;

		// We check that text fields are not empty
		if (!valueLvLA1.getText().isEmpty() && !valueA1A2.getText().isEmpty() && !valueA1A3.getText().isEmpty()
				&& !valueA1A4.getText().isEmpty() && !valueLvLA2.getText().isEmpty() && !valueA2A2.getText().isEmpty()
				&& !valueA2A3.getText().isEmpty() && !valueA2A4.getText().isEmpty()
				&& !valueNbGame.getText().isEmpty()) {

			// We check that text fields are not char
			if (isInteger(valueLvLA1.getText()) && isInteger(valueA1A2.getText()) && isInteger(valueA1A3.getText())
					&& isInteger(valueA1A4.getText()) && isInteger(valueLvLA2.getText())
					&& isInteger(valueA2A2.getText()) && isInteger(valueA2A3.getText())
					&& isInteger(valueA2A4.getText()) && isInteger(valueNbGame.getText())) {

				// We check if the game's number is positive and algorithm's level are
				// acceptable as well
				if (Integer.valueOf(valueNbGame.getText()) > 0 && Integer.valueOf(valueLvLA1.getText()) > 0
						&& Integer.valueOf(valueLvLA2.getText()) > 0 && Integer.valueOf(valueLvLA1.getText()) < 11
						&& Integer.valueOf(valueLvLA2.getText()) < 11) {

					// Data recovery
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
					algorithm1 = new Algorithm(lvlA1, ValueSquare.P2, ValueSquare.P1, algo1alpha2, algo1alpha3,
							algo1alpha4);
					algorithm2 = new Algorithm(lvlA2, ValueSquare.P1, ValueSquare.P2, algo2alpha2, algo2alpha3,
							algo2alpha4);

					// We will simulate all games
					for (int game = 1; game < nbGames + 1; game++) {
						int result = getResultGame(algorithm1, algorithm2);
						
						if(result == 1) {
							valueVictoriesA1++;
						} else if (result == -1) {
							valueVictoriesA2++;
						} else {
							valueDraws++;
						}
					}
					nbVictoriesA1.setText(String.valueOf(valueVictoriesA1));
					nbVictoriesA2.setText(String.valueOf(valueVictoriesA2));
					nbDraws.setText(String.valueOf(valueDraws));

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