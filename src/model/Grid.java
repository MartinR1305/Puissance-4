package model;

import java.util.ArrayList;
import java.util.List;

public class Grid {

	private List<Column> grid;

	/**
	 * Constructor
	 */
	public Grid() {
		grid = new ArrayList<Column>(7);

		// We have to add columns in the grid
		for (int i = 0; i < 7; i++) {
			grid.add(new Column());
		}
	}

	/**
	 * Constructor that creates a new Grid by copying another Grid
	 * 
	 * @param grid : The grid that we copy
	 */
	public Grid(Grid grid) {
		this.grid = new ArrayList<>();
		for (Column column : grid.getGrid()) {
			Column newColumn = new Column(column);
			this.grid.add(newColumn);
		}
	}

	/**
	 * Getter for the grid's list
	 * 
	 * @return list : The list of columns of the grid
	 */
	public List<Column> getGrid() {
		return grid;
	}

	/**
	 * Method that add a coin in the first square that is not empty in the column
	 * 
	 * @param indColumn : Index of the column where we want to add the coin
	 * @param coin      : Coin that we want to add to the column
	 */
	public void addCoinGrid(int indColumn, ValueSquare coin) {
		this.getGrid().get(indColumn).addCoinColumn(coin);
	}

	/**
	 * Method returning if the grid is empty
	 * 
	 * @return boolean : Answer of it is empty or not
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
	 * @return boolean : Answer of if it is full or not
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
	 * @return boolean : Answer of if the player 1 won the game with a line
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
	 * Method returning if the player 2 won the game in a line
	 * 
	 * @return boolean : Answer of if the player 2 won the game with a line
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
	 * Method returning if the player 1 won the game in a diagonal
	 * 
	 * @return boolean : Answer of if the player 1 won the game with a diagonal
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
	 * Method returning if the player 2 won the game in a diagonal
	 * 
	 * @return boolean : Answer of if the player 2 won the game with a diagonal
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
	 * Method returning if the player 1 won the game
	 * 
	 * @return boolean : Answer of if the player 1 won the game
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
	 * Method returning if the player 2 won the game
	 * 
	 * @return boolean : Answer of if the player 2 won the game
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
	 * Method that calculates the number of coin from the grid
	 * 
	 * @return number : The number of coin from the grid
	 */
	public int countCoin() {
		int number = 0;

		for (int indexLine = 0; indexLine < 6; indexLine++) {
			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {
				if (!this.grid.get(indexColumn).getColumn().get(indexLine).getValue().equals(ValueSquare.EMPTY)) {
					number++;
				}
			}
		}

		return number;
	}

	/**
	 * Method that allows to evaluate a grid
	 * 
	 * @param playerToEvaluate : The player that we need to calculate the value
	 * @param alpha2           : The second coefficient
	 * @param alpha3           : The third coefficient
	 * @param alpha4           : The fourth coefficient
	 * @return points : The value of the grid
	 */
	public int evaluateGrid(ValueSquare playerToEvaluate, int alpha2, int alpha3, int alpha4) {
		int points = 0;

		// We explore the grid for line / column / diagonal of 4
		for (int indexLine = 0; indexLine < 6; indexLine++) {
			for (int indexColumn = 0; indexColumn < 7; indexColumn++) {
				// Collect different sequences for each position
				List<Square> lineList = collectLineSequence(indexColumn, indexLine);
				List<Square> columnList = collectColumnSequence(indexColumn, indexLine);
				List<Square> diagonalAscList = collectAscendingDiagonalSequence(indexColumn, indexLine);
				List<Square> diagonalDesList = collectDescendingDiagonalSequence(indexColumn, indexLine);

				// We evaluate the collected sequences and accumulate the points
				points += evaluateSequence(lineList, playerToEvaluate, alpha2, alpha3, alpha4);
				points += evaluateSequence(columnList, playerToEvaluate, alpha2, alpha3, alpha4);
				points += evaluateSequence(diagonalAscList, playerToEvaluate, alpha2, alpha3, alpha4);
				points += evaluateSequence(diagonalDesList, playerToEvaluate, alpha2, alpha3, alpha4);
			}
		}

		return points;
	}

	/**
	 * Method to collect a line sequence
	 * 
	 * @param startColumn : Index of the starting column
	 * @param startLine   : Index of the starting line
	 * @return lineSequence : List of the squares for the line
	 */
	private List<Square> collectLineSequence(int startColumn, int startLine) {
		List<Square> lineSequence = new ArrayList<>();
		if (startColumn < 4) {
			for (int i = 0; i < 4; i++) {
				lineSequence.add(this.getGrid().get(startColumn + i).getColumn().get(startLine));
			}
		}
		return lineSequence;
	}

