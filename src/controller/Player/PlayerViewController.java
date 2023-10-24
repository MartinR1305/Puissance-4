package controller.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.ForAllControllers;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Player;

public class PlayerViewController extends ForAllControllers {
    
    @FXML
    PieChart pieData;

	@FXML
	Label firstName, winRate, lastName, userName, age;

	@FXML
	Button back;
	
	@FXML
	private TableView<List<String>> listMatch;
	
	@FXML
	private TableColumn<List<String>, String> results, opponent;
	
	public void viewPlayer(Player p) {
		
		// We set data of the player selected
		firstName.setText(p.getFirstName());
		lastName.setText(p.getLastName());
		userName.setText(p.getUserName());
		age.setText(Integer.toString(p.getAge()));
		
		// We create a format in order to have only 2 significant figures
        DecimalFormat format = new DecimalFormat("#.##");
        String winRateFormat = format.format(p.getWinRate());
		winRate.setText(winRateFormat + " %");
		
		// We set the data for the table view
		results.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
		opponent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
		
		// We reverse the list
		List<List<String>> listMatchsReverse = new ArrayList<>(p.getListMatchs());
		Collections.reverse(listMatchsReverse);
		listMatch.setItems(FXCollections.observableArrayList(listMatchsReverse));

		// We set data for the pie
        PieChart.Data slice1 = new PieChart.Data("Victories", p.getNbVictory());
        PieChart.Data slice2 = new PieChart.Data("Draws", p.getNbDraw());
        PieChart.Data slice3 = new PieChart.Data("Defeats", p.getNbDefeat());
        pieData.getData().addAll(slice1, slice2, slice3);
        pieData.setLabelsVisible(false);
	}
}
