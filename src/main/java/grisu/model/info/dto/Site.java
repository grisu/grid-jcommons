package grisu.model.info.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.ComparisonChain;

@XmlRootElement(name = "site")
public class Site implements Comparable<Site> {

	private String name;

	public int compareTo(Site o) {
		return ComparisonChain.start().compare(getName(), getName()).result();
	}

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
