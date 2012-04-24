package grisu.model.info.dto;

import grisu.jcommons.constants.Constants;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "version")
public class Version {

	public static final Version ANY_VERSION = new Version(
			Constants.NO_VERSION_INDICATOR_STRING);

	private String version;

	public Version() {
	}

	public Version(String version) {
		setVersion(version);
	}

	@XmlElement(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
