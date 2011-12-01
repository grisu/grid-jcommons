package grisu.jcommons.utils;


import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Strings;

public class SpinUpdater extends TimerTask {

	private volatile String message = null;
	private volatile int lastMessage = 0;
	private int i = 0;

	private volatile boolean mute = false;

	public SpinUpdater(String message) {
		this.message = message;
	}

	public void mute(boolean mute) {
		this.mute = mute;
	}

	@Override
	public synchronized void run() {
		String msg = null;

		if (StringUtils.isBlank(message)) {
			message = "";
			msg = "   [" + CliHelpers.indeterminateProgressStrings[i] + "]";
		} else {
			msg = "   [" + CliHelpers.indeterminateProgressStrings[i] + "]   "
					+ message;
		}

		if (msg.length() < lastMessage) {
			int temp = msg.length();
			msg = Strings.padEnd(msg, lastMessage, ' ');
			lastMessage = temp;
		}


		CliHelpers.writeToTerminal(msg, mute);

		i = i + 1;
		if (i >= CliHelpers.indeterminateProgressStrings.length) {
			i = 0;
		}
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
