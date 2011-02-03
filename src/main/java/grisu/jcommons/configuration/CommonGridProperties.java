package grisu.jcommons.configuration;

import grisu.jcommons.constants.GridEnvironment;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;


public class CommonGridProperties {

	public enum Property {

		SHIB_USERNAME, SHIB_IDP, MYPROXY_USERNAME, MYPROXY_HOST, MYPROXY_PORT, HTTP_PROXY_HOST, HTTP_PROXY_PORT, HTTP_PROXY_USERNAME, DISABLE_DEPENDENCY_MANAGEMENT, DEBUG_UNCAUGHT_EXCEPTIONS

	}

	private static CommonGridProperties singleton = null;

	public static final String GRID_PROPERTIES_FILE = GridEnvironment
	.getGridConfigDirectory() + File.separator + "grid.properties";

	public static CommonGridProperties getDefault() {
		if (singleton == null) {
			singleton = new CommonGridProperties();
		}
		return singleton;
	}

	private final PropertiesConfiguration config;

	private CommonGridProperties() {
		try {
			config = new PropertiesConfiguration(new File(GRID_PROPERTIES_FILE));
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public String getGridProperty(Property prop) {

		String result = config.getString(prop.toString());

		return result;
	}

	public int getGridPropertyInt(Property prop) {
		int result = config.getInt(prop.toString(), Integer.MIN_VALUE);
		return result;
	}

	public String getLastMyProxyUsername() {
		return getGridProperty(Property.MYPROXY_USERNAME);
	}

	public String getLastShibIdp() {
		return getGridProperty(Property.SHIB_IDP);
	}

	public String getLastShibUsername() {

		return getGridProperty(Property.SHIB_USERNAME);

	}

	public void setGridProperty(Property prop, String value) {

		config.setProperty(prop.toString(), value);
		try {
			config.save();
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public void setLastMyProxyUsername(String username) {
		setGridProperty(Property.MYPROXY_USERNAME, username);
	}

	public void setLastShibIdp(String idp) {
		setGridProperty(Property.SHIB_IDP, idp);
	}

	public void setLastShibUsername(String u) {
		setGridProperty(Property.SHIB_USERNAME, u);
	}

}
