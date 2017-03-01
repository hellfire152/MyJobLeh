package controller;

import javafx.fxml.FXML;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import application.MyJobLeh;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

/**
 * 
 * Used in drawerHistory.fxml
 * 
 * @version a.2
 * @since a
 * @author NigelChen
 *
 */
public class DrawerHistoryController extends Controller {
	@FXML
	private Label lbName;
	@FXML
	private Label lbEmail;
	@FXML
	private JFXButton btnHome;
	@FXML
	private JFXButton btnHistory;
	@FXML
	private JFXButton btnProfile;
	@FXML
	private JFXButton btnFeedback;
	@FXML
	private JFXButton btnLogout;

	// Event Listener on JFXButton[#btnHome].onAction
	@FXML
	public void handleHome(ActionEvent event) {
		try {
			m.swapScene("/employee/Home.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Event Listener on JFXButton[#btnHistory].onAction
	@FXML
	public void handleHistory(ActionEvent event) {
		try {
			m.swapScene("/employee/HistoryCurrent.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Event Listener on JFXButton[#btnProfile].onAction
	@FXML
	public void handleProfile(ActionEvent event) {
		try {
			m.swapScene("/employee/Profile.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Event Listener on JFXButton[#btnFeedback].onAction
	@FXML
	public void handleFeedback(ActionEvent event) {
		try {
			m.swapScene("/employee/Feedback.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Event Listener on JFXButton[#btnLogout].onAction
	@FXML
	public void handleLogout(ActionEvent event) {
		try {
			m.swapScene("/employee/EmployeeOrEmployer.fxml");
			s.setAcc(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
