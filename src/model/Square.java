package model;

public class Square {
	ValueSquare value;

	// Constructor
	public Square() {
		this.value = ValueSquare.EMPTY;
	}

	// Getter
	public ValueSquare getValue() {
		return value;
	}

	// Setter
	public void setValue(ValueSquare value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "[ " + value + " ]";
	}
}
