package model;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		
		Grid grid = new Grid();
		
		Square square1 = new Square();
		Column column1 = grid.getGrid().get(0);
		Column column2 = grid.getGrid().get(1);
		Column column3 = grid.getGrid().get(2);
		Column column4 = grid.getGrid().get(3);
		
//		column1.addCoin(ValueSquare.P1);
//		column1.addCoin(ValueSquare.P1);
//		column1.addCoin(ValueSquare.P1);
//		column1.addCoin(ValueSquare.P1);
		
		column1.addCoinColumn(ValueSquare.P1);
		
		column2.addCoinColumn(ValueSquare.P2);
		column2.addCoinColumn(ValueSquare.P1);
		
		column3.addCoinColumn(ValueSquare.P2);
		column3.addCoinColumn(ValueSquare.P2);
		column3.addCoinColumn(ValueSquare.P1);
		
		column4.addCoinColumn(ValueSquare.P2);
		column4.addCoinColumn(ValueSquare.P2);
		column4.addCoinColumn(ValueSquare.P2);
		column4.addCoinColumn(ValueSquare.P1);
		
		
		// Vérifiez si la colonne est vide
	    boolean isEmpty = column1.isColumnEmpty();
	    System.out.println("Is Column Empty? " + isEmpty);

	    // Vérifiez si la colonne est pleine (simulons que la colonne est pleine)
	    for (int i = 0; i < 6; i++) {
	    	column1.addCoinColumn(square1.getValue());
	    }
	    
	    boolean isFull = column1.isColumnFull();
	    System.out.println("Is Column Full? " + isFull);
	    
	    System.out.println(column1);
	    // Vérifiez quel est le premier carré vide de la colonne
	    int firstEmptySquareIndex = column1.calculateFirstEmptySquare();
	    System.out.println("Index of First Empty Square: " + firstEmptySquareIndex);
	    
	    // Vérifiez si le joueur 1 (J1) a gagné dans la colonne
	    boolean isWinJ1 = column1.isColumnWinJ1();
	    System.out.println("Is J1 Win? " + isWinJ1);
	    
	    boolean isWinJ2 = column1.isColumnWinJ2();
	    System.out.println("Is J2 Win? " + isWinJ2);
	    
	    
	    
	    
	    System.out.println("\n");
	    
	    
	    
	    
	    
	    
	    
	    
	    System.out.println(grid);
	    
	    // Vérifiez si la grille est vide
	    boolean isGridEmpty = grid.isGridEmpty();
	    System.out.println("Is Grid Empty? " + isGridEmpty); // Devrait renvoyer true si la grille est vide

	    // Vérifiez si la grille est pleine
	    boolean isGridFull = grid.isGridFull();
	    System.out.println("Is Grid Full? " + isGridFull); // Devrait renvoyer true si la grille est pleine
	    
	    // Vérifiez si le joueur 1 (J1) gagne sur une ligne
	    boolean isLineWinJ2 = grid.isLineJ2win();
	    System.out.println("Is Line Win J2? " + isLineWinJ2); // Devrait renvoyer true si J2 a gagné sur une ligne
	    
	    // Vérifiez si le joueur 1 (J1) gagne sur une ligne
	    boolean isLineWinJ1 = grid.isLineJ1win();
	    System.out.println("Is Line Win J1? " + isLineWinJ1); // Devrait renvoyer true si J1 a gagné sur une ligne
	    
	    // Vérifiez si le joueur 1 (J1) gagne sur une diagonale descendante
	    boolean isDiagonalWinJ1 = grid.isDiagJ1win();
	    System.out.println("Is Diagonal Win J1? " + isDiagonalWinJ1); // Devrait renvoyer true si J1 gagne sur une diagonale
	    
	    boolean isWinJ11 = grid.isJ1win();
	    System.out.println("Is Win J1? " + isWinJ11);
	}




}
