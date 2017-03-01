package controller.employee;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import model.Feedback;

public class FeedbackPaneController extends Controller{
	@FXML
	private StackPane stackPane;
	@FXML
	private Label lbJobName;
	@FXML
	private Label lbMessage;
	private static Feedback fb;

	public void setFeedbackDetails(Feedback f) {
		// TODO Auto-generated method stub
		this.fb = f;
		lbJobName.setText(f.getSubject());
		lbMessage.setText(f.getFeedback());
	}

}
