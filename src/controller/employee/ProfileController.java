package controller.employee;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import controller.Controller;
import dataAccess.ImageUpload;
import dataAccess.sql.AccountDAO_Sql;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import model.account.EmployeeAccount;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * A controller for the Profile.fxml
 * @author Bryan Tan, AJK
 * @version b.3
 * @since a.3
 */
public class ProfileController extends Controller implements Initializable {
	
	@FXML
	private StackPane stackPane;
	
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
    protected Label lbName;
    
    @FXML
    private JFXToggleButton toggleImmediate;
    
    @FXML
    private JFXButton uploadButton;
    
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
    private Label lbExperience;
    
    @FXML
    private VBox appendExperience;
    
    @FXML
    private JFXButton btnGenerate;
    
    @FXML
    private JFXDrawer fxDrawer;
    
    @FXML
    private Label lbHistoryDates;
    @FXML
    private Label lbHistoryInfo;
    
    JFXComboBox <String> comboBox;
	
//	ProfileDAO_Sql data = new ProfileDAO_Sql();
//	UserProfile user = data.getProfile("nigel_ncch@hotmail.com");
//	ArrayList<String []> experience = new ArrayList<String []>();
	
	private EmployeeAccount acc;
	private AccountDAO_Sql accountDAO;
	
	private static JFXDialog dialog;

	{
		accountDAO = new AccountDAO_Sql();
		acc = (EmployeeAccount)s.getAcc();
	}
	
	/**
	 * Bring the user back to home page.
	 * @param event
	 */
	@FXML
	void loadBack(ActionEvent event) {
		if (true) {
			try {
				m.swapScene("/employee/Home.fxml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * A dialog will pop up, and will require the user to enter the following field.
	 * @param event
	 */
	@FXML
	void loadPersDialog(ActionEvent event) {

		try {
			FXMLLoader loader = m.getScene("/employee/EditPersonalDialog.fxml");
			JFXDialogLayout content = new JFXDialogLayout();
			content.setHeading(new Text("Edit Personal Details"));
			content.getChildren().add(loader.load());

			//set cancel button to close dialog on click
			((EditPersonalDialogController)loader.getController()).getCancelBtn().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					dialog.close();
				}
			});
			
			dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.BOTTOM);
			dialog.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * A dialog will pop up to allow user to type in the about me field.
	 * User press update, information will be updated to database, as well as refresh the page to the latest info.
	 * @param event
	 */
	@FXML
	void loadAboutMe(ActionEvent event) {
		
		JFXDialogLayout aboutMeDialog = new JFXDialogLayout();
		JFXTextArea aboutMeTArea = new JFXTextArea();
		
		aboutMeTArea.setPromptText("Describe your key experience, strengths, person....");
		
		aboutMeDialog.setHeading(new Text("Edit About Me"));
		aboutMeDialog.setBody(aboutMeTArea);
		
		
		JFXDialog dialog = new JFXDialog(stackPane, aboutMeDialog, JFXDialog.DialogTransition.BOTTOM);
		JFXButton updateAboutMeBtn = new JFXButton("Update");
		
		updateAboutMeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				JFXSnackbar snackbar = new JFXSnackbar(stackPane);
				
				acc.setAboutMe(aboutMeTArea.getText());
				initialize(null, null);
				
				String title = "Success";
				String message = "About me updated!";
				NotificationType notification = NotificationType.SUCCESS;
				TrayNotification tray = new TrayNotification();
				tray.setTitle(title);
				tray.setMessage(message);
				tray.setNotificationType(notification);
				tray.setAnimationType(AnimationType.POPUP);
				tray.showAndDismiss(Duration.seconds(2));

				accountDAO.updateAccount(s.getAcc().getLoginEmail(), acc);
				
				dialog.close();
			}

		});
		
		aboutMeDialog.setActions(updateAboutMeBtn);
		
