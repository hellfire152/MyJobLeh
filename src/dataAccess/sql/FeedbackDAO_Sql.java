package dataAccess.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import dataAccess.FeedbackDAO;
import model.Feedback;

/**
 * Handle FeedbackDAO
 * @author Ning
 * @version b.2
 * @since a.3
 */
public class FeedbackDAO_Sql extends DatabaseAccessor implements FeedbackDAO{ 
	public boolean createFeedback (String employeeEmail,String subject, String feedback){
		try {
	        PreparedStatement ps = con.prepareStatement(
	        		"INSERT INTO employer_feedback (employee_email , subject, feedback) VALUES ( ? , ? , ?  )");

			ps.setString(1, employeeEmail);
			ps.setString(2, subject);
			ps.setString(3, feedback);
			ps.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public  ArrayList<Feedback> getFeedback(String employeeEmail){
		
		ArrayList<Feedback> feedbackList = new ArrayList<Feedback>();
		try{
			rs=st.executeQuery(
					"SELECT * FROM employer_feedback WHERE employee_email ='" + employeeEmail+"'");
			if(!rs.last()){
				return null;
			}
			rs.beforeFirst();
			while(rs.next()){
				Feedback fb = new Feedback(rs.getString("employee_email"), rs.getString("subject"), rs.getString("feedback"));
			
				feedbackList.add(fb);
			}
			return feedbackList;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public void deleteFeedback(String employeeEmail){
		try{
			PreparedStatement ps=con.prepareStatement(
					"DELETE FROM employer_feedback WHERE employee_email = '"+ employeeEmail +"'");
			ps.executeUpdate();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}