package grisu.jcommons.model.info;

import java.util.Set;

public class Queue {

	private Gateway gateway;

	private Set<Group> groups;

	private String queueName;

	private Queue() {
	}

	public Queue(Gateway gw, String queueName, Set<Group> groups) {
		this.gateway = gw;
		this.queueName = queueName;
		this.groups = groups;
	}

	public Gateway getGateway() {
		return this.gateway;

	}

	public Set<Group> getGroups() {
		return groups;
	}

	public String getQueueName() {
		return this.queueName;
	}

	public Site getSite() {
		return getGateway().getSite();
	}

	@Override
	public String toString() {
		return queueName;
	}

}
