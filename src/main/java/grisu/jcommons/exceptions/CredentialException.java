package grisu.jcommons.exceptions;

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
