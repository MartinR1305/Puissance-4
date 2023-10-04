package model;

public class Square {
	ValueSquare value;

	/**
	 * Constructor
	 */
	public Square() {
		this.value = ValueSquare.EMPTY;
	}

	/**
	 * Getter for the value
	 * @return the value of the square
	 */
	public ValueSquare getValue() {
		return value;
	}
	
	/**
	 * Setter for the value
	 * @param value, the new value for the square 
	 */
	public void setValue(ValueSquare value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "[ " + value + " ]";
	}
}
