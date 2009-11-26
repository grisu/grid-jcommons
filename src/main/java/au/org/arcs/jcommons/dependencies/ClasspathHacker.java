package au.org.arcs.jcommons.dependencies;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;

/**
 * Used to add application specific plugins to the swing client.
 * 
 * @author markus
 *
 */
public class ClasspathHacker {
	
	public static void initFolder(File pluginFolder, FileFilter filter) {
		
		
		if ( pluginFolder == null || ! pluginFolder.isDirectory() || ! pluginFolder.canRead() ) {
			return;
		}
		File[] plugins = pluginFolder.listFiles(filter);

		if ( plugins == null ) {
			return;
		}
		
		for ( File plugin : plugins ) {
			
			try {
				ClasspathHacker.addFile(plugin);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.err);
			}
			
		}
	}
	
	public static void initJarFile(Collection<File> files ) {
		
		for ( File plugin : files ) {
			
			try {
				ClasspathHacker.addFile(plugin);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.err);
			}
			
		}
	}
	
	private static final Class[] parameters = new Class[]{URL.class};
	 
	public static void addFile(String s) throws IOException {
		File f = new File(s);
		addFile(f);
	}//end method
	 
	public static void addFile(File f) throws IOException {
		addURL(f.toURL());
	}//end method
	 
	 
	public static void addURL(URL u) throws IOException {
			
		URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;
	 
		try {
			Method method = sysclass.getDeclaredMethod("addURL",parameters);
			method.setAccessible(true);
			method.invoke(sysloader,new Object[]{ u });
		} catch (Throwable t) {
			t.printStackTrace(System.err);
			throw new IOException("Error, could not add URL to system classloader");
		}//end try catch
			
	}//end method
}
