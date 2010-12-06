package au.org.arcs.jcommons.constants;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import au.org.arcs.jcommons.configuration.CommonArcsProperties;

public class ArcsEnvironment {

	private static final String ARCS_DEFAULT_DIRECTORY = System
			.getProperty("user.home") + File.separator + ".arcs";
	public static final int DEFAULT_MYPROXY_PORT = 7512;
	public static final String DEFAULT_MYPROXY_SERVER = "myproxy.arcs.org.au";

	public static File getArcsCommonDirectory() {

		String dir = getArcsConfigDirectory() + File.separator + "common";
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	public static File getArcsCommonJavaLibDirectory() {

		String dir = getArcsConfigDirectory() + File.separator + "lib";
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	public static File getArcsConfigDirectory() {

		File arcsDir = null;

		if (StringUtils.isNotBlank(System.getProperty("arcs.common.home"))) {
			arcsDir = new File(System.getProperty("arcs.common.home"));
		} else {
			arcsDir = new File(ARCS_DEFAULT_DIRECTORY);
		}

		if (!arcsDir.exists()) {
			arcsDir.mkdirs();
		}

		return arcsDir;
	}

	public static File getArcsHelperScriptsDirectory() {

		String dir = getArcsConfigDirectory() + File.separator
				+ "helperScripts";
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	public static int getDefaultMyProxyPort() {

		int port = CommonArcsProperties.getDefault().getArcsPropertyInt(
				CommonArcsProperties.Property.MYPROXY_PORT);

		if (port != Integer.MIN_VALUE) {
			return port;
		}

		String env = System.getProperty("myproxy.port");
		try {
			port = Integer.parseInt(env);
			return port;
		} catch (Exception e) {
			return DEFAULT_MYPROXY_PORT;
		}

	}

	public static String getDefaultMyProxyServer() {

		String server = CommonArcsProperties.getDefault().getArcsProperty(
				CommonArcsProperties.Property.MYPROXY_HOST);

		if (StringUtils.isNotBlank(server)) {
			return server;
		}

		String env = System.getProperty("myproxy.host");

		if (StringUtils.isNotBlank(env)) {
			return env;
		}

		return DEFAULT_MYPROXY_SERVER;

	}

}
