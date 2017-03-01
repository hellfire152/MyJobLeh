package application;

import java.io.IOException;

import dataAccess.sql.JobsDAO_Sql;
import controller.Controller;
import dataAccess.sql.AccountDAO_Sql;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class MyJobLeh extends Application {
	// static reference for the instance of the program
	private static MyJobLeh m;
	// reference for the window and scene
	private Stage window;
	private Scene scene;
	// for the beginning splash
	public boolean splashFinished = false;
	//contains user and theme info
	private Session s;

	{
		MyJobLeh.m = this;
		s = new Session(this);
	}

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		
		try {
			window.setTitle("MyJobLeh!");
			
			swapScene("/EmployeeOrEmployer.fxml");
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Allows for swapping scenes from the controllers, loading a scene from an
	 * FXML file. Path is relative from the view package
	 * 
	 * The various css for the current theme is added automatically as well
	 * 
	 * For passing info of any sort to the next pane, the controller of the swapped
	 * to scene is returned.
	 * 
	 * @param location
	 *            the URL string to the FXML file
	 * @throws IOException
	 * @version b.2
	 * @since a
	 * @author AJK
	 */
	public Object swapScene(String location) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view" + location));

		scene = new Scene(loader.load());

		window.setScene(scene);
		
		return loader.getController();
	}
	/**
	 * swapScene, that also sets the title of the window
	 * @since b.3
	 */
	public Object swapScene(String location, String windowTitle) throws IOException {
		window.setTitle(windowTitle);
		
		return swapScene(location);
	}
	/**
	 * A version of swapScene that allows for additional css to be added, just in case
	 * 
	 * @param location
	 *            the URL string to the FXML file
	 * @param cssLocations
	 *            file names of the css stylesheets to link
	 * @throws IOException
	 * @version a.3.1
	 * @since a
	 * @author AJK
	 */
	public void swapScene(String location, String... cssLocations) throws IOException {
		swapScene(location);

		for (String s : cssLocations) {
			scene.getStylesheets().add("/css/" + s);
		}
	}

	/**
	 * A bit of shorthand, to make code easier to understand Path also relative
	 * from the view package
	 * 
	 * @param location
	 *            location of the desired scene
	 * @return the FXMLLoader, set to that FXML file specified by the location
	 *         argument
	 * @throws IOException
	 * 
	 * @version a.2
	 * @since a.2
	 * @author AJK
	 */
	public FXMLLoader getScene(String location) throws IOException {
		return new FXMLLoader(getClass().getResource("/view" + location));
	}

	public static MyJobLeh getInstance() {
		return m;
	}
	public Session getSession() {
		return s;
	}
	public Scene getDisplayedScene() {
		return scene;
	}
	public static void main(String[] args) {
		launch(args);
	}

}