		dialog.show();
	}
	
	/**
	 * When this event is called, it will generate out a resume via pdf or image.
	 * 
	 * @param event
	 */
	@FXML
    void handleGenerate(ActionEvent event) {
		
		String mobile, name,email;
		mobile = acc.getContact();
		name = acc.getName();
		email = acc.getLoginEmail();
		
		Document document = new Document();
		
		Rectangle rect = new Rectangle(PageSize.A4);
		document.setPageSize(rect);
		
		try {
			
			PdfWriter.getInstance(document, new FileOutputStream("MyResume.pdf"));
			document.open();
			PdfPTable personTable = new PdfPTable(2);
			personTable.setSpacingAfter(8.0f);
			personTable.setTotalWidth(new float [] {110,316});
			personTable.setLockedWidth(true);
			
			document.add(new Paragraph(name, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16,Font.BOLD, BaseColor.RED)));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("258B Punggol Field #16-01"));
			document.add(new Paragraph("Singapore 822258"));
			
			document.add(new Paragraph(mobile));
			document.add(new Paragraph(email));
			
			//Creation of PDF Table
			PdfPTable eduTable = new PdfPTable(2);
			eduTable.setSpacingBefore(10.0f);
			eduTable.setSpacingAfter(8.0f);
			eduTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			eduTable.setTotalWidth(new float []{110, 316});
			eduTable.setLockedWidth(true);
			//End
			
			//Create cell in eduTable
			PdfPCell cell1 = new PdfPCell(new Paragraph("Education ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.BOLD)));
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.setColspan(2);
			cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			eduTable.addCell(cell1);
			eduTable.addCell("2016 - Current");
			eduTable.addCell("Diploma in Cyber Security and Forensics");
			
			document.add(eduTable);
			
			//Create second table
			PdfPTable skillTable = new PdfPTable(2);
			skillTable.setSpacingBefore(5.0f);
			skillTable.setSpacingAfter(8.0f);
			skillTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			skillTable.setTotalWidth(new float []{110, 316});
			skillTable.setLockedWidth(true);
			
			//Create cells in skillTable
			PdfPCell cell2 = new PdfPCell(new Paragraph("Skills summary: ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.BOLD)));
			cell2.setBorder(Rectangle.NO_BORDER);
			cell2.setColspan(2);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			skillTable.addCell(cell2);
			skillTable.addCell("Programming Skills: ");
			skillTable.addCell("Proficient in Java");
			skillTable.addCell("Scripting Skills: ");
			skillTable.addCell("Well- versed in HTML and CSS");
			skillTable.addCell("SoftWare Skills: ");
			skillTable.addCell("Microsoft Office, Sony Vegas ");
			document.add(skillTable);
			
			//Create third table for working experience
			PdfPTable table = new PdfPTable(2);
			//Margin -top
			table.setSpacingBefore(5.0f);
			//Margin - Bottom
			table.setSpacingAfter(8.0f);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.setTotalWidth(new float []{110, 316});
			table.setLockedWidth(true);
			
			//Cells for workingExperience table
			PdfPCell cell = new PdfPCell(new Paragraph("Working Experience: ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
			
			table.addCell("Nov 2016 - Dec 2016");
			table.addCell("Blah Blah Pte Ltd");
			table.addCell("Dec 2016 - 2017");
			table.addCell("Chapalang Pte Ltd");
			document.add(table);	
			
			//Personal Information
			document.add(new Paragraph("Personal Information: ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Font.BOLD)));
			document.add(new Paragraph(" "));
			document.add(new Paragraph("D.O.B: " ));
			document.add(new Paragraph("Nationality: "));
			document.add(new Paragraph("National Service: "));
			
			
			document.close();
			
			JOptionPane.showMessageDialog(null, "Generating... ");
			
			if (Desktop.isDesktopSupported()) {
			    try {
			        File myFile = new File("MyResume.pdf");
			        Desktop.getDesktop().open(myFile);
			    } catch (IOException ex) {
			        // no application registered for PDFs
			    }
			}
			
		}catch(Exception e){
			System.out.println("Failed to open PDF");
		}
			
	}

    @FXML
    void loadHighestEdu(ActionEvent event) {
    	JFXDialogLayout layout = new JFXDialogLayout();
    	layout.setHeading(new Text("Edit Highest Qualification"));
    	JFXTextField education = new JFXTextField();
    	education.setText(acc.getHighestQualification());
    	layout.setBody(education);
    	
    	JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.BOTTOM);
    	
    	JFXButton update = new JFXButton("Update");
    	
    	update.setOnAction(new EventHandler<ActionEvent>(){
    		@Override
			public void handle(ActionEvent event) {
    			
    			acc.setHighestQualification(education.getText());
    			lbHighestEdu.setText(acc.getHighestQualification());
    			
    			String title = "Success";
				String message = "Highest Education Updated";
				NotificationType notification = NotificationType.SUCCESS;
				TrayNotification tray = new TrayNotification();
				tray.setTitle(title);
				tray.setMessage(message);
				tray.setNotificationType(notification);
				tray.setAnimationType(AnimationType.POPUP);
				tray.showAndDismiss(Duration.seconds(2));
				
				accountDAO.updateAccount(s.getAcc().getLoginEmail(), acc);
				
				dialog.close();
    		}
    	});
    	layout.setActions(update);
    	
    	dialog.show();
    }
    
    @FXML
    void loadSkills(ActionEvent event) {
    	//making a backup
    	ArrayList<String> skillsBackupList = new ArrayList<>(acc.getSkillsList());
    	Collections.copy(skillsBackupList, acc.getSkillsList());
    	
    	JFXDialogLayout layout = new JFXDialogLayout();
    	layout.setHeading(new Text("Edit Skills"));
    	JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.BOTTOM);
    	dialog.setOnDialogClosed(e -> {
    		try {
    			new AccountDAO_Sql().updateAccount(acc.getLoginEmail(), acc);
				m.swapScene("/employee/Profile.fxml");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
    	});
    	
    	BorderPane container = new BorderPane();
    	
    	//skills display
			FlowPane editSkillsDisplay = new FlowPane();
			editSkillsDisplay.setMinHeight(100);
			for (String s : skillsBackupList) {
				HBox skillBox = generateSkillWithRemoveButton(s, skillsBackupList, editSkillsDisplay);
				
				editSkillsDisplay.getChildren().add(skillBox);
			}

		//buttons
			JFXButton cancel = new JFXButton("Cancel");
			cancel.setOnAction(e -> {
				dialog.close();
			});
			JFXButton save = new JFXButton("Save");
			save.setOnAction(e -> {
				acc.setSkillsList(skillsBackupList);
				dialog.close();
			});
			HBox buttonContainer = new HBox(20);
			buttonContainer.getChildren().addAll(cancel, save);
			buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);
			BorderPane.setMargin(buttonContainer, new Insets(0,20,20,0));
			
		//add skill
			JFXTextField newSkill = new JFXTextField();
			newSkill.setPromptText("Add new skill");
			Button addSkill = new Button("Add");
			addSkill.setOnAction(e -> {
				String skill = newSkill.getText().trim();
				if (!skill.equals("")) {
					//add to list
					skillsBackupList.add(skill);
					//add to FlowPane
					HBox skillBox = generateSkillWithRemoveButton(skill, skillsBackupList, editSkillsDisplay);
					FlowPane.setMargin(skillBox, new Insets(0,10,10,10));
					editSkillsDisplay.getChildren().add(skillBox);
				}
			});
			HBox addSkillContainer = new HBox(3);
			addSkillContainer.setAlignment(Pos.CENTER_LEFT);
			addSkillContainer.getChildren().addAll(newSkill, addSkill);
			BorderPane.setMargin(addSkillContainer, new Insets(0,0,10,10));
			
		//adding all nodes to the container
			HBox bottom = new HBox();
			bottom.getChildren().addAll(addSkillContainer, buttonContainer);
			container.setBottom(bottom);
			container.setCenter(editSkillsDisplay);
			
		//adding all of that to the dialog
		layout.setBody(container);
    	dialog.show();
    }
     
	@FXML
	void uploadPicture(ActionEvent event) {
		//get image from file
		FileChooser fc = new FileChooser();
		File fileOfPhoto = fc.showOpenDialog(null);
        BufferedImage image;
		try {
			image = ImageIO.read(fileOfPhoto);
			
			//store image in database
			new AccountDAO_Sql().setProfilePic(s.getAcc().getLoginEmail(), image);
			
			//display image
			imageProfile.setImage(SwingFXUtils.toFXImage(image, null));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
	
	@FXML
	void loadExperience(ActionEvent event) {
		//getting the experienceList and creating a backup to work off
		ArrayList<String> experienceList = acc.getExperienceList();
		ArrayList<String> backupExperienceList = new ArrayList<>(experienceList);
		Collections.copy(backupExperienceList, experienceList);
		
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setMaxWidth(600);
		layout.setHeading(new Text("Edit Experience"));
		JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.BOTTOM);
		dialog.setOnDialogClosed(e -> {
			try {
				new AccountDAO_Sql().updateAccount(acc.getLoginEmail(), acc);
				m.swapScene("/employee/Profile.fxml");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});

		BorderPane container = new BorderPane();
		
		//header
			Label title = new Label("Past Expriences");
			
		//generating past experience display
			ScrollPane experienceScroll = new ScrollPane();
			VBox pastExperienceEditableDisplay = new VBox(10);
			for (String s : backupExperienceList) {
				VBox pastEx = generatePastExperienceWithRemoveButton(
						s, backupExperienceList, pastExperienceEditableDisplay);
				
				pastExperienceEditableDisplay.getChildren().add(0, pastEx);
			}
			pastExperienceEditableDisplay.setPrefHeight(200);
			experienceScroll.setContent(pastExperienceEditableDisplay);
			experienceScroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
			
		//add buttons
			HBox addExperienceContainer = new HBox(2);
			
			JFXTextField jobName = new JFXTextField();
			jobName.setPrefWidth(100);
			jobName.setPromptText("Job Name");
			JFXTextField companyName = new JFXTextField();
			companyName.setPrefWidth(100);
			companyName.setPromptText("Company Name");
			JFXTextField date = new JFXTextField();
			date.setPrefWidth(100);
			date.setPromptText("Date");
			
			JFXButton addExperience = new JFXButton("Add");
			addExperience.setOnAction(e -> {
				//building the experience string
				StringBuilder sb = new StringBuilder();
				sb.append(jobName.getText());
				sb.append(';');
				sb.append(companyName.getText());
				sb.append("\t\t");
				sb.append(date.getText());
				String exp = sb.toString();
				
				//adding it in
				backupExperienceList.add(exp);
				pastExperienceEditableDisplay.getChildren().add(0, 
						generatePastExperienceWithRemoveButton(
								exp, backupExperienceList, pastExperienceEditableDisplay));
			});
			
			//adding to addExperienceContainer
			addExperienceContainer.getChildren().addAll(jobName, companyName, date, addExperience);
			
			
		//Save and cancel buttons
			HBox saveAndCancel = new HBox(10);
			JFXButton cancel = new JFXButton("Cancel");
			cancel.setOnAction(e -> {
				dialog.close();
			});
			JFXButton save = new JFXButton("Save");
			save.setOnAction(e -> {
				acc.setExperienceList(backupExperienceList);
				dialog.close();
			});
			saveAndCancel.getChildren().addAll(cancel, save);
			
		//adding all children to container
			container.setTop(title);
			container.setCenter(experienceScroll);
			HBox bottomRow = new HBox(20);
			bottomRow.getChildren().addAll(addExperienceContainer, saveAndCancel);
			container.setBottom(bottomRow);
			
		layout.setBody(container);
		dialog.show();
	}
	
	/**
	 * Auto activate upon loading controller/ fxml
	 * up date all the label to the latest information
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lbGender.setText(acc.getGender());
		lbMobile.setText(acc.getContact());
		lbName.setText(acc.getName());
		lbAboutMe.setText(acc.getAboutMe());
		
		//format gregorian calendar
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		lbDOB.setText(dateFormat.format(acc.getDateOfBirth().getTime()));
		
		lbHighestEdu.setText(acc.getHighestQualification());

		//setting of profile picture
		BufferedImage profilePic = new AccountDAO_Sql().getProfilePic(s.getAcc().getLoginEmail());
		profilePic = (profilePic == null)? ImageUpload.defaultProfilePic() : profilePic;
		imageProfile.setImage(SwingFXUtils.toFXImage(profilePic, null));
		
		System.out.println(acc.getRatingList());
		System.out.println(acc.averageRating());
		String rating = (Double.isNaN(acc.averageRating()))? "No ratings yet!" :
			String.format("%.2f", acc.averageRating()) +"(\t" +acc.getRatingList().size() +" Ratings)";
		System.out.println(acc.averageRating());
		lbRating.setText(rating);
		
		//displaying skills list
		for(String s : acc.getSkillsList()){
			System.out.println(s);
			skillsDisplay.getChildren().add(generateSkillNode(s));
		}
		
		//displaying experience list
		for (String s : acc.getExperienceList()) {
			appendExperience.getChildren().add(generatePastExperience(s));
		}
		
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
	
	private HBox generateSkillWithRemoveButton(String s, ArrayList<String> skillList, FlowPane skillFlowPane) {
		HBox container = new HBox();
		Label label = generateSkillNode(s);
		label.setAlignment(Pos.CENTER_LEFT);
		
		JFXButton removeSkill = new JFXButton("X");
		removeSkill.setAlignment(Pos.CENTER_RIGHT);
		removeSkill.setTextFill(Color.WHITE);
		removeSkill.setOnAction(e -> {
			skillFlowPane.getChildren().remove(container);
			skillList.remove(s);
		});
		
		
		container.getChildren().addAll(label, removeSkill);
		
		container.setMinWidth(label.getWidth() + 50);
		container.setStyle("-fx-background-radius:12px;-fx-background-color:#303f9f;");
		
		return container;
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
	
	private VBox generatePastExperienceWithRemoveButton(String s, ArrayList<String> experienceList, VBox experienceDisplay) {
		VBox container = generatePastExperience(s);
		
		//creating the remove button
		JFXButton removeButton = new JFXButton("X");
		removeButton.setTextFill(Color.WHITE);
		removeButton.setOnAction(e -> {
			experienceDisplay.getChildren().remove(container);
			experienceList.remove(s);
		});
		removeButton.setAlignment(Pos.CENTER_RIGHT);
		
		//adding the remove button
		((HBox)container.getChildren().get(0)).getChildren().add(removeButton);

		return container;
	}
}