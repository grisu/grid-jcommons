package grisu.jcommons.interfaces;

import grisu.jcommons.constants.JobSubmissionProperty;
import grisu.model.info.dto.Application;
import grisu.model.info.dto.Directory;
import grisu.model.info.dto.JobQueueMatch;
import grisu.model.info.dto.Package;
import grisu.model.info.dto.Queue;
import grisu.model.info.dto.Site;
import grisu.model.info.dto.VO;
import grisu.model.info.dto.Version;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An information manager provides Grisu with information about the grid and
 * it's resources. This will normally happen via mds. But I could imagine
 * writing an InformationManager that reads it's data from config files or such.
 * 
 * @author markus
 * 
 */
public interface InformationManager {

	// /**
	// * Returns all the queues that are able to execute a job with the
	// specified
	// * job properties.
	// *
	// * @param jobProps
	// * the job properties
	// * @param group
	// * the group for which the job is going to be submitted
	// * @return the queues.
	// */
	// public Collection<Queue> findQueues(
	// Map<JobSubmissionProperty, String> jobProps, String group);

	/**
	 * Finds all queues in the grid for a certain fqan and returns all Queues
	 * with information on whether the specified job can be run on it or not.
	 * 
	 * @param job
	 *            the job properties
	 * @param fqan
	 *            the group
	 * @return a list of queues
	 */
	List<JobQueueMatch> findMatches(Map<JobSubmissionProperty, String> job,
			String fqan);

	/**
	 * Finds all queues in the grid that can run a job with the specified
	 * properties and fqan.
	 * 
	 * @param job
	 *            the job properties
	 * @param fqan
	 *            the fqan
	 * @return all queues
	 */
	List<Queue> findQueues(Map<JobSubmissionProperty, String> job, String fqan);

	/**
	 * Returns the names of all the applications at the site.
	 * 
	 * @param site
	 *            name of the site
	 * @return application objects at the site
	 */
	List<Application> getAllApplicationsAtSite(String site);

	/**
	 * Returns all the available applications on the Grid.
	 * 
	 * @return all the applications on the Grid
	 */
	List<Application> getAllApplicationsOnGrid();

	/**
	 * Calculates all the applications that are available for the specified VO
	 * grid-wide.
	 * 
	 * @param fqan
	 *            the vo
	 * @return all available applications
	 */
	List<Application> getAllApplicationsOnGridForVO(String fqan);

	/**
	 * Returns all submission locations on all the sites from MDS.
	 * 
	 * @return all the available submissionlocations on the Grid
	 */
	List<Queue> getAllQueues();

	/**
	 * Returns all the submission locations of all the sites that have the given
	 * application installed on their resources.
	 * 
	 * @param application
	 *            name of the software package
	 * @param version
	 *            version number of the software package
	 * @return submission locations of sites which have the software application
	 *         installed
	 */
	Collection<Queue> getAllQueues(String application, String version);

	/**
	 * Returns all the submissionlocations for the given application. The entry
	 * for the submissionlocations are in this format:
	 * 
	 * <queue-name>:<grid-submission-host>[#<job-manager>]
	 * 
	 * example: hydra@hydra:ng2.sapac.edu.au#PBS sque:ng2.vpac.org
	 * 
	 * If jobmanager is not specified, it is assumed that the submission queue
	 * is of type 'PBS'
	 * 
	 * @param application
	 *            name of the application
	 * @return all the submission queues for the given application.
	 */
	List<Queue> getAllQueuesForApplication(String application);

	/**
	 * Returns all submission locations or a specific VO.
	 * 
	 * @param fqan
	 *            the vo
	 * @return the submission locations
	 */
	Collection<Queue> getAllQueuesForVO(String fqan);

	/**
	 * Return all the names of all the sites from MDS.
	 * 
	 * @return all the sites from MDS
	 */
	List<Site> getAllSites();

