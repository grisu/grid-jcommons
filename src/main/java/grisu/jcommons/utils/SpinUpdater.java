package grisu.jcommons.utils;


import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Strings;

public class SpinUpdater extends TimerTask {

	private String message = null;
	private int lastMessage = 0;
	private int i = 0;

	private volatile boolean mute = false;

	public SpinUpdater(String message) {
		this.message = message;
	}

	public void mute(boolean mute) {
		this.mute = mute;
	}

	@Override
	public void run() {
		String msg = null;

		if (StringUtils.isBlank(message)) {
			message = "";
			msg = "   [" + CliHelpers.indeterminateProgressStrings[i] + "]";
		} else {
			msg = "   [" + CliHelpers.indeterminateProgressStrings[i] + "]   "
					+ message;
		}

		if (msg.length() < lastMessage) {
			CliHelpers
			.writeToTerminal(Strings.padEnd("", lastMessage + 1, ' '));
		}

		lastMessage = msg.length();

		if ( ! mute ) {
			CliHelpers.writeToTerminal(msg);
		}

		i = i + 1;
		if (i >= CliHelpers.indeterminateProgressStrings.length) {
			i = 0;
		}
	}

	public void setMessage(String message) {
		CliHelpers.writeToTerminal("");
		this.message = message;
	}

}
