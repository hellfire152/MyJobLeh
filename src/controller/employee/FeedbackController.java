package controller.employee;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import controller.Controller;
import dataAccess.sql.FeedbackDAO_Sql;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Feedback;

public class FeedbackController extends Controller implements Initializable {

    @FXML
    private JFXHamburger fxHamburg;

    @FXML
    private ScrollPane ScrollPaneCurrent;

    @FXML
    private VBox VBox;

    @FXML
    private JFXDrawer fxDrawer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FeedbackDAO_Sql feedbackDAO = new FeedbackDAO_Sql();

		ArrayList<Feedback> feedbackList = feedbackDAO.getFeedback(s.getAcc().getLoginEmail());
		System.out.println(feedbackList);
		if(feedbackList != null){
			for(Feedback f : feedbackList){
			
					try {
						FXMLLoader loader = m.getScene("/employee/FeedbackPane.fxml");
						VBox.getChildren().add(loader.load());
						
						FeedbackPaneController c = (FeedbackPaneController) loader.getController();
						c.setFeedbackDetails(f);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		} else {
			Label l = new Label("No feedback yet!");
			l.setFont(new Font(40));
			VBox.getChildren().add(l);
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
