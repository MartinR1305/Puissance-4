package controller.Game;

import java.util.Random;

import Serialization.Serialization;
import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import model.Algorithm;
import model.Grid;
import model.Player;
import model.Results;
import model.ValueSquare;

public class GamePvALocalController extends GameController {

	Algorithm algo;

	/**
	 * Method that allows to start a PvA Local game
	 * 
	 * @param p1, player of the game
	 */
	public void startGamePvALocal(Player p, int level) {

		player1 = p;
		player2 = null;
		algo = new Algorithm(level,ValueSquare.P1,ValueSquare.P2, 2,3,10000);
		grid = new Grid();

		// We put all circles in the matrix
		matrixCircles = new Circle[][] { { c00, c01, c02, c03, c04, c05 }, { c10, c11, c12, c13, c14, c15 },
				{ c20, c21, c22, c23, c24, c25 }, { c30, c31, c32, c33, c34, c35 }, { c40, c41, c42, c43, c44, c45 },
				{ c50, c51, c52, c53, c54, c55 }, { c60, c61, c62, c63, c64, c65 } };

		// We choose randomly the player who will start the game
		Random random = new Random();
		turnPlayer = random.nextInt(2) + 1;

		if (turnPlayer == 1) {
			playerPlaying.setText("It's Your Turn !");
		}

		else {
			playerPlaying.setText("The AI is thinking about a move !");
			
			// We use Platform.runLater in order that the javaFX interface do not freeze when we need to modify something on it
			Platform.runLater(()-> {
				long tempsDebut = System.currentTimeMillis();
				
				grid.addCoinGrid(algo.algoMinMax(grid), ValueSquare.P2);
				
				long tempsFin = System.currentTimeMillis();
				long dureeTotaleMillis = tempsFin - tempsDebut;
		        double dureeSecondes = (dureeTotaleMillis / 1000.0) - 1;
		        long minutes = (long) dureeSecondes / 60;
		        double secondes = dureeSecondes % 60;

		        System.out.printf("Algorithme took %d min %.3f sec to find the best move.%n", minutes, secondes);
				playerPlaying.setText("It's Your Turn !");	
				setColorsGrid(grid);
			});
		}
		setColorsGrid(grid);
	}

	/**
	 * Method for managing the action of clicking on "0" to "6" buttons
	 * 
	 * @param event
	 */
	@FXML
	private void indexColumnSetWithButton(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
		String buttonText = clickedButton.getText();

		// Update the columnAddCoin variable according to the button text
		columnAddCoin = Integer.parseInt(buttonText);

		addCoinGamePvALocal();

	}

	/**
	 * Method that allows to add a coin in the game by a player
	 */
	public void addCoinGamePvALocal() {
		
		// Player Turn
		if (!grid.getGrid().get(columnAddCoin).isColumnFull()) {

			grid.addCoinGrid(columnAddCoin, ValueSquare.P1);
			setColorsGrid(grid);
			
			if (grid.isGridFull()) {
				drawGamePvALocal();
				
			} else if (grid.isJ1win()) {
				setColorsWinningCircles(grid,1);
				winGamePvALocal(player1);
				
			} else { 
				// We continue the game	& We start a thread for the algo's turn
				new Thread(()-> algorithmTurn()).start();
			}
		}
	}

	public void algorithmTurn() {

		// We use Platform.runLater in order that the javaFX interface do not freeze when we need to modify something on it
		Platform.runLater(()-> {
			disableAllButtons();
			playerPlaying.setText("The AI is thinking about a move !");
		});
		
		// Algorithm turn
		long tempsDebut = System.currentTimeMillis();
		
		grid.addCoinGrid(algo.algoMinMax(grid), ValueSquare.P2);
		
		long tempsFin = System.currentTimeMillis();
		long dureeTotaleMillis = tempsFin - tempsDebut;
        double dureeSecondes = (dureeTotaleMillis / 1000.0) - 1;
        long minutes = (long) dureeSecondes / 60;
        double secondes = dureeSecondes % 60;

        System.out.printf("Algorithme took %d min %.3f sec to find the best move.%n", minutes, secondes);
		
		// We use Platform.runLater in order that the javaFX interface do not freeze when we need to modify something on it
		Platform.runLater(()-> {
			setColorsGrid(grid);
			if (grid.isGridFull()) {
				drawGamePvALocal();
				
			} else if (grid.isJ2win()) {
				// We use Platform.runLater in order that the javaFX interface do not freeze when we need to modify something on it
				Platform.runLater(()-> setColorsWinningCircles(grid,2)); 

				looseGamePvALocal(player1);
				
			} else { 
				// We continue the game	
				playerPlaying.setText("Its Your Turn !");
				
				// We use Platform.runLater in order that the javaFX interface do not freeze when we need to modify something on it
				Platform.runLater(()-> enableAllButtons());
			}
		}); 
	}
	
	/**
	 * Method that display a message, set data for a draw and then back to the
	 * previous scene
	 */
	public void drawGamePvALocal() {
		disableAllButtons();

		// Display message
		gameFinish.setText("Game is over ! Nobody won ... It is a draw !");
		gameFinish.setVisible(true);
		playerPlaying.setVisible(false);

		// Add the draw on players's data
		player1.addMatch(player2.getUserName(), Results.DRAW, grid.countCoin());

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that display a message, set data for a victory and then back to the
	 * previous scene
	 * 
	 * @param playerWin, player who won the game
	 */
	public void winGamePvALocal(Player playerWin) {
		disableAllButtons();

		// Display message
		gameFinish.setText("Game is over .. You won the game !");
		gameFinish.setVisible(true);
		playerPlaying.setVisible(false);
		
		// Add the draw on players's data
		playerWin.addMatch("Algorithm", Results.VICTORY, grid.countCoin());

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}

	/**
	 * Method that display a message, set data for a defeat and then back to the
	 * previous scene
	 * 
	 * @param playerLoose, player who the lost the game
	 */
	public void looseGamePvALocal(Player playerLoose) {
		disableAllButtons();

		// Display message
		gameFinish.setText("Game is over .. You lost the game !");
		gameFinish.setVisible(true);
		playerPlaying.setVisible(false);

		// Add the draw on players's data
		playerLoose.addMatch("Algorithm", Results.DEFEAT, grid.countCoin());

		// We serialize
		Serialization.serializePlayer(Main.getPlayersData().getValue());
	}
}
