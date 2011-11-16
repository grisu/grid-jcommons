/*
 * Holds several (mostly) Grisu related constants.
 */
package grisu.jcommons.constants;

/**
 * This class contains constants that are used to get certain properties of
 * jobs.
 * 
 * @author Markus Binsteiner
 * 
 */
public class Constants {

	/**
	 * The name of the application to use if you either don't know the proper
	 * one or if it is not published in mds. Grisu will try to figure out the
	 * name to use based on mds information (which will take longer than if you
	 * provide it, so it's always recommended to use the right one). If you
	 * specify this generic application name you might also need to provide the
	 * exact submission location of the job and also the module to load on the
	 * location.
	 */
	public static final String GENERIC_APPLICATION_NAME = "generic";
	/**
	 * Similar to the {@link #GENERIC_APPLICATION_NAME}, this is used to tell
	 * Grisu to use just any version of the application it can find. Again, it's
	 * prefererred to specify the exact version if you know it.
	 */
	public static final String NO_VERSION_INDICATOR_STRING = "any";

	public static final String NO_JOBNAME_INDICATOR_STRING = "<no_jobname>";

	public static final String NO_SUBMISSION_LOCATION_INDICATOR_STRING = "auto";

	/**
	 * A constant to indicate you want to know about all jobs, not just a subset
	 * of them (i.e. only jobs using a certain application). In most cases you
	 * can also specify "null", but this is cleaner. Does not return any jobs
	 * that are part of a batchjob.
	 */
	public static final String ALLJOBS_KEY = "alljobs";

	/**
	 * A constant to indicate you want to know about all jobs, not just a subset
	 * of them (i.e. only jobs using a certain application). Does also include
	 * jobs that are part of a batchjob.
	 */
	public static final String ALLJOBS_INCL_BATCH_KEY = "alljobs_incl_batch";

	/**
	 * The key for the Status property of a job or batchjob.
	 */
	public static final String STATUS_STRING = "status";

	// Static strings for JobProperty objects
	/**
	 * The key for the jobname property of a job or batchjob.
	 */
	public static final String JOBNAME_KEY = "jobname";

	/**
	 * Optional key for a description of the job.
	 */
	public static final String JOB_DESCRIPTION_KEY = "description";

	/**
	 * The key for the application property of a job or batchjob.
	 */
	public static final String APPLICATIONNAME_KEY = "application";
	/**
	 * The key for the application version property of a job or batchjob.
	 */
	public static final String APPLICATIONVERSION_KEY = "applicationVersion";
	/**
	 * The key for specifying the number of cpus to use for a job.
	 */
	public static final String NO_CPUS_KEY = "cpus";
	/**
	 * The key to specify whether to force the submission of a "single" type
	 * job, even though you might have specified more than one cpu.
	 */
	public static final String FORCE_SINGLE_KEY = "force_single";
	/**
	 * The key to specify whether to force the submission of a "mpi" type job,
	 * even though you might have specified only one cpu.
	 */
	public static final String FORCE_MPI_KEY = "force_mpi";
	/**
	 * The key for specifying the amount of memory this job needs to have access
	 * to (in Bytes).
	 */
	public static final String MEMORY_IN_B_KEY = "memory";

	/**
	 * The key for specifying the amount of virtual memory this job needs to
	 * have access to (in Bytes).
	 */
	public static final String VIRTUAL_MEMORY_IN_B_KEY = "virtualMemory";

	/**
	 * The key for specifying the email address to send job status updates to.
	 */
	public static final String EMAIL_ADDRESS_KEY = "email_address";
	/**
	 * The key for specifying whether to send an email once a job starts on the
	 * compute resource.
	 */
	public static final String EMAIL_ON_START_KEY = "email_on_start";
	/**
	 * The key for specifying whether to send an email once a job finishes
	 * execution.
	 */
	public static final String EMAIL_ON_FINISH_KEY = "email_on_finish";
	/**
	 * The key for specifying the overall walltimes of a job (in minutes).
	 */
	public static final String WALLTIME_IN_MINUTES_KEY = "walltime";
	/**
	 * The key for specifying the commandline to execute on the compute resource
	 * (like you would execute it on your local machine, i.e.
	 * "echo hello world").
	 */
	public static final String COMMANDLINE_KEY = "commandline";
	/**
	 * The key for specifying the name of the stdout file.
	 */
	public static final String STDOUT_KEY = "stdout";
	/**
	 * The key for specifying the name of the stderr file.
	 */
	public static final String STDERR_KEY = "stderr";
	/**
	 * The key for specifying the name of the stdin file.
	 */
	public static final String STDIN_KEY = "stdin";
	/**
	 * The key for specifying the submission location of a job. A submission
	 * location is assembled like this: queue:hostname[#jobmanager] (default
	 * jobmanager is: PBS)
	 */
	public static final String SUBMISSIONLOCATION_KEY = "submissionLocation";
	/**
	 * The backend that was used to submit this job.
	 */
	public static final String SUBMISSIONBACKEND_KEY = "submissionBackend";
	/**
	 * The key for the property that holds the urls of all input files used for
	 * the job.
	 */
	public static final String INPUT_FILE_URLS_KEY = "inputFilesUrls";

