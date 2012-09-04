package grisu.model.info.dto;

import grisu.jcommons.utils.FileSystemHelpers;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

@XmlRootElement(name = "filesystem")
public class FileSystem {

	public static final int DEFAULT_PORT = 2811;
	public static final String DEFAULT_PROTOCOL = "gsiftp";

	private String host;

	private Site site;
	private int port;
	private String protocol;

	public FileSystem() {
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

	@XmlElement(name = "host")
	public String getHost() {
		return host;
	}

	@XmlElement(name = "port")
	public int getPort() {
		return port;
	}

	@XmlElement(name = "protocol")
	public String getProtocol() {
		return protocol;
	}

	@XmlElement(name = "site")
	public Site getSite() {
		return site;
	}

	public String getUrl() {
		if (port != DEFAULT_PORT) {
			return protocol + "://" + host + ":" + port;
		} else {
			return protocol + "://" + host;
		}
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}
