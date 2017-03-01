package controller.employee;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;

public class StatisticsPieController extends Controller{

    @FXML
    private JFXButton Homebtn;

    @FXML
    private Label lbStatistics;

    @FXML
    private JFXButton btnPie;

    @FXML
    private JFXButton btnBar;

 
    @FXML
    private PieChart showPieChart;
   
	/**
	 * initialise pie chart
	 * 
	 * @param event
	 */
    public void initialize(){
    	ObservableList<Data> list =FXCollections.observableArrayList(

    			new PieChart.Data("Nigel",50),
    			new PieChart.Data("Bryan", 10),
    			new PieChart.Data("Kuan",30),
    			new PieChart.Data("Ning", 10)
    			
    			);
    	showPieChart.setData(list);
    }
	/**
	 * Change to bar graph
	 * 
	 * @param event
	 */
    @FXML
    void handleBar(ActionEvent event) {
    	try {
			m.swapScene("/employer/StatisticsBar.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	/**
	 * Go home
	 * 
	 * @param event
	 */
    @FXML
    void handleHome(ActionEvent event) {
    	try {
			m.swapScene("/employer/Home.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	/**
	 * Refresh pie
	 * 
	 * @param event
	 */
    @FXML
    void handlePie(ActionEvent event) {
    	try {
			m.swapScene("/employer/StatisticsPie.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
  
}

