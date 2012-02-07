package grisu.jcommons.utils;

public class EndpointHelpers {
	public static String encode(String endpoint) {

		return endpoint.replaceFirst("#", "%23");

	}

	public static String extractEndpointName(String endpoint) {
		int pos = endpoint.indexOf("#");

		if (pos != -1) {
			endpoint = endpoint.substring(pos + 1);
		}

		pos = endpoint.indexOf("/");

		if (pos == -1) {
			return endpoint;
		} else {
			return endpoint.substring(0, pos);
		}

	}

	public static String extractEndpointPart(String go_url) {
		int pos = go_url.indexOf("/");
		if ( pos == -1 ) {
			return null;
		} else {
			return go_url.substring(0, pos);
		}
	}

	public static String extractPathPart(String go_url) {
		int pos = go_url.indexOf("/");

		if (pos == -1) {
			return null;
		} else {
			return go_url.substring(pos);
		}

	}

	public static String extractUsername(String url) {
		int pos = url.indexOf("#");
		if ( pos == -1 ) {
			return null;
		} else {
			return url.substring(0, pos);
		}
	}

	public static boolean isGlobusOnlineUrl(String url) {
		if (!url.startsWith("gsiftp") && encode(url).contains("%23")) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {

		String host = "ng2.canterbury.ac.nz";
		String fqan = "/nz/nesi";

		String epn = translateIntoEndpointName(host, fqan);

		String[] result = translateFromEndpointName(epn);

		System.out.println("Host: " + result[0]);
		System.out.println("Fqan: " + result[1]);

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

	public static String translateIntoEndpointName(String host_or_fsAlias, String fqan) {

		host_or_fsAlias = host_or_fsAlias.replace(".", "_");
		if (fqan.startsWith("/")) {
			fqan = fqan.substring(1);
		}
		fqan = fqan.replace("/", "_");

		return host_or_fsAlias + "--" + fqan;

	}
}
