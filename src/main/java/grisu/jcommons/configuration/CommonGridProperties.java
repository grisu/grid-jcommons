package grisu.jcommons.configuration;

import grisu.jcommons.constants.GridEnvironment;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Class to manage a set of properties that are commonly used when doing
 * grid-related stuff.
 */
public class CommonGridProperties {

    private static final Logger myLogger = LoggerFactory.getLogger(CommonGridProperties.class);

	/**
	 * Property Enums
	 */
	public enum Property {

		/** The last used shibboleth username. */
		SHIB_USERNAME, /** The last used shibboleth idp. */
		SHIB_IDP, /** The last used MyProxy username. */
		SLCS_RESPONSE, /**
		 * The response string of a slcs server to be used to
		 * create a SLCS cert
		 */
		MYPROXY_USERNAME, /** The last used MyProxy host. */
		MYPROXY_HOST, /** The last used MyProxy port. */
		MYPROXY_PORT, /** The last used http proxy host. */
		HTTP_PROXY_HOST, /** The last used http proxy port. */
		HTTP_PROXY_PORT, /** The last used http proxy username. */
		HTTP_PROXY_USERNAME, /** Whether to disable auto-dependency management. */
		DISABLE_DEPENDENCY_MANAGEMENT, /**
		 * Whether to automatically print out
		 * uncaught exceptions.
		 */
		DEBUG_UNCAUGHT_EXCEPTIONS, /**
		 * The password used to create a credential.
		 */
		PASSWORD, /**
		 * The grid info config to use by default. Either "testbed"
		 * (default), "nesi", or the path to a config file.
		 */
		GRID_INFO_CONFIG, /**
		 * Whether to use the grid-session service when
		 * logging into the grid.
		 */
		USE_GRID_SESSION,
		/**
		 * Whether to daemonize the grid-session service or run in the same
		 * process.
		 */
		DAEMONIZE_GRID_SESSION, /**
		 * The location of an ssh key that can be used
		 * to ssh into certain (non-gsi) machines.
		 */
		GRID_SSH_KEY, /**
         * the location of the local cache directory
         */
        GRID_CACHE_DIR

	}

	/** The singleton grid properties object. */
	private static CommonGridProperties singleton = null;

	/**
	 * The location of the grid config file (default:
	 * $HOME/.grid/grid.properties)
	 */
	private static final File GRID_PROPERTIES_FILE = calculateGridPropertiesFile();

	public static final String KEY_NAME = "grid_rsa";
	public static final String CERT_EXTENSION = ".pub";

	public final static String SSH_DIR = getSSHDirectory();

	public static final String GRID_KEY_PATH = SSH_DIR + File.separator
			+ KEY_NAME;

	public static final String DEFAULT_KEY_PATH = SSH_DIR + File.separator
			+ "id_rsa";

	private static File calculateGridPropertiesFile() {

		File temp = new File(GridEnvironment.getGridConfigDirectory()
				+ File.separator + "grid.properties");

		// if (!temp.exists()) {
		//
		// // check whether maybe a host-wide config dir exists
		// File temp2 = new File("/etc/grid/grid.properties");
		// if (temp2.exists()) {
		// temp = temp2;
		// }
		// }

		return temp;

	}

	/**
	 * Gets the singleton properties object.
	 *
	 * @return the singleton object
	 */
	public static CommonGridProperties getDefault() {
		if (singleton == null) {
			singleton = new CommonGridProperties();
		}
		return singleton;
	}

	public static String getSSHDirectory() {
		String tmp = System.getProperty("user.home") + File.separator + ".ssh";
		if (!new File(tmp).exists()) {
			new File(tmp).mkdirs();
		}
		return tmp;
	}

	/** The config. */
	private final PropertiesConfiguration config;

