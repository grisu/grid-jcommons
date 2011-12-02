package grisu.jcommons.view.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MuteProgressDisplay implements ProgressDisplay {

	static final Logger myLogger = LoggerFactory
			.getLogger(MuteProgressDisplay.class.getName());

	public void setIndeterminateProgress(boolean start) {
		myLogger.debug("Muted setting indeterminate progress to: " + start);
	}

	public void setIndeterminateProgress(String message, boolean start) {
		myLogger.debug("Muted setting indeterminate progress to: " + start
				+ ", message: " + message);
	}

	public void setProgress(int completed, int total) {
		myLogger.debug("Muted setting progress to: " + completed + " / "
				+ total);
	}

}
