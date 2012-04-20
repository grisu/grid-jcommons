package grisu.model.info.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dynamicInfo")
public class DynamicInfo {

	private String type;
	private String value;

	@XmlAttribute(name = "type")
	public String getType() {
		return type;
	}

	@XmlAttribute(name = "value")
	public String getValue() {
		return value;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
