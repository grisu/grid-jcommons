package au.org.arcs.jcommons.constants;

public class Enums {

	public enum LoginType {
		SHIBBOLETH("Institutions login"),
		MYPROXY("MyProxy login"),
		LOCAL_PROXY("Local proxy login"),
		X509_CERTIFICATE("Certificate login");

		public static LoginType fromString(String string) {
			return getEnumFromString(LoginType.class, string);

		}

		private final String prettyName;

		private LoginType(String prettyName) {
			this.prettyName = prettyName;
		}

		public String getPrettyName() {
			return prettyName;
		}
	}



	public enum UI {
		COMMANDLINE,
		SWING
	}

	/**
	 * A common method for all enums since they can't have another base class
	 * @param <T> Enum type
	 * @param c enum type. All enums must be all caps.
	 * @param string case insensitive
	 * @return corresponding enum, or null
	 */
	public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string)
	{
		if( (c != null) && (string != null) )
		{
			try
			{
				return Enum.valueOf(c, string.trim().toUpperCase());
			}
			catch(IllegalArgumentException ex)
			{
			}
		}
		return null;
	}


}
