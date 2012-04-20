package grisu.jcommons.model.info;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class Executable extends AbstractResource implements
		Comparable<Executable> {

	private final static Map<String, Executable> cached = Maps.newHashMap();

	public static synchronized Executable get(String exe) {
		if (cached.get(exe) == null) {
			cached.put(exe, new Executable(exe));
		}
		return cached.get(exe);
	}

	public static List<Executable> getList(String... exes) {
		List<Executable> result = Lists.newLinkedList();
		for (String e : exes) {
			result.add(get(e));
		}
		return result;
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
