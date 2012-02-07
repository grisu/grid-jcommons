package grisu.jcommons.model.info;

import grisu.jcommons.constants.Constants;

import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Sets;

public class Group extends AbstractResource implements Comparable<Group> {

	public static Group NO_VO_GROUP = new Group(VO.NON_VO,
			Constants.NON_VO_FQAN);

	public static Group create(VO vo, String fqan) {
		return new Group(vo, fqan);
	}

	private VO vo;
	private String fqan;

	public Group(VO vo, String fqan) {
		this.vo = vo;
		this.fqan = fqan;
	}

	public int compareTo(Group g) {

		return ComparisonChain.start().compare(vo, g.getVO())
				.compare(fqan, g.getFqan()).result();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		final Group o = (Group) obj;

		return Objects.equal(getVO(), o.getVO())
				&& Objects.equal(getFqan(), o.getFqan());
	}

	@Override
	public Set<AbstractResource> getDirectConnections() {

		Set<AbstractResource> result = Sets.newHashSet();
		result.add(getVO());
		return result;
	}

	public String getFqan() {
		return fqan;
	}

	public String getRole() {
		return null;
	}

	public VO getVO() {
		return vo;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(fqan, vo);
	}

	private void setFqan(String fqan) {
		this.fqan = fqan;
	}

	private void setVo(VO vo) {
		this.vo = vo;
	}

	@Override
	public String toString() {
		return getFqan();
	}

}
