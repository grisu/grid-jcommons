package grisu.model.info.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "gateway")
public class Gateway {

	private String host;
	private Site site;
	private Middleware middleware;

	@XmlElement(name = "host")
	public String getHost() {
		return host;
	}

	@XmlElement(name = "middleware")
	public Middleware getMiddleware() {
		return middleware;
	}

	@XmlElement(name = "site")
	public Site getSite() {
		return site;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setMiddleware(Middleware middleware) {
		this.middleware = middleware;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}
