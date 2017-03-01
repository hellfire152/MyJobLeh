package controller.employee;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;

import controller.Controller;
import dataAccess.ImageUpload;
import dataAccess.sql.AccountDAO_Sql;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class DrawerController extends Controller implements Initializable{
	@FXML
	private Label name;
	@FXML
	private Label email;
	@FXML
	private JFXButton jobsButton;
	@FXML
	private JFXButton historyButton;
	@FXML
	private JFXButton profileButton;
	@FXML
	private JFXButton feedbackButton;
	@FXML
	private JFXButton logoutButton;
	@FXML
	private JFXPopup popup;
	@FXML
	private ImageView accountPicture;
	@FXML
	public void showPopup(MouseEvent event) {
		popup.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(),event.getY());
	}
	 //To initialize the popup for history tab in the drawer
    private void initPopup(){
    	JFXButton bl = new JFXButton("Current Jobs");
    	JFXButton bl1 = new JFXButton("Pending Jobs");
    	JFXButton bl2 = new JFXButton("Past Jobs");
    	
    	bl.setPadding(new Insets(10));
    	bl1.setPadding(new Insets(10));
    	bl2.setPadding(new Insets(10));
    	
    	VBox vBox = new VBox(bl,bl1,bl2);
    	
    	popup.setContent(vBox);
    	popup.setSource(historyButton);
    	
    	bl.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event){
				try{
					m.swapScene("/employee/HistoryCurrent.fxml");
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		});
    	bl1.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event){
				try{
					m.swapScene("/employee/HistoryPending.fxml");
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		});
    	bl2.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent event){
				try{
					m.swapScene("/employee/HistoryPastJobs.fxml");
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		});
    	
    }
	
	
	// Event Listener on JFXButton[#jobsButton].onAction
	@FXML
	public void handleJobs(ActionEvent event) {
		try {
			m.swapScene("/employee/Home.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Event Listener on JFXButton[#historyButton].onAction
	@FXML
	public void handleHistory(ActionEvent event) {
		try {
			m.swapScene("/employee/HistoryCurrent.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Event Listener on JFXButton[#profileButton].onAction
	@FXML
	public void handleProfile(ActionEvent event) {
		try {
			m.swapScene("/employee/Profile.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Event Listener on JFXButton[#feedbackButton].onAction
	@FXML
	public void handleFeedback(ActionEvent event) {
		try {
			m.swapScene("/employee/Feedback.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
    void handleCalendar(ActionEvent event) {
    	try {
    		System.out.println("Acb");
			m.swapScene("/employee/ProfileTimetable.fxml");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	// Event Listener on JFXButton[#logoutButton].onAction
	@FXML
	public void handleLogout(ActionEvent event) {
		try {
			m.swapScene("/EmployeeOrEmployer.fxml");
			s.setAcc(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initPopup();
		name.setText(s.getAcc().getName());
		email.setText(s.getAcc().getLoginEmail());		
		
		BufferedImage profilePic = new AccountDAO_Sql().getProfilePic(s.getAcc().getLoginEmail());
		profilePic = (profilePic == null)? ImageUpload.defaultProfilePic() : profilePic;
		accountPicture.setImage(SwingFXUtils.toFXImage(profilePic, null));
	}
}
