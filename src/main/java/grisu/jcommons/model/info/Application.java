package grisu.jcommons.model.info;

import grisu.jcommons.constants.Constants;

import java.util.Map;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class Application extends AbstractResource implements
		Comparable<Application> {

	public static final Application GENERIC_APPLICATION = new Application(
			Constants.GENERIC_APPLICATION_NAME);

	private final static Map<String, Application> cached = Maps.newHashMap();

	public static synchronized Application get(String application) {

		if (cached.get(application) == null) {
			cached.put(application, new Application(application));
		}

		return cached.get(application);
	}

	private String name;

	private Application() {
	}

	public Application(String name) {
		this.name = name;
	}

	public int compareTo(Application o) {
		return this.name.compareTo(o.getName());
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

	@Override
	public Set<AbstractResource> getDirectConnections() {

		Set<AbstractResource> result = Sets.newHashSet();
		return result;

	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getName());
	}

	@Override
	public String toString() {
		return getName();
	}

}
