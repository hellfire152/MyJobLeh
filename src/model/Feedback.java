package model;

public class Feedback {
	private String email;
	private String subject;
	private String feedback;
	public Feedback(String email, String subject, String feedback) {
		super();
		this.email = email;
		this.subject = subject;
		this.feedback = feedback;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
}
