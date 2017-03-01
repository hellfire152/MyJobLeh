package controller.employee;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import controller.Controller;
import dataAccess.sql.JobsDAO_Sql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.account.EmployeeAccount;
import model.event.WorkEvent;

/**
 * Home Controller is used to control the opening and closing of drawer and also swapping of scene 
 * between each cards
 *  @author Bryan Tan
 *  @since b.2
 *  @Version a.2
 *  
 */
public class HomeController extends Controller implements Initializable {
	@FXML
	private JFXHamburger fxHamburg;
	@FXML
	private JFXDrawer fxDrawer;
	@FXML
	private JFXButton apply1;
	@FXML
	private Label lbJobTitle;
	
	@FXML
	private Label lbCompany;
	
	@FXML
	private Label lbDetail1;
	
	@FXML
	private Label lbDetail2;
	
	@FXML
	private Label lbDetail3;
	
	@FXML
	private Label lbLocation;
	
	@FXML
	private VBox appendVBox;
	
	@FXML
	private TextField search;
	/**
	 * Returns a list of all WorkEvent that fulfills these 2 conditions:
	 * 	-not in the employee's pending or accepted lists
	 * 	-today's date is before the end date of the job
	 */
	private ArrayList<WorkEvent> getAvailableJobs() {
		ArrayList<WorkEvent> availableWorkList = new ArrayList<>();
		EmployeeAccount acc = (EmployeeAccount)s.getAcc();
		JobsDAO_Sql jobDAO = new JobsDAO_Sql();
		
		for (WorkEvent w : jobDAO.getAllJobs()) {
			System.out.println(w.getJobTitle());
			boolean taken = false;
			//Searching through the pending job list
			System.out.println(acc);
			for (WorkEvent work : jobDAO.getPendingOfEmployee(acc.getLoginEmail())) {
				System.out.println(work.getJobTitle());
				if (work.getJobTitle().equals(w.getJobTitle())) {
					taken = true;
					break;
				}
			}
			//searching through the accepted job list
			for (WorkEvent work : jobDAO.getAcceptedOfEmployee(acc.getLoginEmail())) {
				System.out.println(work.getJobTitle());
				if (work.getJobTitle().equals(w.getJobTitle())) {
					taken = true;
					break;
				}
			}
			if (!taken /*and end date ...*/) {
				availableWorkList.add(w);
			}
		}
		
		return availableWorkList;
	}
	
	 @FXML
	 void handleSearch(KeyEvent event) {
       if(event.getCode() == KeyCode.ENTER){
       	if(search.getText() != ""){
	        	JobsDAO_Sql data = new JobsDAO_Sql();
	        	ArrayList<WorkEvent> searchJobList = new JobsDAO_Sql().getSearchJob(search.getText());
	        	searchJobList = data.getSearchJob(search.getText());
	        	SearchController.setSearchList(searchJobList);
	        	System.out.println(search.getText());
	        	try {
					m.swapScene("/employee/Search.fxml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
       	}
       }
	 }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		// Start of drawer
		HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(fxHamburg);
		
		//creates panes and sets the text according to the available jobs
		for(WorkEvent w : getAvailableJobs()){
			try {
				FXMLLoader loader = m.getScene("/employee/HomePane.fxml");
				appendVBox.getChildren().add(loader.load());
				((HomePaneController)loader.getController()).setWorkEvent(w);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//end update pane
		
		/* Drawer */
		try {
			VBox box = m.getScene("/Drawer.fxml").load();
			fxDrawer.setSidePane(box);
			transition.setRate(-1);
			fxHamburg.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
				transition.setRate(transition.getRate() * -1);
				transition.play();

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
	}
	@FXML
	 public void loadApplyJob1(ActionEvent event) {
		try{
			m.swapScene("/ApplyJob.fxml");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
