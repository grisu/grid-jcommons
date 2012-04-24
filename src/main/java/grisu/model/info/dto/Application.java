package grisu.model.info.dto;

import grisu.jcommons.constants.Constants;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "application")
public class Application {

	private String name;

	public static final Application GENERIC_APPLICATION = new Application(
			Constants.GENERIC_APPLICATION_NAME);

	public Application() {
	}

	public Application(String name) {
		setName(name);
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}

}
