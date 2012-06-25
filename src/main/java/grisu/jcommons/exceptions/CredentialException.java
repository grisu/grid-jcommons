package grisu.jcommons.exceptions;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "grisu.control.jaxws.exceptions.CredentialException")
public class CredentialException extends RuntimeException {

	public CredentialException() {
	}

	public CredentialException(String s) {
		super(s);
	}

	public CredentialException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public CredentialException(Throwable throwable) {
		super(throwable);
	}

}