	/**
	 * The key for the property that holds the keys and values for the
	 * environment variables that should be set for a job.
	 */
	public static final String ENVIRONMENT_VARIABLES_KEY = "environmentVariables";

	/**
	 * The key for specifying the modules to load for a job.
	 */
	public static final String MODULES_KEY = "modules";
	/**
	 * The key that holds the name of the Grisu submitter plugin that was used
	 * to submit the job.
	 */
	public static final String SUBMISSION_TYPE_KEY = "submissionType";
	/**
	 * The key for specifying the hostcount of a job.
	 */
	public static final String HOSTCOUNT_KEY = "hostCount";

	// Other job property strings
	/**
	 * The name of the queue.
	 */
	public static final String QUEUE_KEY = "queue";
	/**
	 * The hostname of the host the job was submitted to.
	 */
	public static final String SUBMISSION_HOST_KEY = "submissionHost";
	/**
	 * The name of the site the job was submitted to.
	 */
	public static final String SUBMISSION_SITE_KEY = "submissionSite";
	/**
	 * The url of the jobdirectory for the job.
	 */
	public static final String JOBDIRECTORY_KEY = "jobDirectory";
	/**
	 * The jobmanager that was used to submit the job (e.g. PBS).
	 */
	public static final String FACTORY_TYPE_KEY = "factoryType";
	/**
	 * The relative path to the working (aka job-) directory (from the home
	 * directory of the local user on the compute resource).
	 */
	public static final String WORKINGDIRECTORY_KEY = "workingDirectory";
	/**
	 * The FQAN that was used to submit the job.
	 */
	public static final String FQAN_KEY = "fqan";
	/**
	 * The url of the root of the filesystem where the job was/is running.
	 */
	public static final String STAGING_FILE_SYSTEM_KEY = "stagingFileSystem";
	/**
	 * The time when the job was submitted (in UNIX time / convert to long).
	 */
	public static final String SUBMISSION_TIME_KEY = "submissionTime";
	/**
	 * The root url of the mountpoint that was used to submit the job (can
	 * differ from {@link #STAGING_FILE_SYSTEM_KEY}.
	 */
	public static final String MOUNTPOINT_KEY = "mountpoint";
	/**
	 * The key to get the executable used in this job.
	 */
	public static final String EXECUTABLE_KEY = "executable";

	// mds stuff
	/**
	 * Key that is used to get a comma seperated list of executables that are
	 * available for an application.
	 */
	public static final String MDS_EXECUTABLES_KEY = "Executables";
	/**
	 * Key that is used to get a comma seperated list of modules that shoud be
	 * loaded for a job on the compute resource.
	 */
	public static final String MDS_MODULES_KEY = "Module";
	/**
	 * Key to check whether an application is available for parallel execution.
	 */
	public static final String MDS_PARALLEL_AVAIL_KEY = "parallelAvail";
	/**
	 * Key to check whether an application is available for serial execution.
	 */
	public static final String MDS_SERIAL_AVAIL_KEY = "serialAvail";

	/**
	 * Job property that can be attached to a job to indicate which of the files
	 * are relevant output files. Used later on by application specific file
	 * viewers.
	 */
	public static final String JOB_RESULT_FILENAME_PATTERNS = "resultFilenamePatterns";

