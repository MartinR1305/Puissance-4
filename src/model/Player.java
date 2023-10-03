package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player implements Serializable {
	private UUID identifier;
	private String userName;
	private String name;
	private String firstName;
	private int age;
	private int nbVictory;
	private int nbDefeat;
	private int nbDraw;
	private int ptsRank;
	private List<List<String>> listMatchs;

	// Constructor
	public Player(String userName, String name, String firstName, int age) {
		identifier = UUID.randomUUID();
		this.userName = userName;
		this.name = name;
		this.firstName = firstName;

		// Verification if the age is positive
		if (age < 0) {
			throw new IllegalArgumentException("A person's age can't be negative");
		}

		this.age = age;
		nbVictory = 0;
		nbDefeat = 0;
		nbDraw = 0;
		listMatchs = new ArrayList<>();
	}

	// Getter for the identifier
	public UUID getIdentifier() {
		return identifier;
	}

	// Setter for the user name
	public String getUserName() {
		return userName;
	}

	// Setter for the user name
	public void setUserName(String userName) {
		this.userName = userName;
	}

	// Getter for the name
	public String getName() {
		return name;
	}

	// Setter for the name
	public void setNom(String name) {
		this.name = name;
	}

	// Getter for the first name
	public String getFirstName() {
		return firstName;
	}

	// Setter for the first name
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	// Getter for the age
	public int getAge() {
		return age;
	}

	// Setter for the age
	public void setAge(int age) {
		this.age = age;
	}

	// Getter for the number of victories
	public int getNbVictory() {
		return nbVictory;
	}

	// Method that allows to add one victory for the player
	public void addVictory() {
		this.nbVictory++;
	}

	// Getter for the number of defeats
	public int getNbDefeat() {
		return nbDefeat;
	}

	// Method that allows to add one defeat for the player
	public void addDefeat() {
		this.nbDefeat++;
	}

	// Getter for the number of draws
	public int getNbDraw() {
		return nbDraw;
	}

	// Method that allows to add one draw for the player
	public void addDraw() {
		this.nbDraw++;
	}

	// Getter for the matchs's list
	public List<List<String>> getListMatchs() {
		return listMatchs;
	}

	// Setter for the matchs's list
	public void setListMatchs(List<List<String>> listMatchs) {
		this.listMatchs = listMatchs;
	}

	// Method that allows to add a match to a player with the result associate
	public void addMatch(String nomJoueur, Results results) {

		// Here we add the "challenged player + result" to the match's list of the player
		List<String> joueur = new ArrayList<>();
		joueur.add(nomJoueur);
		joueur.add(results.name());
		listMatchs.add(joueur);

		// Here we synchronize his number of defeats / draws / victories
		if (results.equals(Results.DEFEAT)) {
			this.addDefeat();
		} else if (results.equals(Results.DRAW)) {
			this.addDraw();
		} else if (results.equals(Results.VICTORY)) {
			this.addVictory();
		}
	}

	@Override
	public String toString() {
		return "UserName = " + userName + ", Name = " + name + ", FirstName = " + firstName + ", Age = " + age
				+ ", Identifier = " + identifier + ", Number of victories = " + nbVictory + ", Number of defeats = "
				+ nbDefeat + ", Number of draws = " + nbDraw + "% \nListe des Matchs = \n" + listMatchs;
	}
}
