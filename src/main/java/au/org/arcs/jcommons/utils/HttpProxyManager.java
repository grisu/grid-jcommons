package au.org.arcs.jcommons.utils;

public class HttpProxyManager {
	
	public void setHttpProxy(String proxyHost, int proxyPort) {
		
		System.getProperties().put( "proxySet", "true" );
		System.getProperties().put( "proxyHost", proxyHost );
		System.getProperties().put( "proxyPort", proxyPort );

	}
	


}
