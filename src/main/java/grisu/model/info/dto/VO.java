package grisu.model.info.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
public class VO {

	private String voName;
	private String host = null;
	private int port = -1;
	private String hostDN = null;

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

}
