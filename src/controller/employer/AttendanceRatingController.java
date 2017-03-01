package controller.employer;


import java.awt.image.BufferedImage;

import org.controlsfx.control.Rating;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXRadioButton;

import controller.Controller;
import dataAccess.ImageUpload;
import dataAccess.sql.AccountDAO_Sql;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.account.EmployeeAccount;

public class AttendanceRatingController extends Controller {
//	public AttendanceRatingController(String name) {
//		super(name);
//		// TODO Auto-generated constructor stub
//	}
	@FXML
	private Label lbDate;
	@FXML
	private ImageView profilePic;
	@FXML
	private Label lbName;
	@FXML
	private Label lbContact;
	@FXML
	private JFXRadioButton rbYes;
	@FXML
	private JFXRadioButton rbNo;
	@FXML
	private VBox starRatingPane;
	@FXML
	private ToggleGroup lbChoice;
	  
	private JFXDialog d;
	
	private EmployeeAccount acc;
	private DisplayEmployeeInformationController c;
	String choice = " ";
	JFXComboBox <String> comboBox = new JFXComboBox<String>();
	Rating rating = new Rating();
	boolean present = true;

    @FXML
    void handleYesToggle(ActionEvent event) {
    	comboBox.setDisable(true);
		rating.setDisable(false);
		present = true;
    }
    @FXML
    void handleNoToggle(ActionEvent event) {
    	comboBox.setDisable(false);
    	rating.setDisable(true);
    	present = false;
    }
    public void setDialog(JFXDialog d) {
    	this.d = d;
    }
	public void setEmployeeAccount(EmployeeAccount acc) {
		EmployeeAccount user = acc;
		AccountDAO_Sql accDAO = new AccountDAO_Sql();
		System.out.println(user.getName());
		
		comboBox.setPrefWidth(150);
		comboBox.setPromptText("Select Reason");
		comboBox.getItems().addAll("No Reason", "Medical Reason", "Employer Cancel");
		starRatingPane.getChildren().add(comboBox);
		
		//Rating
		rating.setPartialRating(true);
		rating.setPadding(new Insets(10,0,20,0));
		starRatingPane.getChildren().add(rating);
		
		//Put selected option to Yes in radio button
		lbChoice.selectToggle(rbYes);
		
		//Default disable the following
    	comboBox.setDisable(true);
		rating.setDisable(false);
		
		JFXButton submit = new JFXButton();
		submit.setText("Submit");
		submit.setStyle("-fx-border-color: grey");
		submit.setPrefWidth(160);
		submit.setPrefHeight(40);
		starRatingPane.getChildren().add(submit);
		
		lbName.setText(user.getName());
		lbContact.setText(user.getContact());
		BufferedImage img = accDAO.getProfilePic(user.getLoginEmail());
		img = (img == null)? ImageUpload.defaultProfilePic() : img;
		profilePic.setImage(SwingFXUtils.toFXImage(img, null));
		
		rating.setOnMouseClicked(e->{
			rating.getRating();
		});
		
		submit.setOnAction(e -> {
			System.out.println("---Button clicked---");
			user.addRating(rating.getRating());
			System.out.println("---AttendanceCrtl---\nAverage rating: " + user.averageRating());
			
			s.setEmployeeAccount(true);
			new AccountDAO_Sql().updateAccount(user.getLoginEmail(), user);
			s.setEmployeeAccount(false);
			
			d.close();
			c.setEmployeeDetails(acc);
		});
		
	}
	public void setDisplayController(DisplayEmployeeInformationController c) {
		this.c = c;
	}
}
