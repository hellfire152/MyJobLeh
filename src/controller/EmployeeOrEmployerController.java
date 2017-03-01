package controller;

/**
 *
 * Controller for the ScreenOption.fxml
 * @author Bryan 
 * @version a.3
 * @since a
 * 
 */


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.employee.LoginController;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class EmployeeOrEmployerController extends Controller implements Initializable{
	
	@FXML
	private BorderPane root;
	// Event Listener on JFXButton.onAction
	@FXML
	public void handleEmployeeLogin(ActionEvent event) {
		if(true){
			try {
				m.swapScene("/employee/Login.fxml");
				s.setEmployeeAccount(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	// Event Listener on JFXButton.onAction
	@FXML
	public void handleEmployerLogin(ActionEvent event) {
		if(true){
			try {
				m.swapScene("/employer/Login.fxml");
				s.setEmployeeAccount(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		if (!m.splashFinished) {
			loadSplashScreen();
		}
	}
	public void loadSplashScreen() {
		try {
			StackPane pane = m.getScene("/Splash.fxml").load();
			root.setCenter(pane);

			FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), pane);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);

			FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), pane);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);

			fadeIn.play();

			fadeIn.setOnFinished(e -> {
				fadeOut.play();
			});

			fadeOut.setOnFinished(e -> {
				m.splashFinished = true;
				try {
					m.swapScene("/EmployeeOrEmployer.fxml");
					// VBox parentContent =
					// FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
					// root.getChildren().setAll(parentContent);

				} catch (IOException ex) {
					Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
				}

			});

		} catch (IOException ex) {
			Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
