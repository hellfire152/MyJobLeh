package dataAccess.sql;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import application.MyJobLeh;
import dataAccess.AccountDAO;
import model.account.EmployeeAccount;
import model.account.UserAccount;

/**
 *  
 * handle AccountDAO
 * @author NigelChen
 * @version b.1
 * @since a.3
 */
public class AccountDAO_Sql extends DatabaseAccessor implements AccountDAO {
	
	/**
	 * Searches the pending OR accepted applicants lists for an {@link EmployeeAccount}'s email
	 * If the email is found, return true, else return false
	 * 
	 * @param e
	 * @param jobTitle
	 * @param searchInPending
	 * @return
	 */
	private boolean searchListsForemail(UserAccount e, String jobTitle, boolean searchInPending) {
		String columnIndex = (searchInPending)? "pending_employees" : "accepted_employees";
		try {
			rs = st.executeQuery(
					"SELECT * FROM jobs WHERE job_title = '" +jobTitle +"'");
			
			ArrayList<String> list = (ArrayList<String>)bytesToObject(rs.getBytes("object"));
			
			//check all the strings in the database's list, return true if found
			for (String str : list) {
				if (str.equals(e.getLoginEmail())) {
					return true;
				}
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * Gets the account with the associated email from the database
	 */
	@Override
	public UserAccount getAccount(String email) {
		try{
			String query = 
						"SELECT * FROM employee_accounts WHERE email = '" + email + "'"
						+ " UNION "
						+ "SELECT * FROM employer_accounts WHERE email = '" + email + "'";
			rs = st.executeQuery(query);
			rs.beforeFirst();
			
			if (!rs.last()) {
				return null;
			}
			
			rs.beforeFirst();
			
			rs.next();
			UserAccount acc = (UserAccount)bytesToObject(rs.getBytes("object"));

			return acc;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	public boolean createAccount(String email, String password, UserAccount accountObject) {

		if (getAccount(email) == null) {
			try {
		        
		        PreparedStatement ps;
		        if (s.isEmployeeAccount()) { //insert into employee account table
		        	ps = con.prepareStatement(
		        			"INSERT INTO employee_accounts (email , passwordHash, object) VALUES ( ? , ? , ? )");
		        }
		        else { //insert into employer account table
		        	ps = con.prepareStatement(
		        			"INSERT INTO employer_accounts (email , passwordHash, object) VALUES ( ? , ? , ? )");
		        }
		        
		        ps.setString(1, email);
		        ps.setString(2, model.Security.hashSHA1(password));
		        ps.setObject(3, accountObject);
		    
		        ps.executeUpdate();
		        return true;
		        
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Account already exists");
			return false;
		}
		return false;
	}
	
	@Override
	public void updateAccount(String email, UserAccount accountObject) {

		try {
	        PreparedStatement ps;
	        if(s.isEmployeeAccount()){
	        	ps = con.prepareStatement(
	        			"UPDATE employee_accounts SET object = ? WHERE email = " + "'" + email + "'");
	        }
	        else {
	        	ps = con.prepareStatement(
	        			"UPDATE employer_accounts SET object = ? WHERE email = " + "'" + email + "'");
	        }
	        
	        ps.setObject(1, accountObject);
			ps.executeUpdate();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void deleteAccount(String email) {
		try {
			String query = 
						"DELETE FROM WHERE id IN ("
						+ "SELECT id FROM employee_accounts WHERE email = '" + email + "'"
						+ " UNION "
						+ "SELECT id FROM employer_accounts WHERE email = '" + email + "')";
		
			st.executeUpdate(query);
			
			System.out.println("Successful");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean verifyAccount(String email, String password) {
		password = model.Security.hashSHA1(password);
		
		String query;
		if (s.isEmployeeAccount()) {
			query = "SELECT * from employee_accounts WHERE email = '"+ email +"'";
		} else {
			query = "SELECT * from employer_accounts WHERE email = '"+ email +"'";
		}
		
		try{
			rs = st.executeQuery(query);
			
			//Check how many data entry with the username.
			if(!rs.last()){
				return false;
			}
			rs.beforeFirst(); //Reset database counter to 0 so that it is able to start from beginning
			System.out.println("Record from database");
			while(rs.next()){
				
				String passwordRetrieve = rs.getString("passwordHash");
				
				if(!passwordRetrieve.equals(password)){ //Check if password matches the email account.
					break;
				}
				return true;
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return false;
	}
	public BufferedImage getProfilePic(String email) {
		try {
			String query = (s.isEmployeeAccount())? "SELECT * FROM employee_accounts WHERE email = '" +email +"'"
					: "SELECT * FROM employer_accounts WHERE email = '" +email +"'";
			rs = st.executeQuery(query);
			
			if (!rs.last()) {
				return null;
			}
			rs.beforeFirst();
			rs.next();
			
			byte[] imageAsBytes = rs.getBytes("profile_pic");
			if (imageAsBytes == null)
				return null;
			
			return ImageIO.read(new ByteArrayInputStream(imageAsBytes));
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setProfilePic(String email, BufferedImage img) {
		try {
			//turning the BufferedImage to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			ImageIO.write(img, "png", bos);
			bos.close();
			
			//inserting into database
			PreparedStatement ps;
			if (s.isEmployeeAccount()) {
				ps = con.prepareStatement(
						"UPDATE employee_accounts SET profile_pic = ? WHERE email = '" +email +"'");
			} else {
				ps = con.prepareStatement(
						"UPDATE employer_accounts SET profile_pic = ? WHERE email = '" +email +"'");
			}
			
			ps.setObject(1, bos.toByteArray());
			ps.executeUpdate();
			
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}
	
	@Override
	public void updatePassword(String email, String hashPassword) {
		String query = "UPDATE employee_accounts SET passwordHash = '" + hashPassword + "' WHERE email = '"+ email +"'";
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Successful");
	}
	@Override
	public boolean checkEmail(String email) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM employee_accounts WHERE email = '" + email + "'";
		try {
			rs = st.executeQuery(query);
			if(!rs.last()){
				System.out.println("FALSE");
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Successful");
		return true;
	}
	

}
