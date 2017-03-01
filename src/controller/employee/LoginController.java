package controller.employee;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import controller.Controller;
import dataAccess.AccountDAO;
import dataAccess.RememberMeDAO;
import dataAccess.sql.AccountDAO_Sql;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginController extends Controller implements Initializable{
	@FXML
	private StackPane stackPane;
	
	@FXML
	private JFXPasswordField fxPassword;

	@FXML
	private JFXTextField fxEmail;

	@FXML
	private JFXButton fxLoginButton;

	@FXML
	private JFXButton signUpButton;
	
	@FXML
	private JFXButton btnForget;
	
	@FXML
	private VBox root;

    @FXML
    private JFXCheckBox checkRmbMe;
    
	private static JFXDialog dialog;

    RememberMeDAO data;
    
    {
    	s.setEmployeeAccount(true);
    }
	@FXML
	public void handleLogin() {
		System.out.println("ININININININ");
		
		AccountDAO accDAO = new AccountDAO_Sql();
		boolean check = checkRmbMe.isSelected();
		System.out.println(check);
		
		if (accDAO.verifyAccount(fxEmail.getText(), fxPassword.getText())) {
			
			data.storeToFile(check, fxEmail.getText(), fxPassword.getText());
			if(!check){
				System.out.println("Clear data");
				data.clearFile();
			}

			try {
				s.setAcc(accDAO.getAccount(fxEmail.getText()));

				m.swapScene("/employee/Home.fxml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			//display error message
			System.out.println("No such account exists!");
		}
	}
	
	@FXML 
	public void handleForget(){
		try {
			JFXDialogLayout content = new JFXDialogLayout();
			content.getChildren().add(m.getScene("/forgetpassworddialog.fxml").load());

			dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.BOTTOM);
			
			dialog.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleSignUp() {
		try {
			if(s.isEmployeeAccount()) {
				m.swapScene("/employee/Registration.fxml");
			} else {
				m.swapScene("/employer/Registration.fxml");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @FXML
    void keyPressEmail(KeyEvent event) {
    	
        if(event.getCode() == KeyCode.ENTER)
        {
        	handleLogin();
        }
    }
    @FXML
    void keyPressPassword(KeyEvent event) {
    	
        if(event.getCode() == KeyCode.ENTER)
        {
        	handleLogin();
        }
    }

	//Will be removed
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("ININININININ");
		// TODO Auto-generated method stub
		data = new RememberMeDAO();
		try{
			String[] values = data.getFile();
			if(values[0] != null){
				if(values[0].equals("true")){
					fxEmail.setText(values[1]);
					fxPassword.setText(values[2]);
					checkRmbMe.setSelected(true);
					
	//				handleLogin();
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Remember me not stored");
		}
	}

	public static JFXDialog getDialog() {
		return dialog;
	}

	public static void setDialog(JFXDialog dialog) {
		LoginController.dialog = dialog;
	}
}
