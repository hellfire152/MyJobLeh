package controller.employer;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import controller.Controller;
import dataAccess.sql.JobsDAO_Sql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import model.event.WorkEvent;

/**
 * JobDetailsPageController controls the JobDetailsPage
 * 
 * @version a.1.1
 * @since a
 * @author Low Qing Ning
 */
public class JobDetailsPageController extends Controller {
	@FXML
	private Label lbJobName;
	@FXML
	private Label lbJonbDetails;
	@FXML
	private Label lbWeekdayRate;
	
	@FXML
	private Label lbWeekendRate;
	
	@FXML
	private Label lbAdddress;
	
	@FXML
	private Label lbFillInJobDetails;
	
	@FXML
	private Label lbFillInAddress;
	
	@FXML
	private Label lbFillInWeekdayRate;
	
	@FXML
	private Label lbFillInWeekendRate;

	@FXML
	private JFXButton editBtn;

	@FXML
	private JFXButton showEmployeeBtn;
	@FXML
	private JFXButton fileTrannsferBtn;
	@FXML
	private JFXButton btnChat;
	
	@FXML
	private StackPane stackPane;
	@FXML
	private HBox container;
	
	private WorkEvent work;

	/**
	 * Event Listener on JFXButton[#editBtn].onAction
	 * 
	 * @param event
	 */
	@FXML
	public void handleEditBtn(ActionEvent event) {
		
			try {
				FXMLLoader loader = m.getScene("/employer/EditJob.fxml");
				
				JFXDialogLayout content = new JFXDialogLayout();
				content.setHeading(new Text("Edit Job Details"));
			
				content.getChildren().add(loader.load());
				((EditJobController)loader.getController()).setWorkEvent(work);
				
				JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.BOTTOM);
				dialog.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		

	}

	/**
	 * Event Listener on JFXButton[#showEmployeeBtn].onAction
	 * 
	 * @param event
	 */
	@FXML
	public void handleShowEmployeeBtn(ActionEvent event) {
		try {
			ListOfEmployeesController c = (ListOfEmployeesController) m.swapScene("/employer/ListOfEmployees.fxml");
			c.setListOfEmployee(new JobsDAO_Sql().getAcceptedOfJob(work.getJobTitle()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Event Listener on JFXButton[#fileTrannsferBtn].onAction
	 * 
	 * @param event
	 */

	
	@FXML
	public void handleFileTransferBtn(ActionEvent event) {
		try {
			FileTransferController.setWork(work);
			m.swapScene("/employer/FileTransfer.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    @FXML
    void handleChat(ActionEvent event) {
		ChatController.setW(work);
		System.out.println(work.getJobTitle());
		try {
			m.swapScene("/employer/Chat.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


	public void setJobDetails (WorkEvent work){
		this.work =work;
		lbJobName.setText(work.getJobTitle());
		lbFillInJobDetails.setText(work.getJobScope());
		lbFillInAddress.setText(work.getAddress());
		lbFillInWeekdayRate.setText(work.getWeekdayRate());
		lbFillInWeekendRate.setText(work.getWeekendRate());
	}

}
