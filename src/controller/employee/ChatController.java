package controller.employee;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Chat;
import model.account.EmployeeAccount;
import model.event.WorkEvent;

public class ChatController extends Controller implements Initializable{

    @FXML
    private JFXButton btnBack;

    @FXML
    private ScrollPane ScrollPane;

    @FXML
    private VBox VBoxPane;

    @FXML
    private JFXTextField messageField;

    @FXML
    private JFXButton btnSend;
    
    @FXML
    private Label lbJobName;
     
    private static WorkEvent w;

//	private static Stage window;
	
    private Chat chat = new Chat();
    
    @FXML
    void handleBack(ActionEvent event) {
    	try {
			m.swapScene("/employee/historyCurrent.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void handleMessage(ActionEvent event) {
    	//NIL
    }
    
    @FXML
    void keyPressMessage(KeyEvent event) {
    	
        if(event.getCode() == KeyCode.ENTER)
        {
        	String inputMessage = messageField.getText();
        	chat.setInputMessage(inputMessage);
        	System.out.println(inputMessage);
        	try {
				m.swapScene("/employee/Chat.fxml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//        	refresh();
        }
    }
    

    @FXML
    void handleSend(ActionEvent event) {
    	String inputMessage = messageField.getText();
    	chat.setInputMessage(inputMessage);
    	System.out.println(inputMessage);
    	try {
			m.swapScene("/employee/Chat.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

//    public void setChatSession(WorkEvent w, EmployeeAccount acc, Stage window) {
//    	this.w = w;
//    	this.acc = acc;
//    	this.window = window;
//    	System.out.println("IN1");
//    	
//    	chat.setJobName(w.getJobTitle());
//    	
//    	ScrollPane.setVvalue(1.0);
//
//		chat.setJobName(w.getJobTitle());
//		
//		chat.setUserName(acc.getName());
//		chat.setDisplayText();
//		
//		lbJobName.setText(chat.getJobName());
//		
//		for(int i=0 ; i< chat.getDisplayText().size() ; i++){
//			Label lbChat = new Label("Chat" + i);
//			lbChat.setBorder(Border.EMPTY);
//			lbChat.setText(chat.getDisplayText().get(i));
//			VBoxPane.getChildren().add(lbChat);
//		}
//    }
//
//    public void refresh() {
//    	try {
//			window.setScene(m.getScene("/employee/Chat.fxml").load());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }

	public static WorkEvent getW() {
		return w;
	}

	public static void setW(WorkEvent w) {
		ChatController.w = w;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ScrollPane.setVvalue(1.0);
		
		chat.setJobName(w.getJobTitle());
		
		chat.setUserName(s.getAcc().getName());
		chat.setDisplayText();
		
		lbJobName.setText(chat.getJobName());
		
		for(int i=0 ; i< chat.getDisplayText().size() ; i++){
			Label lbChat = new Label("Chat" + i);
			lbChat.setBorder(Border.EMPTY);
			lbChat.setText(chat.getDisplayText().get(i));
			VBoxPane.getChildren().add(lbChat);
		}
	}
}
