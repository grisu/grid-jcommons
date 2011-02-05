package grisu.jcommons.configuration;

import grisu.jcommons.constants.GridEnvironment;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Class to manage a set of properties that are commonly used when doing
 * grid-related stuff.
 */
public class CommonGridProperties {

	/**
	 * Property Enums
	 */
	public enum Property {

		/** The last used shibboleth username. */
		SHIB_USERNAME, /** The last used shibboleth idp. */
		SHIB_IDP, /** The last used MyProxy username. */
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
		DEBUG_UNCAUGHT_EXCEPTIONS

	}

	/** The singleton grid properties object. */
	private static CommonGridProperties singleton = null;

	/**
	 * The location of the grid config file (default:
	 * $HOME/.grid/grid.properties)
	 */
	public static final String GRID_PROPERTIES_FILE = GridEnvironment
	.getGridConfigDirectory() + File.separator + "grid.properties";

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

	/** The config. */
	private final PropertiesConfiguration config;

	/**
	 * Instantiates a new common grid properties.
	 */
	private CommonGridProperties() {
		try {
			config = new PropertiesConfiguration(new File(GRID_PROPERTIES_FILE));
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets a certain common grid property.
	 * 
	 * @param prop
	 *            the property name
	 * @return the property value
	 */
	public String getGridProperty(Property prop) {

		String result = config.getString(prop.toString());

		return result;
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
		return getGridProperty(Property.SHIB_IDP);
	}

	/**
	 * Gets the last used shib username.
	 * 
	 * @return the last used shib username
	 */
	public String getLastShibUsername() {

		return getGridProperty(Property.SHIB_USERNAME);

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

		config.setProperty(prop.toString(), value);
		try {
			config.save();
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
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

}
