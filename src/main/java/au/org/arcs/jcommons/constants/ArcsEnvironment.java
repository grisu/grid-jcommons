package au.org.arcs.jcommons.constants;

import java.io.File;

import org.apache.commons.lang.StringUtils;

public class ArcsEnvironment {

	public static final String ARCS_DEFAULT_DIRECTORY = System
	.getProperty("user.home")
	+ File.separator + ".arcs";
	public static final int DEFAULT_MYPROXY_PORT = 443;
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

		if ( StringUtils.isNotBlank(System.getProperty("arcs.config.home")) ) {
			arcsDir = new File(System.getProperty("arcs.config.home"));
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
		return 443;
	}

	public static String getDefaultMyProxyServer() {
		return "myproxy2.arcs.org.au";
	}

}
