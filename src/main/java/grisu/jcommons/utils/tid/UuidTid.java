package grisu.jcommons.utils.tid;

import java.util.UUID;

public class UuidTid implements TidGenerator {

	public String getTid() {

		return UUID.randomUUID().toString();
	}

}
