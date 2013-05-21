package grisu.jcommons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemoryUtils {

	public static long fromStringToMegaBytes(String arg) {
		long imem = 0;
		try {
			imem = Long.parseLong(arg);
		} catch (final NumberFormatException ex) {
			final Pattern mp = Pattern
					.compile("([0-9]+[gG])?([0-9]+[mM])?([0-9]+[kK])?");
			final Matcher m = mp.matcher(arg);
			if (!m.matches()) {
				throw new IllegalArgumentException(
						"Not a valid memory format: " + arg);
			}

			String gb = m.group(1);
			String mb = m.group(2);
			String kb = m.group(3);

			gb = (gb == null) ? "0" : gb.toLowerCase().replace("g", "");
			mb = (mb == null) ? "0" : mb.toLowerCase().replace("m", "");
			kb = (kb == null) ? "0" : kb.toLowerCase().replace("k", "");

			try {
				imem = (Integer.parseInt(gb) * 1024)
						+ (Integer.parseInt(mb) + (Integer.parseInt(kb) / 1024));
			} catch (final NumberFormatException ex2) {
				throw new IllegalArgumentException(
						"Not a valid memory format: " + arg);
			}
		}

		if (imem < 0) {
			throw new IllegalArgumentException("Memory must be postitive: "
					+ arg);
		}

		return imem;
	}

	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit)
			return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1)
				+ (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

}
