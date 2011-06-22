package grisu.control.info;

import grisu.jcommons.interfaces.GridResource;
import grisu.jcommons.interfaces.Rankable;
import grisu.jcommons.interfaces.RankingAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class GridResourceBackendImpl implements Rankable, GridResource {

	private String contactString;
	private String gramVersion;
	private String jobManager;
	private String queueName;

	// a few items that can be used for ranking resources
	private double siteLatitude;
	private double siteLongitude;
	private String siteName;

	private String applicationName; // don't think this field will be used
	private final List<String> applicationVersions;

	private int freeJobSlots;
	private int runningJobs;
	private int waitingJobs;
	private int totalJobs;

	private int mainMemoryRAMSize;
	private int mainMemoryVirtualSize;

	private final int physicalCPUs;
	private final int logicalCPUs;
	private int smpSize;

	private boolean isHostOnDesiredSite;
	private boolean isDesiredSoftwareVersionInstalled;

	private Set<String> allExecutables = null;

	private RankingAlgorithm rankingAlgorithm;

	/**
	 * Uses SimpleARCSRankingAlgorithm is none is specified
	 */
	public GridResourceBackendImpl() {
		this(new SimpleResourceRankingAlgorithm());
	}

	public GridResourceBackendImpl(RankingAlgorithm rankingAlgorithm) {
		queueName = "";
		contactString = "";
		jobManager = "";
		siteLatitude = 0;
		siteLongitude = 0;
		siteName = "";
		applicationName = "";
		applicationVersions = new ArrayList<String>();
		freeJobSlots = 0;
		runningJobs = 0;
		waitingJobs = 0;
		totalJobs = 0;
		mainMemoryRAMSize = 0;
		mainMemoryVirtualSize = 0;
		physicalCPUs = 0;
		logicalCPUs = 0;
		smpSize = 0;
		isHostOnDesiredSite = false;
		isDesiredSoftwareVersionInstalled = false;
		this.rankingAlgorithm = rankingAlgorithm;
	}

	public void addAvailableApplicationVersion(String applicationVersion) {
		applicationVersions.add(applicationVersion);
	}

	public int compareTo(GridResource o) {

		if (this.getRank() < o.getRank()) {
			return Integer.MAX_VALUE;
		} else if (this.getRank() > o.getRank()) {
			return Integer.MIN_VALUE;
		} else {
			return this.getQueueName().compareTo(o.getQueueName());
		}
	}

	@Override
	public boolean equals(Object o) {

		if (o == null) {
			return false;
		}

		GridResource anotherResource = null;

		try {
			anotherResource = (GridResource) o;
		} catch (Exception e) {
			return false;
		}

		if (queueName.equals(anotherResource.getQueueName())
				&& jobManager.equals(anotherResource.getJobManager())
				&& contactString.equals(anotherResource.getContactString())) {
			return true;
		}
		return false;
	}

	public Set<String> getAllExecutables() {

		return allExecutables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getApplicationName()
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getAvailableApplicationVersion()
	 */
	public List<String> getAvailableApplicationVersion() {
		return applicationVersions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getContactString()
	 */
	public String getContactString() {
		return contactString;
	}

	public String getGRAMVersion() {
		return this.gramVersion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getFreeJobSlots()
	 */
	public int getFreeJobSlots() {
		return freeJobSlots;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getJobManager()
	 */
	public String getJobManager() {
		return jobManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getMainMemoryRAMSize()
	 */
	public int getMainMemoryRAMSize() {
		return mainMemoryRAMSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getMainMemoryVirtualSize()
	 */
	public int getMainMemoryVirtualSize() {
		return mainMemoryVirtualSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getQueueName()
	 */
	public String getQueueName() {
		return queueName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getRank()
	 */
	public int getRank() {
		return rankingAlgorithm.getRank(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getRunningJobs()
	 */
	public int getRunningJobs() {
		return runningJobs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getSiteLatitude()
	 */
	public double getSiteLatitude() {
		return siteLatitude;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getSiteLongitude()
	 */
	public double getSiteLongitude() {
		return siteLongitude;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getSiteName()
	 */
	public String getSiteName() {
		return siteName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getSmpSize()
	 */
	public int getSmpSize() {
		return smpSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getTotalJobs()
	 */
	public int getTotalJobs() {
		return totalJobs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see au.org.arcs.grid.sched.GridResource#getWaitingJobs()
	 */
	public int getWaitingJobs() {
		return waitingJobs;
	}

	@Override
	public int hashCode() {
		return queueName.hashCode() + jobManager.hashCode()
				+ contactString.hashCode() + 23 * getRank();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * au.org.arcs.grid.sched.GridResource#isDesiredSoftwareVersionInstalled()
	 */
	public boolean isDesiredSoftwareVersionInstalled() {
		return isDesiredSoftwareVersionInstalled;
	}

	public boolean isHostOnDesiredSite() {
		return isHostOnDesiredSite;
	}

	public void setAllExecutables(Set<String> allExecutables) {
		this.allExecutables = allExecutables;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public void setContactString(String contactString) {
		this.contactString = contactString;
	}

	public void setGRAMVersion(String gramVersion) {
		this.gramVersion = gramVersion;
	}

	public void setDesiredSoftwareVersionInstalled(
			boolean isDesiredSoftwareVersionInstalled) {
		this.isDesiredSoftwareVersionInstalled = isDesiredSoftwareVersionInstalled;
	}

	public void setFreeJobSlots(int freeJobSlots) {
		this.freeJobSlots = freeJobSlots;
	}

	public void setHostOnDesiredSite(boolean isHostOnDesiredSite) {
		this.isHostOnDesiredSite = isHostOnDesiredSite;
	}

	public void setJobManager(String jobManager) {
		this.jobManager = jobManager;
	}

	public void setMainMemoryRAMSize(int mainMemoryRAMSize) {
		this.mainMemoryRAMSize = mainMemoryRAMSize;
	}

	public void setMainMemoryVirtualSize(int mainMemoryVirtualSize) {
		this.mainMemoryVirtualSize = mainMemoryVirtualSize;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public void setRankingAlgorithm(RankingAlgorithm rankingAlgorithm) {
		this.rankingAlgorithm = rankingAlgorithm;
	}

	public void setRunningJobs(int runningJobs) {
		this.runningJobs = runningJobs;
	}

	public void setSiteLatitude(double siteLatitude) {
		this.siteLatitude = siteLatitude;
	}

	public void setSiteLongitude(double siteLongitude) {
		this.siteLongitude = siteLongitude;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public void setSmpSize(int smpSize) {
		this.smpSize = smpSize;
	}

	public void setTotalJobs(int totalJobs) {
		this.totalJobs = totalJobs;
	}

	public void setWaitingJobs(int waitingJobs) {
		this.waitingJobs = waitingJobs;
	}

	@Override
	public String toString() {
		return getSiteName() + " : " + getQueueName() + " (Ranking: "
				+ getRank() + ")";
	}

	// public int compareTo(GridResource o) {
	// // TODO Auto-generated method stub
	// return 0;
	// }

}
