package dataAccess.sql;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import controller.Controller;

/**
 * An abstract class that defines various fields required for accessing the sql database
 * It's a subclass of controller, so subclasses have a reference to the main app and Session as well.
 * 
 * @author AJK
 * @version b.1
 * @since b.1
 */
public abstract class DatabaseAccessor extends Controller {
	protected Connection con;
	protected Statement st;
	protected ResultSet rs;
	
	{
		try{
			Class.forName("com.mysql.jdbc.Driver"); // Get driver
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myjobleh","root",""); //Connect eclipse IDE to database via URL
			st = con.createStatement();
			
		}
		catch(Exception e){
			System.out.println("Error :" + e);
		}

	}
	
	protected Object bytesToObject(byte[] b) {
		ObjectInputStream oos;
		try {
			oos = new ObjectInputStream(
					new ByteArrayInputStream(b));
			return oos.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
