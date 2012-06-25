package grisu.jcommons.exceptions;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "grisu.control.jaxws.exceptions.KeyException")
public class KeyException extends RuntimeException {

	public KeyException() {
	}

	public KeyException(String message) {
		super(message);
	}

	public KeyException(String message, Throwable cause) {
		super(message, cause);
	}

	public KeyException(Throwable cause) {
		super(cause);
	}

}
