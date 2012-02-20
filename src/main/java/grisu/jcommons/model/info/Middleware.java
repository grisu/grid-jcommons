package grisu.jcommons.model.info;

import java.util.Map;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class Middleware extends AbstractResource implements
		Comparable<Middleware> {

	public static final String DEFAULT_MIDDLEWARE_TYPE = "Globus";
	public static final String DEFAULT_VERSION = "5.0";

	public static final Middleware DEFAULT_MIDDLEWARE = new Middleware(
			DEFAULT_MIDDLEWARE_TYPE, DEFAULT_VERSION);

	private final static Map<String, Middleware> cached = Maps.newHashMap();

	// public static final Middleware ANY_MIDDLEWARE = new Middleware("any",
	// "any");

	public static Middleware get(String name, String version) {

		if (cached.get(name + "_" + version) == null) {
			cached.put(name + "_" + version, new Middleware(name, version));
		}
		return cached.get(name + "_" + version);
	}

	private String name = DEFAULT_MIDDLEWARE_TYPE;
	private String version = DEFAULT_VERSION;

	private Middleware() {
	}

	public Middleware(String name, String version) {
		this.name = name;
		this.version = version;
	}

	public int compareTo(Middleware o) {
		return ComparisonChain.start().compare(getName(), o.getName())
				.compare(getVersion(), o.getVersion()).result();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Middleware other = (Middleware) obj;

		return Objects.equal(getName(), other.getName())
				&& Objects.equal(getVersion(), getVersion());
	}

	@Override
	protected Set<AbstractResource> getDirectConnections() {
		Set<AbstractResource> result = Sets.newHashSet();
		return result;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public int hastCode() {
		return Objects.hashCode(getName(), getVersion());
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return getName() + "/ " + getVersion();

	}

}