	/**
	 * Returns the list of available versions of the software application on the
	 * Grid.
	 * 
	 * @param application
	 *            name of the software package
	 * @return a string array of versions of the software application on the
	 *         Grid
	 */
	List<Version> getAllVersionsOfApplicationOnGrid(String application);

	/**
	 * Returns all VOs that are used.
	 * 
	 * @return the VOs
	 */
	Set<VO> getAllVOs();

	/**
	 * Returns an array of Strings with codes that provide the specified
	 * executable.
	 * 
	 * @param executable
	 *            the executable
	 * @return the codes
	 */
	List<Application> getApplicationsThatProvideExecutable(String executable);

	/**
	 * Returns all the data locations (mount points) on the grid available for
	 * the given VO.
	 * 
	 * @param fqan
	 *            fully qualified attribute name of the VO
	 * @return all the data locations for the VO
	 */
	List<Directory> getDirectoriesForVO(String fqan);

	/**
	 * Returns the jobmanager that submits to the specified queue/site.
	 * 
	 * @param site
	 *            the site
	 * @param queue
	 *            the queue
	 * @return the jobmanager
	 */
	String getJobmanagerOfQueueAtSite(String site, String queue);

	/**
	 * Returns a map of the attribute details of the given application at the
	 * site. This method will give the client the values of the following
	 * attributes: - Module - SerialAvail - ParallelAvail - Executables (comma
	 * separated list) *
	 * 
	 * @param application
	 *            name of the software package
	 * @param version
	 *            version of the software package
	 * @param submissionLocation
	 *            the submissionlocation
	 * @return a map of the attribute details of the given application at the
	 *         site.
	 */
	Package getPackage(String application, String version,
			String submissionLocation);

	/**
	 * Returns the GridResource object for the submissionLocation string.
	 * 
	 * The GridResource object only has dummy values for dynamic stuff like
	 * freejobslots (for now).
	 * 
	 * @param submissionLocation
	 *            the submission location string
	 * @return the grid resource
	 */
	public Queue getQueue(String submissionLocation);

	/**
	 * Checks whether there is a resource of the specified type and name (result
	 * of the toString() method) and returns it.
	 * 
	 * @param type
	 *            the type of the resource
	 * @param name
	 *            the name of the resource
	 * @return the resource or null if it can't be found
	 */
	<T> T getResource(Class<T> type, String name);

	// /**
	// * Finds all resources of the specified type that have connections to the
	// filters specified.
	// * @param resourceClass the type of resources you look for
	// * @param filters the resources that have direct connections to the
	// resources you look for
	// * @return all matching resources
	// */
	// public <T extends AbstractResource> Collection<T> getResources(
	// Class<T> resourceClass, AbstractResource... filters);

	/**
	 * Returns the name of the site where this host or URL belongs.
	 * 
	 * @param host_or_url
	 *            the host or the service's URI
	 * @return the name of the site where this host or URL belongs
	 */
	Site getSiteForHostOrUrl(String host_or_url);

	Set<Directory> getStagingFileSystemForSubmissionLocation(String subLoc);

	/**
	 * Returns the list of available versions of the software application at a
	 * given site.
	 * 
	 * @param application
	 *            name of the software application
	 * @param site
	 *            name of the site
	 * @return an array of string representing the available versions of the
	 *         software application at the site
	 */
	List<Version> getVersionsOfApplicationOnSite(String application, String site);

	/**
	 * Returns the list of available versions of the software application at a
	 * given submissionlocation.
	 * 
	 * @param application
	 *            name of the software application
	 * @param submissionLocation
	 *            name of the submissionlocation
	 * @return an array of string representing the available versions of the
	 *         software application at the submissionlocation
	 */
	List<Version> getVersionsOfApplicationOnSubmissionLocation(
			String application, String submissionLocation);

	/**
	 * Forces a refresh of all information.
	 * 
	 * This might happen asynchronously.
	 */
	void refresh();

}