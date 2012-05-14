package grisu.jcommons.utils;

public class QueueHelpers {

	public static String prettyWalltime(int walltimeInMinutes) {
		if (walltimeInMinutes == Integer.MAX_VALUE || walltimeInMinutes <= 0) {
			return "n/a";
		}

		String[] hr = WalltimeUtils
				.convertSecondsInHumanReadableString(walltimeInMinutes * 60);

		return hr[0] + " " + hr[1];

	}

}
