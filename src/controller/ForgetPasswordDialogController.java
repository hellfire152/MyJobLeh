package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;

import application.Session;
import controller.employee.LoginController;
import dataAccess.sql.AccountDAO_Sql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import model.EmailHandler;
import model.Security;
import model.Validator;

public class ForgetPasswordDialogController extends Controller implements Initializable{

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTextField fieldEmail;

    @FXML
    private JFXButton btnVerify;

    @FXML
    private JFXPasswordField fieldOTP;

    @FXML
    private JFXPasswordField fieldPassword;

    @FXML
    private JFXPasswordField fieldConfirm;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnVerifyOTP;
    
    @FXML
    private JFXButton btnCancel;
    
    @FXML
    private Label lbInform;
    
    private int oneTimePass;
    private int count = 0;
    
    LoginController crtl;
    Validator validation;
    AccountDAO_Sql data;

    @FXML
    void handleCancel(ActionEvent event) {
    	crtl.getDialog().close();
    }

    @FXML
    void handleUpdate(ActionEvent event) {
    	if(validation.validatePassword(fieldPassword.getText())){
    		if(validation.validateConfirmPassword(fieldPassword.getText(), fieldConfirm.getText())){
	    		JFXSnackbar snackbar = new JFXSnackbar(stackPane);
	    		snackbar.show("Updated About Me", 2000);
	    		data.updatePassword(fieldEmail.getText(), Security.hashSHA1(fieldPassword.getText()));
	    		crtl.getDialog().close();
    		}
    		else{
    			lbInform.setVisible(true);
    			lbInform.setText("Password do not match");
    			fieldPassword.setText("");
    		}
    	}
    	else{
    		lbInform.setVisible(true);
    		lbInform.setText("Password not strong enough");
    		fieldConfirm.setText("");
    	}
    }
    
    @FXML
    void handleVerifyOTP(ActionEvent event) {
    	if(fieldOTP.getText().equals(Integer.toString(oneTimePass))){
			fieldPassword.setVisible(true);
			fieldConfirm.setVisible(true);
			btnUpdate.setDisable(false);
			btnVerifyOTP.setDisable(true);
			lbInform.setVisible(false);
			fieldOTP.setDisable(true);
    	}
    	else{
    		lbInform.setText("Incorrect OTP");
    		fieldOTP.setText("");
    		count++;
    	}
    	if(count == 3){
    		oneTimePass = 0;
    		crtl.getDialog().close();
    	}
    }
    
    @FXML
    void handleVerify(ActionEvent event) {
    	System.out.println(s.isEmployeeAccount()+"abc");
    	if(validation.validateEmail(fieldEmail.getText())){
    		if(data.checkEmail(fieldEmail.getText())){
		    	oneTimePass = model.Security.generateOTP();
		    	System.out.println(oneTimePass);
		    	btnVerify.setDisable(true);
		    	fieldEmail.setDisable(true);
		    	lbInform.setText("Please check your email for OTP");
		    	lbInform.setVisible(true);
		    	fieldOTP.setDisable(false);
		    	btnVerifyOTP.setVisible(true);
		    	String emailFormat = EmailHandler.emailOTP(Integer.toString(oneTimePass));
		    	String[] to = {fieldEmail.getText()};
		    	EmailHandler.sendMail("Rest password for MyJobLeh", emailFormat, to);
    		}
    		else{
    			lbInform.setText("Please enter a valid email");
        		lbInform.setVisible(true);
        		fieldEmail.setText("");
    		}
    	}
    	else{
    		lbInform.setText("Please enter a valid email");
    		lbInform.setVisible(true);
    		fieldEmail.setText("");
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		crtl = new LoginController();
		validation = new Validator();
		data = new AccountDAO_Sql();
		
		fieldOTP.setDisable(true);
		lbInform.setVisible(false);
		fieldPassword.setVisible(false);
		fieldConfirm.setVisible(false);
		btnUpdate.setDisable(true);
		btnVerifyOTP.setVisible(false);
	}
	
	public void setEmployee(boolean employeeAccount) {
		s.setEmployeeAccount(employeeAccount);
	}

}
