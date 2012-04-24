package grisu.model.info.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "filesystem")
public class FileSystem {

	public static final int DEFAULT_PORT = 2811;

	private String host;

	private Site site;
	private int port;
	private String protocol;

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
