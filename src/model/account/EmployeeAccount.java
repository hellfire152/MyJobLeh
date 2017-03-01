package model.account;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import model.event.ScheduledEvent;

public class EmployeeAccount extends UserAccount {
	private ArrayList<String> experienceList;
	private ArrayList<String> skillsList;
	private ArrayList<ScheduledEvent> eventList;
	private ArrayList<Double> ratingList;
	private GregorianCalendar dateOfBirth;
	private String aboutMe;
	private String gender;
	private String status;
	private String highestQualification;
	
	{
		experienceList = new ArrayList<>();
		skillsList = new ArrayList<>();
		eventList = new ArrayList<>();
		ratingList = new ArrayList<>();
	}
	
	public EmployeeAccount(String loginEmail, String name) {
		super(loginEmail, name);
	}
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	public ArrayList<String> getSkillsList() {
		return skillsList;
	}
	
	public ArrayList<Double> getRatingList() {
		return ratingList;
	}
	public void addRating(double rating) {
		ratingList.add(rating);
	}
	public double averageRating() {
		double avg = 0;
		for (double d : ratingList) {
			avg += d;
		}
		return avg / ratingList.size();
	}
	
	public void setSkillsList(ArrayList<String> skillsList) {
		this.skillsList = skillsList;
	}
	public String getHighestQualification() {
		return highestQualification;
	}
	public void setHighestQualification(String qualificationList) {
		this.highestQualification = qualificationList;
	}
	public ArrayList<String> getExperienceList() {
		return experienceList;
	}
	public void setExperienceList(ArrayList<String> experienceList) {
		this.experienceList = experienceList;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public ArrayList<ScheduledEvent> getEventList() {
		return eventList;
	}
	public void setEventList(ArrayList<ScheduledEvent> eventList) {
		this.eventList = eventList;
	}
	public GregorianCalendar getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(GregorianCalendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
