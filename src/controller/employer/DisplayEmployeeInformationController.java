package controller.employer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXHamburger;

import controller.Controller;
import dataAccess.ImageUpload;
import dataAccess.sql.AccountDAO_Sql;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.account.EmployeeAccount;

public class DisplayEmployeeInformationController extends Controller implements Initializable{
	   @FXML
	    private JFXHamburger fxHamburg;

	    @FXML
	    private Label lbDOB;

	    @FXML
	    private Label lbGender;

	    @FXML
	    private Label lbStatus;

	    @FXML
	    private Label lbMobile;

	    @FXML
	    private ImageView imageProfile;

	    @FXML
	    private Label lbName;

	    @FXML
	    private Label lbRating;

	    @FXML
	    private Label lbAboutMe;

	    @FXML
	    private Pane qualificationFlowPane;

	    @FXML
	    private Label lbHighestEdu;

	    @FXML
	    private FlowPane skillsDisplay;

	    @FXML
	    private VBox appendExperience;

	@FXML
	private JFXButton btnSkills;	
    @FXML
    private JFXButton btnClose;
    
	private EmployeeAccount acc;
	
		

	@FXML StackPane stackPane;
	public void setEmployeeDetails(EmployeeAccount acc) {
		this.acc = acc;
		GregorianCalendar cal= new GregorianCalendar();
		
//		System.out.println(acc.getDateOfBirth());
//		System.out.println(acc.getAboutMe());
		System.out.println(acc.getName());
		System.out.println(acc.getName());
		lbName.setText(acc.getName());
		lbAboutMe.setText(acc.getAboutMe());
		int year =acc.getDateOfBirth().get(cal.YEAR);
		System.out.println(year);
		int month=acc.getDateOfBirth().get(cal.MONTH);
		int day=acc.getDateOfBirth().get(cal.DAY_OF_MONTH);
		String DOB = year+"/"+month+"/"+day;
		System.out.println(acc.getDateOfBirth());
		lbDOB.setText(DOB);
		lbGender.setText(acc.getGender());
		lbMobile.setText(acc.getContact());
		lbHighestEdu.setText(acc.getHighestQualification());
		for(String s : acc.getSkillsList()){
			System.out.println(s);
			skillsDisplay.getChildren().add(generateSkillNode(s));
		}
		String rating = (Double.isNaN(acc.averageRating()))? "No ratings yet!" :
			String.format("%.2f", acc.averageRating());
		lbRating.setText(rating);
		for (String s : acc.getExperienceList()) {
			appendExperience.getChildren().add(generatePastExperience(s));
		}
		s.setEmployeeAccount(true);
		BufferedImage profilePic = new AccountDAO_Sql().getProfilePic(acc.getLoginEmail());
		s.setEmployeeAccount(false);
		profilePic = (profilePic == null)? ImageUpload.defaultProfilePic() : profilePic;
		imageProfile.setImage(SwingFXUtils.toFXImage(profilePic, null));

	}
	private Label generateSkillNode(String s) {
		Label label = new Label(s);
		label.setText(s);
		label.setTextFill(Color.WHITE);
		label.setStyle("-fx-background-radius:12px;-fx-background-color:#303f9f;");
		label.setPadding(new Insets(10));
		label.setTextAlignment(TextAlignment.CENTER);
		return label;
	}
	private VBox generatePastExperience(String s) {
		VBox container = new VBox(3);
		
		String[] details = s.split(";");
		String jobName = details[0];
		String companyNameAndDate = details[1];
		
		//top row, containing the job name and remove button
		HBox topRow = new HBox(20);
		topRow.setStyle("-fx-background-radius:12px;-fx-background-color:#303f9f;");
		Label jobNameLabel = new Label(jobName);
		jobNameLabel.setFont(new javafx.scene.text.Font(15.0));
		jobNameLabel.setAlignment(Pos.CENTER_LEFT);
		jobNameLabel.setTextFill(Color.WHITE);
		jobNameLabel.setTextAlignment(TextAlignment.CENTER);
		HBox.setMargin(jobNameLabel, new Insets(3,20,3,5));
		
		topRow.setPrefWidth(jobNameLabel.getWidth() + 100);
		topRow.getChildren().addAll(jobNameLabel);
		
		//bottom row, containing the company name and date
		HBox bottomRow = new HBox();
		Label detailsLabel = new Label(companyNameAndDate);
		bottomRow.getChildren().add(detailsLabel);
		
		
		//adding children to container
		container.getChildren().addAll(topRow, bottomRow);
		
		return container;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

    @FXML
    void handleClose(ActionEvent event) {
    	//closes the containing window
		((Stage)btnClose.getScene().getWindow()).close();
    }

    @FXML
    void handleRating(ActionEvent event) {
    	try {
			JFXDialogLayout content = new JFXDialogLayout();
			content.setHeading(new Text("Edit Company Details"));
			FXMLLoader loader = m.getScene("/employer/AttendanceRating.fxml");
			content.getChildren().add(loader.load());
			JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.BOTTOM);
			AttendanceRatingController c = ((AttendanceRatingController)loader.getController());
			c.setDialog(dialog);
			c.setEmployeeAccount(acc);
			c.setDisplayController(this);
			dialog.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
