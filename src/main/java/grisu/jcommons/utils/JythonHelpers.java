package grisu.jcommons.utils;

import grisu.jcommons.constants.GridEnvironment;

import java.io.File;


public class JythonHelpers {

	public static void setJythonCachedir() {
		System.setProperty("python.cachedir",
				GridEnvironment.getGridConfigDirectory() + File.separator
						+ "cachedir");
	}

}
