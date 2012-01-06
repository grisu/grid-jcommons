package grisu.jcommons.utils.tid;

import java.security.SecureRandom;

public class SecureRandomTid implements TidGenerator {

	private static SecureRandom random = new SecureRandom();

	public String getTid() {
		return Long.toString(random.nextLong());
	}

}
