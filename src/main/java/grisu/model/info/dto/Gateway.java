package grisu.model.info.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;
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

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Gateway other = (Gateway) obj;
		return Objects.equal(this.getSite(), other.getSite())
				&& Objects.equal(getHost(), other.getHost());

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

	@Override
	public int hashCode() {
		return Objects.hashCode(getSite(), getHost());
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
