package au.org.arcs.jcommons.dependencies;

import java.io.File;


public enum Dependency {
	
//	LOCALSERVICEINTERFACE("https://code.arcs.org.au/hudson/job/Grisu-SNAPSHOT/org.vpac.grisu$grisu-core/lastSuccessfulBuild/artifact/org.vpac.grisu/grisu-core/0.3-SNAPSHOT/local-backend.jar"),
//	XFIRESERVICEINTERFACECREATOR("https://code.arcs.org.au/hudson/job/Grisu-connectors-SNAPSHOT-binaries/lastSuccessfulBuild/artifact/frontend-modules/xfire-frontend/target/xfire-frontend.jar"),
//	CLIENTSIDEMDS("https://code.arcs.org.au/hudson/job/Grisu-SNAPSHOT-binaries/lastSuccessfulBuild/artifact/frontend/client-side-mds/target/client-side-mds.jar"),
	ARCSGSI("http://code.arcs.org.au/nexus/content/repositories/releases/au/org/arcs/auth/arcs-gsi/${version}", "arcs-gsi-${version}-lib.jar"),
	ARCSGSI_SNAPSHOT("http://code.arcs.org.au/nexus/content/repositories/snapshots/au/org/arcs/auth/arcs-gsi/${version}", "arcs-gsi-${version}-lib.jar"),
	ARCSGSI_WITHOUTPYTHON("http://code.arcs.org.au/nexus/content/repositories/releases/au/org/arcs/auth/arcs-gsi/${version}", "arcs-gsi-${version}-lib-without-jython.jar"),
	ARCSGSI_WITHOUTPYTHON_SNAPSHOT("http://code.arcs.org.au/nexus/content/repositories/snapshots/au/org/arcs/auth/arcs-gsi/${version}", "arcs-gsi-${version}-lib-without-jython.jar"),
	BOUNCYCASTLE("http://www.bouncycastle.org/download/", "bcprov-${version}.jar"), 
	GRISU_LOCAL_BACKEND("http://code.arcs.org.au/nexus/content/repositories/snapshots/org/vpac/grisu/grisu-core/${version}", "grisu-core-${version}-backend.jar");
	
	private String filename;
	private String downloadUrl;
		
	private Dependency(String downloadUrl, String filename) {
		this.filename = filename;
		this.downloadUrl = downloadUrl;
	}
	
	public String getFileName(String version) {
		
		return this.filename.replace("${version}", version);
	}
	
	public String getDownloadUrl(String version) {
		
		return this.downloadUrl.replace("${version}", version) + "/" + getFileName(version);
	}
	
	public File getDependencyFile(String version, File folder) {
		return new File(folder, getFileName(version));
	}
	

}
