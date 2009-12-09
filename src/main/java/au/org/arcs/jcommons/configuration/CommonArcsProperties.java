package au.org.arcs.jcommons.configuration;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import au.org.arcs.jcommons.constants.ArcsEnvironment;

public class CommonArcsProperties {
	
	private static CommonArcsProperties singleton = null;
	
	public static CommonArcsProperties getDefault() {
		if ( singleton == null ) {
			singleton = new CommonArcsProperties();
		}
		return singleton;
	}
	
	public enum Property {
		
		SHIB_USERNAME,
		SHIB_IDP,
		MYPROXY_USERNAME,
		MYPROXY_HOST,
		MYPROXY_PORT,
		HTTP_PROXY_HOST,
		HTTP_PROXY_PORT,
		HTTP_PROXY_USERNAME,
		DISABLE_DEPENDENCY_MANAGEMENT
		
	}
	
	public static final String ARCS_PROPERTIES_FILE = ArcsEnvironment.ARCS_DEFAULT_DIRECTORY + File.separator + "arcs.properties";
	
	private final PropertiesConfiguration config;

	public CommonArcsProperties() {
		try {
			config = new PropertiesConfiguration(new File(ARCS_PROPERTIES_FILE));
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setArcsProperty(Property prop, String value) {
		
		config.setProperty(prop.toString(), value);
		try {
			config.save();
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getArcsProperty(Property prop) {
		
		String result = config.getString(prop.toString());
		
		return result;
	}
	
	public String getLastShibUsername() {

		return getArcsProperty(Property.SHIB_USERNAME);
		
	}
	
	public String getLastShibIdp() {
		return getArcsProperty(Property.SHIB_IDP);
	}
	
	public void setLastShibUsername(String u) {
		setArcsProperty(Property.SHIB_USERNAME, u);
	}
	
	public void setLastShibIdp(String idp) {
		setArcsProperty(Property.SHIB_IDP, idp);
	}

}
