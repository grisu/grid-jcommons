package au.org.arcs.jcommons.utils;

public class NewHttpProxyEvent {
	
	private final String proxyHost;
	private final int proxyPort;
	private final String username;
	private final char[] password;
	
	public NewHttpProxyEvent(String proxyhost, int proxyPort, String username, char[] password) {
		this.proxyHost = proxyhost;
		this.proxyPort = proxyPort;
		this.username = username;
		this.password = password;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public String getUsername() {
		return username;
	}

	public char[] getPassword() {
		return password;
	}

}
