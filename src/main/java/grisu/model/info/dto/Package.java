package grisu.model.info.dto;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "package")
public class Package implements Comparable<Package> {

	private Application application;
	private Set<Executable> executables;
	private Module module;
	private String name;
	private Version version;

	public static final Package GENERIC_PACKAGE = new Package(
			Application.GENERIC_APPLICATION, Version.ANY_VERSION);

	public Package() {
	}

	public Package(Application app, Version version) {
		this.application = app;
		this.version = version;
	}

	public int compareTo(Package o) {
		return ComparisonChain
				.start()
				.compare(getApplication().getName(),
						o.getApplication().getName(), String.CASE_INSENSITIVE_ORDER)
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
		final Package other = (Package) obj;

		return Objects.equal(getApplication(), other.getApplication())
				&& Objects.equal(this.getVersion(), other.getVersion());

	}

	@XmlElement(name = "application")
	public Application getApplication() {
		return application;
	}

	@XmlElement(name = "executable")
	public Set<Executable> getExecutables() {
		return executables;
	}

	@XmlElement(name = "module")
	public Module getModule() {
		return module;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	@XmlElement(name = "version")
	public Version getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getApplication(), getVersion());
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public void setExecutables(Set<Executable> executables) {
		this.executables = executables;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return getName() + " / " + getVersion();
	}

}
