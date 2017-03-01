package controller.employee;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import controller.Controller;
import dataAccess.sql.JobsDAO_Sql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.event.WorkEvent;

/**
 * 
 * @author NigelChen
 * @version a.3
 * @since a.1
 */
public class HistoryPendingController extends Controller {
	@FXML
    private JFXHamburger fxHamburg;

    @FXML
    private TextField fieldSearch;

    @FXML
    private ScrollPane ScrollPaneCurrent;

    @FXML
    private VBox VBox;

    @FXML
    private JFXButton btnCurrent;

    @FXML
    private JFXButton btnPending;

    @FXML
    private JFXDrawer fxDrawer;
    
    private ArrayList<WorkEvent> workList = new JobsDAO_Sql().getPendingOfEmployee(s.getAcc().getLoginEmail());
    
    @FXML
    void handleCurrent(ActionEvent event) {
    	try {
			m.swapScene("/employee/HistoryCurrent.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void handlePending(ActionEvent event) {
    	//already at pending page, so do nothing!
    }

    @FXML
    void handleSearchBox(ActionEvent event) {

    }
    
	/**
	 * Generate a pane from EmployeeHistoryPendingPane.fxml and append
	 * accordingly.
	 */
	public void initialize() {
		try {
			for(WorkEvent w : workList) {
				FXMLLoader loader = m.getScene("/employee/HistoryPendingPane.fxml");
				VBox.getChildren().add(loader.load());
				HistoryPendingPaneController c = (HistoryPendingPaneController) loader.getController();
				c.setWorkDetails(w, new JobsDAO_Sql().getEmployerOfJob(w.getJobTitle()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
