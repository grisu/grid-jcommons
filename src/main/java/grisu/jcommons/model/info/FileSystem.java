package grisu.jcommons.model.info;

import grisu.jcommons.utils.FileSystemHelpers;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

public class FileSystem extends AbstractPhysicalResource implements
		Comparable<FileSystem> {

	public static final int DEFAULT_PORT = 2811;
	public static final String DEFAULT_PROTOCOL = "gsiftp";

	private String host;
	private int port = DEFAULT_PORT;
	private String protocol = DEFAULT_PROTOCOL;

	private Site site = null;

	private final String alias = null;

	private FileSystem() {
	}

	public FileSystem(String url) {

		setHost(FileSystemHelpers.getHost(url));
		int temp = -1;
		try {
			temp = FileSystemHelpers.getPort(url);
		} catch (Exception e) {
			temp = DEFAULT_PORT;
		}

		if (temp < 0) {
			setPort(DEFAULT_PORT);
		} else {
			setPort(temp);
		}

		String tempP = FileSystemHelpers.getProtocol(url);
		if (StringUtils.isBlank(tempP)) {
			setProtocol(DEFAULT_PROTOCOL);
		} else {
			setProtocol(tempP);
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
		} else if (o instanceof FileSystem) {
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

	@Override
	public String getContactString() {
		return getUrl();
	}

	@Override
	public Set<AbstractResource> getDirectConnections() {

		Set<AbstractResource> result = Sets.newHashSet();
		result.add(getSite());
		return result;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public Map<String, String> getProperties() {
		return ImmutableMap.of();
	}

	public String getProtocol() {
		return protocol;
	}

	@Override
	public Site getSite() {
		return this.site;
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
		return Objects.hashCode(host, protocol, port);
	}

	private void setHost(String host) {
		this.host = host;
	}

	private void setPort(int port) {
		if (port < 0) {
			this.port = DEFAULT_PORT;
		} else {
			this.port = port;
		}
	}

	private void setProtocol(String protocol) {

		if (StringUtils.isBlank(protocol)) {
			this.protocol = DEFAULT_PROTOCOL;
		} else {
			this.protocol = protocol;
		}
	}

	private void setSite(Site site) {
		this.site = site;
	}

	@Override
	public String toString() {
		return getUrl();
	}

}
