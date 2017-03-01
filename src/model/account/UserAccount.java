package model.account;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class UserAccount implements Serializable {
	protected String name;
	protected String contact;
	protected String loginEmail;

	public UserAccount(String loginEmail, String name) {
		this.loginEmail = loginEmail;
		this.name = name;
	}

	/*
	 * GETTERS AND SETTERS name - GS loginID - G passwordHash - G
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contactList) {
		this.contact = contactList;
	}
	@Override
	public String toString() {
		return getName();
	}
	
}
