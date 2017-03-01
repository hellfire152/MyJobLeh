package model.timetable;

import java.util.Calendar;
import java.util.GregorianCalendar;

import dataAccess.ImageUpload;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

/**
 * The Custom Pane for the Timetable cells. Used in the timetable (obviously)
 * The TimetableCell stores the represented date and color, checking for
 * conflicts while at it.
 * 
 * Each TimetableCell must be initialized with a year, month, and day in the
 * constructor.
 * 
 * The date is stored using a GregorianCalendar Object, so do keep that in mind
 * 
 * @author AJK
 * @version a.1.1
 * @since a.1.1
 */
public class TimetableCell extends StackPane {

	private GregorianCalendar date = new GregorianCalendar();
	private Label displayLabel;
	private String display;
	private String color;
	private int noOfEvents;
	/**
	 * Given a {@link GregorianCalendar} Object, return a formatted String
	 * 
	 * Format is dd/MM/yyyy
	 * 
	 * @param date
	 * @return String representation of the {@link GregorianCalendar}
	 */
	public static String formatDate(GregorianCalendar date) {
		int d = date.get(GregorianCalendar.DATE);
		int m = date.get(GregorianCalendar.MONTH) + 1;
		String y = Integer.toString(date.get(GregorianCalendar.YEAR));

		StringBuilder s = new StringBuilder();
		s.append((d < 10) ? '0' + d : d);
		s.append('/');
		s.append((m < 10) ? '0' + m : m);
		s.append('/');
		s.append(y);

		return s.toString();
	}

	// Constructors
	public TimetableCell(GregorianCalendar date) {
		this.date = date;
		display = Integer.toString(this.date.get(GregorianCalendar.DATE));
		displayLabel = new Label(display);
		getChildren().add(displayLabel);
	}

	public TimetableCell(int year, int month, int day) {
		this(new GregorianCalendar(year, month, day));
	}

	public TimetableCell() {
		this(new GregorianCalendar());
	}

	/*
	 * GETTERS AND SETTERS
	 * 
	 * displayLabel - none 
	 * the rest - GS
	 */
	public GregorianCalendar getDate() {
		return date;
	}

	// setting the date also clears the color, and updates the display
	public void setDate(GregorianCalendar g) {
		date.set(g.get(Calendar.YEAR), g.get(Calendar.MONTH), g.get(Calendar.DATE));
		color = null;
		setDisplay(Integer.toString(date.get(GregorianCalendar.DATE)));
	}

	// accommodating for int arguments
	public void setDate(int year, int month, int day) {
		setDate(new GregorianCalendar(year, month, day));
	}

	public String getDisplay() {
		return display;
	}

	// also changes the text on the label
	private void setDisplay(String display) {
		this.display = display;
		displayLabel.setText(display);
	}

	public String getColor() {
		return color;
	}

	// only changes the color if no other color is present
	public void setColor(String color) {
		setStyle(null);
		noOfEvents++;
		if (this.color == null) {
			this.color = color;
			setStyle("-fx-background-color: " +color);
		} else {
			setStyle("-fx-background-color: #FFF");
			showConflict();
		}
	}
	public void resetColor() {
		setStyle(null);
		color = null;
		noOfEvents = 0;
	}
	public void blacken() {
		setStyle("-fx-background-color: #000;");
	}
	public void showConflict() {
		ImageView img = new ImageView();
		img.setImage(SwingFXUtils.toFXImage(ImageUpload.eventConflictImage(), null));
		img.setPreserveRatio(true);
		img.setFitHeight(40);
		getChildren().add(0, img);
		
		setDisplay(noOfEvents +"\nevents!");
		displayLabel.setTextAlignment(TextAlignment.CENTER);
	}
}