	/**
	 * Method to collect a column sequence
	 * 
	 * @param startColumn : Index of the starting column
	 * @param startLine   : Index of the starting line
	 * @return columnSequence : List of the squares for the column
	 */
	private List<Square> collectColumnSequence(int startColumn, int startLine) {
		List<Square> columnSequence = new ArrayList<>();
		if (startLine < 3) {
			for (int i = 0; i < 4; i++) {
				columnSequence.add(this.getGrid().get(startColumn).getColumn().get(startLine + i));
			}
		}
		return columnSequence;
	}

	/**
	 * Method to collect an ascending diagonal sequence
	 * 
	 * @param startColumn : Index of the starting column
	 * @param startLine   : Index of the starting line
	 * @return ascendingDiagonalSequence : List of the squares for the ascending
	 *         diagonal
	 */
	private List<Square> collectAscendingDiagonalSequence(int startColumn, int startLine) {
		List<Square> ascendingDiagonalSequence = new ArrayList<>();
		if (startColumn < 4 && startLine < 3) {
			for (int i = 0; i < 4; i++) {
				Square square = this.getGrid().get(startColumn + i).getColumn().get(startLine + i);
				ascendingDiagonalSequence.add(square);
			}
		}
		return ascendingDiagonalSequence;
	}

	/**
	 * Method to collect a descending diagonal sequence
	 * 
	 * @param startColumn : Index of the starting column
	 * @param startLine   : Index of the starting line
	 * @return descendingDiagonalSequence : List of the squares for the descending
	 *         diagonal
	 */
	private List<Square> collectDescendingDiagonalSequence(int startColumn, int startLine) {
		List<Square> descendingDiagonalSequence = new ArrayList<>();
		if (startColumn < 4 && startLine > 2) {
			for (int i = 0; i < 4; i++) {
				Square square = this.getGrid().get(startColumn + i).getColumn().get(startLine - i);
				descendingDiagonalSequence.add(square);
			}
		}
		return descendingDiagonalSequence;
	}

	/**
	 * Method to evaluate a sequence and return the points
	 * 
	 * @param sequence         : Sequence that we will evaluate
	 * @param playerToEvaluate : The player that we will evaluate
	 * @param alpha2           : The second coefficient
	 * @param alpha3           : The third coefficient
	 * @param alpha4           : The fourth coefficient
	 * @return points : The value of the sequence
	 */
	private int evaluateSequence(List<Square> sequence, ValueSquare playerToEvaluate, int alpha2, int alpha3,
			int alpha4) {
		int countP1 = 0;
		int countP2 = 0;
		int points = 0;

		ValueSquare otherPlayer = null;

		if (playerToEvaluate == ValueSquare.P2) {
			otherPlayer = ValueSquare.P1;
		} else {
			otherPlayer = ValueSquare.P2;
		}

		for (Square square : sequence) {
			if (square.getValue().equals(playerToEvaluate)) {
				countP1++;
			} else if (square.getValue().equals(otherPlayer)) {
				countP2++;
			}
		}

		if (countP2 > 0) {
			points += 0;
		} else {
			if (countP1 == 2) {
				// α2
				points += alpha2;
			} else if (countP1 == 3) {
				// α3
				points += alpha3;
			} else if (countP1 == 4) {
				// α4
				points += alpha4;
			}
		}
		return points;
	}

	/**
	 * Method that allows to get the 4 winning squares
	 *
	 * @param nbPlayer : The player who won the game
	 * @return list : List of the four circles
	 */
	public List<List<Integer>> getWinningSquares(int nbPlayer) {
		List<List<Integer>> winningSquares = new ArrayList<List<Integer>>(4);

		for (int i = 0; i < 4; i++) {
			winningSquares.add(new ArrayList<>());
		}

		if (nbPlayer == 1 && isJ1win()) {
			if (isDiagJ1win()) {
				if (checkDiagonalWin(ValueSquare.P1, winningSquares)) {
					return winningSquares;
				}
			} else if (checkLineWin(ValueSquare.P1, winningSquares) || checkColumnWin(ValueSquare.P1, winningSquares)) {
				return winningSquares;
			}
		}
		if (isDiagJ2win()) {
			if (checkDiagonalWin(ValueSquare.P2, winningSquares)) {
				return winningSquares;
			}
		} else if (checkLineWin(ValueSquare.P2, winningSquares) || checkColumnWin(ValueSquare.P2, winningSquares)) {
			return winningSquares;
		}
		return null;
	}

