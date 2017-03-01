package controller.employee;
/**
 * 
 * This is the dialog box for the personal info section of the profile page
 * @author Bryan Tan
 * @since a.2
 * 
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import controller.Controller;
import dataAccess.ImageUpload;
import dataAccess.sql.AccountDAO_Sql;
import dataAccess.sql.JobsDAO_Sql;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import model.account.EmployeeAccount;

public class EditPersonalDialogController extends Controller {
	@FXML
	private StackPane stackPane;
	@FXML
	private JFXTextField fieldName;

	@FXML
	private JFXDatePicker fieldDOB;

	@FXML
	private JFXRadioButton rbMale;

	@FXML
	private JFXRadioButton rbFemale;

	@FXML
	private JFXTextField fieldMobile;

	@FXML
	private JFXRadioButton rbSingle;

	@FXML
	private JFXRadioButton rbMarried;

	@FXML
	private JFXRadioButton rbDivorced;

	@FXML
	private JFXButton cancelBtn;

	@FXML
	private ToggleGroup status;
	 
	@FXML
	private ToggleGroup gender;
	
	@FXML
	private static JFXButton updateBtn;
	
	public void initialize() {
		fieldName.setText(s.getAcc().getName());
	}
	/**
	 * When handleUpdate is called, it will update all the things where the user update to database.
	 */
	@FXML
	void handleUpdate(ActionEvent event) {
		
		EmployeeAccount user = (EmployeeAccount)s.getAcc();
		if(!fieldName.getText().equals("")){
			user.setName(fieldName.getText());
		}
//		user.setDateList(fieldDOB.);
		
		if(rbMale.isSelected()){
			user.setGender(rbMale.getText());
		}else if (rbFemale.isSelected()){
			user.setGender(rbFemale.getText());
		}
		
		if(rbSingle.isSelected()){
			user.setStatus(rbSingle.getText());
		}else if (rbMarried.isSelected()){
			user.setStatus(rbMarried.getText());
		}else if(rbDivorced.isSelected()){
			user.setStatus(rbDivorced.getText());
		}

		
		if(!fieldMobile.getText().equals("")){
			user.setContact(fieldMobile.getText());
		}
		
		//get the date from the calendar in dd/MM/yyy format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedDate = (fieldDOB.getValue()).format(formatter);
		
		//change date in to String to compare
		Scanner dateNo = new Scanner(formattedDate).useDelimiter("/");
		int day = dateNo.nextInt();
		int month = dateNo.nextInt();
		int year = dateNo.nextInt();
		
		user.setDateOfBirth(new GregorianCalendar(year, month, day));
		
		AccountDAO_Sql data = new AccountDAO_Sql();
		data.updateAccount(s.getAcc().getLoginEmail(), user);
		try {
			m.swapScene("/employee/profile.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		JFXSnackbar snackbar = new JFXSnackbar(stackPane);
		snackbar.show("Updated About Me", 2000); 
	}

	public JFXButton getCancelBtn() {
		return cancelBtn;
	}

}
