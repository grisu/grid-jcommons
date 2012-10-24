package grisu.model.info.dto;

import grisu.jcommons.constants.Constants;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

@XmlRootElement(name = "application")
public class Application implements Comparable<Application> {

	private String name;

	public static final Application GENERIC_APPLICATION = new Application(
			Constants.GENERIC_APPLICATION_NAME);

	public Application() {
	}

	public Application(String name) {
		setName(name);
	}

	@Override
	public int compareTo(Application o) {
		return this.name.compareToIgnoreCase(o.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Application other = (Application) obj;
		return Objects.equal(this.getName(), other.getName());
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getName());
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}

}
