package grisu.model.info.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "group")
public class Group {

	private VO vo;
	private String fqan;

	@XmlElement(name = "fqan")
	public String getFqan() {
		return fqan;
	}

	@XmlElement(name = "vo")
	public VO getVo() {
		return vo;
	}

	public void setFqan(String fqan) {
		this.fqan = fqan;
	}

	public void setVo(VO vo) {
		this.vo = vo;
	}
}
