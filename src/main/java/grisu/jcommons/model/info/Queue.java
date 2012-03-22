package grisu.jcommons.model.info;

import grisu.jcommons.constants.Constants;
import grisu.jcommons.constants.JobSubmissionProperty;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Collections2;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Sets;

public class Queue extends AbstractResource implements Comparable<Queue> {

	public static final String PBS_FACTORY_TYPE = "PBS";

	private Gateway gateway;

	private Set<Group> groups;

	private Set<Package> packages = Sets.newTreeSet();

	private String name;

	private Set<Directory> directories = Sets.newHashSet();

	private String factoryType = "PBS";

	// job property restrictions
	private int cpus = Integer.MAX_VALUE;
	private long memory = Long.MAX_VALUE;
	private long virtualMemory = Long.MAX_VALUE;
	private int walltimeInMinutes = Integer.MAX_VALUE;

	private int hosts = Integer.MAX_VALUE;
	private int cpusPerHost = Integer.MAX_VALUE;

	// other queue properties, not considered when calculating whether a job
	// would run on this queue or not
	private String description = "n/a";
	private Integer clockspeedInHz = Integer.MAX_VALUE;

	private Queue() {
	}

	public Queue(Gateway gw, String queueName, Set<Group> groups,
			Set<Directory> stagingFileSystems, Set<Package> packages) {
		this.gateway = gw;
		this.name = queueName;
		this.groups = groups;
		this.directories = stagingFileSystems;
		this.packages = packages;
	}

	public Queue(Gateway gw, String queueName, Set<Group> groups,
			Set<Directory> stagingFileSystems, Set<Package> packages,
			int noCpus, long memoryInBytes, long virtualMemoryInBytes,
			int walltimeInMinutes) {
		this.gateway = gw;
		this.name = queueName;
		this.groups = groups;
		this.directories = stagingFileSystems;
		this.packages = packages;
		this.cpus = noCpus;
		this.memory = memoryInBytes;
		this.virtualMemory = virtualMemoryInBytes;
		this.walltimeInMinutes = walltimeInMinutes;
	}

	public boolean acceptsJob(Map<JobSubmissionProperty, String> jobProperties) {

		for (JobSubmissionProperty p : jobProperties.keySet()) {
			switch (p) {
			case WALLTIME_IN_MINUTES:
				int w = Integer.parseInt(jobProperties.get(p));
				if (w > this.walltimeInMinutes) {
					return false;
				}
				break;
			case NO_CPUS:
				int nocpus = Integer.parseInt(jobProperties.get(p));
				if (nocpus > this.cpus) {
					return false;
				}
				break;
			case MEMORY_IN_B:
				long m = Long.parseLong(jobProperties.get(p));
				if (m > this.memory) {
					return false;
				}
				break;
			case VIRTUAL_MEMORY_IN_B:
				long vm = Long.parseLong(jobProperties.get(p));
				if (vm > this.virtualMemory) {
					return false;
				}
				break;
			case APPLICATIONNAME:
				String name = jobProperties.get(p);
				String version = jobProperties
						.get(JobSubmissionProperty.APPLICATIONVERSION);
				boolean appAvail = providesPackage(name, version);
				if (!appAvail) {
					return false;
				}
				break;
			case HOSTCOUNT:
				int hostcount = Integer.parseInt(jobProperties.get(p));
				if (hostcount > this.hosts) {
					return false;
				}
				break;
			}

		}

		return true;

	}

	public int compareTo(Queue o) {
		return ComparisonChain.start().compare(getSite(), o.getSite())
				.compare(getName(), getName()).result();
	}

	public Collection<Package> filterPackages(Application application) {

		return Collections2.filter(getPackages(),
				Filters.filterResource(application));

	}

	public Collection<Package> filterPackages(String app, String version) {
		return Collections2.filter(
				getPackages(),
				Filters.filterResource(Application.get(app),
						Version.get(version)));
	}

	public Integer getClockspeedInHz() {
		return clockspeedInHz;
	}

	public int getCpus() {
		return cpus;
	}

	public int getCpusPerHost() {
		return cpusPerHost;
	}

	public String getDescription() {
		return this.description;
	}

	@Override
	protected Set<AbstractResource> getDirectConnections() {

		Set<AbstractResource> result = Sets.newHashSet();
		result.addAll(getGroups());
		result.addAll(getDirectories());
		result.addAll(getPackages());
		result.add(getGateway());
		return result;
	}

