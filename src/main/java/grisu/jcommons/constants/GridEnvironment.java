package grisu.jcommons.constants;

import grisu.jcommons.configuration.CommonGridProperties;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to manage certain properties, caches that can be re-used by several
 * grid client applications.
 * 
 * @author Markus Binsteiner
 * 
 */
public class GridEnvironment {

	static final Logger myLogger = LoggerFactory
			.getLogger(GridEnvironment.class.getName());

	private static final String GRID_DEFAULT_DIRECTORY = System
			.getProperty("user.home") + File.separator + ".grid";

    public static String GRID_CACHE_DIR = null;

	/**
	 * The port of the default MyProxy server (7512).
	 */
	private static final int DEFAULT_MYPROXY_PORT = 7512;
	/**
	 * The hostname of the default MyProxy server (myproxy.test.nesi.org.nz).
	 */
	private static final String DEFAULT_MYPROXY_SERVER = "myproxy.nesi.org.nz";
	private static final String DEFAULT_GRID_CONFIG_FILENAME = "info.groovy";

	/**
	 * If this variable is set, it'll be used as the myproxy server for this
	 * instance, otherwise .grid/grid.properties and env variables are checked.
	 */
	public static String MYPROXY_SERVER = null;

	/**
	 * Calculates which MyProxy server to us and returns its port.
	 * 
	 * First checks whether the
	 * {@link CommonGridProperties.Property#MYPROXY_PORT} property is set. If
	 * not, it checks whether the "myproxy.port" environment variable is set. If
	 * not, it returns {@link #DEFAULT_MYPROXY_PORT}.
	 * 
	 * @return the port of the deault myproxy server.
	 */
	public static int getDefaultMyProxyPort() {

		int port = CommonGridProperties.getDefault().getGridPropertyInt(
				CommonGridProperties.Property.MYPROXY_PORT);

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

	/**
	 * Calculates which MyProxy server to us and returns its hostname.
	 * 
	 * First checks whether the
	 * {@link CommonGridProperties.Property#MYPROXY_HOST} property is set. If
	 * not, it checks whether the "myproxy.host" environment variable is set. If
	 * not, it returns {@link #DEFAULT_MYPROXY_HOST}.
	 * 
	 * @return the port of the deault myproxy server.
	 */
	public static String getDefaultMyProxyServer() {

		if (!StringUtils.isBlank(MYPROXY_SERVER)) {
			return MYPROXY_SERVER;
		}

		String server = CommonGridProperties.getDefault().getGridProperty(
				CommonGridProperties.Property.MYPROXY_HOST);

		if (StringUtils.isNotBlank(server)) {
			return server;
		}

		String env = System.getProperty("myproxy.host");

		if (StringUtils.isNotBlank(env)) {
			return env;
		}

		return DEFAULT_MYPROXY_SERVER;

	}

	/**
	 * Returns a reference to a directory that can be used to store java jars
	 * which then can be dynamically added to the classpath of supporting grid
	 * applications (like Grisu).
	 * 
	 * The actual path is a directory called "lib" in the result of
	 * {@link #getGridConfigDirectory()}.
	 * 
	 * @return
	 */
	public static File getGridCommonJavaLibDirectory() {

		String dir = getGridConfigDirectory() + File.separator + "lib";
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * Returns a reference to a directory that can be used to store cached
	 * files.
	 * 
	 * The system property 'grid.common.cache' is used.
	 * 
	 * The default path is a directory called "cache" in the result of
	 * {@link #getGridConfigDirectory()}.
	 * 
	 * @return
	 */
	public static File getGridCommonCacheDirectory() {

		File gridDir = null;

        if (StringUtils.isNotBlank(GRID_CACHE_DIR)) {
            gridDir = new File(GRID_CACHE_DIR);
        } else if (StringUtils.isNotBlank(CommonGridProperties.getDefault().getGridProperty(
                CommonGridProperties.Property.GRID_CACHE_DIR)) ) {
            gridDir = new File(CommonGridProperties.getDefault().getGridProperty(
                    CommonGridProperties.Property.GRID_CACHE_DIR));
        } else if (StringUtils.isNotBlank(System.getProperty("grid.common.cache"))) {
			gridDir = new File(System.getProperty("grid.common.cache"));
		} else {
			gridDir = new File(GRID_DEFAULT_DIRECTORY, "cache");
		}

		if (!gridDir.exists()) {
			gridDir.mkdirs();
		}

		return gridDir;

	}

	// public static File getGridCommonDirectory() {
	//
	// String dir = getGridConfigDirectory() + File.separator + "common";
	// File file = new File(dir);
	// if (!file.exists()) {
	// file.mkdirs();
	// }
	// return file;
	// }

	/**
	 * Calculates the directory where common properties, caches and libraries
	 * for several grid client applications are stored.
	 * 
	 * First checks whether the "grid.common.home" environment variable is set.
	 * If not, it returns $HOME/.grid.
	 * 
	 * @return the reference to the grid config directory
	 */
	public static File getGridConfigDirectory() {

		File gridDir = null;

		if (StringUtils.isNotBlank(System.getProperty("grid.common.home"))) {
			gridDir = new File(System.getProperty("grid.common.home"));
		} else {
			gridDir = new File(GRID_DEFAULT_DIRECTORY);
		}

		if (!gridDir.exists()) {
			gridDir.mkdirs();
		}

		return gridDir;
	}

	public static File getGridInfoConfigFile() {

		String dir = getGridConfigDirectory() + File.separator
				+ DEFAULT_GRID_CONFIG_FILENAME;
		File file = new File(dir);

		return file;

	}

	public static void setDefaultMyProxyHost(String myProxy) {

		CommonGridProperties.getDefault().setGridProperty(
				CommonGridProperties.Property.MYPROXY_HOST, myProxy);

	}

	// public static File getGridHelperScriptsDirectory() {
	//
	// String dir = getGridConfigDirectory() + File.separator
	// + "helperScripts";
	// File file = new File(dir);
	// if (!file.exists()) {
	// file.mkdirs();
	// }
	// return file;
	// }

}
