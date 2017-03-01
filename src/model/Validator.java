package model;

import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.extraWindows.ErrorPopup;

/**
 * Class containing all the method for validation.
 * validateRegistration and validateFeedback are static to make things slightly easier.
 * 
 * @author Ning, AJK
 * @version b.2
 * @since a.3.1
 */
public class Validator {

	public boolean validateEmail(String email) {
		Pattern emailPattern= Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
		Matcher emailMatch= emailPattern.matcher(email);
		if(emailMatch.find()&&emailMatch.group().equals(email)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean validateContactNumber(String contactNumber){
		Pattern numberPattern= Pattern.compile("(65)?[6-9][0-9]{7}");
		Matcher numberMatch= numberPattern.matcher(contactNumber);
		if(numberMatch.find()&&numberMatch.group().equals(contactNumber)){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public boolean validateConfirmPassword(String password, String confirmPassword){
		if (password.equals(confirmPassword)){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public boolean validatePassword(String password){
		Pattern passwordPattern= Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^&*@#$%!]).{6,15})");
		Matcher passwordMatch= passwordPattern.matcher(password);
		if(passwordMatch.find()&&passwordMatch.group().equals(password)){
			return true;
		}
		else{
			return false;
		}
		
	}
	public static boolean validateFeedback(String email,String subject,String feedback) {
		Validator v = new Validator();
		boolean pass=false; 
		if (!email.equals("") && !subject.equals("") && !feedback.equals("")){
			if(v.validateEmail(email)== true){
				pass=true;
			}
			else{
				System.out.print("Please enter a valid email");
				pass=false;
			}
		}
		else{
			System.out.print("Please fill in all the details");
			pass=false;
		}
		return pass;
	}
	public boolean validateMinimumAge(String MinimumAge){
		int MinAge= Integer.parseInt(MinimumAge);
		if (MinAge>25){
			return false;
		}
		else if (MinAge <15){
			return false;
		}
		else{
			return true;
		}
		
	}
	public boolean validateEndDate(GregorianCalendar startdateg , GregorianCalendar enddateg){
		
		
		if (startdateg.after(enddateg)) { 
			return false;
		} else {
			return true;
		}
		
	}
	public static boolean validateEmployerRegistration(String name,String email,String password,String confirmPassword,String contactNumber,String address,String about,String genderMF) {
		Validator v = new Validator();
		boolean pass=false; 
        if (!name.equals("") && !email.equals("") && !password.equals("") &&!address.equals("")&& !confirmPassword.equals("")&& !contactNumber.equals("") && !about.equals("") &&!genderMF.equals("")){
 	       if (v.validateEmail(email)){ 
 				if (v.validatePassword(password)){
 					if(password.equals(confirmPassword)){
 						if (v.validateContactNumber(contactNumber)==true){
 							pass=true;
 						}
 						else{
 							ErrorPopup.display("Please fill in contact number");
 		                	pass=false;
 						}
 					}
 					else{
 						ErrorPopup.display("Password don't match");
 		            	pass=false;
 					}
 				}
 				else {
 					ErrorPopup.display("Your password should be 6-15 digits and contain atleast a number,symbol,uppercase,lowercase letter ");
 		        	pass=false;
 				}
 			}
 			else{
 				ErrorPopup.display("Please enter a valid email");
 		    	pass=false;
 			} 
        }
        else {
           ErrorPopup.display("Please fill in all the details");
        	pass=false;
        }

        return pass;
	}
	
	public static boolean validateEmployeeRegistration(String name,String email,String password,String confirmPassword,String formattedDate,String genderMF) {
		Validator v = new Validator();
		boolean pass=false; 
        if (!name.equals("") && !email.equals("") && !password.equals("") && !confirmPassword.equals("")&& !formattedDate.equals("") && !genderMF.equals("")){
      
 	       if (v.validateEmail(email)== true){ 
 			
 				if (v.validatePassword(password)==true){
 					
 					if(password.equals(confirmPassword)){
 						pass=true;
 					}
 					else{
 						ErrorPopup.display("Password don't match");
 		            	pass=false;
 					}
 				}
 				else{
 					ErrorPopup.display("Your password should be 6-15 digits and contain atleast a number,symbol,uppercase,lowercase letter ");
 		        	pass=false;
 				}
 			}
 			else{
 				ErrorPopup.display("Please enter a valid email");
 		    	pass=false;
 			} 
 	    
        }
        else {
        	ErrorPopup.display("Please fill in all the details");
        	pass=false;
        }

        return pass;
		}
	public static boolean CreateJobVal(String jobTitle, String address, String position, String minimumAge,
			String jobScope, String skillsObtained, String skillsRequired, String startDate, String endDate,
			GregorianCalendar startdateg, GregorianCalendar enddateg,String weekendRate,String weekdayRate){
			Validator b = new Validator();
			boolean pass = false;
		 if (!jobTitle.equals("") && !address.equals("") && !position.equals("") && !minimumAge.equals("")&& !jobScope.equals("") && !skillsObtained.equals("")&& !skillsRequired.equals("") && !startDate.equals("") && !endDate.equals("")&&!weekendRate.equals("")&&!weekdayRate.equals("")){
		      
	 	       if (b.validateMinimumAge(minimumAge)== true){ 
	 			
	 				if (b.validateEndDate(startdateg, enddateg)==true){	 					
	 					pass=true;
	 				}
	 				else{
	 					ErrorPopup.display("End date must be later than start date ");
	 		        	pass=false;
	 				}
	 			}
	 			else{
	 				ErrorPopup.display("Please enter an age from 15 to 25");
	 		    	pass=false;
	 			} 
	 	    
	        }
	        else {
	        	ErrorPopup.display("Please fill in all the details");
	        	pass=false;
	        }

	        return pass;
		}

}


