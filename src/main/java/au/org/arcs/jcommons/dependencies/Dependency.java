package au.org.arcs.jcommons.dependencies;

import java.io.File;

public enum Dependency {

	// LOCALSERVICEINTERFACE("https://code.arcs.org.au/hudson/job/Grisu-SNAPSHOT/org.vpac.grisu$grisu-core/lastSuccessfulBuild/artifact/org.vpac.grisu/grisu-core/0.3-SNAPSHOT/local-backend.jar"),
	// XFIRESERVICEINTERFACECREATOR("https://code.arcs.org.au/hudson/job/Grisu-connectors-SNAPSHOT-binaries/lastSuccessfulBuild/artifact/frontend-modules/xfire-frontend/target/xfire-frontend.jar"),
	// CLIENTSIDEMDS("https://code.arcs.org.au/hudson/job/Grisu-SNAPSHOT-binaries/lastSuccessfulBuild/artifact/frontend/client-side-mds/target/client-side-mds.jar"),
	ARCSGSI("${repository}/au/org/arcs/auth/arcs-gsi/${version}",
			"arcs-gsi-${version}-lib.jar"), ARCSGSI_WITHOUTPYTHON(
			"${repository}/au/org/arcs/auth/arcs-gsi/${version}",
			"arcs-gsi-${version}-lib-without-jython.jar"), BOUNCYCASTLE(
			"http://www.bouncycastle.org/download/", "bcprov-${version}.jar"), GRISU_LOCAL_BACKEND(
			"${repository}/org/vpac/grisu/grisu-core/${version}",
			"grisu-core-${version}-backend.jar"), GRISU_XFIRE_CLIENT_LIBS(
			"$repository}/org/vpac/grisu/grisu-client-xfire/${version}",
			"grisu-client-xfire-${version}.jar"), CLIENT_SIDE_MDS(
			"${repository}/org/vpac/grisu/client-side-mds/${version}",
			"client-side-mds-${version}-side-mds.jar");

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
