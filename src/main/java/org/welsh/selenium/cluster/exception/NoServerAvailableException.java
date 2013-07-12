package org.welsh.selenium.cluster.exception;

/**
 * Exception to be thrown when No Selenium Hub is deemed to be available
 * 
 * @author dwelsh
 *
 */
public class NoServerAvailableException extends Exception {

	private static final long serialVersionUID = 4290322716722717370L;

	/**
	 * 
	 */
	public NoServerAvailableException() {
	}
	
	/**
	 * 
	 * @param arg0
	 */
	public NoServerAvailableException(String arg0) {
		super(arg0);
	}

	/**
	 * 
	 * @param arg0
	 */
	public NoServerAvailableException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * 
	 * @param arg0
	 * @param arg1
	 */
	public NoServerAvailableException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
