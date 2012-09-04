package grisu.model.info.dto;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@XmlRootElement(name = "queue")
public class Queue implements Comparable<Queue> {

	public static final String PBS_FACTORY_TYPE = "PBS";

	public static Queue getQueue(Collection<Queue> queues,
			String submissionLocation) {

		if (queues == null) {
			return null;
		}

		for (Queue q : queues) {
			if (q.toString().equals(submissionLocation)) {
				return q;
			}
		}
		return null;
	}

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
	private Long clockspeedInHz = Long.MAX_VALUE;

	private List<DynamicInfo> dynamicInfo = Lists.newLinkedList();

	public int compareTo(Queue o) {

		int result = ComparisonChain.start()
				.compare(getGateway().getSite(), o.getGateway().getSite())
				.compare(getGateway().getHost(), o.getGateway().getHost())
				.compare(getName(), o.getName()).result();

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Queue other = (Queue) obj;
		return Objects.equal(this.getGateway().getSite(), other.getGateway()
				.getSite())
				&& Objects.equal(getGateway().getHost(), other.getGateway()
						.getHost())
				&& Objects.equal(getName(), other.getName());
	}

	public Set<Directory> findDirectories(Group group) {

		Set<Directory> dirs = Sets.newLinkedHashSet();
		for (Directory d : getDirectories()) {
			if (d.getGroups().contains(group)) {
				dirs.add(d);
			}
		}
		return dirs;
	}

	public Set<Directory> findDirectories(String fqan) {

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

	@XmlElement(name = "clockspeedInHz")
	public Long getClockspeedInHz() {
		return clockspeedInHz;
	}

	@XmlElement(name = "cpus")
	public int getCpus() {
		return cpus;
	}

	@XmlElement(name = "cpusPerHost")
	public int getCpusPerHost() {
		return cpusPerHost;
	}

	@XmlElement(name = "description")
	public String getDescription() {
		return description;
	}

	@XmlElement(name = "directory")
	public Set<Directory> getDirectories() {
		return directories;
	}

	@XmlElement(name = "dynamicInfo")
	public List<DynamicInfo> getDynamicInfo() {
		return dynamicInfo;
	}

	@XmlElement(name = "factoryType")
	public String getFactoryType() {
		return factoryType;
	}

	@XmlElement(name = "gateway")
	public Gateway getGateway() {
		return gateway;
	}

	@XmlElement(name = "group")
	public Set<Group> getGroups() {
		return groups;
	}

	@XmlElement(name = "hosts")
	public int getHosts() {
		return hosts;
	}

	@XmlElement(name = "memory")
	public long getMemory() {
		return memory;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	@XmlElement(name = "package")
	public Set<Package> getPackages() {
		return packages;
	}

	public String calculateSubmissionLocation() {
		return getName() + ":" + getGateway().getHost();
		// if (StringUtils.isBlank(factoryType)
		// || PBS_FACTORY_TYPE.equals(factoryType)) {
		// return getName() + ":" + getGateway().getHost();
		// } else {
		// return getName() + ":" + getGateway().getHost() + "#" + factoryType;
		// }
	}

	@XmlElement(name = "virtualMemory")
	public long getVirtualMemory() {
		return virtualMemory;
	}

	@XmlElement(name = "walltimeInMinutes")
	public int getWalltimeInMinutes() {
		return walltimeInMinutes;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getName(), getGateway().getSite(), getGateway()
				.getHost());
	}

	public void setClockspeedInHz(Long clockspeedInHz) {
		this.clockspeedInHz = clockspeedInHz;
	}

	public void setCpus(int cpus) {
		this.cpus = cpus;
	}

	public void setCpusPerHost(int cpusPerHost) {
		this.cpusPerHost = cpusPerHost;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDirectories(Set<Directory> directories) {
		this.directories = directories;
	}

	public void setDynamicInfo(List<DynamicInfo> dynamicInfo) {
		this.dynamicInfo = dynamicInfo;
	}

	public void setFactoryType(String factoryType) {
		this.factoryType = factoryType;
	}

	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public void setHosts(int hosts) {
		this.hosts = hosts;
	}

	public void setMemory(long memory) {
		this.memory = memory;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPackages(Set<Package> packages) {
		this.packages = packages;
	}

	public void setVirtualMemory(long virtualMemory) {
		this.virtualMemory = virtualMemory;
	}

	public void setWalltimeInMinutes(int walltimeInMinutes) {
		this.walltimeInMinutes = walltimeInMinutes;
	}

	@Override
	public String toString() {
		return calculateSubmissionLocation();
	}

	// @Override
	// public String toString() {
	// return getName() + ":" + getGateway().getHost();
	// // if (StringUtils.isBlank(factoryType)
	// // || PBS_FACTORY_TYPE.equals(factoryType)) {
	// // return getName() + ":" + getGateway().getHost();
	// // } else {
	// // return getName() + ":" + getGateway().getHost() + "#" + factoryType;
	// // }
	// }

}
