package grisu.model.info.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.ComparisonChain;

@XmlRootElement(name = "gateway")
public class Gateway implements Comparable<Gateway> {

	private String host;
	private Site site;
	private Middleware middleware;

	public int compareTo(Gateway o) {
		return ComparisonChain.start().compare(getSite(), o.getSite())
				.compare(getHost(), o.getHost()).result();
	}

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
