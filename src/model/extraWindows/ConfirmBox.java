package model.extraWindows;

import com.jfoenix.controls.JFXButton;

import dataAccess.ImageUpload;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ConfirmBox {
	private static boolean value; 
	public static boolean display(String message) {
		Stage window = new Stage();
		window.setTitle("Confirm");
		
		BorderPane root = new BorderPane();
		root.setMinSize(200, 70);
		Label label = new Label(message);
		label.setFont(new Font(15));
		HBox.setMargin(label, new Insets(10,10,10,10));
		
		//buttons
		JFXButton no = new JFXButton("No");
		no.setOnAction(e -> {
			value = false;
			window.close();
		});
		JFXButton yes = new JFXButton("Yes");
		yes.setOnAction(e -> {
			value = true;
			window.close();
		});
		
		//question mark image
		ImageView question = new ImageView(
				SwingFXUtils.toFXImage(ImageUpload.confirmBoxImage(), null));
		question.setPreserveRatio(true);
		question.setFitHeight(35);
		HBox.setMargin(label, new Insets(5,0,5,0));
		
		//top row container
		HBox topRow = new HBox(30);
		topRow.getChildren().addAll(question, label);
		topRow.setAlignment(Pos.CENTER);
		
		//button containers
		HBox buttonContainer = new HBox();
		buttonContainer.getChildren().addAll(yes, no);
		buttonContainer.setAlignment(Pos.CENTER);
		
		root.setTop(topRow);
		root.setBottom(buttonContainer);
		
		
		window.setScene(new Scene(root));
		
		window.setOnCloseRequest(e -> {
			value = false;
		});
		window.showAndWait();
		return value;
	}
}
