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
	private int threadFinish;

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
		threadFinish = 0;

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

			if (currentDepth == 0) {

				List<Thread> listThreadsMax = new ArrayList<>();

				// Initialization of the list
				List<Integer> listTemp = new ArrayList<>(Collections.nCopies(7, 0));

				for (int indexColumn = 0; indexColumn < 7; indexColumn++) {

					int indexThread = indexColumn;

					Thread thread = new Thread(new Runnable() {
						public void run() {
							repetCodes(grid, listTemp, indexThread, currentDepth, isPlayerMax, playerMax);
						}
					});
					listThreadsMax.add(thread);
					thread.start();
					threadFinish++;
				}

				// Attendez que tous les threads se terminent
				for (Thread thread : listThreadsMax) {
					try {
						thread.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// For the root we want the index and not the value of the maximum
				System.out.println("\n" + listTemp + "\n");
				System.out.println(threadFinish + " threads utilisés.");
				return findIndMax(listTemp, grid);
			}

			else {
				// Initialization of the list
				List<Integer> listTemp = new ArrayList<>(Collections.nCopies(7, 0));

				for (int indexColumn = 0; indexColumn < 7; indexColumn++) {
					repetCodes(grid, listTemp, indexColumn, currentDepth, isPlayerMax, playerMax);
				}
				return findMax(listTemp);
			}
		}

		// We will take the minimum of the 7 values
		else {
			// Initialization of the list
			List<Integer> listTemp = new ArrayList<>(Collections.nCopies(7, 0));

			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {
				repetCodes(grid, listTemp, indexColumn, currentDepth, isPlayerMax, playerMin);
			}

			// For leafs and nodes we want the minimum value ( There will be no root for
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

		List<Integer> tempList = new ArrayList<>();
		tempList = list;

		// we eliminate all full columns
		for (int indexColumn = 0; indexColumn < tempList.size(); indexColumn++) {
			if (grid.getGrid().get(indexColumn).isColumnFull()) {
				tempList.set(indexColumn, -1000000000);
			}
		}

		// Declaration and Initialization
		List<Integer> listIndiceMax = new ArrayList<>();
		boolean isSeveralMax = false;
		int max = tempList.get(0);
		int indice = 0;
		listIndiceMax.add(0);

		for (int indexColumn = 1; indexColumn < tempList.size(); indexColumn++) {

			// If the value is higher than to the max
			if (tempList.get(indexColumn) > max) {
				// We actualize the max and the index
				max = tempList.get(indexColumn);
				indice = indexColumn;

				// Then we actualize the list and the boolean
				isSeveralMax = false;
				listIndiceMax.clear();
				listIndiceMax.add(indexColumn);
			}

			// If the value is higher than to the max
			else if (tempList.get(indexColumn) == max) {
				// Then we actualize the list and the boolean
				isSeveralMax = true;
				listIndiceMax.add(indexColumn);
			}
		}

		// If there are several max in the list
		if (isSeveralMax) {

			// We return the nearest column from the middle with a left priority
			if (listIndiceMax.contains(3)) {
				return 3;
			} else if (listIndiceMax.contains(2)) {
				return 2;
			} else if (listIndiceMax.contains(4)) {
				return 4;
			} else if (listIndiceMax.contains(1)) {
				return 1;
			} else if (listIndiceMax.contains(5)) {
				return 5;
			} else if (listIndiceMax.contains(0)) {
				return 0;
			} else {
				return 6;
			}
		}

		// If there are not
		else {
			return indice;
		}
	}

	public void repetCodes(Grid grid, List<Integer> listTemp, int index, int currentDepth, boolean isPlayerMax,
			ValueSquare player) {
		// We check if the grid is not full
		if (!grid.getGrid().get(index).isColumnFull()) {

			// We check if the is not winning for a player
			if (!grid.isJ1win() && !grid.isJ2win()) {
				// We create a grid
				Grid newGrid = new Grid(grid);

				// We add the coin in the column
				newGrid.addCoinGrid(index, player);

				// We add the good value by calling the the recursive method
				listTemp.set(index, minimax(newGrid, currentDepth + 1, !isPlayerMax));
			}

			// If it is
			else {
				listTemp.set(index, grid.evaluateGrid(playerMax) - grid.evaluateGrid(playerMin));
			}
		}

		// If the column is full we stop here ( we don't need to call the recursive
		// method )
		else {
			listTemp.set(index, grid.evaluateGrid(playerMax) - grid.evaluateGrid(playerMin));
		}
	}
}
