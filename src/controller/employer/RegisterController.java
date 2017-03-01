package controller.employer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import controller.Controller;
import dataAccess.AccountDAO;
import dataAccess.sql.AccountDAO_Sql;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import model.Validator;
import model.account.EmployerAccount;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.text.producer.DefaultTextProducer;

public class RegisterController extends Controller implements Initializable {
	@FXML
    private JFXButton backbtn;
	
    @FXML
    private JFXTextField fieldEmail;

    @FXML
    private JFXTextField fieldName;

    @FXML
    private JFXPasswordField fieldPassword;

    @FXML
    private JFXPasswordField fieldConfirmPassword;

    @FXML
    private JFXTextField fieldNumber;

    @FXML
    private JFXRadioButton radiobtnMale;
    @FXML
    private JFXTextArea areaAddress;

    @FXML
    private ToggleGroup Gender;

    @FXML
    private JFXRadioButton radiobtnFemale;

    @FXML
    private JFXButton btnRegister;
  
    @FXML
    private JFXTextArea areaAbout;
    @FXML
    private ImageView captchaImage; // this one not inside FXML go set

    @FXML
    private JFXTextField captchaAnswer;
    
    private Captcha captcha;
    
    public void captchaShow(){
		
	  	captcha = new Captcha.Builder(200, 50)
				.addText(new DefaultTextProducer())
				.addBackground(new GradiatedBackgroundProducer())
				.addBorder()
				.addNoise()
				.gimp()
				.build();
	  	System.out.println(captcha.getImage());
	  	this.captchaImage.setImage(SwingFXUtils.toFXImage(captcha.getImage(),null));
	
		captcha.getAnswer();
	}
	
	public boolean captchaCheck(){
		//CaptchaAnswer will be the textField idcan
		if (captchaAnswer.getText().equals(captcha.getAnswer())){
			return true;
		}
		else{
			return false;
		}
	}


    // Event Listener on JFXButton[#backbtn].onAction
    @FXML
    public void handleBack(ActionEvent event) {
    	try {
    		//Link to the page where they choose employer or employee
    		m.swapScene("/EmployeeOrEmployer.fxml");
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }


 
    @FXML
    void handleLoginHere(ActionEvent event) {
    	try {
    	
    		//Link to the page where they login
    		m.swapScene("/employer/Login.fxml");
    	
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    @FXML
    public void handleRegister(ActionEvent event) {
    
    	if(captchaCheck()==true){
    	String name= fieldName.getText();
    	String email=fieldEmail.getText();    	
		String password=fieldPassword.getText();
		String confirmPassword=fieldConfirmPassword.getText();	
		String contactNumber=fieldNumber.getText();
		String address=areaAddress.getText();
		String about=areaAbout.getText();
		
		String genderMF ="";
		// Radio Button value
		
		if (radiobtnMale.isSelected()){
			genderMF = radiobtnMale.getText();
		}
		if (radiobtnFemale.isSelected()){
			genderMF = radiobtnFemale.getText();
		}
		
		boolean validate= Validator.validateEmployerRegistration(name,email, password,confirmPassword,contactNumber,address,about,genderMF);

		if (validate) {//can u add it in im falling asleep where your old file copy paste can le
			//create employer object
			EmployerAccount acc = new EmployerAccount(email, name);
			acc.setContact(contactNumber);
			acc.setCompanyName(name);
			acc.setAddress(address);
			acc.setAbout(about);
			try {
				//store to database
				AccountDAO accDAO = new AccountDAO_Sql();
				accDAO.createAccount(email, confirmPassword, acc);
				m.swapScene("/employer/Login.fxml");				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			//show error message 
		}

    	}
    	else{
    		System.out.println("Please enter the correct captcha");
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		captchaShow();
	}

}
