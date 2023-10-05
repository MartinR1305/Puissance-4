package model;

import java.util.ArrayList;
import java.util.List;

public class Grid {
	
	private List<Column> grid;
	
	/**
	 * Constructor
	 * @param grid
	 */
	public Grid() {
		grid = new ArrayList<Column>(7);
		
		// We have to add columns in the grid
	    for (int i = 0; i < 7; i++) { 
	        grid.add(new Column());
	    }
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
	 * Method returning if the player 1 is winning the game in a line
	 * @return boolean
	 */
	public boolean isLineJ1win() {
		
		for (int line = 0; line < 6; line++) {
			
			//We count the consecutive P1 coins 
	        int consecutiveCount = 0; 

	        for (Column column : grid) {
	            if (column.getColumn().size() <= line) {
	            	
	            	//We reinitialize to the counter if there is no coins in the line
	                consecutiveCount = 0;
	                
	                //We pass to the following column
	                continue; 
	            }

	            Square square = column.getColumn().get(line);

	            
	            //If the line's coin is P1, we increment the counter
	            if (square.getValue() == ValueSquare.P1) {
	                consecutiveCount++;
	            } else {
	            	
	            	//We reinitialize the counter if there is no P1 coins
	                consecutiveCount = 0;
	            }

	            //If we have 4 consecutive  P1 coins, the player 1 wins
	            if (consecutiveCount >= 4) {
	                return true;
	            }
	        }
	    }

	    //The player 1 doesn't win the game
	    return false;
	}

	
	/**
	 * Method returning if the player 2 is winning the game in a line
	 * @return boolean
	 */
	public boolean isLineJ2win() {
		
		for (int line = 0; line < 6; line++) {
			
			//We count the consecutive P2 coins 
	        int consecutiveCount = 0; 

	        for (Column column : grid) {
	            if (column.getColumn().size() <= line) {
	            	
	            	//We reinitialize to the counter if there is no coins in the line
	                consecutiveCount = 0;
	                
	                //We pass to the following column
	                continue; 
	            }

	            Square square = column.getColumn().get(line);

	            
	            //If the line's coin is P2, we increment the counter
	            if (square.getValue() == ValueSquare.P2) {
	                consecutiveCount++;
	            } else {
	            	
	            	//We reinitialize the counter if there is no P2 coins
	                consecutiveCount = 0;
	            }

	            //If we have 4 consecutive  P2 coins, the player 2 wins
	            if (consecutiveCount >= 4) {
	                return true;
	            }
	        }
	    }

	    //The player 2 doesn't win the game
	    return false;
	}
	
	/**
	 * Method returning if the player 1 is winning the game in a diagonal
	 * @return boolean
	 */
	public boolean isDiagJ1win() {
		
		//Number of lines in the grid
		int rowCount = 6; 
		
		//Number of columns in the grid
	    int colCount = 7; 

	    //We check descending diagonals
	    for (int row = 0; row < rowCount - 3; row++) {
	        for (int col = 0; col < colCount - 3; col++) {
	            boolean isWin = true;

	            for (int i = 0; i < 4; i++) {
	                Square square = grid.get(col + i).getColumn().get(row + i);

	                if (square.getValue() != ValueSquare.P1) {
	                    isWin = false;
	                    break;
	                }
	            }

	            if (isWin) {
	            	
	            	//The player 1 wins on a descending diagonal
	                return true; 
	            }
	        }
	    }

	    //We check ascending diagonals
	    for (int row = 3; row < rowCount; row++) {
	        for (int col = 0; col < colCount - 3; col++) {
	            boolean isWin = true;

	            for (int i = 0; i < 4; i++) {
	                Square square = grid.get(col + i).getColumn().get(row - i);

	                if (square.getValue() != ValueSquare.P1) {
	                    isWin = false;
	                    break;
	                }
	            }

	            if (isWin) {
	            	
	            	//The player 1 wins on an ascending diagonal
	                return true; 
	            }
	        }
	    }

	    //The player 1 doesn't win on a diagonal
	    return false;
	}
	
	/**
	 * Method returning if the player 2 is winning the game in a diagonal
	 * @return boolean
	 */
	public boolean isDiagJ2win() {
		
		//Number of lines in the grid
		int rowCount = 6; 
		
		//Number of columns in the grid
	    int colCount = 7; 

	    //We check descending diagonals
	    for (int row = 0; row < rowCount - 3; row++) {
	        for (int col = 0; col < colCount - 3; col++) {
	            boolean isWin = true;

	            for (int i = 0; i < 4; i++) {
	                Square square = grid.get(col + i).getColumn().get(row + i);

	                if (square.getValue() != ValueSquare.P2) {
	                    isWin = false;
	                    break;
	                }
	            }

	            if (isWin) {
	            	
	            	//The player 2 wins on a descending diagonal
	                return true; 
	            }
	        }
	    }

	    //We check ascending diagonals
	    for (int row = 3; row < rowCount; row++) {
	        for (int col = 0; col < colCount - 3; col++) {
	            boolean isWin = true;

	            for (int i = 0; i < 4; i++) {
	                Square square = grid.get(col + i).getColumn().get(row - i);

	                if (square.getValue() != ValueSquare.P2) {
	                    isWin = false;
	                    break;
	                }
	            }

	            if (isWin) {
	            	
	            	//The player 2 wins on an ascending diagonal
	                return true; 
	            }
	        }
	    }

	    //The player 2 doesn't win on a diagonal
	    return false;
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
	    if(isLineJ1win()) {
	    	return true;
	    }
	    
	    //We verify the diagonals
	    if(isDiagJ1win()) {
	    	return true;
	    }

	    //There is no combinations
	    return false;
	}
	
	
	/**
	 * Method returning if the player 2 wins the game
	 * @return boolean
	 */
	public boolean isJ2win() {
		
		//First we verify the columns
	    for (Column column : grid) {
	        if (column.isColumnWinJ2()) {
	            return true;
	        }
	    }

	    //We verify the rows
	    if(isLineJ2win()) {
	    	return true;
	    }
	    
	    //We verify the diagonals
	    if(isDiagJ2win()) {
	    	return true;
	    }

	    //There is no combinations
	    return false;
	}

	@Override
	public String toString() {
		return "Grid [grid=" + grid + "]";
	}
	
	
	
	
	
	

}
