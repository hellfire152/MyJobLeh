package model;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.activation.*;
/**
 * There will only be one address send to recipient.
 * Connect program to the gmail database.
 * email: myJobLeh@gmail.com
 * password: myJobLeh2017
 * @author NigelChen
 * @version a.3
 * @since a.3
 *
 */
public class EmailHandler {
	/**
	 * Create a proper email format for the reciever to view.
	 * School WIFI deny access to this. Use personal hotspot.
	 * 
	 * @param employerName - Employer name
	 * @param employeeName - Employee name
	 * @param employeeEmail - Employer Email
	 * @param subject - Subject of the email
	 * @param message - Message that the user want to send
	 * @return - a formated email.
	 */
	
	public static String emailOTP(String otp){
		
		String finalMessage = "Dear user, \n\nYour One Time Password is ["+otp+"]\nThis password will expire after three failed tries\n\nRegards,\nMyJobLeh";
		return finalMessage;
	}
	public static String appliedJobToEmployerFormat(String employerName, String employeeName, String employeeEmail, String message){
		
		String finalMessage = "Dear " + employerName +",\n\n\tWe are writing on behalf of a myJobLeh user " + employeeName + " (" + employeeEmail + ").\n\n\t" + message + "\n\nYours sincerely,\n" + employeeName + "\n(myJobLeh user)";
		return finalMessage;
	}
	
	public static String exEmployeeToEmployerFormat(String employerName, String employeeName, String employeeEmail, String message){
		
		String finalMessage = "Dear " + employerName +",\n\n\tWe are writing on behalf of your previous employee " + employeeName + " (" + employeeEmail + ").\n\n\t" + message + "\n\nYours sincerely,\n" + employeeName + "\n(myJobLeh user)";
		return finalMessage;
	}
	
	
	public static boolean sendMail(String subject, String message, String to[]){
		String from = "myjobleh@gmail.com";
		String password = "myJobLeh2017"; 
		String host = "smtp.gmail.com";
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
		Session session = Session.getDefaultInstance(props, new GMailAuthenticator(from, password));
		
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(new InternetAddress(from));
			//Now get the address of the recipient
			InternetAddress[] toAddress = new InternetAddress[to.length];
			for(int i=0; i<to.length; i++){
				toAddress[i] = new InternetAddress(to[i]);
				
			}
			//Now add all the to Address elements to mimeMessage
			for(int i=0 ; i<toAddress.length; i++){
				mimeMessage.addRecipient(RecipientType.TO, toAddress[i]);
				
			}
			//Add subject
			mimeMessage.setSubject(subject);
			//Set message 
			mimeMessage.setText(message);
			Transport transport = session.getTransport("smtps");
			transport.connect(host, from, password);
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			transport.close();
			return true;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void main(String[] args) {
//		System.out.println(appliedJobToEmployerFormat("Boss" , "Nigel" , "nigel_ncch@hotmail.com", "I am bryan"));
	}
}
