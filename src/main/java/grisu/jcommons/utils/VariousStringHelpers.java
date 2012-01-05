package grisu.jcommons.utils;

import org.apache.commons.lang.StringUtils;

public class VariousStringHelpers {

	public static String getCN(String dn) {

		if (StringUtils.isBlank(dn)) {
			return "";
		}

		int index = dn.lastIndexOf("=");
		if (index < 0) {
			index = -1;
		}
		return dn.substring(index + 1);

	}

}
