package controller.employee;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;

import controller.Controller;
import dataAccess.sql.JobsDAO_Sql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.event.WorkEvent;

public class SearchController extends Controller implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private TextField search;

    @FXML
    private VBox appendVBox;

    @FXML
    private JFXDrawer fxDrawer;
    
    private static ArrayList<WorkEvent> searchList;

    @FXML
    void handleBack(ActionEvent event) {
    	try {
			m.swapScene("/employee/home.fxml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void handleSearch(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
        	if(search.getText() != ""){
	        	JobsDAO_Sql data = new JobsDAO_Sql();
	        	ArrayList<WorkEvent> searchJobList = new JobsDAO_Sql().getSearchJob(search.getText());
	        	searchJobList = data.getSearchJob(search.getText());
	        	SearchController.setSearchList(searchJobList);
	        	System.out.println(search.getText());
	        	try {
					m.swapScene("/employee/Search.fxml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
	 }

//    private ArrayList<WorkEvent> getSearchedJob(){
//    	
//    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(!searchList.isEmpty()){
			for(WorkEvent w : searchList){
				try {
					FXMLLoader loader = m.getScene("/employee/HomePane.fxml");
					appendVBox.getChildren().add(loader.load());
					((HomePaneController)loader.getController()).setWorkEvent(w);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				System.out.println(m);
			}
		}else{
			Label label = new Label();
			label.setText("No Search results found! ");
			appendVBox.getChildren().add(label);
		}
		//end update pane
		
	}

	public static ArrayList<WorkEvent> getSearchList() {
		return searchList;
	}

	public static void setSearchList(ArrayList<WorkEvent> searchList) {
		SearchController.searchList = searchList;
	}

}
