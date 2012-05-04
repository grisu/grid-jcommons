package grisu.model.info.dto;

import grisu.jcommons.constants.Constants;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "version")
public class Version implements Comparable<Version> {

	public static final Version ANY_VERSION = new Version(
			Constants.NO_VERSION_INDICATOR_STRING);

	private String version;

	public Version() {
	}

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

	@XmlElement(name = "version")
	public String getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		return 24 * version.hashCode();
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return getVersion();
	}
}
