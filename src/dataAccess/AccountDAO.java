package dataAccess;

import model.account.EmployeeAccount;
import model.account.UserAccount;

/**
 * DAO for accounts!
 * 
 * Account entries in databases are supposed to have these fields:
 * email, passwordHash, and object (UserAccount .ser file)
 * 
 * As such, this DAO defines methods for creating, getting, updating, and deleting accounts.
 * There's also a method for verification of passwords in there.
 *
 * @author NigelChen
 * @version b.1
 * @since a.3
 */
public interface AccountDAO {
	/**
	 * Get account from database
	 * @param email
	 * @return
	 */
	public UserAccount getAccount(String email);
	
	public boolean verifyAccount(String email, String password);
	/**
	 * Add new account to database
	 * @param email
	 * @param password
	 */
	public boolean createAccount(String email, String password, UserAccount accountObject);
	/**
	 * Update account to database
	 * @param email
	 */
	public void updateAccount(String email, UserAccount accountObject);
	/**
	 * Delete the account from database
	 * Not working.
	 * @param email
	 */
	public void deleteAccount(String email);
	
	public void updatePassword(String email, String newPassword);
	public boolean checkEmail(String email);
}
