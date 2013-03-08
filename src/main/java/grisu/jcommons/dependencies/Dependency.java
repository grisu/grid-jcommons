package grisu.jcommons.dependencies;

import java.io.File;

public enum Dependency {

	BOUNCYCASTLE(
			"http://code.ceres.auckland.ac.nz/stable-downloads/bcprov/bcprov.jar",
			"bcprov.jar"),

	GRISU_LOCAL_BACKEND(
			"http://code.ceres.auckland.ac.nz/stable-downloads/grisu-backend-local/local-backend.jar",
			"local-backend.jar");

	public static final String RELEASES_DEFAULT_URL = "http://code.arcs.org.au/nexus/content/repositories/releases";
	public static final String SNAPSHOTS_DEFAULT_URL = "http://code.arcs.org.au/nexus/content/repositories/snapshots";

	private String filename;
	private String downloadUrl;

	private Dependency(String downloadUrl, String filename) {
		this.filename = filename;
		this.downloadUrl = downloadUrl;
	}

	public File getDependencyFile(String version, File folder) {
		return new File(folder, getFileName(version));
	}

	public String getDownloadUrl(String version) {

		String temp = this.downloadUrl.replace("${version}", version) + "/"
				+ getFileName(version);

		if (version.indexOf("SNAPSHOT") >= 0) {
			temp = temp.replace("${repository}", SNAPSHOTS_DEFAULT_URL);
		} else {
			temp = temp.replace("${repository}", RELEASES_DEFAULT_URL);
		}
		return temp;
	}

	public String getFileName(String version) {

		return this.filename.replace("${version}", version);
	}

}
