package grisu.jcommons.model.info;


import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Sets;

public class Gateway extends AbstractPhysicalResource implements
Comparable<Gateway> {

	// public static final Gateway ANY_GATEWAY = new Gateway(Site.ANY_SITE,
	// "any",
	// Middleware.ANY_MIDDLEWARE);

	public static Gateway create(Site site, String host, Middleware mw) {
		return new Gateway(site, host, mw);
	}

	private Site site;
	private String host;

	private Middleware middleware = Middleware.DEFAULT_MIDDLEWARE;

	private Gateway() {
	}

	public Gateway(Site site, String host, Middleware mw) {
		setSite(site);
		setHost(host);
		setMiddleware(mw);
	}

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

		return com.google.common.base.Objects.equal(getSite(), other.getSite())
				&& com.google.common.base.Objects.equal(this.getHost(),
						other.getHost());

	}

	@Override
	public String getContactString() {
		return getHost();

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

	public Middleware getMiddleware() {
		return this.middleware;
	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getSite(), getHost());
	}

	private void setHost(String host) {
		this.host = host;
	}

	private void setMiddleware(Middleware mw) {
		this.middleware = mw;
	}

	private void setSite(Site site) {
		this.site = site;
	}

	@Override
	public String toString() {
		return host;
	}

}
