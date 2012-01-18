package grisu.jcommons.model.info;

public class Application {

	private String name;

	// private final Map<String, Set<Package>> packages = Maps.newHashMap();

	private Application() {
	}

	public Application(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	// public synchronized void addVersion(String version) {
	// Package p = new Package(this, version);
	// // addVersion is done in Package constructor
	// }
	//
	// public synchronized void addVersion(String version, Package p) {
	// Set<Package> temp = packages.get(version);
	// if (temp == null) {
	// temp = Sets.newHashSet();
	// packages.put(version, temp);
	// }
	// temp.add(p);
	// }
}
