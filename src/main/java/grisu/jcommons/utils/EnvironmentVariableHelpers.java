package grisu.jcommons.utils;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class EnvironmentVariableHelpers {

	public static void loadEnvironmentVariablesToSystemProperties() {

		Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {

			String value = env.get(envName);
			if (StringUtils.isNotBlank(value) && value.startsWith("~")) {
				value = value
						.replaceFirst("~", System.getProperty("user.home"));
			}

			System.setProperty(envName, value);
		}

		// for (Object key : System.getProperties().keySet()) {
		// System.out.println("Key: " + key.toString() + " / value: "
		// + System.getProperty((String) key));
		// }

	}

}
