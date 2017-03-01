package controller.employer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import controller.Controller;
import dataAccess.JobsDAO;
import dataAccess.sql.JobsDAO_Sql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Callback;
import model.Validator;
import model.event.WorkEvent;

public class CreateJobController extends Controller{

    @FXML
    private JFXButton btnBack;

    @FXML
    private Label lbCreateJob;

    @FXML
    private JFXTextField fieldTitle;

    @FXML
    private JFXTextField fieldAddress;

    @FXML
    private JFXTextField fieldPosition;

    @FXML
    private JFXTextField fieldMinimumAge;

    @FXML
    private JFXTextArea areaJobScope;

    @FXML
    private JFXTextArea areaSkillsObtained;

    @FXML
    private JFXTextArea areaSkillsRequired;

    @FXML
    private JFXDatePicker fieldStartDate;

    @FXML
    private JFXDatePicker fieldEndDate;

    @FXML
    private JFXButton btnCreate;
    
    @FXML
    private JFXTextField fieldWeekdayRate;

    @FXML
    private JFXTextField fieldWeekendRate;


    
    public void initialize() {
    	//get current date
    	LocalDate currentDate = LocalDate.now();
    	//set start date 
    	DatePicker startDatePicker = new DatePicker();
    	startDatePicker.setValue(currentDate);
    	System.out.println("diwjdij");
    	//color out the cells that are too old 
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
        	
          @Override
          public DateCell call(final DatePicker datePicker) {
            return new DateCell() {
              @Override
              public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
               

                if (item.isBefore(startDatePicker.getValue().plusDays(1))) {
                  setDisable(true);
                  setStyle("-fx-background-color: #EEEEEE;");
                }
              }
            };
          }
        };
        System.out.println(fieldStartDate);
        fieldStartDate.setDayCellFactory(dayCellFactory);

    }

    @FXML
    void handleBack(ActionEvent event) {
    	try {
			m.swapScene("/employer/Home.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    @FXML
    void handleCreate(ActionEvent event) {
    	String jobTitle= fieldTitle.getText();
    	String address=fieldAddress.getText();
    	String position=fieldPosition.getText();
    	String minimumAge=fieldMinimumAge.getText();
    	String jobScope=areaJobScope.getText();
    	String skillsObtained=areaSkillsObtained.getText();
    	String skillsRequired=areaSkillsRequired.getText();
    	String weekendRate=fieldWeekendRate.getText();
    	String weekdayRate=fieldWeekdayRate.getText();
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String startDate = (fieldStartDate.getValue()).format(formatter);
		String endDate=(fieldEndDate.getValue().format(formatter));
    	
		//change date in to String to compare
			Scanner sdateNo = new Scanner(startDate).useDelimiter("/");
			int sday = sdateNo.nextInt();
			int smonth = sdateNo.nextInt();
			int syear = sdateNo.nextInt();
			
			Scanner edateNo = new Scanner(endDate).useDelimiter("/");
			int eday = edateNo.nextInt();
			int emonth = edateNo.nextInt();
			int eyear = edateNo.nextInt();
				
				
			GregorianCalendar startdateg = new GregorianCalendar(syear,smonth-1,sday);
			GregorianCalendar enddateg = new GregorianCalendar(eyear,emonth-1,eday);
			
    	
		boolean validate=Validator.CreateJobVal(jobTitle, address, position, minimumAge, jobScope, skillsObtained,
				skillsRequired, startDate, endDate , startdateg, enddateg,weekendRate,weekdayRate);
		
		if(validate){
			
			//create Job object
			WorkEvent work = new WorkEvent(s.getAcc().getLoginEmail(), jobTitle, s.getAcc().getName(), address, position, minimumAge, jobScope,
					startDate, endDate, skillsObtained, skillsRequired, weekendRate, weekdayRate);

			//set dates
			while (startdateg.compareTo(enddateg) <= 0) {
				work.getDateList().add(new GregorianCalendar(startdateg.get(Calendar.YEAR),
						startdateg.get(Calendar.MONTH), startdateg.get(Calendar.DATE)));
				startdateg.add(Calendar.DATE, 1);
			}

			//store to database
			JobsDAO jobDAO = new JobsDAO_Sql();
			jobDAO.createJob(s.getAcc().getLoginEmail(), s.getAcc().getName(), jobTitle, work);
			
			try {
				m.swapScene("/employer/Home.fxml");				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			//show error message 
		}
    }
}

