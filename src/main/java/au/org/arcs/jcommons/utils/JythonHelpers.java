package au.org.arcs.jcommons.utils;

import java.io.File;

import au.org.arcs.jcommons.constants.ArcsEnvironment;

public class JythonHelpers {
	
	public static void setJythonCachedir() {
		System.setProperty("python.cachedir", ArcsEnvironment.ARCS_DEFAULT_DIRECTORY+File.separator+"cachedir");
	}

}
