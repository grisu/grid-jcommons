package grisu.jcommons.model.info;


import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Sets;

public class Package extends AbstractResource implements Comparable<Package> {

	public static final Package GENERIC = new Package(
			Application.GENERIC_APPLICATION, Version.ANY_VERSION);

	public static Package create(String application2, String applicationVersion) {
		return new Package(Application.create(application2), Version.create(applicationVersion));
	}

	private Application application;

	private Version version;

	private Set<Executable> executables = Sets.newTreeSet();


	private Module module;

	private Package() {
	}

	public Package(Application app, Version version) {
		this.application = app;
		this.version = version;
	}

	public Package(Application app, Version version,
			Set<Executable> e, Module m) {
		this.application = app;
		this.version = version;
		this.executables = e;
		this.module = m;
	}

	public Package(String app, String version) {
		this.application = new Application(app);
		this.version = new Version(version);
	}

	public int compareTo(Package o) {
		return ComparisonChain
				.start()
				.compare(getApplication().getName(),
						o.getApplication().getName())
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

	public Application getApplication() {
		return application;
	}

	@Override
	public Set<AbstractResource> getDirectConnections() {

		Set<AbstractResource> result = Sets.newHashSet();
		result.add(getVersion());
		result.add(getApplication());
		result.addAll(getExecutables());

		return result;

	}


	public Set<Executable> getExecutables() {
		return this.executables;
	}

	public Module getModule() {
		return module;
	}

	public String getName() {
		return getApplication().getName() + "_" + getVersion();
	}

	public Version getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getApplication(), getVersion());
	}

	private void setApplication(Application a) {
		this.application = a;
	}

	private void setExecutables(Set<Executable> exes) {
		this.executables = exes;
	}

	private void setModule(Module module) {
		this.module = module;
	}

	private void setVersion(Version version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return getApplication() + " / " + getVersion();
	}
}
