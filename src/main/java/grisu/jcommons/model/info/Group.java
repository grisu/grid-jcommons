package grisu.jcommons.model.info;

import com.google.common.collect.ComparisonChain;

public class Group implements Comparable<Group> {

	private final VO vo;
	private final String fqan;

	public Group(VO vo, String fqan) {
		this.vo = vo;
		this.fqan = fqan;
	}

	public int compareTo(Group g) {

		return ComparisonChain.start().compare(vo, g.getVO())
				.compare(fqan, g.getFqan()).result();
	}

	public String getFqan() {
		return fqan;
	}

	public VO getVO() {
		return vo;
	}

}
