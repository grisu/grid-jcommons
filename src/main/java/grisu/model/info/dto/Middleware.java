package grisu.model.info.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "middleware")
public class Middleware {

	private String name;
	private String version;

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	@XmlElement(name = "version")
	public String getVersion() {
		return version;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
