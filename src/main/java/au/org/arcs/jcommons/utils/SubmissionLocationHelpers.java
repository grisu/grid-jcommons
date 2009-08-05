package au.org.arcs.jcommons.utils;

import au.org.arcs.jcommons.interfaces.GridResource;
import au.org.arcs.jcommons.interfaces.InformationManager;


/**
 * Helper class to calculate the string format that is used within Grisu and
 * which is called a submission location.
 * 
 * @author markus
 * 
 */
public final class SubmissionLocationHelpers {
	
	private SubmissionLocationHelpers() {
	}

	/**
	 * Extract the queuename from the submission location.
	 * 
	 * @param subLoc
	 *            the submission location
	 * @return the queue name
	 */
	public static String extractQueue(final String subLoc) {

		int endIndex = subLoc.indexOf(":");
		if (endIndex <= 0) {
			// return null;
			return "Not available";
		}

		return subLoc.substring(0, endIndex);
	}

	/**
	 * Extract the hostname from the submission location.
	 * 
	 * @param subLoc
	 *            the submission location
	 * @return the host name
	 */
	public static String extractHost(final String subLoc) {

		int startIndex = subLoc.indexOf(":") + 1;
		if (startIndex == -1) {
			startIndex = 0;
		}

		int endIndex = subLoc.indexOf("#");
		if (endIndex == -1) {
			endIndex = subLoc.length();
		}

		return subLoc.substring(startIndex, endIndex);

	}

	/**
	 * Calculates the submission location string for the specified GridResource.
	 * 
	 * @param gridResource
	 *            the GridResource
	 * @return the submission location string
	 */
	public static String createSubmissionLocationString(
			final GridResource gridResource) {

		String contactString = gridResource.getContactString();
		
		String hostname = contactString.substring(
				contactString.indexOf("https://") != 0 ? 0
						: 8, contactString.indexOf(":8443"));

		return createSubmissionLocationString(hostname, gridResource
				.getQueueName(), gridResource.getJobManager());
	}

	/**
	 * Calculates the submission location string.
	 * 
	 * @param im
	 *            a information manager
	 * @param contactString
	 *            the contact string for this submission location
	 * @param queue
	 *            the queue name for this submission location
	 * @return the submission location string
	 */
	public static String createSubmissionLocationString(final InformationManager im,
			final String contactString, final String queue) {

		String hostname = contactString.substring(contactString
				.indexOf("https://") != 0 ? 0 : 8, contactString
				.indexOf(":8443"));

		String site = im.getSiteForHostOrUrl(hostname);

		String jobmanager = im.getJobmanagerOfQueueAtSite(site, queue);

		return createSubmissionLocationString(hostname, queue, jobmanager);
	}

	/**
	 * Creates the submission location string.
	 * 
	 * @param hostname
	 *            the hostname for this submission location
	 * @param queue
	 *            the quename for this submission location
	 * @param jobManager
	 *            the jobmanager for this submission location
	 * @return the submission location string
	 */
	public static String createSubmissionLocationString(final String hostname,
			final String queue, final String jobManager) {

		StringBuffer result = new StringBuffer(queue);
		result.append(":");
		result.append(hostname);
		if (jobManager != null && jobManager.length() > 0) {
			if (jobManager.toLowerCase().indexOf("pbs") < 0) {
				result.append("#");
				result.append(jobManager);
			}
		}
		return result.toString();

	}

}
