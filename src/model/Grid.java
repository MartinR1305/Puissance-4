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
	 * Method returning if the player 1 is winning the game in a line
	 * @return boolean
	 */
	public boolean isLineJ1win() {
		
		for (int line = 0; line < 6; line++) { 
	        boolean isLineWin = true;

	        for (Column column : grid) {
	            Square square = column.getColumn().get(line);
	            
	            //If a line's square is not P1, there is no win
	            if (square.getValue() != ValueSquare.P1) {
	                isLineWin = false;
	                
	                //We quit the loop because there is no win
	                break;
	            }
	        }
	        
	        //If there is a win on the line, the player 1 win the game
	        if (isLineWin) {
	            return true;
	        }
	    }
		
		//The player 1 is not winning the game
	    return false; 
	}

	
	/**
	 * Method returning if the player 2 is winning the game in a line
	 * @return boolean
	 */
	public boolean isLineJ2win() {
		
		for (int line = 0; line < 6; line++) { 
	        boolean isLineWin = true;

	        for (Column column : grid) {
	            Square square = column.getColumn().get(line);
	            
	            //If a line's square is not P2, there is no win
	            if (square.getValue() != ValueSquare.P2) {
	                isLineWin = false;
	                
	                //We quit the loop because there is no win
	                break;
	            }
	        }
	        
	        //If there is a win on the line, the player 2 win the game
	        if (isLineWin) {
	            return true;
	        }
	    }
		
		//The player 2 is not winning the game
	    return false; 
	}
	
	/**
	 * Method returning if the player 1 is winning the game in a diagonal
	 * @return boolean
	 */
	public boolean isDiagJ1win() {
		
		//We have 6 lines on the grid
		for (int line = 0; line < 3; line++) {
	        for (int col = 0; col < 7; col++) {
	        	
	            int consecutiveP1Count = 0;

	            //We verify the descending diagonal from left to right
	            //To win the game, we have to align 4 coins on the diagonal
	            for (int i = 0; i < 4; i++) { 
	                int r = line + i;
	                int c = col + i;

	                if (r >= 0 && r < 6 && c >= 0 && c < 7) {
	                    if (grid.get(c).getColumn().get(r).getValue() == ValueSquare.P1) {
	                        consecutiveP1Count++;
	                        
	                        //The player 1 wins the game with a descending diagonal
	                        if (consecutiveP1Count >= 4) {
	                            return true;
	                        }
	                    } else {
	                    	
	                    	//We reinitialize the count if we have something else than P1
	                        consecutiveP1Count = 0;
	                    }
	                }
	            }
	            
	            //Now we have to verify the rising diagonal
	            
	            consecutiveP1Count = 0; 


	            for (int i = 0; i < 4; i++) {
	                int r = line - i;
	                int c = col + i;

	                if (r >= 0 && r < 6 && c >= 0 && c < 7) {
	                    if (grid.get(c).getColumn().get(r).getValue() == ValueSquare.P1) {
	                        consecutiveP1Count++;
	                        
	                      //The player 1 wins the game with a rinsing diagonal
	                        if (consecutiveP1Count >= 4) {
	                            return true;
	                        }
	                    } else {
	                    	//We reinitialize the count if we have something else than P1
	                        consecutiveP1Count = 0;
	                    }
	                }
	            }
	        }
	    }
		
		//The player 1 win doesn't win the game with a diagonal
	    return false;
	}
	
	/**
	 * Method returning if the player 2 is winning the game in a diagonal
	 * @return boolean
	 */
	public boolean isDiagJ2win() {
		
		//We have 6 lines on the grid
		for (int line = 0; line < 3; line++) {
	        for (int col = 0; col < 7; col++) {
	        	
	            int consecutiveP2Count = 0;

	            //We verify the descending diagonal from left to right
	            //To win the game, we have to align 4 coins on the diagonal
	            for (int i = 0; i < 4; i++) { 
	                int r = line + i;
	                int c = col + i;

	                if (r >= 0 && r < 6 && c >= 0 && c < 7) {
	                    if (grid.get(c).getColumn().get(r).getValue() == ValueSquare.P2) {
	                    	consecutiveP2Count++;
	                        
	                        //The player 2 wins the game with a descending diagonal
	                        if (consecutiveP2Count >= 4) {
	                            return true;
	                        }
	                    } else {
	                    	
	                    	//We reinitialize the count if we have something else than P2
	                    	consecutiveP2Count = 0;
	                    }
	                }
	            }
	            
	            //Now we have to verify the rising diagonal
	            
	            consecutiveP2Count = 0; 


	            for (int i = 0; i < 4; i++) {
	                int r = line - i;
	                int c = col + i;

	                if (r >= 0 && r < 6 && c >= 0 && c < 7) {
	                    if (grid.get(c).getColumn().get(r).getValue() == ValueSquare.P2) {
	                    	consecutiveP2Count++;
	                        
	                      //The player 2 wins the game with a rinsing diagonal
	                        if (consecutiveP2Count >= 4) {
	                            return true;
	                        }
	                    } else {
	                    	//We reinitialize the count if we have something else than P2
	                    	consecutiveP2Count = 0;
	                    }
	                }
	            }
	        }
	    }
		
		//The player 2 win doesn't win the game with a diagonal
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
	
	
	
	
	

}
