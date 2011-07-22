package grisu.jcommons.dependencies;

import grisu.jcommons.configuration.CommonGridProperties;
import grisu.jcommons.configuration.CommonGridProperties.Property;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;


public class DependencyManager {

	public static final String DISABLE_DEPENDENCY_MANAGEMENT = "disableDependencyManagement";

	public static boolean showDownloadDialog = false;

	static final Logger myLogger = Logger.getLogger(DependencyManager.class
			.getName());

	private static HttpClient httpClient = new HttpClient();

	public static void addDependencies(Map<Dependency, String> dependencies,
			File folder) {

		addDependencies(dependencies, folder, false);

	}

	public static void addDependencies(Map<Dependency, String> dependencies,
			File folder, boolean forceDependencyManagement) {

		String disable = System.getProperty("disableDependencyManagement");

		if (!forceDependencyManagement
				&& ((disable == null) || !disable.toLowerCase().equals("true"))) {

			myLogger.info("Dependency management disabled. Not resolving dependencies.");
			return;

		}

		String value = CommonGridProperties.getDefault().getGridProperty(
				Property.DISABLE_DEPENDENCY_MANAGEMENT);
		if ((value != null) && "true".equals(value.toLowerCase())) {
			myLogger.info("Dependency management disabled. Not resolving dependencies.");
			return;
		}

		try {
			boolean displayedDialog = false;
			DownloadingDialog dialog = null;
			if (showDownloadDialog) {
				dialog = new DownloadingDialog("Checking dependencies");
				displayedDialog = true;
				dialog.setVisible(true);
			}

			for (Dependency dp : dependencies.keySet()) {

				String filename = dp.getFileName(dependencies.get(dp));
				String version = dependencies.get(dp);
				if (showDownloadDialog) {
					dialog.setMessage("Downloading: " + filename);
				}

				try {
					addVersionedDependency(dp, version, folder);
				} catch (Exception e) {

					String message = "Could not download dependency \""
							+ filename + "\"\n\nPlease download the file \""
							+ dp.getDownloadUrl(version)
							+ "\" manually and put it in the folder:\n"
							+ folder.toString();

					myLogger.error(message);

					if (showDownloadDialog) {
						dialog.dispose();
						JOptionPane.showMessageDialog(null, message,
								"Download error", JOptionPane.ERROR_MESSAGE);
					}

					System.exit(1);

				}

			}

			if (displayedDialog) {
				dialog.dispose();
			}
		} catch (Exception e) {
			myLogger.error(e);
		}

	}

	private static void addVersionedDependency(Dependency dependency,
			String version, File folder) {

		try {

			File depFile = dependency.getDependencyFile(version, folder);

			if (version.indexOf("SNAPSHOT") >= 0) {

				if (depFile.exists()) {

					// only download files that were not downloaded today
					Calendar today = Calendar.getInstance();

					Calendar fileDate = Calendar.getInstance();
					fileDate.setTimeInMillis(depFile.lastModified());

					if (today.get(Calendar.DAY_OF_MONTH) != fileDate
							.get(Calendar.DAY_OF_MONTH)) {
						depFile.delete();
					}

				}
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
