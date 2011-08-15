package grisu.jcommons.utils;

import org.apache.commons.lang.StringUtils;

public class FileSystemHelpers {

	public static String getHost(String url) {

		if (StringUtils.isBlank(url) || !url.contains("://")) {
			return null;
		}

		int start = url.indexOf("://");
		int end = url.lastIndexOf(":");
		if (end <= start) {
			end = url.substring(start + 3).indexOf("/");
			if (end < 0) {
				return url.substring(start + 3);
			} else {
				return url.substring(start + 3, end + start + 3);
			}
		} else {
			return url.substring(start + 3, end);
		}

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

	public static void main(String[] args) {

		// String s = "gsiftp://ng2.vpac.org:2811";
		String s = "gsiftp://ng2.vpac.org:2811/home";
		// String s = "gsiftp://ng2.vpac.org";
		// String s = "gsiftp://ng2.vpac.org/home";

		System.out.println("host: _" + getHost(s) + "_");
		System.out.println("port: _" + getPort(s) + "_");

	}

}
