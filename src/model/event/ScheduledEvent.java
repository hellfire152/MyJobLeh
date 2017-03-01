package model.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * ScheduledEvent, contains:
 * date list,
 * name,
 * color,
 * time (as a String, to make things easier)
 * 
 *
 * @author AJK
 * @version b.2.2
 * @since a
 */
public class ScheduledEvent implements Serializable {
	private ArrayList<GregorianCalendar> dateList = new ArrayList<>();

	private String name;
	private String color;
	private int colorVariable;
	private String time;
	
	public ScheduledEvent() {
		colorVariable = ThreadLocalRandom.current().nextInt(0, 360 + 1);
		color = "hsb(" +colorVariable +",90%,100%)";
	}
	/**
	 * Generates a (bright, pastel-coloured) color and returns that color
	 * @return
	 */
	public static float generateRandomFloat() {
		Random random = new Random();
		return random.nextFloat();
	}
	
	public ArrayList<GregorianCalendar> getDateList() {
		return dateList;
	}
	
	public void setDateList(ArrayList<GregorianCalendar> dateList) {
		this.dateList = dateList;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getColorVariable() {
		return colorVariable;
	}
	public void setColorVariable(int colorVariable) {
		this.colorVariable = colorVariable;
	}
	/**
	 * Returns an int array, representing the day, month, and year respectively,
	 * from an argument string of the format "D/M/Y" (D, M, Y is any int).
	 * 
	 * @param date
	 * @return an array representing day, monthm and year
	 */
	public static int[] splitDate(String date) {
		int[] d = new int[3];

		try (Scanner sc = new Scanner(date).useDelimiter("/");) {
			for (int i = 0; i < d.length; i++)
				d[i] = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Date not properly written");
			e.printStackTrace();
		}

		return d;
	}

}
