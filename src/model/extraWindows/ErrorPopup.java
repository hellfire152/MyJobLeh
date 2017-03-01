package model.extraWindows;

import dataAccess.ImageUpload;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Contains that contains a method that displays an error message,
 * complete with an exclaimation mark image!
 * 
 * @author AJK
 * @version b.3
 * @since b.3
 */
public class ErrorPopup {
	public static void display(String message) {
		Stage window = new Stage();
		window.setTitle("Error!");
		
		HBox root = new HBox();
		
		Label l = new Label(message);
		l.setAlignment(Pos.CENTER_LEFT);
		l.setFont(new Font(15));
		HBox.setMargin(l, new Insets(10, 10, 10, 10));
		
		ImageView errImg = new ImageView(
				SwingFXUtils.toFXImage(ImageUpload.errorImage(), null));
		errImg.setPreserveRatio(true);
		errImg.setFitHeight(35);
		HBox.setMargin(errImg, new Insets(10,10,10,10));
		
		root.getChildren().addAll(errImg, l);
		
		window.setScene(new Scene(root));
		window.showAndWait();
	}
}
