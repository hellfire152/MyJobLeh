package application;

import model.account.UserAccount;

public class Session {
	private static MyJobLeh m;
	private UserAccount acc;
	private boolean employeeAccount;
	
	/* GETTERS AND SETTERS
	 * 
	 * All - GS
	 */
	public UserAccount getAcc() {
		return acc;
	}
	public void setAcc(UserAccount acc) {
		this.acc = acc;
	}
	public Session(MyJobLeh m) {
		this.m = m;
	}
	public void setEmployeeAccount(boolean b) {
		employeeAccount = b;
	}
	public boolean isEmployeeAccount() {
		return employeeAccount;
	}
}
