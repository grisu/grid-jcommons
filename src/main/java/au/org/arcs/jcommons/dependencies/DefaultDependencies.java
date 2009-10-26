package au.org.arcs.jcommons.dependencies;


public enum DefaultDependencies {
	
	LOCALSERVICEINTERFACE("org.vpac.grisu.control.serviceInterfaces.LocalServiceInterface", "https://code.arcs.org.au/hudson/job/Grisu-SNAPSHOT/org.vpac.grisu$grisu-core/lastSuccessfulBuild/artifact/org.vpac.grisu/grisu-core/0.3-SNAPSHOT/local-backend.jar"),
	XFIRESERVICEINTERFACECREATOR("org.vpac.grisu.client.control.XFireServiceInterfaceCreator", "https://code.arcs.org.au/hudson/job/Grisu-connectors-SNAPSHOT-binaries/lastSuccessfulBuild/artifact/frontend-modules/xfire-frontend/target/xfire-frontend.jar"),
	CLIENTSIDEMDS("org.vpac.grisu.frontend.info.clientsidemds.ClientSideGrisuRegistry", "https://code.arcs.org.au/hudson/job/Grisu-SNAPSHOT-binaries/lastSuccessfulBuild/artifact/frontend/client-side-mds/target/client-side-mds.jar"),
	ARCSGSI("au.org.arcs.auth.slcs.ArcsGsiPackageIndicator", "https://code.arcs.org.au/nexus/content/repositories/releases/au/org/arcs/auth/arcs-gsi/${version}/arcs-gsi-${version}-lib.jar"),
	ARCSGSI_WITHOUTPYTHON("au.org.arcs.auth.slcs.ArcsGsiPackageIndicator", "https://code.arcs.org.au/nexus/content/repositories/releases/au/org/arcs/auth/arcs-gsi/${version}/arcs-gsi-${version}-lib-without-jython.jar"),
	BOUNCYCASTLE("org.bouncycastle.jce.provider.BouncyCastleProvider", "http://www.bouncycastle.org/download/bcprov-jdk15-143.jar");
	
	private String className;
	private String downloadUrl;
		
	private DefaultDependencies(String className, String downloadUrl) {
		this.className = className;
		this.downloadUrl = downloadUrl;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public String getDownloadUrl(String version) {
		
		if (version == null ) {
			return this.downloadUrl;
		}
		
		return this.downloadUrl.replace("${version}", version);
	}
	

}
