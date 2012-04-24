package grisu.model.info.dto;

import grisu.jcommons.constants.Constants;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

/**
 * A VO consist of the name, a host the management software (VOMS/VOMRS) is
 * hosted, the port to contact the VOMS server (for voms proxy creation) and the
 * DN of the host. Also the url for the endpoint of the VOMRS webservice. At the
 * moment this url is created throught String concatenation in a dodgy way. So,
 * unless your VOMRS server is set up in a way to accomodate that, don't rely on
 * it.
 * 
 * @author Markus Binsteiner
 * 
 */
@XmlRootElement(name = "vo")
public class VO implements Comparable<VO> {

	public static VO NON_VO = new VO(Constants.NON_VO_NAME, "", -1, "");

	private String voName;
	private String host = null;
	private int port = -1;
	private String hostDN = null;

	public VO() {
	}

	public VO(String voName, String host, int port, String hostDN) {
		this.voName = voName;
		this.host = host;
		this.port = port;
		this.hostDN = hostDN;
	}

	public int compareTo(VO vo) {

		int result = voName.compareTo(vo.getVoName());
		if (result != 0) {
			return result;
		}

		result = host.compareTo(vo.getHost());
		if (result != 0) {
			return result;
		}

		result = hostDN.compareTo(vo.getHostDN());
		return result;

	}

	@Override
	public boolean equals(Object otherObject) {
		if (otherObject instanceof VO) {
			VO other = (VO) otherObject;
			if (voName.equals(other.getVoName())
					&& host.equals(other.getHost())
					&& (port == other.getPort()) && hostDN.equals(other.hostDN)) {

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@XmlElement(name = "host")
	public String getHost() {
		return host;
	}

	@XmlElement(name = "hostDN")
	public String getHostDN() {
		return hostDN;
	}

	@XmlElement(name = "port")
	public int getPort() {
		return port;
	}

	@XmlElement(name = "name")
	public String getVoName() {
		return voName;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(voName.hashCode(), host.hashCode(), port,
				hostDN.hashCode());
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setHostDN(String hostDN) {
		this.hostDN = hostDN;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setVoName(String voName) {
		this.voName = voName;
	}

	@Override
	public String toString() {
		return getVoName();
	}

}
