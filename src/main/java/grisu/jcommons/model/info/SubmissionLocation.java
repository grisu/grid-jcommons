package grisu.jcommons.model.info;

import java.util.Collection;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

public class SubmissionLocation {

	private final Set<Directory> stagingFileSystems = Sets.newLinkedHashSet();

	private final Queue queue;
	private final Group group;

	public SubmissionLocation(Queue queue, Group group) {
		this.queue = queue;
		this.group = group;
	}

	public synchronized boolean addStagingFileSystem(Directory dir) {

		Collection<Group> otherGroups = dir.getGroups();

		if (otherGroups.contains(group)) {

			stagingFileSystems.add(dir);
			return true;
		}

		return false;

	}

	@Override
	public boolean equals(Object subloc) {
		if (subloc == null) {
			return false;
		}
		if (getClass() != subloc.getClass()) {
			return false;
		}
		final SubmissionLocation other = (SubmissionLocation) subloc;

		return Objects.equal(this.getQueue(), other.getQueue())
				&& Objects.equal(this.getGroup(), other.getGroup());

	}

	public Group getGroup() {
		return group;
	}

	public Queue getQueue() {
		return queue;
	}

	public Site getSite() {
		return getQueue().getSite();
	}

	public Set<Directory> getStagingFileSystems(Group group) {
		return stagingFileSystems;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getQueue(), getGroup());
	}
}