	/**
	 * Instantiates a new common grid properties.
	 */
	private CommonGridProperties() {
		try {
			config = new PropertiesConfiguration(GRID_PROPERTIES_FILE);
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean daemonizeGridSession() {

		return getGridPropertyBoolean(Property.DAEMONIZE_GRID_SESSION, false);

	}

    public String getGridInfoConfig() {
        return getGridProperty(Property.GRID_INFO_CONFIG);
    }

    public String getGridCacheDir() {
        return getGridProperty(Property.GRID_CACHE_DIR);
    }

	/**
	 * Gets a certain common grid property.
	 *
	 * @param prop
	 *            the property name
	 * @return the property value
	 */
	public String getGridProperty(Property prop) {

		String result = System.getProperty(prop.toString());

		if (StringUtils.isBlank(result)) {
			result = config.getString(prop.toString());
		}

		return result;
	}

	public boolean getGridPropertyBoolean(Property prop, Boolean defaultValue) {

		String result = System.getProperty(prop.toString());
		if (StringUtils.isNotBlank(result)) {
			return Boolean.parseBoolean(result);
		}

		boolean resultBool = config.getBoolean(prop.toString(), defaultValue);
		return resultBool;
	}

	/**
	 * Gets a certain grid property as an integer.
	 *
	 * @param prop
	 *            the property name
	 * @return the property value as an integer
	 */
	public int getGridPropertyInt(Property prop) {
		int result = config.getInt(prop.toString(), Integer.MIN_VALUE);
		return result;
	}

	public String getGridSSHCert() {
		String key = getGridSSHKey();
		if (StringUtils.isBlank(key)) {
			return null;
		} else {
			return key + CERT_EXTENSION;
		}
	}

	public String getGridSSHKey() {
		String key = getGridProperty(Property.GRID_SSH_KEY);
		if (StringUtils.isBlank(key)) {

			if (new File(GRID_KEY_PATH).exists()
					&& new File(GRID_KEY_PATH + CERT_EXTENSION).exists()) {
				return GRID_KEY_PATH;
			}

			if (new File(DEFAULT_KEY_PATH).exists()
					&& new File(DEFAULT_KEY_PATH + CERT_EXTENSION).exists()) {
				return DEFAULT_KEY_PATH;
			}

			return GRID_KEY_PATH;
		} else {
			return key;
		}
	}

	/**
	 * Gets the last used my proxy username.
	 *
	 * @return the last used my proxy username
	 */
	public String getLastMyProxyUsername() {
		return getGridProperty(Property.MYPROXY_USERNAME);
	}

	/**
	 * Gets the last used shib idp.
	 *
	 * @return the last used shib idp
	 */
	public String getLastShibIdp() {
		String idp = getGridProperty(Property.SHIB_IDP);
		if (idp == null || idp.toLowerCase().startsWith("error")) {
			return null;
		} else {
			return idp;
		}
	}

	/**
	 * Gets the last used shib username.
	 *
	 * @return the last used shib username
	 */
	public String getLastShibUsername() {

		return getGridProperty(Property.SHIB_USERNAME);

	}

	public String getOtherGridProperty(String storeKey) {
		String result = System.getProperty(storeKey);

		if (StringUtils.isBlank(result)) {
			result = config.getString(storeKey);
		}

		return result;
	}

    public void setGridInfoConfig(String c) {
        setGridProperty(Property.GRID_INFO_CONFIG, c);
    }
    public void setGridCacheDir(String c) {
        setGridProperty(Property.GRID_CACHE_DIR, c);
    }

	/**
	 * Sets a certain grid property
	 *
	 * @param prop
	 *            the property key
	 * @param value
	 *            the property value
	 */
	public void setGridProperty(Property prop, String value) {

		if (StringUtils.isBlank(value)) {
			config.clearProperty(prop.toString());
		} else {
			config.setProperty(prop.toString(), value);
		}
		try {
			config.save();
		} catch (ConfigurationException e) {
//			throw new RuntimeException(e);
            myLogger.error("Can't write property {}: {}", new String[]{prop.toString(), e.getLocalizedMessage()}, e);
		}
	}

	/**
	 * Sets the last used my proxy username.
	 *
	 * @param username
	 *            the new last used my proxy username
	 */
	public void setLastMyProxyUsername(String username) {
		setGridProperty(Property.MYPROXY_USERNAME, username);
	}

	/**
	 * Sets the last used shib idp.
	 *
	 * @param idp
	 *            the new last used shib idp
	 */
	public void setLastShibIdp(String idp) {
		if (idp == null || idp.toLowerCase().startsWith("error")) {
			return;
		}
		setGridProperty(Property.SHIB_IDP, idp);
	}

	/**
	 * Sets the last used shib username.
	 *
	 * @param u
	 *            the new last used shib username
	 */
	public void setLastShibUsername(String u) {
		setGridProperty(Property.SHIB_USERNAME, u);
	}

	public void setOtherGridProperty(String key, String value) {
		if (StringUtils.isBlank(value)) {
			config.clearProperty(key);
		} else {
			config.setProperty(key, value);
		}
		try {
			config.save();
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean startGridSessionThreadOrDaemon() {

		return getGridPropertyBoolean(Property.USE_GRID_SESSION, false);

	}

}
