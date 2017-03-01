package controller.employer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import application.MyJobLeh;
import controller.Controller;
import dataAccess.sql.JobsDAO_Sql;
import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.account.EmployeeAccount;
import model.event.WorkEvent;

/**
 * ApplicantPageController controls the ApplicantPage and appends the
 * AddApplicantPage for different users that apply for the job created by the
 * employer
 * 
 * @version a.2
 * @since a
 * @author Low Qing Ning
 */
public class ApplicantPageController extends Controller {
	@FXML
	private JFXButton Homebtn;
	@FXML
	private Label lbJob;
	@FXML
	private JFXButton lbBack;
	@FXML
	private Label lbApplicant;
	@FXML
	private ScrollPane ScrollPaneApplicationPage;
	@FXML
	private VBox VBoxApplication;

	{
		m = MyJobLeh.getInstance();
	}

	public void initialize() {
		JobsDAO_Sql jobDAO = new JobsDAO_Sql();

		ArrayList<WorkEvent> workList = jobDAO.getJobOfEmployer(s.getAcc().getLoginEmail());
		
		if(workList != null){
			for(WorkEvent w : workList){
				System.out.println(w.getJobTitle() +" " +w.getEmployeeEmail());
				ArrayList<EmployeeAccount> employeeList = jobDAO.getPendingOfJob(w.getJobTitle());
				System.out.println(employeeList);
				for (EmployeeAccount acc : employeeList) {
					System.out.println(acc.getLoginEmail());
					try {
						FXMLLoader loader = m.getScene("/employer/AddApplicantPage.fxml");
						
						VBoxApplication.getChildren().add(loader.load());
						
						AddApplicantPageController c = (AddApplicantPageController) loader.getController();
						c.setPendingDetails(w, acc);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
	}

    	
	@FXML public void handleBack(ActionEvent event) {
		try {
			m.swapScene("/employer/Home.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
		
}
	


