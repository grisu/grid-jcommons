package au.org.arcs.jcommons.dependencies;

import java.io.File;
import java.io.FileFilter;

/**
 * Helper class for the swing client plugin system.
 * 
 * @author markus
 *
 */
public class JarFilenameFilter implements FileFilter {

	public boolean accept(File arg0, String arg1) {

		if ( arg1.trim().endsWith(".jar") ) {
			return true;
		} else {
			return false;
		}
		
	}

	public boolean accept(File pathname) {

		if ( ! pathname.toString().endsWith(".jar") ) {
			return false;
		}
		
		if ( pathname.exists() && pathname.isFile() ) {
			return true;
		} else {
			return false;
		}
		
	}

}
