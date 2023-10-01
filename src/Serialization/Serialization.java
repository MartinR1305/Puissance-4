package Serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import model.Player;

public class Serialization {

	// Method that allows to save data from players
	public static void serializePlayer(List<Player> listJoueur) {

		try {

			FileOutputStream fos = new FileOutputStream("dataPlayer.ser");

			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(listJoueur);
			oos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Method that allows to update data for players
	public static List<Player> deserializePlayer() {

		try {

			FileInputStream fis = new FileInputStream("dataPlayer.ser");

			ObjectInputStream ois = new ObjectInputStream(fis);

			@SuppressWarnings("unchecked")
			List<Player> paramStr = (List<Player>) ois.readObject();
			ois.close();

			return paramStr;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}