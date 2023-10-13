package model;

import java.util.Random;

public class Algorithm {
	
	private int lvl;
	
	public Algorithm(int lvl) {
		this.lvl = lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public int testAlgo(Grid grid) {
		
		Random random = new Random();
		return random.nextInt(6) + 1;
	}

}
