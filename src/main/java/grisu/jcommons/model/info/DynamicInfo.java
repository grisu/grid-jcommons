package grisu.jcommons.model.info;

public class DynamicInfo {

	public enum TYPE {
		free_job_slots, running_jobs
	}

	private final TYPE type;
	private final String value;

	public DynamicInfo(TYPE type, String value) {
		this.type = type;
		this.value = value;
	}

	public TYPE getType() {
		return this.type;
	}

	public String getValue() {
		return value;
	}

}
