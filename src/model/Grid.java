package model;

import java.util.ArrayList;
import java.util.List;

public class Grid {

	private List<Column> grid;

	/**
	 * Constructor
	 * 
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
	 * 
	 * @return
	 */
	public List<Column> getGrid() {
		return grid;
	}

	/**
	 * Method that add a coin in the first square that is not empty in the column
	 * 
	 * @param indColumn, index of the column where we want to add the coin
	 * @param coin,      coin that we want to add to the column
	 */
	public void addCoinGrid(int indColumn, ValueSquare coin) {
		this.getGrid().get(indColumn).addCoinColumn(coin);
	}

	/**
	 * Method returning if the grid is empty
	 * 
	 * @return boolean
	 */
	public boolean isGridEmpty() {

		for (Column column : grid) {

			// If the column is not empty, the grid neither
			if (!column.isColumnEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method returning if the grid is full
	 * 
	 * @return boolean
	 */
	public boolean isGridFull() {

		for (Column column : grid) {

			// If the column is not full, the grid neither
			if (!column.isColumnFull()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method returning if the player 1 is winning the game in a line
	 * 
	 * @return boolean
	 */
	public boolean isLineJ1win() {

		for (int line = 0; line < 6; line++) {

			// We count the consecutive P1 coins
			int consecutiveCount = 0;

			for (Column column : grid) {
				if (column.getColumn().size() <= line) {

					// We reinitialize to the counter if there is no coins in the line
					consecutiveCount = 0;

					// We pass to the following column
					continue;
				}

				Square square = column.getColumn().get(line);

				// If the line's coin is P1, we increment the counter
				if (square.getValue() == ValueSquare.P1) {
					consecutiveCount++;
				} else {

					// We reinitialize the counter if there is no P1 coins
					consecutiveCount = 0;
				}

				// If we have 4 consecutive P1 coins, the player 1 wins
				if (consecutiveCount >= 4) {
					return true;
				}
			}
		}

		// The player 1 doesn't win the game
		return false;
	}

	/**
	 * Method returning if the player 2 is winning the game in a line
	 * 
	 * @return boolean
	 */
	public boolean isLineJ2win() {

		for (int line = 0; line < 6; line++) {

			// We count the consecutive P2 coins
			int consecutiveCount = 0;

			for (Column column : grid) {
				if (column.getColumn().size() <= line) {

					// We reinitialize to the counter if there is no coins in the line
					consecutiveCount = 0;

					// We pass to the following column
					continue;
				}

				Square square = column.getColumn().get(line);

				// If the line's coin is P2, we increment the counter
				if (square.getValue() == ValueSquare.P2) {
					consecutiveCount++;
				} else {

					// We reinitialize the counter if there is no P2 coins
					consecutiveCount = 0;
				}

				// If we have 4 consecutive P2 coins, the player 2 wins
				if (consecutiveCount >= 4) {
					return true;
				}
			}
		}

		// The player 2 doesn't win the game
		return false;
	}

	/**
	 * Method returning if the player 1 is winning the game in a diagonal
	 * 
	 * @return boolean
	 */
	public boolean isDiagJ1win() {

		// Number of lines in the grid
		int lineCount = 6;

		// Number of columns in the grid
		int colCount = 7;

		// We check descending diagonals
		for (int line = 0; line < lineCount - 3; line++) {
			for (int col = 0; col < colCount - 3; col++) {
				boolean isWin = true;

				for (int i = 0; i < 4; i++) {
					Square square = grid.get(col + i).getColumn().get(line + i);

					if (square.getValue() != ValueSquare.P1) {
						isWin = false;
						break;
					}
				}

				if (isWin) {

					// The player 1 wins on a descending diagonal
					return true;
				}
			}
		}

		// We check ascending diagonals
		for (int line = 3; line < lineCount; line++) {
			for (int col = 0; col < colCount - 3; col++) {
				boolean isWin = true;

				for (int i = 0; i < 4; i++) {
					Square square = grid.get(col + i).getColumn().get(line - i);

					if (square.getValue() != ValueSquare.P1) {
						isWin = false;
						break;
					}
				}

				if (isWin) {

					// The player 1 wins on an ascending diagonal
					return true;
				}
			}
		}

		// The player 1 doesn't win on a diagonal
		return false;
	}

	/**
	 * Method returning if the player 2 is winning the game in a diagonal
	 * 
	 * @return boolean
	 */
	public boolean isDiagJ2win() {

		// Number of lines in the grid
		int lineCount = 6;

		// Number of columns in the grid
		int colCount = 7;

		// We check descending diagonals
		for (int line = 0; line < lineCount - 3; line++) {
			for (int col = 0; col < colCount - 3; col++) {
				boolean isWin = true;

				for (int i = 0; i < 4; i++) {
					Square square = grid.get(col + i).getColumn().get(line + i);

					if (square.getValue() != ValueSquare.P2) {
						isWin = false;
						break;
					}
				}

				if (isWin) {

					// The player 2 wins on a descending diagonal
					return true;
				}
			}
		}

		// We check ascending diagonals
		for (int line = 3; line < lineCount; line++) {
			for (int col = 0; col < colCount - 3; col++) {
				boolean isWin = true;

				for (int i = 0; i < 4; i++) {
					Square square = grid.get(col + i).getColumn().get(line - i);

					if (square.getValue() != ValueSquare.P2) {
						isWin = false;
						break;
					}
				}

				if (isWin) {

					// The player 2 wins on an ascending diagonal
					return true;
				}
			}
		}

		// The player 2 doesn't win on a diagonal
		return false;
	}

	/**
	 * Method returning if the player 1 wins the game
	 * 
	 * @return boolean
	 */
	public boolean isJ1win() {

		// First we verify the columns
		for (Column column : grid) {
			if (column.isColumnWinJ1()) {
				return true;
			}
		}

		// We verify the rows
		if (isLineJ1win()) {
			return true;
		}

		// We verify the diagonals
		if (isDiagJ1win()) {
			return true;
		}

		// There is no combinations
		return false;
	}

	/**
	 * Method returning if the player 2 wins the game
	 * 
	 * @return boolean
	 */
	public boolean isJ2win() {

		// First we verify the columns
		for (Column column : grid) {
			if (column.isColumnWinJ2()) {
				return true;
			}
		}

		// We verify the rows
		if (isLineJ2win()) {
			return true;
		}

		// We verify the diagonals
		if (isDiagJ2win()) {
			return true;
		}

		// There is no combinations
		return false;
	}

	/**
	 * Method that allows to get the 4 winning squares
	 * 
	 * @param grid
	 * @param player
	 * @return
	 */
	public List<List<Integer>> getWinningSquares(int nbPlayer) {

		List<List<Integer>> winningSquares = new ArrayList<List<Integer>>(4);

		List<Integer> liste1 = new ArrayList<>();
		winningSquares.add(liste1);

		List<Integer> liste2 = new ArrayList<>();
		winningSquares.add(liste2);

		List<Integer> liste3 = new ArrayList<>();
		winningSquares.add(liste3);

		List<Integer> liste4 = new ArrayList<>();
		winningSquares.add(liste4);

		if (nbPlayer == 1) {
			if (this.isJ1win()) {

				// We search for a diagonal of coin
				if (this.isDiagJ1win()) {

					// Up Diagonal
					for (int indexColumn = 0; indexColumn < 4; indexColumn++) {
						for (int indexLine = 0; indexLine < 3; indexLine++) {
							if (this.getGrid().get(indexColumn).getColumn().get(indexLine).getValue()
									.equals(ValueSquare.P1)) {

								// We add the couple (column; line) to the list
								winningSquares.get(0).add(indexColumn);
								winningSquares.get(0).add(indexLine);

								if (this.getGrid().get(indexColumn + 1).getColumn().get(indexLine + 1).getValue()
										.equals(ValueSquare.P1)) {

									// We add the couple (column; line) to the list
									winningSquares.get(1).add(indexColumn + 1);
									winningSquares.get(1).add(indexLine + 1);

									if (this.getGrid().get(indexColumn + 2).getColumn().get(indexLine + 2).getValue()
											.equals(ValueSquare.P1)) {

										// We add the couple (column; line) to the list
										winningSquares.get(2).add(indexColumn + 2);
										winningSquares.get(2).add(indexLine + 2);

										if (this.getGrid().get(indexColumn + 3).getColumn().get(indexLine + 3)
												.getValue().equals(ValueSquare.P1)) {

											// We add the couple (column; line) to the list
											winningSquares.get(3).add(indexColumn + 3);
											winningSquares.get(3).add(indexLine + 3);

											return winningSquares;
										}
										// We clean the list
										for (List<Integer> liste : winningSquares) {
											liste.clear();
										}

									}
									// We clean the list
									for (List<Integer> liste : winningSquares) {
										liste.clear();
									}
								}
								// We clean the list
								for (List<Integer> liste : winningSquares) {
									liste.clear();
								}
							}
						}
					}

					// Down Diagonal
					for (int indexColumn = 0; indexColumn < 4; indexColumn++) {
						for (int indexLine = 5; indexLine > 1; indexLine--) {
							if (this.getGrid().get(indexColumn).getColumn().get(indexLine).getValue()
									.equals(ValueSquare.P1)) {

								// We add the couple (column; line) to the list
								winningSquares.get(0).add(indexColumn);
								winningSquares.get(0).add(indexLine);

								if (this.getGrid().get(indexColumn + 1).getColumn().get(indexLine - 1).getValue()
										.equals(ValueSquare.P1)) {

									// We add the couple (column; line) to the list
									winningSquares.get(1).add(indexColumn + 1);
									winningSquares.get(1).add(indexLine - 1);

									if (this.getGrid().get(indexColumn + 2).getColumn().get(indexLine - 2).getValue()
											.equals(ValueSquare.P1)) {

										// We add the couple (column; line) to the list
										winningSquares.get(2).add(indexColumn + 2);
										winningSquares.get(2).add(indexLine - 2);

										if (this.getGrid().get(indexColumn + 3).getColumn().get(indexLine - 3)
												.getValue().equals(ValueSquare.P1)) {

											// We add the couple (column; line) to the list
											winningSquares.get(3).add(indexColumn + 3);
											winningSquares.get(3).add(indexLine - 3);

											return winningSquares;
										}
										// We clean the list
										for (List<Integer> liste : winningSquares) {
											liste.clear();
										}
									}
									// We clean the list
									for (List<Integer> liste : winningSquares) {
										liste.clear();
									}
								}
								// We clean the list
								for (List<Integer> liste : winningSquares) {
									liste.clear();
								}
							}
						}
					}
				}

				// We search for a line of coin
				else if (this.isLineJ1win()) {

					for (int indexColumn = 0; indexColumn < 7; indexColumn++) {
						for (int indexLine = 0; indexLine < 6; indexLine++) {
							if (this.getGrid().get(indexColumn).getColumn().get(indexLine).getValue()
									.equals(ValueSquare.P1)) {

								// We add the couple (column; line) to the list
								winningSquares.get(0).add(indexColumn);
								winningSquares.get(0).add(indexLine);

								if (this.getGrid().get(indexColumn + 1).getColumn().get(indexLine).getValue()
										.equals(ValueSquare.P1)) {

									// We add the couple (column; line) to the list
									winningSquares.get(1).add(indexColumn + 1);
									winningSquares.get(1).add(indexLine);

									if (this.getGrid().get(indexColumn + 2).getColumn().get(indexLine).getValue()
											.equals(ValueSquare.P1)) {

										// We add the couple (column; line) to the list
										winningSquares.get(2).add(indexColumn + 2);
										winningSquares.get(2).add(indexLine);

										if (this.getGrid().get(indexColumn + 3).getColumn().get(indexLine).getValue()
												.equals(ValueSquare.P1)) {

											// We add the couple (column; line) to the list
											winningSquares.get(3).add(indexColumn + 3);
											winningSquares.get(3).add(indexLine);

											return winningSquares;
										}
										// We clean the list
										for (List<Integer> liste : winningSquares) {
											liste.clear();
										}
									}
									// We clean the list
									for (List<Integer> liste : winningSquares) {
										liste.clear();
									}
								}
								// We clean the list
								for (List<Integer> liste : winningSquares) {
									liste.clear();
								}
							}
						}
					}
				}

				// We search for a column of coin
				else {

					int indexColumn = 0;
					for (Column column : this.getGrid()) {
						for (int indexLine = 0; indexLine < 3; indexLine++) {
							if (column.getColumn().get(indexLine).getValue().equals(ValueSquare.P1)) {

								// We add the couple (column; line) to the list
								winningSquares.get(0).add(indexColumn);
								winningSquares.get(0).add(indexLine);

								if (column.getColumn().get(indexLine + 1).getValue().equals(ValueSquare.P1)) {

									// We add the couple (column; line) to the list
									winningSquares.get(1).add(indexColumn);
									winningSquares.get(1).add(indexLine + 1);

									if (column.getColumn().get(indexLine + 2).getValue().equals(ValueSquare.P1)) {

										// We add the couple (column; line) to the list
										winningSquares.get(2).add(indexColumn);
										winningSquares.get(2).add(indexLine + 2);

										if (column.getColumn().get(indexLine + 3).getValue().equals(ValueSquare.P1)) {

											// We add the couple (column; line) to the list
											winningSquares.get(3).add(indexColumn);
											winningSquares.get(3).add(indexLine + 3);

											return winningSquares;
										}
										// We clean the list
										for (List<Integer> liste : winningSquares) {
											liste.clear();
										}
									}
									// We clean the list
									for (List<Integer> liste : winningSquares) {
										liste.clear();
									}
								}
								// We clean the list
								for (List<Integer> liste : winningSquares) {
									liste.clear();
								}
							}
						}
						indexColumn++;
					}
				}
			}
		}

		else if (nbPlayer == 2) {
			if (this.isJ2win()) {

				// We search for a diagonal of coin
				if (this.isDiagJ2win()) {

					// Up Diagonal
					for (int indexColumn = 0; indexColumn < 4; indexColumn++) {
						for (int indexLine = 0; indexLine < 3; indexLine++) {
							if (this.getGrid().get(indexColumn).getColumn().get(indexLine).getValue()
									.equals(ValueSquare.P2)) {

								// We add the couple (column; line) to the list
								winningSquares.get(0).add(indexColumn);
								winningSquares.get(0).add(indexLine);

								if (this.getGrid().get(indexColumn + 1).getColumn().get(indexLine + 1).getValue()
										.equals(ValueSquare.P2)) {

									// We add the couple (column; line) to the list
									winningSquares.get(1).add(indexColumn + 1);
									winningSquares.get(1).add(indexLine + 1);

									if (this.getGrid().get(indexColumn + 2).getColumn().get(indexLine + 2).getValue()
											.equals(ValueSquare.P2)) {

										// We add the couple (column; line) to the list
										winningSquares.get(2).add(indexColumn + 2);
										winningSquares.get(2).add(indexLine + 2);

										if (this.getGrid().get(indexColumn + 3).getColumn().get(indexLine + 3)
												.getValue().equals(ValueSquare.P2)) {

											// We add the couple (column; line) to the list
											winningSquares.get(3).add(indexColumn + 3);
											winningSquares.get(3).add(indexLine + 3);

											return winningSquares;
										}
										// We clean the list
										for (List<Integer> liste : winningSquares) {
											liste.clear();
										}
									}
									// We clean the list
									for (List<Integer> liste : winningSquares) {
										liste.clear();
									}
								}
								// We clean the list
								for (List<Integer> liste : winningSquares) {
									liste.clear();
								}
							}
						}
					}
					
					// Down Diagonal
					for (int indexColumn = 0; indexColumn < 4; indexColumn++) {
						for (int indexLine = 5; indexLine > 1; indexLine--) {
							if (this.getGrid().get(indexColumn).getColumn().get(indexLine).getValue()
									.equals(ValueSquare.P2)) {

								// We add the couple (column; line) to the list
								winningSquares.get(0).add(indexColumn);
								winningSquares.get(0).add(indexLine);

								if (this.getGrid().get(indexColumn + 1).getColumn().get(indexLine - 1).getValue()
										.equals(ValueSquare.P2)) {

									// We add the couple (column; line) to the list
									winningSquares.get(1).add(indexColumn + 1);
									winningSquares.get(1).add(indexLine - 1);

									if (this.getGrid().get(indexColumn + 2).getColumn().get(indexLine - 2).getValue()
											.equals(ValueSquare.P2)) {

										// We add the couple (column; line) to the list
										winningSquares.get(2).add(indexColumn + 2);
										winningSquares.get(2).add(indexLine - 2);

										if (this.getGrid().get(indexColumn + 3).getColumn().get(indexLine - 3)
												.getValue().equals(ValueSquare.P2)) {

											// We add the couple (column; line) to the list
											winningSquares.get(3).add(indexColumn + 3);
											winningSquares.get(3).add(indexLine - 3);

											return winningSquares;
										}
										// We clean the list
										for (List<Integer> liste : winningSquares) {
											liste.clear();
										}
									}
									// We clean the list
									for (List<Integer> liste : winningSquares) {
										liste.clear();
									}
								}
								// We clean the list
								for (List<Integer> liste : winningSquares) {
									liste.clear();
								}
							}
						}
					}
				}

				// We search for a line of coin
				else if (this.isLineJ2win()) {

					for (int indexColumn = 0; indexColumn < 7; indexColumn++) {
						for (int indexLine = 0; indexLine < 6; indexLine++) {
							if (this.getGrid().get(indexColumn).getColumn().get(indexLine).getValue()
									.equals(ValueSquare.P2)) {

								// We add the couple (column; line) to the list
								winningSquares.get(0).add(indexColumn);
								winningSquares.get(0).add(indexLine);

								if (this.getGrid().get(indexColumn + 1).getColumn().get(indexLine).getValue()
										.equals(ValueSquare.P2)) {

									// We add the couple (column; line) to the list
									winningSquares.get(1).add(indexColumn + 1);
									winningSquares.get(1).add(indexLine);

									if (this.getGrid().get(indexColumn + 2).getColumn().get(indexLine).getValue()
											.equals(ValueSquare.P2)) {

										// We add the couple (column; line) to the list
										winningSquares.get(2).add(indexColumn + 2);
										winningSquares.get(2).add(indexLine);

										if (this.getGrid().get(indexColumn + 3).getColumn().get(indexLine).getValue()
												.equals(ValueSquare.P2)) {

											// We add the couple (column; line) to the list
											winningSquares.get(3).add(indexColumn + 3);
											winningSquares.get(3).add(indexLine);

											return winningSquares;
										}
										// We clean the list
										for (List<Integer> liste : winningSquares) {
											liste.clear();
										}
									}
									// We clean the list
									for (List<Integer> liste : winningSquares) {
										liste.clear();
									}
								}
								// We clean the list
								for (List<Integer> liste : winningSquares) {
									liste.clear();
								}
							}
						}
					}
				}

				// We search for a column of coin
				else {

					int indexColumn = 0;
					for (Column column : this.getGrid()) {
						for (int indexLine = 0; indexLine < 3; indexLine++) {
							if (column.getColumn().get(indexLine).getValue().equals(ValueSquare.P2)) {

								// We add the couple (column; line) to the list
								winningSquares.get(0).add(indexColumn);
								winningSquares.get(0).add(indexLine);

								if (column.getColumn().get(indexLine + 1).getValue().equals(ValueSquare.P2)) {

									// We add the couple (column; line) to the list
									winningSquares.get(1).add(indexColumn);
									winningSquares.get(1).add(indexLine + 1);

									if (column.getColumn().get(indexLine + 2).getValue().equals(ValueSquare.P2)) {

										// We add the couple (column; line) to the list
										winningSquares.get(2).add(indexColumn);
										winningSquares.get(2).add(indexLine + 2);

										if (column.getColumn().get(indexLine + 3).getValue().equals(ValueSquare.P2)) {

											// We add the couple (column; line) to the list
											winningSquares.get(3).add(indexColumn);
											winningSquares.get(3).add(indexLine + 3);

											return winningSquares;
										}
										// We clean the list
										for (List<Integer> liste : winningSquares) {
											liste.clear();
										}
									}
									// We clean the list
									for (List<Integer> liste : winningSquares) {
										liste.clear();
									}
								}
								// We clean the list
								for (List<Integer> liste : winningSquares) {
									liste.clear();
								}
							}
						}
						indexColumn++;
					}
				}
			}
		}
		return null;
	}
	
	
	public int evaluateGrid(Grid grid) {
		int points = 0;

		//We explore the grid
		for (int indexLine = 0; indexLine < 6; indexLine++) {
			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {
				// Collect different sequences for each position
				List<Square> lineList = collectLineSequence(grid, indexColumn, indexLine);
				List<Square> columnList = collectColumnSequence(grid, indexColumn, indexLine);
				List<Square> diagonalAscList = collectAscendingDiagonalSequence(grid, indexColumn, indexLine);
				List<Square> diagonalDesList = collectDescendingDiagonalSequence(grid, indexColumn, indexLine);

				//We evaluate the collected sequences and accumulate the points
				points += evaluateSequence(lineList);
				points += evaluateSequence(columnList);
				points += evaluateSequence(diagonalAscList);
				points += evaluateSequence(diagonalDesList);
			}
		}

		return points;
	}

	//Method to collect a line sequence
	private List<Square> collectLineSequence(Grid grid, int startColumn, int startLine) {
		List<Square> lineSequence = new ArrayList<>();
		if(startColumn < 4) {
			for (int i = 0; i < 4 ; i++) {
				lineSequence.add(grid.getGrid().get(startColumn + i).getColumn().get(startLine));
			}
		}
		return lineSequence;
	}

	//Method to collect a column sequence
	private List<Square> collectColumnSequence(Grid grid, int startColumn, int startLine) {
		List<Square> columnSequence = new ArrayList<>();
		if(startLine < 3) {
			for (int i = 0; i < 4 ; i++) {
				columnSequence.add(grid.getGrid().get(startColumn).getColumn().get(startLine + i));
			}
		}
		return columnSequence;
	}

//	//Method to collect an ascending diagonal sequence
	private List<Square> collectAscendingDiagonalSequence(Grid grid, int startColumn, int startLine) {
		List<Square> ascendingDiagonalSequence = new ArrayList<>();
		if (startColumn < 4 && startLine < 3) {
			for (int i = 0; i < 4 ; i++) {
				Square square = grid.getGrid().get(startColumn + i).getColumn().get(startLine + i);
				ascendingDiagonalSequence.add(square);
			}
		}
		return ascendingDiagonalSequence;
	}

//	//Method to collect a descending diagonal sequence
	private List<Square> collectDescendingDiagonalSequence(Grid grid, int startColumn, int startLine) {
		List<Square> descendingDiagonalSequence = new ArrayList<>();
		if (startColumn < 4 && startLine > 2) {
			for (int i = 0; i < 4 ; i++) {
				Square square = grid.getGrid().get(startColumn + i).getColumn().get(startLine - i);
				descendingDiagonalSequence.add(square);
			}
		}
		return descendingDiagonalSequence;
	}



	//Method to evaluate a sequence and return the points
	private int evaluateSequence(List<Square> sequence) {
	    int countP1 = 0;
	    int countP2 = 0;
	    int points = 0;
	    
	    for (Square square : sequence) {
	        if (square.getValue().equals(ValueSquare.P1)) {
	            countP1++;
	        } else if (square.getValue().equals(ValueSquare.P2)) {
	            countP2++;
	        }
	    }

	    if (countP2 > 0) {
	        points += 0 ;
	    } else {
	    	if (countP1 == 1) {
				points += 1;
			} else if (countP1 == 2) {
				points += 2;
			} else if (countP1 == 3) {
				points += 3;
			} else if (countP1 == 4) {
				points += 4;
			}
	    }
	    
	    return points;
	}


	@Override
	public String toString() {
		return "Grid [grid=" + grid + "]";
	}

}
