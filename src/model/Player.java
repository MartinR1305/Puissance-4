package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player implements Serializable {
	private UUID identifier;
	private String userName;
	private String lastName;
	private String firstName;
	private int age;
	private int nbVictory;
	private int nbDefeat;
	private int nbDraw;
	private double winRate;
	private int ptsRanked;
	private List<List<String>> listMatchs;

	/**
	 * Constructor
	 * 
	 * @param userName : The user name of the player 
	 * @param lastName : The last name of the player 
	 * @param firstName : The first name of the player 
	 * @param age : The age of the player 
	 */
	public Player(String userName, String lastName, String firstName, int age) {
		identifier = UUID.randomUUID();
		this.userName = userName;
		this.lastName = lastName;
		this.firstName = firstName;
		this.age = age;
		nbVictory = 0;
		nbDefeat = 0;
		nbDraw = 0;
		winRate = 0D;
		ptsRanked = 0;
		listMatchs = new ArrayList<>();

		// Checking if the age is positive
		if (age < 0) {
			throw new IllegalArgumentException("A person's age can't be negative");
		}
	}

	/**
	 * Getter for the identifier
	 * 
	 * @return identifier : The identifier of the player
	 */
	public UUID getIdentifier() {
		return identifier;
	}

	/**
	 * Setter for the user name
	 * 
	 * @return userName : The user name of the player
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Setter for the user name
	 * 
	 * @param userName : The new user name of the player
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Getter for the last name
	 * 
	 * @return lastName : The last name of the player
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for the last name
	 * 
	 * @param lastName : The new last name of the player
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for the first name
	 * 
	 * @return firstName : The first name of the player
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for the first name
	 * 
	 * @param firstName : the new first name of the player
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for the age
	 * 
	 * @return age : The age of the player
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Setter for the age
	 * 
	 * @param age : The new age of the player
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Getter for the number of victories
	 * 
	 * @return nbVictory : The number of victories of the player
	 */
	public int getNbVictory() {
		return nbVictory;
	}

	/**
	 * Method that allows to add one victory for the player
	 */
	public void addVictory(int nbCoins) {
		this.nbVictory++;
		
		int ptsWon = 43 - nbCoins;
		// We actualize the ranked's points
		setPtsRanked(ptsRanked + ptsWon);
	}

	/**
	 * Getter for the number of defeats
	 * 
	 * @return nbDefeat : The number of defeats of the player
	 */
	public int getNbDefeat() {
		return nbDefeat;
	}

	/**
	 * Method that allows to add one defeat for the player
	 */
	public void addDefeat(int nbCoins) {
		this.nbDefeat++;

		int ptsLost = 43 - nbCoins;
		// We actualize the ranked's points
		setPtsRanked(ptsRanked - ptsLost);
	}

	/**
	 * Getter for the number of draws
	 * 
	 * @return nbDraw : The number of draws of the player
	 */
	public int getNbDraw() {
		return nbDraw;
	}

	/**
	 * Method that allows to add one draw for the player
	 */
	public void addDraw() {
		this.nbDraw++;
	}

	/**
	 * Getter for the win rate
	 * @return winRate : The win rate of the player
	 */
	public double getWinRate() {
		return winRate;
	}

	/**
	 * Method that allows to actualize the win rate of the player
	 */
	public void actualizeWinRate() {
		this.winRate = ((double) this.nbVictory / (double) (this.nbDefeat + this.nbDraw + this.nbVictory)) * 100;
	}

	/**
	 * Getter for the ranked's points
	 * @return ptsRanked : The ranked's points of the player
	 */
	public int getPtsRanked() {
		return ptsRanked;
	}

	/**
	 * Setter for the ranked's points
	 * 
	 * @param ptsRanked : New ranked's points of the player
	 */
	public void setPtsRanked(int ptsRanked) {

		// We check if the ptsRanked are not negative
		if (ptsRanked >= 0) {
			this.ptsRanked = ptsRanked;
		}

		else {
			this.ptsRanked = 0;
		}
	}

	/** Getter for the matchs's list
	 * @return listMatchs : The matchs's list of the player
	 */
	public List<List<String>> getListMatchs() {
		return listMatchs;
	}

	/**
	 * Method that allows to add a match to a player with the result associate
	 * 
	 * @param opponent : The opponent that faced the player during the match
	 * @param results : The results of the match
	 * @param nbCoins : Coins' number of the grid
	 */
	public void addMatch(String opponent, Results results, int nbCoins) {

		// Here we add the "challenged player + result" to the match's list of the
		// player
		List<String> joueur = new ArrayList<>();
		joueur.add(opponent);
		joueur.add(results.name());
		listMatchs.add(joueur);

		// Here we synchronize his number of defeats / draws / victories
		if (results.equals(Results.DEFEAT)) {
			this.addDefeat(nbCoins);
		} else if (results.equals(Results.DRAW)) {
			this.addDraw();
		} else if (results.equals(Results.VICTORY)) {
			this.addVictory(nbCoins);
		}

		this.actualizeWinRate();
	}

	/**
	 * Method that allows to display information of the player
	 */
	@Override
	public String toString() {
		return "UserName = " + userName + ", LastName = " + lastName + ", FirstName = " + firstName + ", Age = " + age
				+ ", Identifier = " + identifier + ", Number of victories = " + nbVictory + ", Number of defeats = "
				+ nbDefeat + ", Number of draws = " + nbDraw + "% \nListe des Matchs = \n" + listMatchs;
	}
}