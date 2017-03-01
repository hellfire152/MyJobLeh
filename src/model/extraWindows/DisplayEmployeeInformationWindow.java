package model.extraWindows;

import java.io.IOException;

import application.MyJobLeh;
import controller.employer.DisplayEmployeeInformationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.account.EmployeeAccount;
import model.event.WorkEvent;

/**
 * Contains a method that shows employer.DisplayEmployeeInformation.fxml in a new window
 * 
 * @author Ning
 * @version b.2
 * @since b.2
 */
public class DisplayEmployeeInformationWindow {
	public static void display(MyJobLeh m, EmployeeAccount acc) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		
		try {
			FXMLLoader loader = m.getScene("/employer/DisplayEmployeeInformation.fxml");
			
			window.setScene(new Scene(loader.load()));
			
			((DisplayEmployeeInformationController)loader.getController()).setEmployeeDetails(acc);
			
			window.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
