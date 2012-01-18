package grisu.jcommons.model.info;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;

public class Grid {

	private final Set<FileSystem> filesystems = Sets.newHashSet();
	private final Set<Directory> directories = Sets.newHashSet();
	private final Set<Site> sites = Sets.newHashSet();
	private final Set<Gateway> gateways = Sets.newHashSet();
	private final Set<Queue> queues = Sets.newHashSet();
	private final Set<Application> applications = Sets.newHashSet();
	private final Set<Package> packages = Sets.newHashSet();
	private final Set<Group> groups = Sets.newHashSet();
	private final Set<VO> vos = Sets.newTreeSet();

	private final Set<SubmissionLocation> submissionLocations = Sets
			.newHashSet();

	public void addApplication(Application a) {
		applications.add(a);
	}

	public void addDirectory(Directory d) {
		addFileSystem(d.getFilesystem());
		addGroups(d.getGroups());
		directories.add(d);
	}

	public void addFileSystem(FileSystem fs) {
		addSite(fs.getSite());
		filesystems.add(fs);
	}

	public void addGateway(Gateway gw) {
		addSite(gw.getSite());
		gateways.add(gw);
	}

	public void addGroup(Group g) {
		addVo(g.getVO());
		groups.add(g);
	}

	public void addGroups(Collection<Group> groups) {
		for (Group g : groups) {
			addGroup(g);
		}
	}

	public void addPackage(Package p) {
		addApplication(p.getApplication());
		addGroups(p.getGroups());
		packages.add(p);
	}

	public void addQueue(Queue q) {
		addGateway(q.getGateway());
		queues.add(q);
	}

	public void addSite(Site site) {
		sites.add(site);
	}

	public void addSubmissionLocation(SubmissionLocation sl) {
		submissionLocations.add(sl);
	}

	public void addSubmissionLocations(Collection<SubmissionLocation> sls) {
		submissionLocations.addAll(sls);
	}

	public void addVo(VO vo) {
		vos.add(vo);
	}

	public Set<Application> getApplications() {
		return applications;
	}

	public Set<Directory> getDirectories() {
		return directories;
	}

	public Set<FileSystem> getFilesystems() {
		return filesystems;
	}

	public Set<Gateway> getGateways() {
		return gateways;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public Set<Package> getPackages() {
		return packages;
	}

	public Set<Queue> getQueues() {
		return queues;
	}

	public Set<Site> getSites() {
		return sites;
	}

	public Set<SubmissionLocation> getSubmissionLocations() {
		return submissionLocations;
	}

	public Set<VO> getVos() {
		return vos;
	}

}