	/**
	 * Method that check if the player won with a diagonal
	 * 
	 * @param player         : The player that we check if he won
	 * @param winningSquares : List where we will put the fourth circles
	 * @return boolean : Answer if the player won with a diagonal
	 */
	private boolean checkDiagonalWin(ValueSquare player, List<List<Integer>> winningSquares) {
		// Up Diagonal
		for (int indexColumn = 0; indexColumn < 4; indexColumn++) {
			for (int indexLine = 0; indexLine < 3; indexLine++) {
				if (checkSequence(player, indexColumn, indexLine, 1, 1, winningSquares)) {
					return true;
				}
			}
		}
		clearingSequence(winningSquares);

		// Down Diagonal
		for (int indexColumn = 6; indexColumn > -1; indexColumn--) {
			for (int indexLine = 0; indexLine < 3; indexLine++) {
				if (checkSequence(player, indexColumn, indexLine, -1, 1, winningSquares)) {
					return true;
				}
			}
		}
		clearingSequence(winningSquares);
		return false;

	}

	/**
	 * Method that check if the player won with a line
	 * 
	 * @param player         : The player that we check if he won
	 * @param winningSquares : List where we will put the fourth circles
	 * @return boolean : Answer if the player won with a line
	 */
	private boolean checkLineWin(ValueSquare player, List<List<Integer>> winningSquares) {
		for (int indexColumn = 0; indexColumn < 7; indexColumn++) {
			for (int indexLine = 0; indexLine < 6; indexLine++) {
				if (checkSequence(player, indexColumn, indexLine, 1, 0, winningSquares)) {
					return true;
				}
			}
		}
		clearingSequence(winningSquares);
		return false;
	}

	/**
	 * Method that check if the player won with a column
	 * 
	 * @param player         : The player that we check if he won
	 * @param winningSquares : List where we will put the fourth circles
	 * @return boolean : Answer if the player won with a column
	 */
	private boolean checkColumnWin(ValueSquare player, List<List<Integer>> winningSquares) {
		int indexColumn = 0;
		for (Column column : getGrid()) {
			for (int indexLine = 0; indexLine < 3; indexLine++) {
				if (checkSequence(player, indexColumn, indexLine, 0, 1, winningSquares)) {
					return true;
				}
			}
			indexColumn++;
		}
		clearingSequence(winningSquares);
		return false;
	}

	/**
	 * Method that check if the player won with this sequence
	 * 
	 * @param player          : The player that we check if he won
	 * @param startColumn     : Index of the starting column
	 * @param startLine       : Index of the starting line
	 * @param columnIncrement : Increment for the next square for the column
	 * @param lineIncrement   : Increment for the next square for the line
	 * @param winningSquares  : List where we will put the fourth circles
	 * @return boolean : Answer if the player won with this sequence
	 */
	private boolean checkSequence(ValueSquare player, int startColumn, int startLine, int columnIncrement,
			int lineIncrement, List<List<Integer>> winningSquares) {
		for (int i = 0; i < 4; i++) {
			int column = startColumn + i * columnIncrement;
			int line = startLine + i * lineIncrement;

			if (!getGrid().get(column).getColumn().get(line).getValue().equals(player)) {
				clearingSequence(winningSquares);
				return false;
			}

			winningSquares.get(i).add(column);
			winningSquares.get(i).add(line);
		}
		return true;
	}

	/**
	 * Method that clear the circles for the list winningSquares
	 * 
	 * @param winningSquares : The list that we want to clear the cases
	 */
	public void clearingSequence(List<List<Integer>> winningSquares) {
		for (int i = 0; i < 4; i++) {
			winningSquares.get(i).clear();
		}
	}

	/**
	 * Method that allows to display the information of the grid
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		// Iterate through each row
		for (int row = 5; row > -1; row--) {
			// Iterate through each column
			for (int col = 0; col < 7; col++) {
				// Get the value of the square in the current column and row
				ValueSquare squareValue = grid.get(col).getColumn().size() > row
						? grid.get(col).getColumn().get(row).getValue()
						: null;

				// Append the square's value to the result
				result.append(squareValue != null ? squareValue.toString() : " ").append(" ");
			}
			// Move to the next line after each row
			result.append("\n");
		}

		return result.toString();
	}

}
