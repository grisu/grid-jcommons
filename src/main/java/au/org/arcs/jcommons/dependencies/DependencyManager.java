package au.org.arcs.jcommons.dependencies;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import au.org.arcs.jcommons.constants.ArcsEnvironment;

public class DependencyManager {

	static final Logger myLogger = Logger.getLogger(DependencyManager.class
			.getName());

	private static HttpClient httpClient = new HttpClient();

	public static void initArcsCommonJavaLibDir() {
		ClasspathHacker.initFolder(ArcsEnvironment
				.getArcsCommonJavaLibDirectory(), new JarFilenameFilter());
	}

	public static void checkForBouncyCastleDependency() {
		checkForDependency(DefaultDependencies.BOUNCYCASTLE,
				ArcsEnvironment.getArcsCommonJavaLibDirectory());
	}

	public static void checkForArcsGsiDependency(String version,
			boolean withJython) {

		List<String> temp = new LinkedList<String>();
		temp.add(version);
		checkForArcsGsiDependency(temp, withJython);

	}

	public static void checkForArcsGsiDependency(List<String> versions,
			boolean withJython) {

		if (withJython) {
			checkForVersionedDependency(
					DefaultDependencies.ARCSGSI,
					versions, ArcsEnvironment.getArcsCommonJavaLibDirectory());
		} else {
			checkForVersionedDependency(
					DefaultDependencies.ARCSGSI_WITHOUTPYTHON,
					versions,
					ArcsEnvironment.getArcsCommonJavaLibDirectory());
		}

	}

	public static void checkForVersionedDependency(DefaultDependencies dependency,
			String version, File targetFolder) {
		List<String> temp = new LinkedList<String>();
		temp.add(version);
		checkForVersionedDependency(dependency, temp, targetFolder);
	}

	public static void checkForVersionedDependency(DefaultDependencies dependency,
			List<String> versions, File targetFolder) {

		boolean download = false;

		try {
			Class classObject = Class.forName(dependency.getClassName());

			PackageIndicator classImpl = (PackageIndicator)(classObject.newInstance());

			String version = classImpl.getCurrentVersion();

			if (version != null && version.indexOf("SNAPSHOT") >= 0) {
				download = true;
			} else {
				
				if (versions.contains(version)) {
					download = false;
				} else {
					download = true;
				}
			}

		} catch (Exception e) {
			download = true;
		}

		if (download) {
			try {
				// means we need to download the jar file
				File downloadedFile = downloadJar(dependency.getDownloadUrl(), targetFolder);

				ClasspathHacker.addFile(downloadedFile);
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}
		}

	}

	public static void checkForDependency(DefaultDependencies dependency, File targetFolder) {

		try {
			Class classObject = Class.forName(dependency.getClassName());
		} catch (ClassNotFoundException e) {
			try {
				// means we need to download the jar file
				File downloadedFile = downloadJar(dependency.getDownloadUrl(), targetFolder);

				ClasspathHacker.addFile(downloadedFile);
			} catch (Exception e2) {
				throw new RuntimeException(e2);
			}

		}

	}

	public static File downloadJar(String url, File targetFolder)
			throws IOException {

		myLogger.info("Downloading dependency jar: " + url);
		
		System.out.println("Downloading dependency jar: " + url);
		
		// create a method instance
		GetMethod getMethod = new GetMethod(url);

		String filename = url.substring(url.lastIndexOf("/")+1);
		
		File file = new File(targetFolder, filename);
		
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
