package grisu.jcommons.dependencies;

import grisu.jcommons.constants.GridEnvironment;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.Security;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class BouncyCastleTool {

	private static Logger myLogger = LoggerFactory
			.getLogger(BouncyCastleTool.class);

	private static final String[] possiblePaths = new String[] {
			BouncyCastleTool.class.getProtectionDomain().getCodeSource()
					.getLocation().getPath(), "/usr/share/java",
			GridEnvironment.getGridCommonJavaLibDirectory().getAbsolutePath() };

	private static boolean addExternalBouncyCastle() {

		for (String path : possiblePaths) {

			myLogger.debug("Looking for bouncy-castle library in: {}", path);

			File dir = new File(path);
			if (!dir.exists() || !dir.canRead()) {
				continue;
			}

			if (dir.isFile()) {
				dir = dir.getParentFile();
			}

			String[] files = dir.list(new FilenameFilter() {

				public boolean accept(File arg0, String arg1) {
					File file = new File(arg0, arg1);
					if (file.isFile() && file.canRead()
							&& arg1.startsWith("bcprov")) {
						return true;
					} else {
						return false;
					}
				}
			});
			if (files == null) {
				continue;
			}

			for (String file : files) {
				try {
					ClasspathHacker.addFile(new File(dir, file));
					myLogger.debug("Loaded bouncy castle from: {}", file);
					return true;
				} catch (IOException e) {
					myLogger.debug("Could not load: {}", file);
				}

			}
		}
		myLogger.error("Could not find/load bouncy castle provider...");

		// trying to download
		// DependencyManager.addDependencies(dependencies, folder)

		return false;
	}

	public synchronized static int initBouncyCastle()
			throws ClassNotFoundException {

		// System.out.println("SimpleProxyLib updated");

		// try {
		Class bcClass = null;
		try {
			bcClass = Class
					.forName("org.bouncycastle.jce.provider.BouncyCastleProvider");
		} catch (ClassNotFoundException e1) {
			if (!BouncyCastleTool.addExternalBouncyCastle()) {

				myLogger.debug("Trying to get bouncycastle dependency.");
				Map<Dependency, String> deps = Maps.newHashMap();
				deps.put(Dependency.BOUNCYCASTLE, "ignore");
				DependencyManager.addDependencies(deps,
						GridEnvironment.getGridCommonJavaLibDirectory());
				myLogger.debug("Bouncycastle dependency available");

				BouncyCastleTool.addExternalBouncyCastle();

			}
			bcClass = bcClass
					.forName("org.bouncycastle.jce.provider.BouncyCastleProvider");
		}

		final Class temp = bcClass;
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			public Void run() {

				try {

					// bouncy castle
					if (Security.addProvider((Provider) temp.newInstance()) == -1) {
						myLogger.debug("Could not add BouncyCastleProvider because it is already installed.");
					}
					return null;
				} catch (Throwable e) {
					// e.printStackTrace();
					myLogger.error("Could not load BouncyCastleProvider.", e);
					return null;
				}
			}
		});
		// } catch (Throwable e) {
		// // e.printStackTrace();
		// myLogger.error("Could not load BouncyCastleProvider.", e);
		// // throw new RuntimeException(e);
		// return -1;
		// }
		myLogger.info("Loaded BouncyCastleProvider.");
		return 0;
	}

}
