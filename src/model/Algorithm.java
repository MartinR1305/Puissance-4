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

		// Cas où playerMax = P1
		if (playerMax == ValueSquare.P1) {

			// On vérifie si P1 gagne avec 1 coup d'avance
			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {

				Grid gridTestWin = new Grid(grid);
				gridTestWin.addCoinGrid(indexColumn, playerMax);

				if (gridTestWin.isJ1win()) {
					return indexColumn;
				}
			}
		}
		// Cas où playerMax = P2
		else {
			// On vérifie si P2 gagne avec 1 coup d'avance
			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {

				Grid gridTestWin = new Grid(grid);
				gridTestWin.addCoinGrid(indexColumn, playerMax);

				if (gridTestWin.isJ2win()) {
					return indexColumn;
				}
			}
		}

		// Cas où playerMin = P1
		if (playerMin == ValueSquare.P1) {
			// On vérifie si P1 gagne avec 1 coup d'avance
			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {

				Grid gridTestLoose = new Grid(grid);
				gridTestLoose.addCoinGrid(indexColumn, playerMin);

				if (gridTestLoose.isJ1win()) {
					return indexColumn;
				}
			}
		}

		// Cas où playerMin = P1
		else {
			// On vérifie si P2 gagne avec 1 coup d'avance
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

		// Condition d'arrêt
		if (profondeur == level) {
			return grid.evaluateGrid(playerMax) - grid.evaluateGrid(playerMin);
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
					listTemp.set(indexColumn, minimax(newGrid, profondeur + 1, false));
				}
				
				else {
					listTemp.set(indexColumn, grid.evaluateGrid(playerMax) - grid.evaluateGrid(playerMin));
				}
			}

			if (profondeur == 0) {
				System.out.println("\n" + listTemp + "\n");
				return findIndMax(listTemp, grid);
			}
			
			else {
				return findMax(listTemp);
			}
			
		}

		else {
			List<Integer> listTemp = new ArrayList<>(Collections.nCopies(7, 0));
			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {

				// If the grid is not full
				if (!grid.getGrid().get(indexColumn).isColumnFull()) {

					// We create a grid
					Grid newGrid = new Grid(grid);

					// We add the coin in the column
					newGrid.addCoinGrid(indexColumn, playerMin);
					listTemp.set(indexColumn, minimax(newGrid, profondeur + 1, true));
				}
				
				else {
					listTemp.set(indexColumn, grid.evaluateGrid(playerMax) - grid.evaluateGrid(playerMin));
				}
			}
			return findMin(listTemp);
		}
	}

	// VERIFIER QUE LA COLONNE EST PAS PLEINE

	// Fonction pour trouver le maximum d'une ArrayList d'entiers
	public int findMax(List<Integer> list) {
		if (list == null || list.isEmpty()) {
			// Gérer le cas d'une liste vide
			throw new IllegalArgumentException("La liste est vide ou nulle.");
		}

		// Initialiser la variable de maximum avec la première valeur de la liste
		int max = list.get(0);

		// Parcourir la liste pour trouver le maximum
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i) > max) {
				max = list.get(i);
			}
		}

		return max;
	}

	// Fonction pour trouver le maximum d'une ArrayList d'entiers
	public int findMin(List<Integer> list) {
		if (list == null || list.isEmpty()) {
			// Gérer le cas d'une liste vide
			throw new IllegalArgumentException("La liste est vide ou nulle.");
		}

		// Initialiser la variable de maximum avec la première valeur de la liste
		int min = list.get(0);

		// Parcourir la liste pour trouver le maximum
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i) < min) {
				min = list.get(i);
			}
		}

		return min;
	}

	// Fonction pour trouver le maximum d'une ArrayList d'entiers
	public int findIndMax(List<Integer> list, Grid grid) {
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

		if (!grid.getGrid().get(indice).isColumnFull()) {
			return indice;
		}
		
		else {
			List<Integer> newList = new ArrayList<>();
			newList = list;
			newList.set(indice, -1000000000);
			return findIndMax(newList, grid);
		}
	}
}
