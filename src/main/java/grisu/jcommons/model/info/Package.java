package grisu.jcommons.model.info;

import java.util.Set;

public class Package {

	private Application application;

	private String version;

	private Set<Group> groups;

	private Package() {
	}

	public Package(Application app, String version) {
		this.application = app;
		this.version = version;
	}

	// public Set<SubmissionLocation> getAllSubmissionLocations() {
	// Set<SubmissionLocation> result = new TreeSet<SubmissionLocation>();
	//
	// for (Collection<SubmissionLocation> sls : submissionLocations.values()) {
	// result.addAll(sls);
	// }
	//
	// return result;
	// }

	public Application getApplication() {
		return application;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	// public Map<Group, Set<SubmissionLocation>> getSubmissionLocations() {
	// return submissionLocations;
	// }

	public String getName() {
		return getApplication().getName() + "_" + getVersion();
	}

	public String getVersion() {
		return version;
	}

	// private void setSubmissionLocations(Map<Group, Set<SubmissionLocation>>
	// sls) {
	// this.submissionLocations = sls;
	// }

}
