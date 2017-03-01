package controller.employee;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;

import controller.Controller;
import dataAccess.sql.JobsDAO_Sql;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import model.account.EmployerAccount;
import model.event.WorkEvent;

public class HistoryPastJobsPaneController extends Controller {
	@FXML
	private StackPane stackPane;
	@FXML
	private Label lbJobName;
	@FXML
	private Label lbManagerName;
	@FXML
	private Label lbAddress;
	@FXML
	private Label lbRate;
	@FXML
	private Label lbTotalSalary;
	@FXML
	private JFXButton btnEmail;

	private WorkEvent work;
	private EmployerAccount acc;
	
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
				   if(message.length() >=20){
					   String finalMessage = model.EmailHandler.exEmployeeToEmployerFormat(
							   acc.getName(), s.getAcc().getName(), s.getAcc().getLoginEmail(), message);
					   String subject = "Your past employee";
					   if(model.EmailHandler.sendMail(subject, finalMessage, to)){
						   System.out.println("Success");
						   snackbar.show("Send liaoo", 2000);
						   dialog.close();
					   }
					   else{
						   System.out.println("fail");
					   }
				   }
				   else{
					   System.out.println("Must be more than 20 character");
				   }
				}
			});
		
		aboutMeDialog.setActions(updateAboutMeBtn);
		dialog.show();
	}

	public void setWorkDetails(WorkEvent work, EmployerAccount acc) {
		this.work = work;
		this.acc = acc;
		
		lbJobName.setText(work.getJobTitle());
		lbManagerName.setText("Employer: " + acc.getName());
		
		lbRate.setText("Rate: $" + work.getWeekdayRate() + "/hr");
		lbAddress.setText("Address: " + work.getAddress());
		
	}

}
