package grisu.jcommons.model.info;

import grisu.jcommons.utils.FileSystemHelpers;

import org.apache.commons.lang.StringUtils;

public class FileSystem implements Comparable<FileSystem> {

	public static final int DEFAULT_PORT = 2811;
	public static final String DEFAULT_PROTOCOL = "gsiftp";

	private String host;
	private int port = DEFAULT_PORT;
	private String protocol = DEFAULT_PROTOCOL;

	public FileSystem() {
	}

	public FileSystem(String url) {

		this.host = FileSystemHelpers.getHost(url);
		int temp = -1;
		try {
			temp = FileSystemHelpers.getPort(url);
		} catch (Exception e) {
			temp = DEFAULT_PORT;
		}

		if (temp < 0) {
			this.port = DEFAULT_PORT;
		} else {
			this.port = temp;
		}

		String tempP = FileSystemHelpers.getProtocol(url);
		if (StringUtils.isBlank(tempP)) {
			this.protocol = DEFAULT_PROTOCOL;
		} else {
			this.protocol = tempP;
		}

	}

	public int compareTo(FileSystem o) {
		return getUrl().compareTo(o.getUrl());
	}

	@Override
	public boolean equals(Object o) {

		FileSystem other = null;
		if (o instanceof String) {
			try {
				other = new FileSystem((String) o);
			} catch (Exception e) {
				return false;
			}
		} else
			if (o instanceof FileSystem) {
				other = (FileSystem) o;
			} else {
				return false;
			}

		if (host.equals(other.getHost()) && (port == other.getPort())
				&& protocol.equals(other.getProtocol())) {
			return true;
		} else {
			return false;
		}
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getUrl() {
		if (port != DEFAULT_PORT) {
			return protocol + "://" + host + ":" + port;
		} else {
			return protocol + "://" + host;
		}
	}

	@Override
	public int hashCode() {
		return this.host.hashCode()+this.protocol.hashCode()+port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		if (port < 0) {
			this.port = DEFAULT_PORT;
		} else {
			this.port = port;
		}
	}

	public void setProtocol(String protocol) {

		if (StringUtils.isBlank(protocol)) {
			this.protocol = DEFAULT_PROTOCOL;
		} else {
			this.protocol = protocol;
		}
	}

	@Override
	public String toString() {
		return getUrl();
	}

}
