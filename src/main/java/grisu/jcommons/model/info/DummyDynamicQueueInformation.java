package grisu.jcommons.model.info;

public class DummyDynamicQueueInformation implements DynamicQueueInformation {

	private final Queue queue;

	public DummyDynamicQueueInformation(Queue queue) {
		this.queue = queue;
	}

	public int freeCpus() {
		return 0;
	}

}
