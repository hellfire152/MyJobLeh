package dataAccess.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import dataAccess.JobsDAO;
import model.account.EmployeeAccount;
import model.account.EmployerAccount;
import model.event.WorkEvent;

/**
 * SQL implementation of {@link JobsDAO}
 * 
 * @author Nigel, AJK, Ning
 * @version b.3
 * @since a.3
 */
public class JobsDAO_Sql extends DatabaseAccessor implements JobsDAO {
	
	@Override
	public WorkEvent getJob(String jobTitle) {
		try {
			rs = st.executeQuery(
					"SELECT * FROM jobs WHERE job_title = '" +jobTitle +"'");
			if (!rs.last()) {
				return null;
			}
			rs.beforeFirst();
			rs.next();

			return (WorkEvent)(bytesToObject(rs.getBytes("object")));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public ArrayList<WorkEvent> getAllJobs() {
		ArrayList<WorkEvent> workList = new ArrayList<>();
		try {
			rs = st.executeQuery(
					"SELECT * FROM jobs");
			
			rs.beforeFirst();
			
			while (rs.next()) {
				workList.add((WorkEvent)bytesToObject(rs.getBytes("object")));
			}
			
			return workList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public void updateJob(String jobTitle, WorkEvent e) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(
					"UPDATE jobs SET object = ? WHERE job_title = '" +jobTitle +"'");

			ps.setObject(1, e);
			ps.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}

	@Override
	public boolean createJob(String email, String employerName, String jobTitle, WorkEvent jobObject) {
		if (getJob(jobTitle) == null) {
			try {
		        PreparedStatement ps = con.prepareStatement(
		        		"INSERT INTO jobs (owner_email , owner_name , job_title, object) VALUES ( ? , ? , ? , ? )");
	
				ps.setString(1, email);
				ps.setString(2, employerName);
				ps.setString(3, jobTitle);
				ps.setObject(4, jobObject);
				ps.executeUpdate();
				
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Job already exists!");
			return false;
		}
		return false;
	}

	@Override
	public void deleteJob(String jobTitle) {
		try {
			PreparedStatement ps = con.prepareStatement(
					"DELETE FROM jobs WHERE job_title = '" +jobTitle +"'");
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<WorkEvent> getJobOfEmployer(String email){
		ArrayList<WorkEvent> jobList = new ArrayList<>();
		try{
			rs=st.executeQuery(
					"SELECT * FROM jobs WHERE owner_email = '"+email+"'");
			rs.beforeFirst();
			while(rs.next()){
				jobList.add((WorkEvent)bytesToObject(rs.getBytes("object")));
			}
			
			return jobList;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<WorkEvent> getPendingOfEmployee(String employeeEmail) {
		ArrayList<WorkEvent> workList = new ArrayList<>();
		try {
			rs = st.executeQuery(
					"SELECT jobs.* FROM pending_to_jobs "
					+ "JOIN employee_accounts ON employee_accounts.id = pending_to_jobs.employee_id "
					+ "JOIN jobs ON jobs.id = pending_to_jobs.job_id "
					+ "WHERE employee_accounts.email = '" +employeeEmail +"'");
			
			rs.beforeFirst();
			while(rs.next()) {
				workList.add((WorkEvent)bytesToObject(rs.getBytes("object")));
			}
			
			return workList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<WorkEvent> getAcceptedOfEmployee(String employeeEmail) {
		ArrayList<WorkEvent> workList = new ArrayList<>();
		try {
			rs = st.executeQuery(
					"SELECT jobs.* FROM accepted_to_jobs "
					+ "JOIN employee_accounts ON employee_accounts.id = accepted_to_jobs.employee_id "
					+ "JOIN jobs ON jobs.id = accepted_to_jobs.job_id "
					+ "WHERE employee_accounts.email = '" +employeeEmail +"'");
			
			rs.beforeFirst();
			while(rs.next()) {
				workList.add((WorkEvent)bytesToObject(rs.getBytes("object")));
			}
			
			return workList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ArrayList<WorkEvent> getPastOfEmployee(String employeeEmail) {
		ArrayList<WorkEvent> workList = new ArrayList<>();
		try {
			rs = st.executeQuery(
					"SELECT jobs.* FROM accepted_to_resign "
					+ "JOIN employee_accounts ON employee_accounts.id = accepted_to_resign.employee_id "
					+ "JOIN jobs ON jobs.id = accepted_to_resign.job_id "
					+ "WHERE employee_accounts.email = '" +employeeEmail +"'");
			
			rs.beforeFirst();
			while(rs.next()) {
				workList.add((WorkEvent)bytesToObject(rs.getBytes("object")));
			}
			
			return workList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<EmployeeAccount> getPendingOfJob(String jobTitle) {
		ArrayList<EmployeeAccount> accountList = new ArrayList<>();
		try {
			rs = st.executeQuery(
					"SELECT employee_accounts.* FROM pending_to_jobs "
					+ "JOIN employee_accounts ON employee_accounts.id = pending_to_jobs.employee_id "
					+ "JOIN jobs ON jobs.id = pending_to_jobs.job_id "
					+ "WHERE jobs.job_title  = '" +jobTitle +"'");
			
			rs.beforeFirst();
			while(rs.next()) {
				accountList.add((EmployeeAccount)bytesToObject(rs.getBytes("object")));
			}
			
			return accountList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<EmployeeAccount> getAcceptedOfJob(String jobTitle) {
		ArrayList<EmployeeAccount> accountList = new ArrayList<>();
		try {
			rs = st.executeQuery(
					"SELECT employee_accounts.* FROM accepted_to_jobs "
					+ "JOIN employee_accounts ON employee_accounts.id = accepted_to_jobs.employee_id "
					+ "JOIN jobs ON jobs.id = accepted_to_jobs.job_id "
					+ "WHERE jobs.job_title  = '" +jobTitle +"'");
			
			rs.beforeFirst();
			while(rs.next()) {
				accountList.add((EmployeeAccount)bytesToObject(rs.getBytes("object")));
			}
			
			return accountList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<EmployeeAccount> getPendingOfResignation(String jobTitle) {
		ArrayList<EmployeeAccount> accountList = new ArrayList<>();
		try {
			rs = st.executeQuery(
					"SELECT employee_accounts.* FROM pending_to_resign "
					+ "JOIN employee_accounts ON employee_accounts.id = pending_to_resign.employee_id "
					+ "JOIN jobs ON jobs.id = pending_to_resign.job_id "
					+ "WHERE jobs.job_title  = '" +jobTitle +"'");
			
			rs.beforeFirst();
			while(rs.next()) {
				accountList.add((EmployeeAccount)bytesToObject(rs.getBytes("object")));
			}
			
			return accountList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void insertIntoPending(String employeeEmail, String jobTitle) {
		try {
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO pending_to_jobs (employee_id, job_id) "
					+ "SELECT acc.id, work.id FROM "
					+ "( SELECT id FROM employee_accounts WHERE email = '" +employeeEmail +"' "
					+ ") AS acc "
					+ "CROSS JOIN "
					+ "( SELECT id FROM jobs WHERE job_title = '" +jobTitle +"' "
					+ ") AS work");
			
			ps.executeUpdate();
			System.out.println("Successful");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void insertIntoPendingResign(String employeeEmail, String jobTitle) {
		try {
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO pending_to_resign (employee_id, job_id) "
					+ "SELECT acc.id, work.id FROM "
					+ "( SELECT id FROM employee_accounts WHERE email = '" +employeeEmail +"' "
					+ ") AS acc "
					+ "CROSS JOIN "
					+ "( SELECT id FROM jobs WHERE job_title = '" +jobTitle +"' "
					+ ") AS work");
			
			ps.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void insertIntoAccepted(String employeeEmail, String jobTitle) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(
					"INSERT INTO accepted_to_jobs (employee_id, job_id) "
					+ "SELECT acc.id, work.id FROM "
					+ "( SELECT id FROM employee_accounts WHERE email = '" +employeeEmail +"' "
					+ ") AS acc "
					+ "CROSS JOIN "
					+ "( SELECT id FROM jobs WHERE job_title = '" +jobTitle +"' "
					+ ") AS work");
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertIntoAcceptedResign(String employeeEmail, String jobTitle) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(
					"INSERT INTO accepted_to_resign (employee_id, job_id) "
					+ "SELECT acc.id, work.id FROM "
					+ "( SELECT id FROM employee_accounts WHERE email = '" +employeeEmail +"' "
					+ ") AS acc "
					+ "CROSS JOIN "
					+ "( SELECT id FROM jobs WHERE job_title = '" +jobTitle +"' "
					+ ") AS work");
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertIntoPast(String employeeEmail, String jobTitle) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(
					"INSERT INTO past_to_jobs (employee_id, job_id) "
					+ "SELECT acc.id, work.id FROM "
					+ "( SELECT id FROM employee_accounts WHERE email = '" +employeeEmail +"' "
					+ ") AS acc "
					+ "CROSS JOIN "
					+ "( SELECT id FROM jobs WHERE job_title = '" +jobTitle +"' "
					+ ") AS work");
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void deleteFromPending(String employeeEmail, String jobTitle) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(
					"DELETE p.* FROM pending_to_jobs AS p "
					+ "JOIN employee_accounts ON employee_accounts.id = p.employee_id "
					+ "JOIN jobs ON p.job_id = jobs.id "
					+ "WHERE employee_accounts.email = '" +employeeEmail +"' "
					+ "AND jobs.job_title = '" +jobTitle +"'");
			ps.executeUpdate();
			System.out.println(ps);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Successful");
	}
	
	public void deleteFromAccepted(String employeeEmail, String jobTitle) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(
					"DELETE a.* FROM accepted_to_jobs AS a "
					+ "JOIN employee_accounts ON employee_accounts.id = a.employee_id "
					+ "JOIN jobs ON a.job_id = jobs.id "
					+ "WHERE employee_accounts.email = '" +employeeEmail +"' "
					+ "AND jobs.job_title = '" +jobTitle +"'");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteFromPendingResign(String employeeEmail, String jobTitle) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(
					"DELETE p.* FROM pending_to_resign AS p "
					+ "JOIN employee_accounts ON employee_accounts.id = p.employee_id "
					+ "JOIN jobs ON p.job_id = jobs.id "
					+ "WHERE employee_accounts.email = '" +employeeEmail +"' "
					+ "AND jobs.job_title = '" +jobTitle +"'");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void transferFromPendingToAccepted(String employeeEmail, String jobTitle) {
		deleteFromPending(employeeEmail, jobTitle);
		insertIntoAccepted(employeeEmail, jobTitle);
	}
	
	@Override
	public void transferFromAcceptedToPendingResign(String employeeEmail, String jobTitle) {
//		deleteFromAccepted(employeeEmail, jobTitle);
		insertIntoPendingResign(employeeEmail, jobTitle);
	}

	public void acceptResignation(String employeeEmail, String jobTitle) {
		deleteFromPendingResign(employeeEmail, jobTitle);
		deleteFromAccepted(employeeEmail, jobTitle);
		insertIntoAcceptedResign(employeeEmail, jobTitle);
//		transferFromAcceptedToPendingResign(employeeEmail, jobTitle);
	}
	
	public void transferFromPendingResignToAccepted(String employeeEmail, String jobTitle) {
		deleteFromPendingResign(employeeEmail, jobTitle);
		insertIntoAcceptedResign(employeeEmail, jobTitle);
	}
	
	@Override
	public EmployerAccount getEmployerOfJob(String jobTitle) {
		try {
			rs = st.executeQuery(
					"SELECT * FROM jobs WHERE job_title = '" +jobTitle +"'");
			
			if (!rs.last()) {
				return null;
			}
		
			rs.beforeFirst();
			rs.next();
			
			return (EmployerAccount)new AccountDAO_Sql().getAccount(rs.getString("owner_email"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ArrayList<WorkEvent> getSearchJob(String search) {
		
		System.out.println("In search");
		ArrayList<WorkEvent> workList = new ArrayList<>();
		try {
			
			//Search for the particular word and return th message with the match found
			rs = st.executeQuery(
					"SELECT * FROM jobs WHERE job_title LIKE '%" + search + "%'");
			
			rs.beforeFirst();
			
			while (rs.next()) {
				workList.add((WorkEvent)bytesToObject(rs.getBytes("object")));
			}
			
			return workList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
