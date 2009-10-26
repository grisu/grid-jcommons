package au.org.arcs.jcommons.dependencies;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

public class DependencyManager {

	public static boolean showDownloadDialog = false;

	static final Logger myLogger = Logger.getLogger(DependencyManager.class
			.getName());

	private static HttpClient httpClient = new HttpClient();

	public static void addDependencies(
			Map<Dependency, String> dependencies, File folder) {

		try {
			boolean displayedDialog = false;
			DownloadingDialog dialog = null;
			if (showDownloadDialog) {
				dialog = new DownloadingDialog("Downloading dependencies");
				displayedDialog = true;
				dialog.setVisible(true);
			}

			for (Dependency dp : dependencies.keySet()) {

				String filename = dp.getFileName(dependencies.get(dp));
				String version = dependencies.get(dp);
				if ( showDownloadDialog) {
					dialog.setMessage("Downloading: "+filename);
				}
				
				try {
					addVersionedDependency(dp, version, folder);
				} catch (Exception e) {
					
					String message = "Could not download dependency \""+ 
						filename+"\"\n\nPlease download the file \""+dp.getDownloadUrl(version)+"\" manually and put it in the folder:\n"
						+ folder.toString();
					
					System.err.println(message);
					
					if ( showDownloadDialog ) {
						dialog.dispose();
						JOptionPane.showMessageDialog(null,
						    message,
						    "Download error",
						    JOptionPane.ERROR_MESSAGE);
					}
					
					System.exit(1);

					
				}

			}

			if (displayedDialog) {
				dialog.dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void addVersionedDependency(Dependency dependency,
			String version, File folder) {

		try {

			File depFile = dependency.getDependencyFile(version, folder);

			if (version.indexOf("SNAPSHOT") >= 0) {
				depFile.delete();
			}

			if (!depFile.exists()) {
				downloadJar(dependency.getDownloadUrl(version), depFile);
			}

			ClasspathHacker.addFile(depFile);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static File downloadJar(String url, File file) throws IOException {

		myLogger.info("Downloading dependency jar: " + url);

		System.out.println("Downloading dependency jar: " + url);

		String filename = url.substring(url.lastIndexOf("/") + 1);

		// create a method instance
		GetMethod getMethod = new GetMethod(url);

		file.delete();

		try {

			// execute the method
			int statusCode = httpClient.executeMethod(getMethod);

			// TODO: check statusCode = 200
			// get the resonse as an InputStream
			InputStream in = getMethod.getResponseBodyAsStream();

			byte[] b = new byte[1024];
			int len;

			OutputStream out = new FileOutputStream(file);
			while ((len = in.read(b)) != -1) {
				// write byte to file
				out.write(b, 0, len);
			}

			in.close();
			return file;

		} finally {
			// release the connection
			getMethod.releaseConnection();

		}

	}

}
