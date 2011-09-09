package org.bestgrid.goji.utils;

public class EndpointHelpers {
	public static void main(String[] args) {

		String host = "ng2.canterbury.ac.nz";
		String fqan = "/nz/nesi";

		String epn = translateIntoEndpointName(host, fqan);

		String[] result = translateFromEndpointName(epn);

		System.out.println("Host: " + result[0]);
		System.out.println("Fqan: " + result[1]);

	}

	public static String removeHash(String endpoint) {
		int pos = endpoint.indexOf("#");

		if (pos == -1) {
			return endpoint;
		} else {
			return endpoint.substring(pos + 1);
		}

	}

	/**
	 * This one is not reliable, only works if hostname/fqan don't contain "_".
	 * 
	 * @param endpointName
	 * @return
	 */
	public static String[] translateFromEndpointName(String endpointName) {

		int i = endpointName.indexOf("--");
		String host = endpointName.substring(0, i);
		host = host.replace("_", ".");
		String fqan = endpointName.substring(i + 2);
		fqan = fqan.replace("_", "/");
		if (!fqan.startsWith("/")) {
			fqan = "/" + fqan;
		}

		return new String[] { host, fqan };

	}

	public static String translateIntoEndpointName(String host, String fqan) {

		host = host.replace(".", "_");
		if (fqan.startsWith("/")) {
			fqan = fqan.substring(1);
		}
		fqan = fqan.replace("/", "_");

		return host + "--" + fqan;

	}
}
