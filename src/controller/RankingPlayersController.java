package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Player;
import model.Square;
import model.ValueSquare;

public class RankingPlayersController extends ForAllControllers implements Initializable {

	@FXML
	private TableView<Player> listPlayers;

	@FXML
	private TableColumn<Player, Integer> rank;

	@FXML
	private TableColumn<Player, String> userName;

	@FXML
	private TableColumn<Player, Integer> ptsRanked, nbVictory;

	@FXML
	private TableColumn<Player, String> winRate;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// We attribute the values to the good table column
		userName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName()));
		rank.setCellValueFactory(cellData -> new SimpleObjectProperty<Integer>(getRank(cellData.getValue())));
		ptsRanked
				.setCellValueFactory(cellData -> new SimpleObjectProperty<Integer>(cellData.getValue().getPtsRanked()));
		nbVictory
				.setCellValueFactory(cellData -> new SimpleObjectProperty<Integer>(cellData.getValue().getNbVictory()));
		winRate.setCellValueFactory(cellData -> new SimpleObjectProperty<String>(
				String.format("%.2f%%", cellData.getValue().getWinRate())));

		// Create a sorter for the ptsRanked column
		ptsRanked.setSortType(TableColumn.SortType.DESCENDING);

		// We set the table view
		listPlayers.setItems(FXCollections.observableArrayList(Main.getPlayersData().getValue()));

		// Sort the TableView by the ptsRanked column
		listPlayers.getSortOrder().add(ptsRanked);
		listPlayers.sort();
	}

	// Method that allows to know the rank of the player who depends of his ranked's
	// points
	public int getRank(Player player) {

		int rank = 1;

		for (Player playerdata : Main.getPlayersData().getValue()) {

			// We look if a player has more points
			if ((player.getPtsRanked() < playerdata.getPtsRanked())
					&& (player.getIdentifier() != playerdata.getIdentifier())) {
				rank++;
			}
		}

		return rank;
	}

}
