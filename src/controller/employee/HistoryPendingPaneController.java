package controller.employee;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;

import controller.Controller;
import dataAccess.sql.JobsDAO_Sql;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import model.account.EmployeeAccount;
import model.account.EmployerAccount;
import model.event.WorkEvent;
import model.extraWindows.ApplyJobWindow;

/**
 * 
 * @version a.3
 * @since a.1
 * @author NigelChen
 * 
 */
public class HistoryPendingPaneController extends Controller {
	@FXML
	private AnchorPane anchorCurrentPane;
	@FXML
	private Label lbJobName;
	@FXML
	private Label lbManagerName;
	@FXML
	private Label lbHourlyRate;
	@FXML
	private Label lbContact;
	@FXML
	private JFXButton btnMoreDetails;
	@FXML
	private JFXButton btnCancel;
	@FXML
	private JFXButton btnEmail;
	@FXML
	private StackPane stackPane;

	private WorkEvent work;
	private EmployerAccount acc;
	
	// Event Listener on JFXButton[#btnMoreDetails].onAction
	@FXML
	public void handleMoreDetails(ActionEvent event) {
		ApplyJobWindow.display(m, work, (EmployeeAccount)s.getAcc());
	}

	// Event Listener on JFXButton[#btnCancel].onAction
	@FXML
	public void handleCancel(ActionEvent event) {
		JobsDAO_Sql jobDAO = new JobsDAO_Sql();
		jobDAO.deleteFromPending(s.getAcc().getLoginEmail(), work.getJobTitle());
		try {
			m.swapScene("/employee/HistoryPending.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Event Listener on JFXButton[#btnEmail].onAction
	@FXML
	public void handleEmail(ActionEvent event) {
		JFXDialogLayout aboutMeDialog = new JFXDialogLayout();
		JFXTextArea emailTextArea = new JFXTextArea();
		emailTextArea.setPromptText("Enter your Message here: ");
		aboutMeDialog.setHeading(new Text("Send your message: "));
		aboutMeDialog.setBody(emailTextArea);
		
		JFXDialog dialog = new JFXDialog(stackPane, aboutMeDialog, JFXDialog.DialogTransition.RIGHT);
		JFXButton updateAboutMeBtn = new JFXButton("Send");
		updateAboutMeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				JFXSnackbar snackbar = new JFXSnackbar(stackPane);
				String employerEmail = new JobsDAO_Sql().getEmployerOfJob(work.getJobTitle()).getLoginEmail();
				   String[] to = {employerEmail}; //Email to my mail.
				   String message = emailTextArea.getText();
				   String finalMessage = model.EmailHandler.appliedJobToEmployerFormat(acc.getName(), s.getAcc().getName(), s.getAcc().getLoginEmail(), message);
				   String subject = "Enquries about the job";
				   if(model.EmailHandler.sendMail(subject, finalMessage, to)){
					   System.out.println("Success");
					   snackbar.show("Send liaoo", 2000);
				   }
				   else{
					   System.out.println("fail");
					   }
						
						dialog.close();
					}
				});
		
		aboutMeDialog.setActions(updateAboutMeBtn);
		dialog.show();
	}
	
	public void setWorkDetails(WorkEvent work, EmployerAccount acc) {
		this.work = work;
		this.acc = acc;
		
		lbJobName.setText(work.getJobTitle());
		lbHourlyRate.setText("Rate: $" + work.getWeekdayRate());
		lbContact.setText("Address: " + work.getAddress());
		lbManagerName.setText("Employer: " + acc.getName());
		System.out.println(":as");
		
		
	}
}
