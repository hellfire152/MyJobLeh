package controller.employer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import controller.Controller;
import dataAccess.sql.AccountDAO_Sql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Validator;
import model.account.EmployerAccount;

public class EditCompanyDetailsController extends Controller implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private ImageView imageView;

    @FXML
    private Label lbCompanyDetails;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXTextArea areaAddress;

    @FXML
    private JFXTextField fieldCompanyName;

    @FXML
    private JFXTextField fieldContactName;


    @FXML
    void handleSave(ActionEvent event) {
    	String companyName=fieldCompanyName.getText();
    	String contact=fieldContactName.getText();
    	String address=areaAddress.getText();
    	Validator v = new Validator();
    	if(!companyName.equals("")&&!contact.equals("")&&!address.equals("")){
    		if(v.validateContactNumber(contact)){
    			AccountDAO_Sql accUpdate1 =new AccountDAO_Sql();
    	    	EmployerAccount acc = (EmployerAccount)s.getAcc();
    	    	acc.setAddress(address);
    	    	acc.setCompanyName(companyName);
    	    	acc.setContact(contact);
    	    	accUpdate1.updateAccount(s.getAcc().getLoginEmail(), acc);

    	    	try {
    				m.swapScene("/employer/Profile.fxml");
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    	    }
    		else{
    			System.out.println("Please fill in correct contact number");
    		}
    	}
    	else{
    		System.out.println("Please fill all the details");
    	}
    	}
    	

    	
    	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		EmployerAccount acc = (EmployerAccount)s.getAcc();
		
		fieldCompanyName.setText(acc.getCompanyName());
		fieldContactName.setText(acc.getContact());
		areaAddress.setText(acc.getAddress());
		
	}

}
