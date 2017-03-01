package dataAccess;

import java.util.ArrayList;

import model.account.EmployeeAccount;
import model.account.EmployerAccount;
import model.event.WorkEvent;

/**
 * JobDAO for accessing jobs from any database!
 * 
 * There are quite a few columns a job entry is supposed to have, namely:
 * owner_name, owner_email, job_title, data (WorkEvent .ser file), pending_employees, and accepted_employees
 * That's quite a few columns.
 * 
 * As such, this interface just defines what's basically getters and setters for the above, with an extra
 * createJob and deleteJob
 * 
 * @author Nigel, AJK
 * @version b.1
 * @since a.3
 */
public interface JobsDAO {
	/**
	 * Retrieve job
	 * @param index
	 * @return
	 */
	public WorkEvent getJob(String jobTitle);
	public void updateJob(String jobTitle, WorkEvent e);
	/**
	 * Create new jobs and save it to database
	 * @param email
	 * @param jobTitle
	 * @param EmployerName
	 */
	public boolean createJob(String email, String employerName, String jobTitle, WorkEvent jobObject);
	public void deleteJob(String jobTitle);
	public ArrayList<WorkEvent> getPendingOfEmployee(String employeeEmail);
	public ArrayList<WorkEvent> getAcceptedOfEmployee(String employeeEmail);
	public ArrayList<WorkEvent> getPastOfEmployee(String employeeEmail);
	public ArrayList<EmployeeAccount> getPendingOfJob(String jobTitle);
	public ArrayList<EmployeeAccount> getAcceptedOfJob(String jobTitle);
	public void insertIntoPending(String employeeEmail, String jobTitle);
	public void insertIntoAccepted(String employeeEmail, String jobTitle);
	public void insertIntoPast(String employeeEmail, String jobTitle);
	public void deleteFromPending(String employeeEmail, String jobTitle);
	public void deleteFromAccepted(String employeeEmail, String jobTitle);
	public void transferFromPendingToAccepted(String employeeEmail, String jobTitle);
	public void transferFromAcceptedToPendingResign(String employeeEmail, String jobTitle);
	public EmployerAccount getEmployerOfJob(String jobTitle);
	public ArrayList<WorkEvent> getSearchJob(String search);
}