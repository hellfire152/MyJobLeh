package controller.employee;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.Scanner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import controller.Controller;
import dataAccess.AccountDAO;
import dataAccess.sql.AccountDAO_Sql;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import model.Validator;
import model.account.EmployeeAccount;
import model.extraWindows.ErrorPopup;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.text.producer.DefaultTextProducer;

public class RegisterController extends Controller{

    @FXML
    private JFXButton backBtn;

    @FXML
    private Label lbRegister;

    @FXML
    private JFXTextField fieldName;

    @FXML
    private JFXTextField fieldEmail;

    @FXML
    private JFXPasswordField fieldPassword;

    @FXML
    private JFXPasswordField fieldConfirmPassword;

    @FXML
    private JFXDatePicker fieldDOB;

    @FXML
    private JFXRadioButton radioBtnMale;

    @FXML
    private ToggleGroup gender;

    @FXML
    private JFXRadioButton radioBtnFemale;

    @FXML
    private JFXButton btnregister;

 

    @FXML
    private JFXButton registerButton;
    @FXML
    private ImageView captchaImage;

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


    public void initialize() {
    	captchaShow();
    	//set gender toggleGroup
    	gender = new ToggleGroup();
    	gender.getToggles().addAll(radioBtnFemale, radioBtnMale);
    	
    	//get current date
    	LocalDate currentDate = LocalDate.now();
    	//set start date 
    	DatePicker startDatePicker = new DatePicker();
    	startDatePicker.setValue(LocalDate.of(currentDate.getYear() - 25, currentDate.getMonthValue(), currentDate.getDayOfMonth()));
        //set end date
    	DatePicker endDatePicker = new DatePicker();
    	endDatePicker.setValue(LocalDate.of(currentDate.getYear() - 15, currentDate.getMonthValue(), currentDate.getDayOfMonth()));
    	//color out the cells that are too old or too young
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
          @Override
          public DateCell call(final DatePicker datePicker) {
            return new DateCell() {
              @Override
              public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item.isBefore(startDatePicker.getValue().plusDays(1)) || item.isAfter(endDatePicker.getValue().plusDays(1))) {
                  setDisable(true);
                  setStyle("-fx-background-color: #EEEEEE;");
                }
              }
            };
          }
        };
        //set default date to start from 25 years ago
        fieldDOB.setDayCellFactory(dayCellFactory);
        fieldDOB.setValue(startDatePicker.getValue());
    }
    
    @FXML
    public void handleRegister(ActionEvent event) {
    	if (captchaCheck()==true){
    		//get all the values from text field
        	String name= fieldName.getText();
        	String email=fieldEmail.getText();    	
    		String password=fieldPassword.getText();
    		String confirmPassword=fieldConfirmPassword.getText();
    		
    		//get the date from the calendar in dd/MM/yyy format
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    		String formattedDate = (fieldDOB.getValue()).format(formatter);
    		
    		//change date in to String to compare
    		Scanner dateNo = new Scanner(formattedDate).useDelimiter("/");
    		int day = dateNo.nextInt();
    		int month = dateNo.nextInt();
    		int year = dateNo.nextInt();
    		
    		
    		GregorianCalendar dateOfBirth = new GregorianCalendar(year,month,day);
    		String genderMF ="";
    		// Radio Button value
    		
    		if (radioBtnMale.isSelected()){
    			genderMF = radioBtnMale.getText();
    		}
    		if (radioBtnFemale.isSelected()){
    			genderMF = radioBtnFemale.getText();
    		}
    		
    		
    		boolean validate= Validator.validateEmployeeRegistration(name,email, password,confirmPassword,formattedDate,genderMF);
    		
    		if (validate) {
    			//create new employee account
    			EmployeeAccount acc = new EmployeeAccount(email, name);
    			acc.setDateOfBirth(dateOfBirth);
    			acc.setGender(genderMF);
    			try {
    				//store to database
    				AccountDAO accDAO = new AccountDAO_Sql();
    				if (accDAO.createAccount(email, confirmPassword, acc)) {
    					m.swapScene("/employee/Login.fxml");									
    				}
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		} else {
    			System.out.println("fail");
    		}
    		
    	}
    	else{
    		System.out.println("Please enter correct captcha");
    	}
    	
    }
    
    public void handleBack() {
    	try {
			m.swapScene("/employee/Login.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
