package model.event;

import java.util.ArrayList;

import dataAccess.sql.JobsDAO_Sql;

/**
 * WorkEvent, a ScheduledEvent that involves money!
 * 
 * @author AJK
 * @version a.3.1
 * @since a
 */
public class WorkEvent extends ScheduledEvent {
	private static ArrayList<WorkEvent> allWorkList = new JobsDAO_Sql().getAllJobs();
	private String employeeEmail;
	private String jobTitle;
	private String companyName;
	private String address;
	private String weekdayRate;
	private String weekendRate;
	private String position;
	private String minimumAge;
	private String jobScope;
	private String startDate;
	private String endDate;
	private String skillsObtained;
	private String skillsRequired;
	
	public WorkEvent(){
		//delete this after testing
	}
	public WorkEvent(String employeeEmail, String jobTitle, String companyName, String address,
			String position, String minimumAge, String jobScope, String startDate, String endDate,
			String skillsObtained, String skillsRequired, String weekendRate, String weekdayRate) {
		super();
		setName(jobTitle);
		this.employeeEmail = employeeEmail;
		this.jobTitle = jobTitle;
		this.companyName = companyName;
		this.address = address;
		this.position = position;
		this.minimumAge = minimumAge;
		this.jobScope = jobScope;
		this.startDate = startDate;
		this.endDate = endDate;
		this.skillsObtained = skillsObtained;
		this.skillsRequired = skillsRequired;
		this.weekdayRate = weekdayRate;
		this.weekendRate = weekendRate;
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMinimumAge() {
		return minimumAge;
	}
	public void setMinimumAge(String minimumAge) {
		this.minimumAge = minimumAge;
	}
	public String getJobScope() {
		return jobScope;
	}
	public void setJobScope(String jobScope) {
		this.jobScope = jobScope;
	}
	public String getSkillsObtained() {
		return skillsObtained;
	}
	public void setSkillsObtained(String skillsObtained) {
		this.skillsObtained = skillsObtained;
	}
	public String getSkillsRequired() {
		return skillsRequired;
	}
	public void setSkillsRequired(String skillsRequired) {
		this.skillsRequired = skillsRequired;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
		setName(jobTitle);
	}
	public static ArrayList<WorkEvent> getAllWorkList() {
		return allWorkList;
	}
	public String getWeekdayRate() {
		return weekdayRate;
	}
	public void setWeekdayRate(String weekdayRate) {
		this.weekdayRate = weekdayRate;
	}
	public String getWeekendRate() {
		return weekendRate;
	}
	public void setWeekendRate(String weekendRate) {
		this.weekendRate = weekendRate;
	}
	
}
