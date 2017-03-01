package controller;

import application.MyJobLeh;
import application.Session;

/**
 * Controller class, all controllers extend from this class
 * 
 * Extending this class allows the children controllers to automatically link
 * itself to the main app.
 * 
 * @author AJK
 * @version a.2
 * @since a.1
 */
public abstract class Controller {
	protected MyJobLeh m;
	protected Session s;

	{
		m = MyJobLeh.getInstance();	
		s = m.getSession();
	}
	
	/**
	 * Class for handling the enter key press, for some quality of life improvements
	 */
	public void handleEnter() {}
}
