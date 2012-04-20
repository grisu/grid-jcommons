package grisu.model.info.dto;

import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@XmlRootElement(name = "queue")
public class Queue {

	private Gateway gateway;

	private Set<Group> groups;

	private Set<Package> packages = Sets.newHashSet();

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

	private Map<String, DynamicInfo> dynamicInfo = Maps
			.newTreeMap();

	@XmlElement(name = "clockspeedInHz")
	public Integer getClockspeedInHz() {
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
	public Map<String, DynamicInfo> getDynamicInfo() {
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

	@XmlElement(name = "virtualMemory")
	public long getVirtualMemory() {
		return virtualMemory;
	}

	@XmlElement(name = "walltimeInMinutes")
	public int getWalltimeInMinutes() {
		return walltimeInMinutes;
	}

	public void setClockspeedInHz(Integer clockspeedInHz) {
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

	public void setDynamicInfo(Map<String, DynamicInfo> dynamicInfo) {
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

}
