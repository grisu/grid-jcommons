package grisu.jcommons.constants;

/**
 * A few enums used from the Grisu client library.
 * 
 * @author Markus Binsteiner
 * 
 */
public class Enums {

	/**
	 * Enums to specify which method to use to login to a Grisu backend.
	 * 
	 * @author Markus Binsteiner
	 * 
	 */
	public enum LoginType {
		/**
		 * Shibboleth login.
		 * 
		 * Shibboleth login consists of getting an auth-token from your IdP,
		 * creating a SLCS certificate from it, uploading that to MyProxy and
		 * then using the resulting MyProxy username and password to log into
		 * the Grisu backend.
		 */
		SHIBBOLETH("Institutions login"), /**
		 * MyProxy login.
		 * 
		 * Use this login if you created a MyProxy credential through other
		 * means. Only the MyProxy username and password need to be forwarded to
		 * the Grisu backend in order to login.
		 */
		MYPROXY("MyProxy login"), /**
		 * Login via a proxy that already exists on
		 * the local machine where the Grisu frontend is executed from.
		 * 
		 * This login method uploads a MyProxy credential from the local proxy
		 * and forwards the resulting MyProxy username and password to the Grisu
		 * backend in order to login.
		 */
		LOCAL_PROXY("Local proxy login"), /**
		 * X509 certificate login.
		 * 
		 * This login method unlocks the users X509 certificate (usually in
		 * $HOME/.globus/usercert.pem), uploads a MyProxy credential from it and
		 * uses the resulting MyProxy username and password to login to the
		 * Grisu backend.
		 */
		X509_CERTIFICATE("Certificate login");

		/**
		 * Translates a String into a {@link LoginType} enum.
		 * 
		 * @param string
		 *            the name of the enum.
		 * 
		 * @return the enum
		 */
		public static LoginType fromString(String string) {
			return getEnumFromString(LoginType.class, string);

		}

		private final String prettyName;

		private LoginType(String prettyName) {
			this.prettyName = prettyName;
		}

		/**
		 * Gets the displayable name of the login method.
		 * 
		 * @return the name of the login method
		 */
		public String getPrettyName() {
			return prettyName;
		}
	}

	/**
	 * Enums to determine the type of UI that is used to login.
	 * 
	 * @author Markus Binsteiner
	 * 
	 */
	public enum UI {
		/**
		 * Commandline login.
		 */
		COMMANDLINE, /**
		 * Swing based login.
		 */
		SWING
	}

	/**
	 * A common method for all enums since they can't have another base class
	 * 
	 * @param <T>
	 *            Enum type
	 * @param c
	 *            enum type. All enums must be all caps.
	 * @param string
	 *            case insensitive
	 * @return corresponding enum, or null
	 */
	public static <T extends Enum<T>> T getEnumFromString(Class<T> c,
			String string) {
		if ((c != null) && (string != null)) {
			try {
				return Enum.valueOf(c, string.trim().toUpperCase());
			} catch (IllegalArgumentException ex) {
			}
		}
		return null;
	}

}
