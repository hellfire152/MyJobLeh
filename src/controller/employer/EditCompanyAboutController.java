package controller.employer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import controller.Controller;
import dataAccess.sql.AccountDAO_Sql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.account.EmployerAccount;

public class EditCompanyAboutController extends Controller implements Initializable{

    @FXML
    private StackPane stackPane;

    @FXML
    private ImageView imageView;

    @FXML
    private Label lbCompanyDetails;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXTextArea areaAbout;

    @FXML
    void handleSave(ActionEvent event) {
    	String about = areaAbout.getText();
    	if(!areaAbout.equals("")){
    		AccountDAO_Sql accUpdate1 =new AccountDAO_Sql();
	    	EmployerAccount acc = (EmployerAccount)s.getAcc();
	    	acc.setAbout(about);
	    	accUpdate1.updateAccount(s.getAcc().getLoginEmail(), acc);
	     	try {
				m.swapScene("/employer/Profile.fxml");
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	
    	}
    	else{
    		System.out.println("Please fill in");
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		EmployerAccount acc = (EmployerAccount)s.getAcc();
		
	
		areaAbout.setText(acc.getAbout());
	}
    
}

