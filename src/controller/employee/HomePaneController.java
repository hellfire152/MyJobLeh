package controller.employee;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import controller.Controller;
import dataAccess.sql.JobsDAO_Sql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.account.EmployeeAccount;
import model.event.WorkEvent;
import model.extraWindows.ApplyJobWindow;

public class HomePaneController extends Controller {
	@FXML
	private Label lbJobTitle;
	@FXML
	private Label lbCompany;
	@FXML
	private Label lbNormalRate;
	@FXML
	private Label lbWeekendRate;
	@FXML
	private Label lbLocation;
	@FXML
	private Label lbDate;
	@FXML
	private JFXButton apply1;

	private WorkEvent e;	

	// Event Listener on JFXButton[#apply1].onAction
	@FXML
	public void loadApplyJob1(ActionEvent event) {
		ApplyJobWindow.display(m, e, (EmployeeAccount)s.getAcc());
		try {
			m.swapScene("/employee/Home.fxml");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Method to set all the text in this pane from a specified {@link WorkEvent}
	 * @param e
	 */
	public void setWorkEvent(WorkEvent e) {
		this.e = e;
		lbJobTitle.setText(e.getJobTitle());
		lbCompany.setText(e.getCompanyName());
		lbNormalRate.setText("normalrate");
		lbWeekendRate.setText("weekendrate");
		lbLocation.setText(e.getAddress());
		lbDate.setText(e.getStartDate() +" to " +e.getEndDate());
		
	}
	
}