	// properties that display what happend during jsdl calculation
	/**
	 * Key to a boolean value that can tell you whether the application name
	 * that was used for a job was caluculated by Grisu or supplied when
	 * creating the job.
	 */
	public static final String APPLICATIONNAME_CALCULATED_KEY = "application_name_calculated";
	/**
	 * Key to a boolean value that can tell you whether the application version
	 * that was used for a job was caluculated by Grisu or supplied when
	 * creating the job.
	 */
	public static final String APPLICATIONVERSION_CALCULATED_KEY = "application_version_calculated";
	/**
	 * Key to a boolean value that can tell you whether the submission location
	 * that was used for a job was caluculated by Grisu or supplied when
	 * creating the job.
	 */
	public static final String SUBMISSIONLOCATION_CALCULATED_KEY = "submissionlocation_calculated";

	/**
	 * Marker constant that is used to indicate that a job was submitted without
	 * a voms attribute certificate attached to a proxy.
	 * 
	 * Usually you can also specify "null", but this is cleaner.
	 */
	public static final String NON_VO_FQAN = "None";

	/**
	 * Only used internally.
	 */
	public static final String SEND_EMAIL_ON_JOB_START_ATTRIBUTE_KEY = "sendOnJobStart";
	/**
	 * Only used internally.
	 */
	public static final String SEND_EMAIL_ON_JOB_END_ATTRIBUTE_KEY = "sendOnJobFinish";
	/**
	 * The key for specifying whether to write out the resulting pbs script that
	 * was constructed on the compute resource (only works for sites that
	 * support this value).
	 */
	public static final String PBSDEBUG_KEY = "pbsDebug";

	// job creation method names
	/**
	 * Not really a job name creation method. Specifying this only indicates
	 * that you don't want the Grisu backend to choose a name for you, you'd
	 * rather have it throw an exception if a job with the same name exists...
	 */
	public static final String FORCE_NAME_METHOD = "force-name";
	/**
	 * Appends an UUID to the name of the job.
	 */
	public static final String UUID_NAME_METHOD = "uuid";
	/**
	 * Appends a timestamp to the name of the job.
	 */
	public static final String TIMESTAMP_METHOD = "timestamp";
	/**
	 * Appends a number to the name of the job if an active job with the same
	 * name already exists. Counts upwards until it finds a number that is not
	 * already used.
	 */
	public static final String UNIQUE_NUMBER_METHOD = "uniqueNumber";

	// batchjob properties
	/**
	 * The key for the name of the batchjob.
	 */
	public static final String BATCHJOB_NAME = "batchjobname";
	/**
	 * Key for the value that tells you the relative path from a (child-)job
	 * directory to the jobdirectory of the parent batchjob.
	 */
	public static final String RELATIVE_PATH_FROM_JOBDIR = "relativePathToBatchDir";
	/**
	 * The key to retrieve the jobdirectory of the batchjob from the root of the
	 * mountpoint(s) of each site that executes child-jobs for the batchjob.
	 */
	public static final String RELATIVE_BATCHJOB_DIRECTORY_KEY = "batchjob_directory";
	/**
	 * Property to set the default method that determines how child jobnames are
	 * created.
	 */
	public static final String JOBNAME_CREATION_METHOD_KEY = "jobnameCreationMethod";
	/**
	 * Key to retrieve the a String that tells you how exactly jobs where
	 * reshuffled after an optimization.
	 */
	public static final String BATCHJOB_OPTIMIZATION_RESULT = "optimizationResult";

	// batchjob distribution
	/**
	 * The key to determine how child-jobs should be distributed (if done
	 * automatically).
	 */
	public static final String DISTRIBUTION_METHOD = "distributionMethod";
	/**
	 * Batchjob distribution method that determines that every submission
	 * location gets an equal amount of child jobs.
	 */
	public static final String DISTRIBUTION_METHOD_EQUAL = "equal";
	/**
	 * Batchjob distribution method that determines that every submission
	 * location gets the same percentage of child jobs as the percentage of its
	 * amount of free job slots is in relation to other submissionlocations.
	 */
	public static final String DISTRIBUTION_METHOD_PERCENTAGE = "percentage";

