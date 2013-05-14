package grisu.jcommons.utils;

import grisu.jcommons.constants.GridEnvironment;

public class JythonHelpers {

	public static void setJythonCachedir() {

		System.setProperty("python.verbose", "error");

		System.setProperty("python.cachedir", GridEnvironment
				.getGridCommonCacheDirectory().getAbsolutePath());
	}

}
