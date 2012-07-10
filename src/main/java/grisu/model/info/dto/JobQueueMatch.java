package grisu.model.info.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

/**
 * A model to hold information about whether and why and how well a job will run
 * on a queue.
 * 
 * @author markus
 * 
 */
@XmlRootElement(name = "jobqueuematch")
public class JobQueueMatch implements Comparable<JobQueueMatch> {

	private Queue queue;
	private DtoProperties job;

	private boolean valid = true;
	private int rank = 100;

	private DtoProperties propertiesDetails;

	public JobQueueMatch() {
	}

	public JobQueueMatch(DtoProperties job) {
		this.job = job;
	}

	public JobQueueMatch(Queue queue, DtoProperties job) {
		this.queue = queue;
		this.job = job;
	}

	public int compareTo(JobQueueMatch o) {
		return ComparisonChain.start().compare(getQueue(), o.getQueue())
				.result();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final JobQueueMatch other = (JobQueueMatch) obj;

		return Objects.equal(getQueue(), other.getQueue())
				&& Objects.equal(this.getRank(), other.getRank());
	}

	@XmlElement(name = "job")
	public DtoProperties getJob() {
		return job;
	}

	@XmlElement(name = "propertiesdetail")
	public DtoProperties getPropertiesDetails() {
		return propertiesDetails;
	}

	@XmlElement(name = "queue")
	public Queue getQueue() {
		return queue;
	}

	@XmlAttribute(name = "rank")
	public int getRank() {
		return rank;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getQueue(), getRank());
	}

	@XmlAttribute(name = "valid")
	public boolean isValid() {
		return valid;
	}

	public void setJob(DtoProperties job) {
		this.job = job;
	}

	public void setPropertiesDetails(DtoProperties propertiesDetails) {
		this.propertiesDetails = propertiesDetails;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return getQueue().toString();

	}

}
