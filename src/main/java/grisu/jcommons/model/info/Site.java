package grisu.jcommons.model.info;


import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Sets;

public class Site extends AbstractResource implements Comparable<Site> {

	public static final Site ANY_SITE = new Site("any");

	private String name;

	private Site() {
	}

	public Site(String name) {
		setName(name);
	}

	public int compareTo(Site o) {
		return ComparisonChain.start().compare(getName(), o.getName()).result();
	}

	@Override
	public boolean equals(Object site) {
		if (site == null) {
			return false;
		}
		if (getClass() != site.getClass()) {
			return false;
		}
		final Site other = (Site) site;

		return Objects.equal(this.name, other.name);

	}

	@Override
	public Set<AbstractResource> getDirectConnections() {

		Set<AbstractResource> result = Sets.newHashSet();
		return result;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.name);
	}

	private void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}

}
