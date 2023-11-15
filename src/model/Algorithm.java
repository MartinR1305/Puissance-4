package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Algorithm {

	private int level;
	private ValueSquare playerMin;
	private ValueSquare playerMax;

	public Algorithm(int level) {
		this.level = level;
	}

	public void setLvl(int level) {
		this.level = level;
	}

	public int testAlgo(Grid grid) {

		Random random = new Random();
		return random.nextInt(6) + 1;
	}

	public int makeMinimax(Grid grid, int level) {

		int gridScore = 0;

		for (int indexLevel = 1; indexLevel <= level; indexLevel++) {
			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {

				if (!grid.getGrid().get(indexColumn).isColumnFull()) { // If the grid is not full

					// We create a grid
					Grid newGrid = new Grid(grid);

					// We add the coin in the column
					newGrid.addCoinGrid(indexColumn, ValueSquare.P2);

					// We evaluate the grid with the new coin
					// gridScore = newGrid.evaluateGrid();

				}

			}
		}
		return -1;
	}

	// Méthode publique appelée pour lancer l'algorithme Minimax
	public int algoMinMax(Grid grid, ValueSquare playerMax) {

		this.playerMax = playerMax;

		// We assign the good token to the other player
		if (playerMax == ValueSquare.P1) {
			playerMin = ValueSquare.P2;
		} else {
			playerMin = ValueSquare.P1;
		}
		
		
		if (playerMax == ValueSquare.P1) {
			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {
				Grid gridTestWin = new Grid(grid);
				gridTestWin.addCoinGrid(indexColumn, playerMax);
				if (gridTestWin.isJ1win()) {
					return indexColumn;
				}
			}
		} else {
			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {
				Grid gridTestWin = new Grid(grid);
				gridTestWin.addCoinGrid(indexColumn, playerMax);
				if (gridTestWin.isJ2win()) {
					return indexColumn;
				}
			}
		}


		if (playerMin == ValueSquare.P1) {
			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {
				Grid gridTestLoose = new Grid(grid);
				gridTestLoose.addCoinGrid(indexColumn, playerMin);
				if (gridTestLoose.isJ1win()) {
					return indexColumn;
				}
			}
		} else {
			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {
				Grid gridTestLoose = new Grid(grid);
				gridTestLoose.addCoinGrid(indexColumn, playerMin);
				if (gridTestLoose.isJ2win()) {
					return indexColumn;
				}
			}
		}
		// Appel de la méthode Minimax avec les paramètres appropriés
		return minimax(grid, 0, true);
	}

	// Méthode Minimax récursive
	private int minimax(Grid grid, int profondeur, boolean estJoueurMax) {

		// Condition d'arrêt : si la profondeur maximale est atteinte ou si la grille
		// est pleine, évaluer la grille ou un joueur a gagné
		if (profondeur == level || grid.isGridFull()) {
			if (estJoueurMax) {
				System.out.println("Profondeur = " + profondeur + " | " + estJoueurMax + " : " + grid.evaluateGrid(playerMax));
				return grid.evaluateGrid(playerMax);
			} else {
				System.out.println("Profondeur = " + profondeur + " | " + estJoueurMax + " : " + grid.evaluateGrid(playerMin));
				return grid.evaluateGrid(playerMin);
			}
		}

		// Si c'est le tour du joueur maximisant, maximiser la valeur
		if (estJoueurMax) {
			List<Integer> listTemp = new ArrayList<>(Collections.nCopies(7, 0));

			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {

				// If the grid is not full
				if (!grid.getGrid().get(indexColumn).isColumnFull()) {

					// We create a grid
					Grid newGrid = new Grid(grid);

					// We add the coin in the column
					newGrid.addCoinGrid(indexColumn, playerMax);
					listTemp.set(indexColumn, minimax(newGrid, profondeur, false) + newGrid.evaluateGrid(playerMax));
				}
			}
			if(profondeur == 0) {
				System.out.println("\n" + listTemp + "\n");
			}

			return findIndMax(listTemp);
		}

		else {
			List<Integer> listTemp = new ArrayList<>(Collections.nCopies(7, 0));

			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {

				if (!grid.getGrid().get(indexColumn).isColumnFull()) { // If the grid is not full

					// We create a grid
					Grid newGrid = new Grid(grid);

					// We add the coin in the column
					newGrid.addCoinGrid(indexColumn, playerMin);
					listTemp.set(indexColumn, minimax(newGrid, profondeur + 1, true) - newGrid.evaluateGrid(playerMin));
				}
			}
			return findIndMin(listTemp);
		}
	}

	// Fonction pour trouver le maximum d'une ArrayList d'entiers
	public int findIndMax(List<Integer> list) {
		if (list == null || list.isEmpty()) {
			// Gérer le cas d'une liste vide
			throw new IllegalArgumentException("La liste est vide ou nulle.");
		}

		// Initialiser la variable de maximum avec la première valeur de la liste
		int max = list.get(0);
		int indice = 0;

		// Parcourir la liste pour trouver le maximum
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i) > max) {
				max = list.get(i);
				indice = i;
			}
		}

		return indice;
	}

	// Fonction pour trouver le maximum d'une ArrayList d'entiers
	public int findIndMin(List<Integer> list) {
		if (list == null || list.isEmpty()) {
			// Gérer le cas d'une liste vide
			throw new IllegalArgumentException("La liste est vide ou nulle.");
		}

		for (int i = 1; i < list.size(); i++) {
			if (list.get(i) > 9000) {
				System.out.println("icicici");
				return findIndMax(list);
			}
		}

		// Initialiser la variable de maximum avec la première valeur de la liste
		int min = list.get(0);
		int indice = 0;

		// Parcourir la liste pour trouver le maximum
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i) < min) {
				min = list.get(i);
				indice = i;
			}
		}

		return indice;
	}
}
