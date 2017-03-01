package controller.employer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import controller.Controller;
import dataAccess.ImageUpload;
import dataAccess.sql.AccountDAO_Sql;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.account.EmployerAccount;

/**
 * fxml loadexception error
 * 
 * @version a.2
 * @since a.1
 * @author Ning
 */
public class ProfileController extends Controller implements Initializable{
	 @FXML
	    private JFXHamburger fxHamburg;

	    @FXML
	    private ImageView profilePicView;

	    @FXML
	    private Label lbName;

	    @FXML
	    private Label lbContact;

	    @FXML
	    private Label lbAddress;

	    @FXML
	    private JFXButton btnEditCompanyDetails;

	    @FXML
	    private JFXButton btnEditAbout;

	    @FXML
	    private Label lbAbout;

	    @FXML
	    private JFXDrawer fxDrawer;
    @FXML
    private StackPane stackPaneEditCompany;
    @FXML
    private StackPane stackPaneEditAbout;

	@FXML
	public void handleHome(ActionEvent event) {
		try {
			m.swapScene("/employer/Home.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleFeedBack(ActionEvent event) {
		try {
			m.swapScene("/employer/Feedback.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 @FXML
	 void handleStatistics(ActionEvent event) {
		 try {
				m.swapScene("/employer/StatisticsPie.fxml");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	 /**
		 * Event Listener on JFXButton[#btnJobs].onAction
		 * 
		 * @param event
		 */
	    @FXML
	    void handleJobs(ActionEvent event) {
	    	try {
				m.swapScene("/employer/CreateJob.fxml");

			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

	@FXML
	public void handleLogout(ActionEvent event) {
		try {
			m.swapScene("/EmployeeOrEmployer.fxml");
			s.setAcc(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void handleProfile(ActionEvent event) {
		try {
			m.swapScene("/employer/Profile.fxml");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		EmployerAccount acc = (EmployerAccount)s.getAcc();
		//did you set the things to begin with? oh yeah... no wad is get from database ma
		//if you never set it to begin with the database won't have it either isnt that it
		//wait
		System.out.println(acc.getAddress());
		System.out.println(acc.getCompanyName());
		System.out.println(acc.getContact());
		lbName.setText(acc.getCompanyName());
		lbContact.setText(acc.getContact());
		lbAddress.setText(acc.getAddress());
		lbAbout.setText(acc.getAbout());
		
		BufferedImage profilePic = new AccountDAO_Sql().getProfilePic(s.getAcc().getLoginEmail());
		profilePic = (profilePic == null) ? ImageUpload.defaultProfilePic() : profilePic;
		profilePicView.setImage(SwingFXUtils.toFXImage(profilePic, null));
		
		/* Drawer */
		try {
			VBox box = m.getScene("/employer/Employerdrawer.fxml").load();
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
		// end of drawer
		
	}
    @FXML
    void handleEditCompanyDetails(ActionEvent event) {
 
    	try {
			JFXDialogLayout content = new JFXDialogLayout();
			content.setHeading(new Text("Edit Company Details"));
			content.getChildren().add(m.getScene("/employer/EditCompanyDetails.fxml").load());
			JFXDialog dialog = new JFXDialog(stackPaneEditCompany, content, JFXDialog.DialogTransition.BOTTOM);
			dialog.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
    	
    }

    @FXML
    void handleEditAbout(ActionEvent event) {
    	try {
			JFXDialogLayout content = new JFXDialogLayout();
			content.setHeading(new Text("Edit Company About"));
			content.getChildren().add(m.getScene("/employer/EditCompanyAbout.fxml").load());
			JFXDialog dialog = new JFXDialog(stackPaneEditAbout, content, JFXDialog.DialogTransition.BOTTOM);
			dialog.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
    }
    
    @FXML
    public void handleImageUpload(ActionEvent e) {
		//get image from file
		FileChooser fc = new FileChooser();
		File fileOfPhoto = fc.showOpenDialog(null);
        BufferedImage image;
		try {
			image = ImageIO.read(fileOfPhoto);
			
			//store image in database
			new AccountDAO_Sql().setProfilePic(s.getAcc().getLoginEmail(), image);
			
			//display image
			profilePicView.setImage(SwingFXUtils.toFXImage(image, null));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
        
    }
}
