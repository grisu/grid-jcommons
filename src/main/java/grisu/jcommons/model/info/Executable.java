package grisu.jcommons.model.info;


import java.util.Set;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Sets;

public class Executable extends AbstractResource implements
Comparable<Executable> {

	public static Executable create(String exe) {
		return new Executable(exe);
	}

	private String executable;

	public Executable() {
	}

	public Executable(String executable) {
		this.executable = executable;
	}

	public int compareTo(Executable o) {
		return ComparisonChain.start()
				.compare(getExecutable(), o.getExecutable()).result();
	}

	@Override
	protected Set<AbstractResource> getDirectConnections() {
		Set<AbstractResource> result = Sets.newHashSet();
		return result;
	}

	public String getExecutable() {
		return this.executable;
	}

	@Override
	public String toString() {
		return getExecutable();
	}

}
