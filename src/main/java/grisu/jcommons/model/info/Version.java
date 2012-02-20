package grisu.jcommons.model.info;

import grisu.jcommons.constants.Constants;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class Version extends AbstractResource implements Comparable<Version> {

	public static Version ANY_VERSION = new Version(
			Constants.NO_VERSION_INDICATOR_STRING);

	private final static Map<String, Version> cached = Maps.newHashMap();

	public static synchronized Version get(String applicationVersion) {
		if (cached.get(applicationVersion) == null) {
			cached.put(applicationVersion, new Version(applicationVersion));
		}
		return cached.get(applicationVersion);
	}

	private String version;

	public Version(String version) {
		setVersion(version);
	}

	public int compareTo(Version o) {
		return version.compareTo(o.getVersion());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Version other = (Version) obj;

		return getVersion().equals(other.getVersion());
	}

	@Override
	public Set<AbstractResource> getDirectConnections() {

		Set<AbstractResource> result = Sets.newHashSet();
		return result;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		return version.hashCode();
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return getVersion();
	}

}
