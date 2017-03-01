package controller.employer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import controller.Controller;
import dataAccess.JobsDAO;
import dataAccess.sql.JobsDAO_Sql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.event.WorkEvent;

public class EditJobController extends Controller {
	@FXML
	private JFXButton Homebtn;

	@FXML
	private Label lbJob;

	@FXML
	private JFXButton lbBack;

	@FXML
	private Label lbEdit;

	@FXML
	private Label lbJobName;

	@FXML
    private JFXTextArea areaJobDetails;

    @FXML
    private JFXTextField fieldWeekdayRate;

    @FXML
    private JFXTextField fieldWeekendRate;

    @FXML
    private JFXTextArea areaAddress;

	@FXML
	private JFXButton btnSave;
	
	private WorkEvent work;
		
	// Event Listener on JFXButton[#Homebtn].onAction
	@FXML
	public void handleHome(ActionEvent event) {
		try {
			m.swapScene("/employer/Home.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    @FXML
    public void handleSave(ActionEvent event) {
    	String jobScope = areaJobDetails.getText(); 
    	String address = areaAddress.getText();
    	String weekdayRate=fieldWeekdayRate.getText();
    	String weekendRate=fieldWeekendRate.getText();
    	
    	if (!jobScope.equals("")&&!address.equals("")&&!weekdayRate.equals("")&&!weekendRate.equals("")){
    		JobsDAO_Sql jobDAO = new JobsDAO_Sql();
    		
    		//store to database
    		work.setJobScope(jobScope);
    		work.setAddress(address);
    		work.setWeekdayRate(weekdayRate);
    		work.setWeekendRate(weekendRate);
    		jobDAO.updateJob(work.getJobTitle(), work);
    		
    		try {
				m.swapScene("/employer/Home.fxml");
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	else{
    		//error message
    		System.out.println("Please describe the job");
    	}
    }

    public void setWorkEvent(WorkEvent work) {
    	this.work = work;
    	
		lbJobName.setText(work.getJobTitle());
		areaJobDetails.setText(work.getJobScope());
		fieldWeekdayRate.setText(work.getWeekdayRate());
		fieldWeekendRate.setText(work.getWeekendRate());
		areaAddress.setText(work.getAddress());
    }
}
