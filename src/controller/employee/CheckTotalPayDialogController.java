package controller.employee;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.event.WorkEvent;

public class CheckTotalPayDialogController extends Controller implements Initializable{
	@FXML
	private Label lbRate;
	@FXML
	private JFXTextField hoursWorked;
	@FXML
	private Label lbTotal;
	@FXML
	private Label lbCompanyName;
	@FXML
	private JFXButton btnDone;
	
	private static WorkEvent workObj;
	private int hours;

	// Event Listener on JFXButton[#btnDone].onAction
	@FXML
	public void handleDone(ActionEvent event) {
		HistoryCurrentPaneController crtl = new HistoryCurrentPaneController();
		crtl.getDialog().close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hoursWorked.textProperty().addListener(
		        (observable, oldValue, newValue) -> {
		        	try {
		        		hours = Integer.parseInt(newValue);
		        		if (hours > 10000) {
		        			lbTotal.setText("I call bullshit");
		        		} else {
		        			calculatePay();		        		
		        		}
		        	} catch (NumberFormatException e) {
		        		System.out.println("not an int");
		        		lbTotal.setText("--");
		        	}
		        }
		);

		DecimalFormat df = new DecimalFormat("$0.00");
		lbCompanyName.setText(workObj.getCompanyName());
		lbRate.setText("$"+ workObj.getWeekdayRate() + "/h");
	}

	public static WorkEvent getWorkObj() {
		return workObj;
	}

	public static void setWorkObj(WorkEvent workObj) {
		CheckTotalPayDialogController.workObj = workObj;
	}
	
	@FXML
	public void calculatePay() {
		double rate = Double.parseDouble(workObj.getWeekdayRate());
		String total = String.format("$%.2f", hours * rate);
		lbTotal.setText(total);
	}
	
}
