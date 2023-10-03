package model;

import java.util.ArrayList;
import java.util.List;

public class Grid {
	
	private List<Column> grid;
	
	/**
	 * Constructor
	 * @param grid
	 */
	public Grid(List<Column> grid) {
		grid = new ArrayList<Column>(7);
	}
	
	/**
	 * Getter for the grid list
	 * @return
	 */
	public List<Column> getGrid() {
		return grid;
	}
	
	/**
	 * Method returning if the grid is empty
	 * @return boolean
	 */
	public boolean isGridEmpty() {
		
		for (Column column : grid) {
			
			//If the column is not empty, the grid neither
	        if (!column.isColumnEmpty()) {
	            return false; 
	        }
	    }
	    return true; 
	}
	
	/**
	 * Method returning if the grid is full
	 * @return boolean
	 */
	public boolean isGridFull() {
		
		for (Column column : grid) {
			
			//If the column is not full, the grid neither
	        if (!column.isColumnFull()) {
	            return false; 
	        }
	    }
	    return true; 
	}
	
	/**
	 * Method returning if the player 1 wins the game
	 * @return boolean
	 */
	public boolean isJ1win() {
		
		//First we verify the columns
	    for (Column column : grid) {
	        if (column.isColumnWinJ1()) {
	            return true;
	        }
	    }

	    //We verify the rows
	    
	    //We verify the diagonals

	    //There is no combinations
	    return false;
	}
	
	
	
	
	

}
