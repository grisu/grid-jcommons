package grisu.model.info.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

@XmlRootElement(name = "site")
public class Site implements Comparable<Site> {

	private String name;

	public int compareTo(Site o) {
		return ComparisonChain.start().compare(getName(), o.getName()).result();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Site other = (Site) obj;
		return Objects.equal(this.getName(), other.getName());

	}

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getName());
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}

}
