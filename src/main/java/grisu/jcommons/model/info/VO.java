/* Copyright 2006 VPAC
 * 
 * This file is part of grix-proxy.
 * Grix is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * any later version.

 * Grix is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with Grix; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package grisu.jcommons.model.info;

import grisu.jcommons.constants.Constants;

import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

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
public class VO extends AbstractResource implements Comparable<VO> {

	public static VO NON_VO = new VO(Constants.NON_VO_NAME, "", -1, "");

	private String voName = null;
	private String host = null;
	private int port = -1;
	private String hostDN = null;

	/**
	 * The default constructor. As said above, the vomrs_url is created in a
	 * dodgy way, so beware.
	 * 
	 * @param voName
	 *            the name of the VO
	 * @param host
	 *            the host of the VOMS/VOMRS server of this VO
	 * @param port
	 *            the port of this VO on the VOMS server
	 * @param hostDN
	 *            the host dn
	 */
	public VO(String voName, String host, int port, String hostDN) {
		this.voName = voName;
		this.host = host;
		this.port = port;
		this.hostDN = hostDN;
	}

	// /**
	// * Just for testing. Don't use.
	// * @return
	// */
	// public static VO getDefaultVO(){
	// //TODO
	// return new VO("Chris", "vomrsdev.vpac.org", 15003,
	// "/C=AU/O=APACGrid/OU=VPAC/CN=vomrsdev.vpac.org");
	// }

	// public VO(String voName, String host, int port, String hostDn,
	// String vomrsURL) {
	// this.voName = voName;
	// this.host = host;
	// this.port = port;
	// this.hostDN = hostDn;
	// this.vomrs_url = vomrsURL;
	// }

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
					&& host.equals(other.getHost()) && (port == other.getPort())
					&& hostDN.equals(other.hostDN)) {

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public Set<AbstractResource> getDirectConnections() {

		Set<AbstractResource> result = Sets.newHashSet();
		return result;
	}

	public String getHost() {
		return host;
	}

	public String getHostDN() {
		return hostDN;
	}

	public int getPort() {
		return port;
	}

	public String getVoName() {
		return voName;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(voName.hashCode(), host.hashCode(), port,
				hostDN.hashCode());
	}

	@Override
	public String toString() {
		return getVoName();
	}

}
