package au.org.arcs.jcommons.constants;

public class Constants {
	
	public static final String GENERIC_APPLICATION_NAME = "generic";
	public static final String NO_VERSION_INDICATOR_STRING = "any_version";
	
	public static final String STATUS_STRING = "status";
	
	// Static strings for JobProperty objects
	public static final String JOBNAME_KEY = "jobname";
	public static final String APPLICATIONNAME_KEY = "application";
	public static final String APPLICATIONVERSION_KEY = "version";
	public static final String NO_CPUS_KEY = "cpus";
	public static final String FORCE_SINGLE_KEY = "force_single";
	public static final String FORCE_MPI_KEY = "force_mpi";
	public static final String MEMORY_IN_B_KEY = "memory";
	public static final String EMAIL_ADDRESS_KEY = "email_address";
	public static final String EMAIL_ON_START_KEY = "email_on_start";
	public static final String EMAIL_ON_FINISH_KEY = "email_on_finish";
	public static final String WALLTIME_IN_MINUTES_KEY = "walltime";
	public static final String COMMANDLINE_KEY = "commandline";
	public static final String STDOUT_KEY = "stdout";
	public static final String STDERR_KEY = "stderr";
	public static final String STDIN_KEY = "stdin";
	public static final String SUBMISSIONLOCATION_KEY = "submissionlocation";
	public static final String INPUT_FILE_URLS_KEY = "input_files";
	public static final String MODULES_KEY = "modules";
	public static final String SUBMISSION_TYPE_KEY = "submissionType";

	// Other job property strings
	public static final String QUEUE_KEY = "queue";
	public static final String SUBMISSION_HOST_KEY = "submissionHost";
	public static final String SUBMISSION_SITE_KEY = "submissionSite";
	public static final String JOBDIRECTORY_KEY = "jobDirectory";
	public static final String FACTORY_TYPE_KEY = "factoryType";
	public static final String WORKINGDIRECTORY_KEY = "workingDirectory";
	public static final String FQAN_KEY = "fqan";
//	public static final String JOB_STATUS_KEY = "status";
	public static final String STAGING_FILE_SYSTEM_KEY = "stagingFileSystem";
	public static final String SUBMISSION_TIME_KEY = "submissionTime";
	public static final String MOUNTPOINT_KEY = "mountpoint";
	public static final String MDS_EXECUTABLES_KEY = "Executables";
	public static final String MDS_MODULES_KEY = "Module";
	public static final String MDS_PARALLEL_AVAIL_KEY = "parallelAvail";
	public static final String MDS_SERIAL_AVAIL_KEY = "serialAvail";
	
	public static final String NON_VO_FQAN = "None";
	
	public static final String SEND_EMAIL_ON_JOB_START_ATTRIBUTE_KEY = "sendOnJobStart";
	public static final String SEND_EMAIL_ON_JOB_END_ATTRIBUTE_KEY = "sendOnJobFinish";
	public static final String PBSDEBUG_KEY = "pbsDebug";

	// job creation method names
	public static final String FORCE_NAME_METHOD = "force-name";
	public static final String UUID_NAME_METHOD = "uuid";
	public static final String TIMESTAMP_METHOD = "timestamp";
	
	// multipartjob properties
	public static final String MULTIJOB_NAME = "multijobname";
	public static final String RELATIVE_PATH_FROM_JOBDIR = "relativePathToMultiPartJobDir";
	public static final String RELATIVE_MULTIJOB_DIRECTORY_KEY = "multijob_directory";
	public static final String JOBNAME_CREATION_METHOD_KEY = "jobnameCreationMethod";
	public static final String OPTIMIZE_STATS = "optimizeStats";
	
	public static final String SITES_TO_INCLUDE_KEY = "sitesToInclude";
	public static final String SITES_TO_EXCLUDE_KEY = "sitesToExclude";
	
	public static final String ERROR_REASON = "errorReason";
	
	// user properties
	public static final String DEFAULT_FQAN = "defaultFqan";
	
	
	// default job properties
	public static final String GENERIC_JOB_LAST_WALLTIME_IN_MINUTES = "genericJobLastWalltime";
	public static final String GENERIC_JOB_LAST_CPUS = "genericJobLastCpus";
	public static final String GENERIC_JOB_LAST_EMAIL = "genericJobLastEmail";
	public static final String GENERIC_JOB_LAST_USE_EMAIL = "genericJobLastUseEmail";
	public static final String LAST_VERSION = "lastVersion";
	public static final String GENERIC_JOB_LAST_INPUTFILE_URL = "generic_job_last_inputfile_url";
	
	// other user properties
	public static final String LEFT_FILE_BROWSER_LAST_URL = "left_fileBrowser_last_url";
	public static final String RIGHT_FILE_BROWSER_LAST_URL = "right_fileBrowser_last_url";
	public static final String DEFAULT_VERSION = "default_version_";


}
