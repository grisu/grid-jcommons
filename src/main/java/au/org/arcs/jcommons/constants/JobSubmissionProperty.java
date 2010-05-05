package au.org.arcs.jcommons.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic properties for a job that are needed to create a job.
 * 
 * @author markus
 */
public enum JobSubmissionProperty {

	/**
	 * The (grisu-backend unique) name of this job. Defaults to "grisu_job".
	 */
	JOBNAME(Constants.JOBNAME_KEY, "grisu_job"),
	/**
	 * The name of the application for this job. If you don't want to use mds
	 * for this job, specify the value of
	 * {@link Constants#GENERIC_APPLICATION_NAME} here. Defaults to null.
	 */
	APPLICATIONNAME(Constants.APPLICATIONNAME_KEY, ""),
	/**
	 * The version of the application for this job. If you want the Grisu
	 * backend to pick a version, specify the value of
	 * {@link Constants#NO_VERSION_INDICATOR_STRING} here.
	 */
	APPLICATIONVERSION(Constants.APPLICATIONVERSION_KEY,
			Constants.NO_VERSION_INDICATOR_STRING),
	/**
	 * The numbers of cpus for this job. If you don't specify
	 * {@link #FORCE_SINGLE} or {@link #FORCE_MPI} here, the Grisu backend will
	 * auto-select a jobtype. Defaults to 1.
	 */
	NO_CPUS(Constants.NO_CPUS_KEY, "1"),
	/**
	 * Forces a "single" type job, even if you specify more than one cpus in
	 * {@link #NO_CPUS}. Defaults to false.
	 */
	FORCE_SINGLE(Constants.FORCE_SINGLE_KEY, "false"),
	/**
	 * Forces a "mpi" type job, even if you specify only one cpu in
	 * {@link #NO_CPUS}. Defaults to false
	 */
	FORCE_MPI(Constants.FORCE_MPI_KEY, "false"),
	/**
	 * The minimum amount of memory that this job needs. Defaults to 0.
	 */
	MEMORY_IN_B(Constants.MEMORY_IN_B_KEY, ""),
	/**
	 * The email address to use when specifying {@link #EMAIL_ON_START} or
	 * {@link #EMAIL_ON_FINISH}.
	 */
	EMAIL_ADDRESS(Constants.EMAIL_ADDRESS_KEY, ""),
	/**
	 * Specifies whether you want an email sent to you after the job started on
	 * the cluster. Defaults to false.
	 */
	EMAIL_ON_START(Constants.EMAIL_ON_START_KEY, "false"),
	/**
	 * Specifies whether you want an email sent to you after the job finished on
	 * the cluster. Defaults to false.
	 */
	EMAIL_ON_FINISH(Constants.EMAIL_ON_FINISH_KEY, "false"),
	/**
	 * The walltime of your jobs in minutes. Defaults to 1440 (1 day).
	 */
	WALLTIME_IN_MINUTES(Constants.WALLTIME_IN_MINUTES_KEY, "1440"),
	/**
	 * The commandline to run on the cluster (e.g. "java -version"). The backend
	 * will try to figure out which {@link #APPLICATIONNAME} to set (if you
	 * didn't set one) according to the executable you specify here.
	 */
	COMMANDLINE(Constants.COMMANDLINE_KEY, ""),
	/**
	 * The name of the stdout file. Defaults to stdout.txt.
	 */
	STDOUT(Constants.STDOUT_KEY, "stdout.txt"),
	/**
	 * The name of the stderr file. Defaults to stderr.txt.
	 */
	STDERR(Constants.STDERR_KEY, "stderr.txt"),
	/**
	 * The name of the stdIn file. Defaults to null.
	 */
	STDIN(Constants.STDIN_KEY, ""),
	/**
	 * The name of the submission location to submit this job to. Use the
	 * {@link GrisuRegistry} object and it's childs to find one. If you don't
	 * specify one, the backend will try to find the best one for you. efaults
	 * to null.
	 */
	SUBMISSIONLOCATION(Constants.SUBMISSIONLOCATION_KEY, ""),
	/**
	 * A comma seperated list of urls of input files. You can specify local or
	 * remote files here. Defaults to null.
	 */
	INPUT_FILE_URLS(Constants.INPUT_FILE_URLS_KEY, ""),
	/**
	 * A comma-seperated list of modules you want to load. This should only be
	 * used in if you know what you are doing. Defaults to null.
	 */
	MODULES(Constants.MODULES_KEY, ""), PBSDEBUG(Constants.PBSDEBUG_KEY, ""),

	/**
	 * The number of hosts to spread the job. Only important for threaded jobs.
	 */
	HOSTCOUNT(Constants.HOSTCOUNT_KEY, "-1");

	private static final Map<String, JobSubmissionProperty> stringToJobPropertyMap = new HashMap<String, JobSubmissionProperty>();
	static {
		for (JobSubmissionProperty jp : values()) {
			stringToJobPropertyMap.put(jp.toString(), jp);
		}
	}

	public static JobSubmissionProperty fromString(final String key) {
		return stringToJobPropertyMap.get(key);
	}

	private final String keyName;

	private final String defaultValue;

	JobSubmissionProperty(final String keyName, final String defaultValue) {
		this.keyName = keyName;
		this.defaultValue = defaultValue;
	}

	public String defaultValue() {
		return this.defaultValue;
	}

	@Override
	public String toString() {
		return this.keyName;
	}
}
