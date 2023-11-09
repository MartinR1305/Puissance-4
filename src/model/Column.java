package model;

import java.util.ArrayList;
import java.util.List;

public class Column {

	private List<Square> column;

	/**
	 * Constructor
	 * 
	 * @param column
	 */
	public Column() {

		column = new ArrayList<Square>(6);

		// We have to add empty squares in the column
		for (int i = 0; i < 6; i++) {
			column.add(new Square());
		}

	}
	
	/**
	 * Constructor that creates a new column by copying an existing column.
	 *
	 * @param originalColumn The column to be copied
	 */
	public Column(Column column) {
	    this.column = new ArrayList<>();

	    // Iterating through the original column to create a copy
	    for (Square square : column.getColumn()) {
	    	
	        // Creating a new Square with the value of the square from the original column
	        Square newSquare = new Square(square.getValue());
	        this.column.add(newSquare);
	    }
	}


	/**
	 * Getter for column
	 * 
	 * @return the list column
	 */
	public List<Square> getColumn() {
		return column;
	}

	/**
	 * Method searching the first empty square of the column
	 * 
	 * @return int
	 */
	public int calculateFirstEmptySquare() {
		for (int i = 0; i < column.size(); i++) {
			Square square = column.get(i);
			if (square.getValue() == ValueSquare.EMPTY) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Method that add a coin in the first square that is not empty
	 * 
	 * @param coin
	 */
	public void addCoinColumn(ValueSquare coin) {
		if (!this.isColumnFull() && !coin.equals(ValueSquare.EMPTY)) {
			this.getColumn().get(calculateFirstEmptySquare()).setValue(coin);
		}
	}

	/**
	 * Method telling if the column is empty or not
	 * 
	 * @return boolean
	 */
	public boolean isColumnEmpty() {

		for (Square square : column) {
			if (square.getValue() != ValueSquare.EMPTY) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method telling if the column is full or not
	 * 
	 * @return boolean
	 */
	public boolean isColumnFull() {

		for (Square square : column) {
			if (square.getValue() == ValueSquare.EMPTY) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method return if player 1 wins the game in the column
	 * 
	 * @return boolean
	 */
	public boolean isColumnWinJ1() {

		int countConsecutive = 0;

		for (int i = 0; i < column.size(); i++) {
			Square square = column.get(i);
			if (square.getValue() == ValueSquare.P1) {
				countConsecutive++;

				// The player 1 win
				if (countConsecutive >= 4) {
					return true;
				}
			} else {
				countConsecutive = 0;
			}

		}
		// the player 1 doesn't win
		return false;

	}

	/**
	 * Method return if player 2 wins the game in the column
	 * 
	 * @return boolean
	 */
	public boolean isColumnWinJ2() {
		int countConsecutive = 0;

		for (int i = 0; i < column.size(); i++) {
			Square square = column.get(i);
			if (square.getValue() == ValueSquare.P2) {
				countConsecutive++;

				// The player 2 win
				if (countConsecutive >= 4) {
					return true;
				}
			} else {
				countConsecutive = 0;
			}

		}
		// the player 2 doesn't win
		return false;
	}

	@Override
	public String toString() {
		return "Column [column =" + column + "]";
	}

}
