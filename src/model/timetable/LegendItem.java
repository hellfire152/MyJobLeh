package model.timetable;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LegendItem extends HBox {

	private String name;
	private int color;
	private CheckBox displayed;

	public LegendItem(int colour, String name) {
		System.out.println(name);
		this.color = colour;
		this.name = name;

		StackPane r = new StackPane();

		displayed = new CheckBox();
		displayed.setSelected(true);
		displayed.setMaxSize(8.0, 8.0);
		displayed.setAllowIndeterminate(false);

		Rectangle displayColour = new Rectangle(30, 30);
		
		displayColour.setFill(Color.hsb(color, 0.9f, 1.0f));

		r.getChildren().addAll(displayColour, displayed);

		Label text = new Label(name);
		text.setPadding(new Insets(5, 0, 5, 5));
		getChildren().addAll(r, text);
		getStyleClass().add("legendItem-border");

	}

	public CheckBox getCheckBox() {
		return displayed;
	}
}
