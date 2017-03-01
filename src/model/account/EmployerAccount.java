package model.account;

import java.util.ArrayList;

/**
 * Object for employer account
 * @author NigelChen
 * @version a.3
 * @since a.3
 */
public class EmployerAccount extends UserAccount{
	private String companyName, address,about;
	
	public EmployerAccount(String loginEmail, String name) {
		super(loginEmail, name);
		// TODO Auto-generated constructor stub
	}


	/*
	 * GETTERS AND SETTERS
	 */
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getAbout() {
		return about;
	}


	public void setAbout(String about) {
		this.about = about;
	}



}
