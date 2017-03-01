package controller.employer;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import controller.Controller;
import dataAccess.sql.JobsDAO_Sql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import model.account.EmployeeAccount;
import model.event.WorkEvent;

public class StatisticsBarController extends Controller {

    @FXML
    private JFXButton Homebtn;

    @FXML
    private Label lbStatistics;

    @FXML
    private BarChart<?, ?> showBarChart;

    @FXML
    private CategoryAxis monthAxis;

    @FXML
    private NumberAxis noOfEmployeeAxis;

    @FXML
    private JFXButton btnPie;

    @FXML
    private JFXButton btnBar;
	/**
	 * initialise bar graph
	 * 
	 * @param event
	 */
    public void initialize(){
    	XYChart.Series list = new XYChart.Series<>();
    	JobsDAO_Sql jobDAO = new JobsDAO_Sql();
    	System.out.println(s.getAcc().getLoginEmail());
		ArrayList<WorkEvent> workArr = jobDAO.getJobOfEmployer(s.getAcc().getLoginEmail());
		System.out.println(workArr);
		if (workArr != null ){
			
    	for(int i=0;i<workArr.size();i++){
			ArrayList<EmployeeAccount> employeeList = jobDAO.getAcceptedOfJob(workArr.get(i).getJobTitle());			
			if (employeeList!=null &&employeeList.size()>0){
				list.getData().add(new XYChart.Data(workArr.get(i).getJobTitle(),employeeList.size()));
			
			}
		
		}    		
    	showBarChart.setTitle("Number of employees");
    	showBarChart.setLegendSide(Side.LEFT);

    	showBarChart.getData().addAll(list);
		}
    }
    /**
	 * Refresh to bar graph
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
	 * Change to pie graph
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
