package controller.employer;

import java.awt.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import application.MyJobLeh;
import controller.Controller;
import dataAccess.sql.JobsDAO_Sql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import model.account.EmployeeAccount;
import model.event.WorkEvent;
import model.extraWindows.ApplyJobWindow;
import model.extraWindows.DisplayEmployeeInformationWindow;

public class ListOfEmployeesController extends Controller implements Initializable {
	@FXML
	private JFXListView listEmployeeList;
	@FXML
	private JFXButton btnDisplayInformation;

	private ObservableList<EmployeeAccount> employeeList;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		employeeList = FXCollections.observableArrayList();
		listEmployeeList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	/**
	 * Event Listener on JFXButton[#btnDisplayInformation].onAction
	 * 
	 * @param event
	 */
	@FXML
	public void handleDisplayInformation(ActionEvent event) {
		
		ObservableList<EmployeeAccount> selectedEmployees = listEmployeeList.getSelectionModel().getSelectedItems();
		//displays employee information in new window
		for (EmployeeAccount acc: selectedEmployees){
			DisplayEmployeeInformationWindow.display(m, acc);
		}		
	}

	// Event Listener on JFXButton[#Homebtn].onAction
	@FXML
	public void handleHome(ActionEvent event) {
		try {
			m.swapScene("/employer/Home.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setListOfEmployee(ArrayList<EmployeeAccount> listEmployee){
		employeeList.setAll(listEmployee);
		
		//display names in listView
		listEmployeeList.setItems(employeeList);
	}
	
}
