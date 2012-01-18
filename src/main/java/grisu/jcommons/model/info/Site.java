package grisu.jcommons.model.info;

import com.google.common.base.Objects;

public class Site {

	private final String name;

	public Site(String name) {
		this.name = name;
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

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.name);
	}

}
