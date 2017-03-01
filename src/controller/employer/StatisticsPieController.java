package controller.employer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.MyJobLeh;
import controller.Controller;
import dataAccess.sql.JobsDAO_Sql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.account.EmployeeAccount;
import model.account.EmployerAccount;
import model.event.WorkEvent;
import model.extraWindows.ErrorPopup;

public class StatisticsPieController extends Controller implements Initializable{

    @FXML
    private JFXButton Homebtn;

    @FXML
    private Label lbStatistics;

    @FXML
    private JFXButton btnPie;

    @FXML
    private JFXButton btnBar;

    @FXML
    private AnchorPane container;
 
    @FXML
    private PieChart showPieChart;
    
    private JobsDAO_Sql jobDAO = new JobsDAO_Sql();
   
	{
		m = MyJobLeh.getInstance();
	}

    

	/**
	 * Change to bar graph
	 * 
	 * @param event
	 */
    @FXML
    void handleBar(ActionEvent event) {
    	try {
    		EmployerAccount acc = (EmployerAccount)s.getAcc();
    		System.out.println(acc);
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
	/**
	 * initialise pie chart
	 * 
	 * @param event
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// TODO Auto-generated method stub
		System.out.println(s.getAcc().getLoginEmail());
		ArrayList<WorkEvent> workArr = jobDAO.getJobOfEmployer(s.getAcc().getLoginEmail());
		System.out.println(workArr);
		if (workArr != null ){
			System.out.println("hi");
    	ObservableList<Data> list =FXCollections.observableArrayList();
    	for(int i=0;i<workArr.size();i++){
			ArrayList<EmployeeAccount> employeeList = jobDAO.getAcceptedOfJob(workArr.get(i).getJobTitle());			
			if (employeeList!=null &&employeeList.size()>0){
				list.add(new PieChart.Data(workArr.get(i).getJobTitle(), employeeList.size()));
//				System.out.println(employeeList.size());
			}
			
			
		}    		
    	showPieChart.setTitle("Number of employees");
    	showPieChart.setData(list);
    	showPieChart.setLegendSide(Side.LEFT);
    	final Label caption = new Label("");
    	caption.setTextFill(Color.WHITE);
    	caption.setStyle("-fx-font: 24 arial;");
    	
    	System.out.println(caption);
    	for (final Data data : showPieChart.getData()) {
    	    data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
    	        new EventHandler<MouseEvent>() {
    	    	@Override
    	            public void handle(MouseEvent e) {
    	    		caption.setText(String.valueOf(data.getPieValue()));
    	                caption.setTranslateX(e.getSceneX() + 10);
    	                caption.setTranslateY(e.getSceneY() - 50);
    	                System.out.println(caption);
    	             }
    	        });
    	}
    	//check later when add employee
    	System.out.println(container);
    	container.getChildren().add(caption);//this thing not working
		}
		
		
	}

}

