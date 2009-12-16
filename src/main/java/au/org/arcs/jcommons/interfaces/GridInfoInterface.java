package au.org.arcs.jcommons.interfaces;

public interface GridInfoInterface {

	// GERSON: will remove All from all the methods for consistency

	public abstract String[] getApplicationNamesThatProvideExecutable(
			String executable);

	/**
	 * Get the names of all the clusters at a particular site.
	 * 
	 * @param site
	 *            The site to check
	 * @return An array of cluster names
	 */
	public abstract String[] getClusterNamesAtSite(String site);

	// *************************************************
	// GERSON: StorageElement specific methods follow...

	public abstract String[] getClustersForCodeAtSite(String site, String code,
			String version);

	// GERSON: This used to be called getGatewayGridFTPServerAtSite
	// having Gateway in the name makes it too specific to the Gateway method
	// and
	// not generic enough to be used by other Grids

	/**
	 * Get all the codes at a particular site.
	 * 
	 * @param site
	 *            The site to check
	 * @return An array of codes
	 */
	public abstract String[] getCodesAtSite(String site);

	// GERSON: I've added the following methods which can be used to return
	// GridFTP details for a queue and site. The old method which is called
	// getClusterGridFTPServerAtSite won't work because it has the assumption
	// that all the queues in a cluster can only use one GridFTP server

	/**
	 * Get an array of all the different (unique) codes available on the Grid.
	 * 
	 * @return An array of available codes
	 */
	public abstract String[] getCodesOnGrid();

	/**
	 * Get the GRAM URIs of the queue at the site
	 * 
	 * @param site
	 * @param queue
	 * @return contact string of the queue
	 */
	public abstract String[] getContactStringOfQueueAtSite(String site,
			String queue);

	public abstract String[] getDataDir(String site, String storageElement,
			String FQAN);

	public abstract String getDefaultStorageElementForQueueAtSite(String site,
			String queue);

	/**
	 * Get the name of the executable to be run. This may or may not be same as
	 * the name of the code. For instance 'List' maps to <code>ls</code>.
	 * 
	 * @param site
	 *            The site to check
	 * @param code
	 *            The code to check
	 * @param version
	 *            The version of the code
	 * @return The executable name of the code
	 */
	public abstract String[] getExeNameOfCodeAtSite(String site, String code,
			String version);

	public abstract String[] getExeNameOfCodeForSubmissionLocation(
			String subLoc, String code, String version);

	// **************************************************
	// GERSON: SoftwarePackage specific methods follow...

	/**
	 * Get the GridFTP endpoint addresses for this site
	 * 
	 * @param site
	 *            The site to check
	 * @return The addresses of the GridFTP servers for this site
	 */
	public abstract String[] getGridFTPServersAtSite(String site);

	public abstract String[] getGridFTPServersForQueueAtSite(String site,
			String queue);

	public abstract String[] getGridFTPServersForStorageElementAtSite(
			String site, String storageElement);

	/**
	 * Get an array containing the addresses of all the GridFTP servers on the
	 * Grid
	 * 
	 * @return An array of GridFTP server addresses
	 */
	public abstract String[] getGridFTPServersOnGrid();

	/**
	 * Get the job manager of the cluster at a particular site.
	 * 
	 * @param site
	 *            The site to check
	 * @param queue
	 *            The name of the queue
	 * @return The job manager of the particular queue
	 */
	public abstract String getJobManagerOfQueueAtSite(String site, String queue);

	/**
	 * Get the job type that the specified code supports.
	 * 
	 * @param site
	 *            The site to check
	 * @param code
	 *            The code to check
	 * @param version
	 *            The version of the code
	 * @return The job type
	 */
	public abstract String getJobTypeOfCodeAtSite(String site, String code,
			String version);

	public abstract String getLRMSTypeOfQueueAtSite(String site, String queue);

	/**
	 * Get the name of the module required by a particular version of a code at
	 * a particular site.
	 * 
	 * @param site
	 *            The site to check
	 * @param code
	 *            The code to check
	 * @param version
	 *            The version of the code
	 * @return The name of the module needed
	 */
	public abstract String getModuleNameOfCodeForSubmissionLocation(
			String site, String code, String version);

	/**
	 * Get the names of all the queues at a particular site.
	 * 
	 * @param site
	 *            The site to check
	 * @return An array of job queues
	 */
	public abstract String[] getQueueNamesAtSite(String site);

	/**
	 * Get the names of all the queues at a particular site.
	 * 
	 * @param site
	 *            The site to check
	 * @param fqan
	 *            FQAN
	 * @return An array of job queues
	 */
	public abstract String[] getQueueNamesAtSite(String site, String fqan);

	// GERSON: An issue with the three methods below is that they both assume
	// that
	// a site with more than one cluster will have the same software executable
	// naming convention on the hosts where these applications are installed

	/**
	 * Get the queue names of the cluster at site site.
	 * 
	 * @param site
	 *            The site to check
	 * @return An array of compute elements
	 */
	public abstract String[] getQueueNamesForClusterAtSite(String site,
			String cluster);

	public abstract String[] getQueueNamesForCodeAtSite(String site, String code);

	public abstract String[] getQueueNamesForCodeAtSite(String site,
			String code, String version);

	/**
	 * Get the site where this host belongs to
	 * 
	 * @param host
	 *            hostname
	 * @return the site where the host belongs to
	 */
	public abstract String getSiteForHost(String host);

	/**
	 * Get an array containing the names of all the sites on the Grid.
	 * 
	 * @return An array of site names
	 */
	public abstract String[] getSitesOnGrid();

	/**
	 * Get a list of all sites that have a particular version of the specified
	 * code.
	 * 
	 * @param code
	 *            The code to check
	 * @param version
	 *            The version of the code to check
	 * @return A list of sites that have the specified version of the code
	 */
	public abstract String[] getSitesWithAVersionOfACode(String code,
			String version);

	public abstract String[] getSitesWithCode(String code);

	// ***************************************************
	// GERSON: ComputingElement specific methods follow...

	public abstract String getStorageElementForGridFTPServer(String gridFtp);

	public abstract String[] getStorageElementsForSite(String site);

	/**
	 * Get all the versions of a particular code at a site.
	 * 
	 * @param site
	 *            The site to check
	 * @param code
	 *            The code to check
	 * @return An array of versions
	 */
	public abstract String[] getVersionsOfCodeAtSite(String site, String code);

	public abstract String[] getVersionsOfCodeForQueueAndContactString(
			String queueName, String contactString, String code);

	// GERSON: has been renamed from getComputeElementsOfClusterAtSite to be
	// consistent with calling ComputingElements as Queues

	/**
	 * Get an array of all the different versions of a Code available on the
	 * Grid.
	 * 
	 * @param code
	 *            The code to check
	 * @return An array of version labels
	 */
	public abstract String[] getVersionsOfCodeOnGrid(String code);

	// ***************************************************
	// GERSON: Cluster specific methods follow...

	public abstract boolean isParallelAvailForCodeForSubmissionLocation(
			String subLoc, String code, String version);

	public abstract boolean isSerialAvailForCodeForSubmissionLocation(
			String subLoc, String code, String version);

}
