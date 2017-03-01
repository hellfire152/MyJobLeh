package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dataAccess.ChatDAO;

public class Chat {
	private ArrayList<String> displayText;
	private String inputMessage;
	private String userName;
	private String jobName;

	ChatDAO chatDAO = new ChatDAO();
	
	public ArrayList<String> getDisplayText() {
		return displayText;
	}
	public void setDisplayText() {
		this.displayText = chatDAO.readFromChatFile(this.jobName);
	}
	public String getInputMessage() {
		return inputMessage;
	}
	public void setInputMessage(String inputMessage) {
		System.out.println(inputMessage);
		String transformMessage = toString(inputMessage);
		this.inputMessage = transformMessage;
		chatDAO.writeToChatFile(this.jobName, this.inputMessage);
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public String toString(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(" (yyyy/MM/dd HH:mm:ss)");
        Date date = new Date();
		return this.userName + dateFormat.format(date) + ": " + message;
	}
}
