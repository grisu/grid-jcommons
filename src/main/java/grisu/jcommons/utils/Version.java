package grisu.jcommons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class Version {

	public static String get(String module) {

		try {
			List<String> result = readTextFromJar("/" + module + ".version");

			if ((result == null) || (result.size() == 0)
					|| StringUtils.isEmpty(result.get(0))) {
				return "N/A";
			}

			if ("VERSION_TOKEN".equals(result.get(0))) {
				return "N/A";
			}

			return result.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return "N/A";
		}

	}

	public static List<String> readTextFromJar(String s) {
		InputStream is = null;
		BufferedReader br = null;
		String line;
		ArrayList<String> list = new ArrayList<String>();

		try {
			is = FileUtils.class.getResourceAsStream(s);
			br = new BufferedReader(new InputStreamReader(is));
			while (null != (line = br.readLine())) {
				list.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
