package model.timetable;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.event.ScheduledEvent;

public class TimetableTest extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		Timetable t = new Timetable(600, 400);
		
		ScheduledEvent s = new ScheduledEvent();
		for (TimetableCell tc : t.getCells()) {
			//System.out.println(tc.getDate().toString());
		}
		s.setName("test");
		s.setColor("#ABC");
		s.getDateList().add(new GregorianCalendar(2017, 0, 17));
		s.getDateList().add(new GregorianCalendar(2017, 0, 18));
		s.getDateList().add(new GregorianCalendar(2017, 0, 19));
		s.getDateList().add(new GregorianCalendar(2017, 0, 20));
		s.getDateList().add(new GregorianCalendar(2017, 0, 21));
		
		ArrayList<ScheduledEvent> a = new ArrayList<>();
		a.add(s);
		t.loadEvents(a);
		
		primaryStage.setScene(new Scene(t));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
