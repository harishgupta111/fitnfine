package com.fitnfine.exception;

public class FFSystemException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 477368487543575373L;

	public FFSystemException() {
	}

	public FFSystemException(String message) {
		super(message);
	}

	public FFSystemException(Throwable cause) {
		super(cause);
	}

	public FFSystemException(String message, Throwable cause) {
		super(message, cause);
	}



}
