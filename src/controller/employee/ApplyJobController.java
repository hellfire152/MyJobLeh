package controller.employee;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSnackbar;

import controller.Controller;
import dataAccess.sql.JobsDAO_Sql;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.event.WorkEvent;
import model.extraWindows.MapWindow;

public class ApplyJobController extends Controller {
	@FXML
    private StackPane stackPane;

    @FXML
    private JFXButton close;

    @FXML
    private TextField search;

    @FXML
    private Label lbJobTitle;

    @FXML
    private Label lbNormalRate;

    @FXML
    private Label lbWeekendRate;

    @FXML
    private Text lbDate;

    @FXML
    private ImageView companyPic;

    @FXML
    private Label lbContent;

    @FXML
    private Label lbSpecial;

    @FXML
    private Text lbCompany;

    @FXML
    private Label lbLocation;
    
    @FXML
    private JFXButton mapsBtn;
    
    private WorkEvent work;
	    
	@FXML
	public void handleClose(ActionEvent event) {
		//closes the containing window
		((Stage)close.getScene().getWindow()).close();
	}
	
	@FXML
	void loadApplyDialog(ActionEvent event) {

		JFXDialogLayout content = new JFXDialogLayout();
		content.setHeading(new Text("Notice"));
		content.setBody(new Text(
				"Only apply if you are truly interested in this job. \n If you do not respond to Employer or show up for interview \n then you will receive a bad rating"));
		JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.BOTTOM);
		JFXButton button1 = new JFXButton("Agree");
		button1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				JFXSnackbar snackbar = new JFXSnackbar(stackPane);
				//add to database
				new JobsDAO_Sql().insertIntoPending(s.getAcc().getLoginEmail(), work.getJobTitle());
				snackbar.show("Successful", 2000);
				dialog.close();
			}
		});
		content.setActions(button1);

		dialog.show();
	}

	@FXML
    void loadSaveJob(ActionEvent event) {

    }
	
	/**
	 * Takes in a {@link WorkEvent}, and sets all the text in the window appropriately
	 * @param e
	 */
	public void setWorkEvent(WorkEvent work) {
		this.work = work;
//		lbPayRange.setText("rate");
		lbDate.setText(work.getStartDate());
		lbJobTitle.setText(work.getJobTitle());
		lbCompany.setText(work.getCompanyName());
		lbLocation.setText(work.getAddress());
		lbContent.setText(work.getJobScope());
		lbNormalRate.setText("Weekdays: " + work.getWeekdayRate());
		lbWeekendRate.setText("Weekend Pay");
		
	}

	@FXML
	void loadLocation(ActionEvent event){
        MapWindow.display(work);
	}
	

    @FXML
    void loadDirections(ActionEvent event) {

    }

}
   
