package grisu.jcommons.utils;

import org.apache.commons.lang.StringUtils;

public class FileSystemHelpers {

	public static String getHost(String url) {

		if (StringUtils.isBlank(url)) {
			return null;
		}

		int start = 0;
		if (url.contains("://")) {
			start = url.indexOf("://") + 3;
		}

		String temp = url.substring(start);
		int end = temp.lastIndexOf(":");

		if (end < 0) {
			end = temp.indexOf("/");
			if (end < 0) {
				return temp;
			} else {
				return temp.substring(0, end);
			}
		} else {
			return temp.substring(0, end);
		}

	}

	public static String getPath(String url) {

		if (StringUtils.isBlank(url)) {
			return null;
		}

		if (url.contains("://")) {
			url = url.substring(url.indexOf("://") + 3);
		}

		if (!url.contains("/")) {
			return null;
		}

		return url.substring(url.indexOf("/"));

	}

	public static int getPort(String url) {

		int i = url.indexOf("://");
		int end = url.substring(i + 3).indexOf(":");
		if (end < 0) {
			String p = url.substring(i);
			return Integer.parseInt(p);
		} else {
			String p = url.substring(end + i + 3 + 1);
			int j = p.indexOf("/");
			if (j < 0) {
				return Integer.parseInt(p);
			} else {
				return Integer.parseInt(p.substring(0, j));
			}
		}
	}

	public static String getProtocol(String url) {

		int i = url.indexOf("://");
		if ( i < 0 ) {
			return null;
		}
		return url.substring(0, i);
	}

	public static void main(String[] args) {

		// String s = "gsiftp://ng2.vpac.org:2811";
		String s = "gsiftp://ng2.vpac.org:2811/home";
		// String s = "gsiftp://ng2.vpac.org";
		// String s = "gsiftp://ng2.vpac.org/home";

		System.out.println("host: _" + getHost(s) + "_");
		System.out.println("port: _" + getPort(s) + "_");

	}

}
