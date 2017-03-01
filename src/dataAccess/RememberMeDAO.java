package dataAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import model.Security;

/**
 * Store user info into temp file when remember me is checked.
 * @author NigelChen
 *
 */
public class RememberMeDAO {
	/**
	 * Clear the temp file if remember me is unchecked.
	 */
	public void clearFile(){
		File file = new File("C:/temp/rmbMe.txt");
		
		try (PrintWriter writer=new PrintWriter(file))
		{
			writer.print("No user data stored");
			
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Sucessful");
	}

	/**
	 * Store new temp remember me info intoo txt file
	 * @param check
	 * @param email
	 * @param password
	 */
	public void storeToFile(boolean check, String email, String password){
		File file = new File("C:/temp/rmbMe.txt");
		
		String output = check + " " + email + " " + password;
		String[] values = {Boolean.toString(check), email, password};
		
		try (PrintWriter writer=new PrintWriter(file))
		{
			writer.print(Security.encryptCaesar(output));
			
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Sucessful");
	}
	
	/**
	 * Get the temp file and put it in an array for login
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public String[] getFile() throws ArrayIndexOutOfBoundsException{
		
		int index = 0;
		String[] input= new String[3];
		File file = new File("C:/temp/rmbMe.txt");
		File folder = new File("C:","temp");
		folder.mkdir();
		
		try (Scanner in=new Scanner(file))
		{
		while (in.hasNext()) {
			input[index++] = Security.decryptCaesar(in.next());
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return input;
		
	}
	
}
