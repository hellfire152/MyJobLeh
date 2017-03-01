package model.extraWindows;

import java.io.IOException;

import application.MyJobLeh;
import controller.employee.ApplyJobController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.account.EmployeeAccount;
import model.event.WorkEvent;

/**
 * Contains a method that launches a new window for employee.ApplyJob.fxml
 * This is supposed to run when the view button on the jobs in the employee home pane
 * is clicked
 * 
 * @author AJK
 * @version b.2
 * @since b.2
 */
public class ApplyJobWindow {
	public static void display(MyJobLeh m, WorkEvent e, EmployeeAccount acc) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		
		try {
			FXMLLoader loader = m.getScene("/employee/ApplyJob.fxml");
			
			window.setScene(new Scene(loader.load()));
			
			((ApplyJobController)loader.getController()).setWorkEvent(e);
			
			window.showAndWait();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
