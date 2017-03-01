package controller.employer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import controller.Controller;
import dataAccess.sql.FeedbackDAO_Sql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Validator;
import model.account.EmployerAccount;

public class FeedbackController extends Controller implements Initializable{
	@FXML
    private JFXHamburger fxHamburg;

    @FXML
    private ScrollPane ScrollPaneEmployerHome;

    @FXML
    private VBox VBoxCurrent;

    @FXML
    private JFXTextField fieldReciever;

    @FXML
    private JFXTextField fieldSubject;

    @FXML
    private JFXTextArea areaFeedback;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXDrawer fxDrawer;


	/**
	 * Event Listener on JFXButton[#btnCalendar].onAction
	 * 
	 * @param event
	 */
	@FXML
	void handleCalendar(ActionEvent event) {

	}

	/**
	 * Event Listener on JFXButton[#btnFeedback].onAction
	 * 
	 * @param event
	 */
	@FXML
	public void handleFeedBack(ActionEvent event) {
		try {
			m.swapScene("/employer/Feedback.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Event Listener on JFXButton[#btnHome].onAction
	 * 
	 * @param event
	 */
	@FXML
	public void handleHome(ActionEvent event) {
		try {
			m.swapScene("/employer/Home.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Event Listener on JFXButton[#btnLogout].onAction
	 * 
	 * @param event
	 */
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

	/**
	 * Event Listener on JFXButton[#btnProfile].onAction
	 * 
	 * @param event
	 */
	@FXML
	public void handleProfile(ActionEvent event) {
		try {
			m.swapScene("/employer/Profile.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Event Listener on JFXButton[#fieldSearchBox].onAction
	 * 
	 * @param event
	 */
	@FXML
	public void handleSearchBox(ActionEvent event) {

	}
	/**
	 * Event Listener on JFXButton[#btnSubmit].onAction
	 * 
	 * @param event
	 */
	@FXML
	public void handleSubmit(ActionEvent event) {
		String email= fieldReciever.getText();
		String subject=fieldSubject.getText();
		String feedback=areaFeedback.getText();
		
		System.out.println(email);
		System.out.println(subject);
		System.out.println(feedback);
		boolean validate= Validator.validateFeedback(email, subject,feedback);

		if (validate){
			//store to database and email to recepient
			FeedbackDAO_Sql feedbackDAO = new FeedbackDAO_Sql();
			feedbackDAO.createFeedback(email, subject, feedback);
			System.out.println(email);
			System.out.println(subject);
			System.out.println(feedback);
			try {
				m.swapScene("/employer/Home.fxml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			//error message
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		EmployerAccount acc = (EmployerAccount)s.getAcc();
		
		/* Drawer */
		try {
			VBox box = m.getScene("/employer/Employerdrawer.fxml").load();
			fxDrawer.setSidePane(box);
			fxHamburg.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

				if (fxDrawer.isShown()) {
					fxDrawer.close();
				} else {
					fxDrawer.open();
				}
			});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// end of drawer
		
//		lbName.setText(acc.getCompanyName());
//		lbEmail.setText(acc.getLoginEmail());
		
	}

	@FXML public void handleStatistics(ActionEvent event) {
		try {
			m.swapScene("/employer/StatisticsPie.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML public void handleJobs(ActionEvent event) {
		try {
			m.swapScene("/employer/CreateJob.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
