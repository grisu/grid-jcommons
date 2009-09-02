package au.org.arcs.jcommons.constants;

import java.io.File;

public class ArcsEnvironment {
	
	public static final String ARCS_DEFAULT_DIRECTORY = System.getProperty("user.home") + File.separator + ".arcs";
	
	public static File getArcsConfigDirectory() {

			File arcsDir = null;
			arcsDir = new File(ARCS_DEFAULT_DIRECTORY);

			if (!arcsDir.exists()) {
				arcsDir.mkdirs();
			}
			
			return arcsDir;
	}
	
	public static File getArcsCommonJavaLibDirectory() {
		
		String dir = getArcsConfigDirectory() + File.separator + "lib";
		File file = new File(dir);
		if ( ! file.exists() ) {
			file.mkdirs();
		}
		return file;
	}

}
