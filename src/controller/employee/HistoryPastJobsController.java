package controller.employee;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import controller.Controller;
import dataAccess.sql.JobsDAO_Sql;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.account.EmployerAccount;
import model.event.WorkEvent;

public class HistoryPastJobsController extends Controller implements Initializable{

    @FXML
    private JFXHamburger fxHamburg;

    @FXML
    private TextField fieldSearch;

    @FXML
    private ScrollPane ScrollPaneCurrent;

    @FXML
    private VBox VBox;

    @FXML
    private JFXDrawer fxDrawer;

    private ArrayList<WorkEvent> workList = new JobsDAO_Sql().getPastOfEmployee(s.getAcc().getLoginEmail());

    @FXML
    void handleSearchBox(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		for(WorkEvent w : workList) {
			w.getJobTitle();
			try {
				FXMLLoader loader = m.getScene("/employee/HistoryPastJobsPane.fxml");
				VBox.getChildren().add(loader.load());
				HistoryPastJobsPaneController c = (HistoryPastJobsPaneController) loader.getController();
				c.setWorkDetails(w, new JobsDAO_Sql().getEmployerOfJob(w.getJobTitle()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		try {
			VBox box = m.getScene("/Drawer.fxml").load();
			fxDrawer.setSidePane(box);
			fxHamburg.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
				

				if (fxDrawer.isShown()) {
					fxDrawer.close();
				} else {
					fxDrawer.open();
				}
			});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
    
    

}
