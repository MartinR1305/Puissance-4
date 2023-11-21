package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithm {

	// ------------- A FAIRE ------------- //
	// - Gérer le cas où il y a deux max ou deux min
	// - Ajouter des threads pour réduire le temps de calcul
	// - Calculer la limite
	// - Mettre 1s de délai + gérer affichage du tour

	private int level;
	private ValueSquare playerMin;
	private ValueSquare playerMax;

	/**
	 * Constructor
	 * 
	 * @param level
	 * @param playerMin
	 * @param playerMax
	 */
	public Algorithm(int level, ValueSquare playerMin, ValueSquare playerMax) {
		this.level = level;
		this.playerMax = playerMax;
		this.playerMin = playerMin;
	}

	/**
	 * Setter for the level
	 * 
	 * @param level
	 */
	public void setLvl(int level) {
		this.level = level;
	}

	/**
	 * Getter for the level
	 * 
	 * @return
	 */
	public int getLvl() {
		return this.level;
	}

	/**
	 * Method that allows to apply the MinMax algorithm for a grid
	 * 
	 * @param grid
	 * @return
	 */
	public int algoMinMax(Grid grid) {

		// We check if the other wins with a next move
		for (int indexColumn = 0; indexColumn < 7; indexColumn++) {

			Grid gridTestWin = new Grid(grid);
			gridTestWin.addCoinGrid(indexColumn, playerMax);

			if (gridTestWin.isJ1win()) {
				return indexColumn;
			}
		}

		// We call the recursive method
		return minimax(grid, 0, true);
	}

	/**
	 * Recursive method for the MinMax algorithm for a grid
	 * 
	 * @param grid
	 * @param profondeur
	 * @param estJoueurMax
	 * @return
	 */
	private int minimax(Grid grid, int currentDepth, boolean isPlayerMax) {
		
		// Stop condition : if we have reached the level we wanted
		if (currentDepth == level) {
			return grid.evaluateGrid(playerMax) - grid.evaluateGrid(playerMin);
		}

		// We will take the maximum of the 7 values
		if (isPlayerMax) {

			// Initialization of the list
			List<Integer> listTemp = new ArrayList<>(Collections.nCopies(7, 0));

			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {

				// We check if the grid is not full
				if (!grid.getGrid().get(indexColumn).isColumnFull()) {

					// We check if the is not winning for a player
					if (!grid.isJ1win() && !grid.isJ2win()) {
						// We create a grid
						Grid newGrid = new Grid(grid);

						// We add the coin in the column
						newGrid.addCoinGrid(indexColumn, playerMax);

						// We add the good value by calling the the recursive method
						listTemp.set(indexColumn, minimax(newGrid, currentDepth + 1, false));
					}

					// If it is
					else {
						listTemp.set(indexColumn, grid.evaluateGrid(playerMax) - grid.evaluateGrid(playerMin));
					}
				}

				// If the column is full we stop here ( we don't need to call the recursive
				// method )
				else {
					listTemp.set(indexColumn, grid.evaluateGrid(playerMax) - grid.evaluateGrid(playerMin));
				}
			}

			// For the root we want the index and not the value of the maximum
			if (currentDepth == 0) {
				System.out.println("\n" + listTemp + "\n");
				return findIndMax(listTemp, grid);
			}

			// For leafs and nodes we want the maximum value
			else {
				return findMax(listTemp);
			}
		}

		// We will take the minimum of the 7 values
		else {

			// Initialization of the list
			List<Integer> listTemp = new ArrayList<>(Collections.nCopies(7, 0));

			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {

				// We check if the grid is not full
				if (!grid.getGrid().get(indexColumn).isColumnFull()) {

					// We check if the is not winning for a player
					if (!grid.isJ1win() && !grid.isJ2win()) {

						// We create a grid
						Grid newGrid = new Grid(grid);

						// We add the coin in the column
						newGrid.addCoinGrid(indexColumn, playerMin);

						// We add the good value by calling the the recursive method
						listTemp.set(indexColumn, minimax(newGrid, currentDepth + 1, true));

					}
					
					// If it is
					else  {
						listTemp.set(indexColumn, grid.evaluateGrid(playerMax) - grid.evaluateGrid(playerMin));
					}
				}

				// If the column is full we stop here ( we don't need to call the recursive
				// method )
				else {
					listTemp.set(indexColumn, grid.evaluateGrid(playerMax) - grid.evaluateGrid(playerMin));
				}
			}

			// For leafs and nodes we want the maximum value ( There will be no root for
			// playerMin )
			return findMin(listTemp);
		}
	}

	/**
	 * Method that allows to obtain the max of a list
	 * 
	 * @param list
	 * @return
	 */
	public int findMax(List<Integer> list) {

		// We check that the list is not null or empty
		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException("The list is empty or null.");
		}

		int max = list.get(0);

		for (int i = 1; i < list.size(); i++) {

			if (list.get(i) > max) {
				max = list.get(i);
			}
		}
		return max;
	}

	/**
	 * Method that allows to obtain the min of a list
	 * 
	 * @param list
	 * @return
	 */
	public int findMin(List<Integer> list) {

		// We check that the list is not null or empty
		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException("The list is empty or null.");
		}

		int min = list.get(0);

		for (int i = 1; i < list.size(); i++) {

			if (list.get(i) < min) {
				min = list.get(i);
			}
		}
		return min;
	}

	/**
	 * Method that allows to obtain the index of the maximum value
	 * 
	 * @param list
	 * @param grid
	 * @return
	 */
	public int findIndMax(List<Integer> list, Grid grid) {

		// We check that the list is not null or empty
		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException("The list is empty or null.");
		}

		int max = list.get(0);
		int indice = 0;

		for (int i = 1; i < list.size(); i++) {

			if (list.get(i) > max) {
				max = list.get(i);
				indice = i;
			}
		}

		// We check that the column where we can not to add the coin is not full
		if (!grid.getGrid().get(indice).isColumnFull()) {
			return indice;
		}

		// If it's full
		else {
			List<Integer> newList = new ArrayList<>();
			newList = list;
			newList.set(indice, -1000000000);
			return findIndMax(newList, grid);
		}
	}
}
