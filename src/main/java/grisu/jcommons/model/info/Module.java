package grisu.jcommons.model.info;


import java.util.Set;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Sets;

public class Module extends AbstractResource implements Comparable<Module> {

	public static Module create(String modulename) {
		return new Module(modulename);
	}

	private String module = null;

	private Module() {
	}

	public Module(String module) {
		this.module = module;
	}

	public int compareTo(Module o) {
		return ComparisonChain.start().compare(getModule(), o.getModule())
				.result();
	}

	@Override
	protected Set<AbstractResource> getDirectConnections() {
		Set<AbstractResource> result = Sets.newHashSet();
		return result;
	}

	public String getModule() {
		return this.module;
	}

	private void setModule(String module) {
		this.module = module;
	}

	@Override
	public String toString() {
		return getModule();
	}

}