	public Set<Directory> getDirectories() {
		return directories;
	}

	public Set<Directory> getDirectories(Group group) {

		Set<Directory> dirs = Sets.newLinkedHashSet();
		for (Directory d : getDirectories()) {
			if (d.getGroups().contains(group)) {
				dirs.add(d);
			}
		}
		return dirs;
	}

	public Set<Directory> getDirectories(String fqan) {

		Set<Directory> dirs = Sets.newLinkedHashSet();
		for (Directory d : getDirectories()) {
			for (Group g : d.getGroups()) {
				if (g.getFqan().equals(fqan)) {
					dirs.add(d);
				}
			}
		}
		return dirs;

	}

	public String getFactoryType() {
		return this.factoryType;
	}

	public Set<FileSystem> getFileSystems(Group group) {
		Set<FileSystem> fss = Sets.newLinkedHashSet();
		for (Directory d : getDirectories()) {
			if (d.getGroups().contains(group)) {
				fss.add(d.getFilesystem());
			}
		}
		return fss;
	}

	public Set<FileSystem> getFileSystems(String fqan) {
		Set<FileSystem> fss = Sets.newLinkedHashSet();
		for (Directory d : getDirectories()) {
			for (Group g : d.getGroups()) {
				if (g.getFqan().equals(fqan)) {
					fss.add(d.getFilesystem());
				}
			}
		}

		return fss;
	}

	public Gateway getGateway() {
		return this.gateway;

	}

	public Set<Group> getGroups() {
		return groups;
	}

	public int getHosts() {
		return hosts;
	}

	/**
	 * In bytes.
	 * 
	 * @return
	 */
	public long getMemory() {
		return memory;
	}

	public String getName() {
		return this.name;
	}

	public Set<Package> getPackages() {
		return packages;
	}

	public Site getSite() {
		return getGateway().getSite();
	}

	public String getSubmissionLocation() {
		if (StringUtils.isBlank(factoryType)
				|| PBS_FACTORY_TYPE.equals(factoryType)) {
			return getName() + ":" + getGateway().getHost();
		} else {
			return getName() + ":" + getGateway().getHost() + "#" + factoryType;
		}
	}

	/**
	 * In bytes.
	 * 
	 * @return
	 */
	public long getVirtualMemory() {
		return virtualMemory;
	}

	public int getWalltimeInMinutes() {
		return walltimeInMinutes;
	}

	public boolean providesPackage(Package p) {

		String app = p.getApplication().getName();
		String version = p.getVersion().getVersion();
		return providesPackage(app, version);
	}

	public boolean providesPackage(String app, String version) {

		if (Constants.GENERIC_APPLICATION_NAME.equals(app)) {
			return true;
		}

		for (Package temp : getPackages()) {
			String tempApp = temp.getApplication().getName();
			String tempVersion = temp.getVersion().getVersion();

			if (app.equalsIgnoreCase(tempApp)) {
				if (StringUtils.isBlank(version)
						|| Constants.NO_VERSION_INDICATOR_STRING
								.equals(version)) {
					return true;
				}
				return version.equalsIgnoreCase(tempVersion);
			}

		}

		return false;

	}

	public void setClockspeedInHz(Integer clockspeedInHz) {
		this.clockspeedInHz = clockspeedInHz;
	}

	private void setCpus(int cpus) {
		this.cpus = cpus;
	}

	public void setCpusPerHost(int ch) {
		this.cpusPerHost = ch;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	private void setDirectories(Set<Directory> d) {
		this.directories = d;
	}

	public void setFactoryType(String ft) {
		this.factoryType = ft;
	}

	private void setGateway(Gateway gw) {
		this.gateway = gw;
	}

	private void setGroups(Set<Group> g) {
		this.groups = g;
	}

	public void setHosts(int h) {
		this.hosts = h;
	}

	private void setMemoryInBytes(long m) {
		this.memory = m;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setPackages(Set<Package> p) {
		this.packages = p;
	}

	private void setVirtualMemoryInBytes(long m) {
		this.virtualMemory = m;
	}

	private void setWalltimeInMinutes(int w) {
		this.walltimeInMinutes = w;
	}

	@Override
	public String toString() {
		return getSubmissionLocation();
	}

}
