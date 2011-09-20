package grisu.jcommons.utils;


public class FileAndUrlHelpers {

	/**
	 * Conveninec method to calculate a human readable String to indicate file
	 * size from the bytesize of a file.
	 * 
	 * @param size
	 *            the size in bytes
	 * @return a human readable String that indicates filesize
	 */
	public static String calculateSizeString(Long size) {

		String sizeString;

		if (size < 0) {
			sizeString = "";
		} else if (size.equals(0L)) {
			sizeString = "0 B";
		} else {

			if (size > (1024 * 1024)) {
				sizeString = (size / (1024 * 1024)) + " MB";
			} else if (size > 1024) {
				sizeString = (size / 1024) + " KB";
			} else {
				sizeString = size + " B";
			}
		}

		return sizeString;

	}

	public static String ensureTrailingSlash(String url) {

		if (!url.endsWith("/")) {
			return url +"/";
		} else {
			return url;
		}
	}

	/**
	 * Helper method to extract the filename out of an url.
	 * 
	 * @param url
	 *            the url
	 * @return the filename
	 */
	public static String getFilename(String url) {

		while (url.endsWith("/")) {
			url = url.substring(0, url.length() - 2);
		}
		int lastIndex = url.lastIndexOf("/") + 1;
		if (lastIndex <= 0) {
			return url;
		}
		String filename = url.substring(lastIndex);

		return filename;

	}

}
