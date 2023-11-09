package model;

import java.util.Random;

public class Algorithm {
	
	private int level;
	
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
		
		for(int indexLevel = 1; indexLevel <= level; indexLevel++) {
			for(int indexColumn = 0; indexColumn < 7; indexColumn++) {
				
				if(!grid.getGrid().get(indexColumn).isColumnFull()) { //If the grid is not full
					
					//We create a grid
					Grid newGrid = new Grid(grid);
					
					//We add the coin in the column
					newGrid.addCoinGrid(indexColumn, ValueSquare.P2);
					
					//We evaluate the grid with the new coin
					gridScore = newGrid.evaluateGrid(newGrid);
					
				}
				
				

			}
		}
		return -1;
	}

}
