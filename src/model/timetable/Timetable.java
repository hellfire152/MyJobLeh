package model.timetable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import application.MyJobLeh;
import application.Session;
import dataAccess.sql.JobsDAO_Sql;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.account.EmployeeAccount;
import model.account.UserAccount;
import model.event.ScheduledEvent;
import model.event.WorkEvent;

/**
 * Custom Timetable pane, initializes with a width and height.
 * 
 * Do keep in mind that the layout for the timetable is NOT responsive; once the
 * timetable is initialized, it will not, and cannot resize.
 * 
 * The timetable works off a BorderPane, where: top -> header left -> timetable
 * right -> legend (center and bottom are not used)
 * 
 * This class also defines a nested class, serving as the controller.
 * 
 * The calendar portion works off the GregorianCalender API for getting the day,
 * month, and year; checking for leap years every time the year changes
 * 
 * TODO::Switch the timetable FlowPane to a GridPane based layout TODO::Solve
 * the legend scollbar not working problem
 * 
 * @author AJK
 * @version b.1
 * @since a
 */
public class Timetable extends BorderPane {
	// All the fields/components!
	private static String[] months = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };
	private static String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
			"Sunday" };
	private static int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private double height, width;
	private static GregorianCalendar currentTime;
	private String clickcolor;
	private HBox topSection, bottomSection;
	private Button nextMonth, previousMonth, editLegend, showAll, showNone;
	private Label displayedMonth;
	private FlowPane timetableCellContainer;
	private StackPane legendContainer;
	private AnchorPane leftSection;
	private VBox legendContent;
	private TimetableHandler th = new TimetableHandler();
	private TimetableCell[] cells;
	private int month, day, year;
	
	private Session s = MyJobLeh.getInstance().getSession();
	
	// for storage of data
	private ArrayList<ScheduledEvent> loadedEventList = new ArrayList<>();
	private ArrayList<Boolean> displayCheckList = new ArrayList<>();
	
	// on first class initialization, set current date
	static {
		currentTime = new GregorianCalendar();
	}
	
	// initializing the fields for day, month, and year
	{
		day = currentTime.get(Calendar.DATE);
		month = currentTime.get(Calendar.MONTH);
		year = currentTime.get(Calendar.YEAR);
		leapYearUpdate();
		getStylesheets().add(getClass().getResource("/css/timetable.css").toExternalForm());

		cells = new TimetableCell[42];
	}

	/**
	 * Updates the daysInMonth array based on whether the displayed year is a
	 * leap year or not
	 * 
	 * leap year -> 29 not -> 28
	 */
	private void leapYearUpdate() {
		if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
			daysInMonth[1] = 29;
		else
			daysInMonth[1] = 28;
	}

	//constructor
	public Timetable(double width, double height) {
		super();
		this.height = height;
		this.width = width;

		generateTop();
		generateTimetable();
		generateLegend();
		generateLeft();

		setTop(topSection);
		setLeft(leftSection);
		setBottom(bottomSection);
		setRight(legendContainer);

		setMinSize(width, height);
		getStyleClass().add("timetable");
		
		addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		    	th.updateTimetableOnClick();
		    }
		});
		
		EmployeeAccount acc = (EmployeeAccount)s.getAcc();		
		loadEvents(acc.getEventList()); //user events
		ArrayList<WorkEvent> a = new JobsDAO_Sql().getAcceptedOfEmployee(acc.getLoginEmail());

		loadEvents(a); //accepted jobs
		display(month, year);
	}

	/**
	 * Generates the top part, including the next and previous month Buttons,
	 * and the display for the current month and year. Also assigns the event
	 * handlers.
	 * 
	 * Uses the "timetable-header" and "timetable-header-title" style classes.
	 * 
	 * Essentially, topSection is defined after running this method.
	 */
	private void generateTop() {
		// creating the container HBox
		topSection = new HBox(width / 6);
		topSection.setPrefSize(width, height / 9);
		topSection.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
		topSection.setAlignment(Pos.CENTER);

		// Buttons
		nextMonth = new Button("Next Month >>");
		previousMonth = new Button("<< Previous Month");

		// Button event listeners
		nextMonth.setOnAction(e -> th.nextMonth());
		previousMonth.setOnAction(e -> th.previousMonth());

		// Display for current month
		displayedMonth = new Label();
		displayedMonth.getStyleClass().add("timetable-header-title");
		displayedMonth.setMinSize(width / 5, USE_COMPUTED_SIZE);
		displayedMonth.setTextFill(Color.web("#FFF"));

		topSection.getChildren().addAll(previousMonth, displayedMonth, nextMonth);
		topSection.getStyleClass().add("timetable-header");
	}

	/**
	 * Generates the timetable, using a flowpane to contain all the cells
	 * TODO-Switch to GridPane
	 * 
	 * Uses the style classes "timetable-container", "timetable-dayLabel",
	 * "timetable-cell"
	 * 
	 * The dimensions are all fractions of the height and width, which may lead
	 * to the timetable looking off with different aspect ratios.
	 * 
	 * Manually adjust the widths and heights if needed.
	 */
	private void generateTimetable() {
		double tWidth = width * 5 / 7;
		double tHeight = height * 3 / 7;
		timetableCellContainer = new FlowPane();
		timetableCellContainer.setPrefWrapLength(tWidth);
		timetableCellContainer.setPrefHeight(tHeight);
		timetableCellContainer.setAlignment(Pos.CENTER_LEFT);
		timetableCellContainer.getStyleClass().add("timetable-container");

		double cellWidth = tWidth / 7.2;
		double cellHeight = tHeight / 4;

		// day labels
		StackPane[] dayLabels = new StackPane[7];
		for (int i = 0; i < 7; i++) {
			dayLabels[i] = new StackPane();
			dayLabels[i].getChildren().add(new Text(days[i].substring(0, 3)));
			dayLabels[i].setMaxSize(cellWidth, cellHeight / 2);
			dayLabels[i].setMinSize(cellWidth, cellHeight / 2);
			dayLabels[i].getStyleClass().add("timetable-dayLabel");
		}

		// cells
		for (int i = 0; i < 42; i++) {
			cells[i] = new TimetableCell();
			cells[i].setMaxSize(cellWidth, cellHeight);
			cells[i].setMinSize(cellWidth, cellHeight);
			cells[i].getStyleClass().add("timetable-cell");
		}
		timetableCellContainer.getChildren().addAll(dayLabels);
		timetableCellContainer.getChildren().addAll(cells);
		
		display(month, year);
	}

	/**
	 * Generates the legend on the right side of the containing BorderPane
	 * 
	 * Uses the "bordered-titled-title", "bordered-titled-border",
	 * "bordered-titled-content" style classes.
	 * 
	 * The legend's appearance is more dependent on CSS than java, so edit that
	 * if the appearance is not satisfactory
	 * 
	 * The legend is scrollable, with the bars showing up as needed.
	 */
	private void generateLegend() {

		// label that says "legend"
		Label l = new Label("Legend");
		l.getStyleClass().add("bordered-titled-title");
		StackPane.setAlignment(l, Pos.TOP_CENTER);

		// content pane, with scrollbar
		legendContent = new VBox(10);
		legendContent.setPrefWidth(width * 2 / 7 - 10);
		legendContent.setMaxSize(USE_PREF_SIZE, height);
		legendContent.getStyleClass().add("bordered-titled-border");
		ScrollPane legendScroll = new ScrollPane(legendContent);
		legendScroll.setMaxHeight(height);
		legendScroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		legendScroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);

		// for the styles
		StackPane contentPane = new StackPane();
		legendContent.getStyleClass().add("bordered-titled-content");
		contentPane.getChildren().add(legendContent);

		// adding it all together...
		legendContainer = new StackPane();
		legendContainer.setAlignment(Pos.CENTER);
		legendContainer.getChildren().addAll(contentPane, l);

	}

	/**
	 * Generates the whole left side of the BorderPane; defines leftSection.
	 * This is done to include the Buttons for edit, showAll, and showNone
	 */
	private void generateLeft() {
		leftSection = new AnchorPane();

		// container for the Buttons
		HBox ButtonContainer = new HBox(20);
		AnchorPane.setBottomAnchor(ButtonContainer, 20.0);
		AnchorPane.setLeftAnchor(ButtonContainer, 10.0);
		// initializing the Buttons
		editLegend = new Button("Edit");
		showAll = new Button("Show All");
		showNone = new Button("Hide All");

		// alignment for the Buttons
		editLegend.setAlignment(Pos.BOTTOM_LEFT);
		showAll.setAlignment(Pos.BOTTOM_LEFT);
		showNone.setAlignment(Pos.BOTTOM_LEFT);

		// adding event handlers
		editLegend.setOnAction(e -> th.edit());
		showAll.setOnAction(e -> th.showAll());
		showNone.setOnAction(e -> th.showNone());

		ButtonContainer.getChildren().addAll(editLegend, showAll, showNone);
		leftSection.getChildren().addAll(timetableCellContainer, ButtonContainer);
	}

	/**
	 * Resets the color of all {@link TimetableCell} in the timetable to white
	 */
	private void resetCellColor() {
		for (TimetableCell t : cells) {
			t.resetColor();
		}
	}
	
	/**
	 * Gets the array of {@link TimetableCell}
	 * @return
	 */
	public TimetableCell[] getCells() {
		return cells;
	}

	/**
	 * Loads events into the timetable's own array for easier reference
	 * @param e
	 */
	public void loadEvents(ArrayList<? extends ScheduledEvent> e) {
		for (ScheduledEvent s : e) {
			loadedEventList.add(s);
			addLegendItem(new LegendItem(s.getColorVariable(), s.getName()));
			displayCheckList.add(true);
		}
	}
	
	/**
	 * Populates the blank Labels in the timetable with numbers, based on the
	 * specified month and year. Will also include the numbers for the previous
	 * and next months, and update the timetable header.
	 * 
	 * @param month
	 * @param year
	 */
	public void display(int month, int year) {
		int previousMonth, nextMonth, previousMonthYear, nextMonthYear;
		if (month - 1 < 0) {
			previousMonth = 12;
			previousMonthYear = year - 1;
		} else {
			previousMonth = month - 1;
			previousMonthYear = year;
		}
		if (month + 1 > 11) {
			nextMonth = 0;
			nextMonthYear = year + 1;
		} else {
			nextMonth = month + 1;
			nextMonthYear = year;
		}

		GregorianCalendar date = new GregorianCalendar(year, month, 1);
		int daysInPreviousMonth = date.get(GregorianCalendar.DAY_OF_WEEK) - 1;

		// going back according to the number of displayed dates in the previous
		// month
		for (int i = 0; i < daysInPreviousMonth; i++) {
			date.add(GregorianCalendar.DATE, -1);
		}

		// setting the dates
		for (int i = 0; i < cells.length; i++) {
			cells[i].setDate(date);
			date.add(GregorianCalendar.DATE, 1);
		}
	
		// update the header
		displayedMonth.setText(months[month] + ' ' + year);
	}

	/**
	 * Adds events based from the loaded event list based on whether the checkbox is selected
	 */
	public void addEventFromList() {
		resetCellColor();
		for (int i = 0; i < loadedEventList.size(); i++) {
			if (displayCheckList.get(i)) {
				addEvent(loadedEventList.get(i));
			}
		}
	}
	
	/**
	 * Adds an event into the timetable, by highlighting the appropriate timetable cells
	 * @param e
	 */
	public void addEvent(ScheduledEvent e) {
		for (GregorianCalendar g : e.getDateList()) {
			for (TimetableCell t : cells) {
				if (t.getDate().get(Calendar.YEAR) == g.get(Calendar.YEAR) &&
					t.getDate().get(Calendar.MONTH) == g.get(Calendar.MONTH) &&
					t.getDate().get(Calendar.DATE) == g.get(Calendar.DATE)) {
					t.setColor(e.getColor());
					break;
				}
			}
		}
	}

	/**
	 * Adds the specified LegendItem to the legend and to the storage list
	 * 
	 * @param l
	 *            the LegendItem to be added
	 */
	private void addLegendItem(LegendItem l) {
		legendContent.getChildren().add(l);
	}

	/**
	 * Contains all the methods that are supposed to be called on during event handling
	 * 
	 * @author AJK
	 *
	 */
	private class TimetableHandler {

		public void nextMonth() {
			if (++month > 11) {
				month = 0;
				year++;
				leapYearUpdate();
			}
			display(month, year);
		}

		public void previousMonth() {
			if (--month < 0) {
				month = 11;
				year--;
				leapYearUpdate();
			}
			display(month, year);
		}

		public void updateTimetableOnClick() {
			for (int i = 0; i < legendContent.getChildren().size(); i++) {
				if (((LegendItem)(legendContent.getChildren().get(i))).getCheckBox().isSelected()) {
					displayCheckList.set(i, true);
				} else {
					displayCheckList.set(i, false);
				}
			}
			addEventFromList();
		}

		public void edit() {

		}

		public void showAll() {
			for (int i = 0; i < legendContent.getChildren().size(); i++) {
				((LegendItem)legendContent.getChildren().get(i)).getCheckBox().setSelected(true);
			}
			addEventFromList();
		}

		public void showNone() {
			for (int i = 0; i < legendContent.getChildren().size(); i++) {
				((LegendItem)legendContent.getChildren().get(i)).getCheckBox().setSelected(false);
			}
			addEventFromList();
		}
	}
}