	// batchjob submit policy
	/**
	 * The key to determine the restart policy for a batchjob.
	 */
	public static final String SUBMIT_POLICY = "restartPolicy";
	/**
	 * Default restart policy. Grisu tries to distribute failed and non-active
	 * jobs to locations that have got finished or running jobs.
	 */
	public static final String SUBMIT_POLICY_RESTART_DEFAULT = "defaultRestart";
	/**
	 * Restart policy that enables the restart of specific jobs (which you'll
	 * have to specify).
	 */
	public static final String SUBMIT_POLICY_RESTART_SPECIFIC_JOBS = "restartSpecificJobs";
	/**
	 * Key to specfiy the jobs to restart when using the
	 * {@link #SUBMIT_POLICY_RESTART_SPECIFIC_JOBS} policy.
	 */
	public static final String JOBNAMES_TO_RESTART = "jobnamesToRestart";
	/**
	 * Key to specfiy the submission locations to submit restarted jobs to when
	 * using the {@link #SUBMIT_POLICY_RESTART_SPECIFIC_JOBS} policy.
	 */
	public static final String SUBMISSIONLOCATIONS_TO_RESTART = "submissionLocationsToRestart";

	/**
	 * List of comma-seperated patterns (simple string-matching, no shell or
	 * reg-ex-pattern) that tell Grisu which submission locations to include
	 * when optimizing/auto-determining submisssionlocations for a batchjob.
	 * 
	 * This property overwrites a possible setting of the
	 * {@link #LOCATIONS_TO_EXCLUDE_KEY} property.
	 */
	public static final String LOCATIONS_TO_INCLUDE_KEY = "sitesToInclude";
	/**
	 * List of comma-seperated patterns (simple string-matching, no shell or
	 * reg-ex-pattern) that tell Grisu which submission locations to exclude
	 * when optimizing/auto-determining submisssionlocations for a batchjob.
	 * 
	 * This property is overwritten by a possible setting of the
	 * {@link #LOCATIONS_TO_INCLUDE_KEY} property.
	 */
	public static final String LOCATIONS_TO_EXCLUDE_KEY = "sitesToExclude";

	// public static final String ERROR_REASON = "errorReason";

	// user properties
	/**
	 * The VO that was used for the last job the user submitted.
	 */
	public static final String DEFAULT_FQAN = "defaultFqan";
	/**
	 * The archive location to use as a default (when not specifying a special
	 * one in the archive job method).
	 */
	public static final String DEFAULT_JOB_ARCHIVE_LOCATION = "Job archive";
	/**
	 * Used to add another archive location to the users properties.
	 * 
	 * Archive locations are used to indicate to Grisu which urls to check for
	 * archived jobs. Use null as a value if you want to delete an existing one.
	 */
	public static final String JOB_ARCHIVE_LOCATION = "archiveLocation";
	public static final String JOB_ARCHIVE_LOCATION_AUTO = "_autoArchiveLocation_";

	// default job properties
	// public static final String GENERIC_JOB_LAST_WALLTIME_IN_MINUTES =
	// "genericJobLastWalltime";
	// public static final String GENERIC_JOB_LAST_CPUS = "genericJobLastCpus";
	// public static final String GENERIC_JOB_LAST_EMAIL =
	// "genericJobLastEmail";
	// public static final String GENERIC_JOB_LAST_USE_EMAIL =
	// "genericJobLastUseEmail";
	// public static final String LAST_VERSION = "lastVersion";
	// public static final String GENERIC_JOB_LAST_INPUTFILE_URL =
	// "generic_job_last_inputfile_url";
	// public static final String DEFAULT_VERSION = "default_version_";

	// other user properties
	/**
	 * Url of the last selected directory for the left side of the file
	 * management panel.
	 */
	public static final String LEFT_FILE_BROWSER_LAST_URL = "left_fileBrowser_last_url";
	/**
	 * Url of the last selected directory for the right side of the file
	 * management panel.
	 */
	public static final String RIGHT_FILE_BROWSER_LAST_URL = "right_fileBrowser_last_url";
	/**
	 * If you set this property to "true", the next time a user logs in, the
	 * backend will clear its filesystem cache and try to reconnect to all the
	 * filessystems for a user that are listed in mds. That can take a bit of
	 * time but it is useful if a filesystem gets back into a working state
	 * after it was broken (or disconnected).
	 */
	public static final String CLEAR_MOUNTPOINT_CACHE = "clearMountPointCache";

	// Status prefixes

	/**
	 * Prefix for handle (REDISTRIBUTE_jobname) for when you want to monitor the
	 * status of a job redistribution of a batchjob.
	 */
	public static final String REDISTRIBUTE = "REDISTRIBUTE_";


}
