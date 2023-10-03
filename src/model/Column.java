package model;

import java.util.ArrayList;
import java.util.List;

public class Column {
	
	private List<Square> column;

	public Column(List<Square> column) {
		
		column = new ArrayList<Square>(7);
		
	}

	public List<Square> getColumn() {
		return column;
	}
	
	public int calculateFirstEmptySquare() {
	    for (int i = 0; i < column.size(); i++) {
	        Square square = column.get(i);
	        if (square.getValue() == ValueSquare.EMPTY) {
	            return i; 
	        }
	    }
	    return -1; 
	}

	
	public void addCoin(ValueSquare coin) {
		
		
	}
	
	public boolean isColumnEmpty() {
		
		for(Square square : column) {
			if(square.getValue() != ValueSquare.EMPTY) {
				return false;
			}
		}
		return true;
	}
	
	public  boolean isColumnFull() {
		
		for(Square square : column) {
			if(square.getValue() != ValueSquare.P1 || square.getValue() != ValueSquare.P2) {
				return false;
			}
		}
		return true;
	}
	
	
	public  boolean isColumnWinJ1() {
		for (int i = 0; i < column.size(); i++) {
			Square square = column.get(i);
			if(square.getValue() == ValueSquare.P1) {
				
			}
			
			
		}
		return false;
	        
	}
	
	public  boolean isColumnWinJ2() {
		return false;
	}
	
	@Override
	public String toString() {
		return "Column [column=" + column + "]";
	}
	
}
