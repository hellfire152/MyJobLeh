package controller.employer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import controller.Controller;
import dataAccess.JobsDAO;
import dataAccess.sql.JobsDAO_Sql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.account.EmployeeAccount;
import model.event.WorkEvent;

/**
 * AddApplicantPageController controls the AddApplicantPage
 * 
 * @version a
 * @since a
 * @author Low Qing Ning
 */
public class AddApplicantPageController extends Controller {
	@FXML
	private Label lbJobName;
	@FXML
	private Label lbApplicantName;
	@FXML
	private Label lbReasonsToHire;
	@FXML
	private JFXButton Acceptbtn;
	@FXML
	private JFXButton Declinebtn;
	
	private JobsDAO_Sql jobDAO = new JobsDAO_Sql();
	
	private WorkEvent w;
	private EmployeeAccount acc;
	
	public void setPendingDetails(WorkEvent w, EmployeeAccount acc) {
		this.w = w;
		this.acc = acc;
		lbJobName.setText(w.getJobTitle());
		lbApplicantName.setText(acc.getName());
		lbReasonsToHire.setText(acc.getAboutMe());
	}
	
	// Event Listener on JFXButton[#Acceptbtn].onAction
	public void handleAcceptBtn(ActionEvent event) {
		jobDAO.transferFromPendingToAccepted(acc.getLoginEmail(), w.getJobTitle());	
		try {
			m.swapScene("/employer/ApplicantPage.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Event Listener on JFXButton[#Declinebtn].onAction
	@FXML
	public void handleDeclineBtn(ActionEvent event) {
		jobDAO.deleteFromPending(acc.getLoginEmail(), w.getJobTitle());
		try {
			m.swapScene("/employer/ApplicantPage.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
