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

		if (message == null) {
			message = "";
		}

		if (message.length() < lastMessage) {
			int temp = message.length();
			message = Strings.padEnd(message, lastMessage, ' ');
			lastMessage = temp;
		} else {
			lastMessage = message.length();
		}

		if (StringUtils.isBlank(message)) {
			msg = "   [" + CliHelpers.indeterminateProgressStrings[i] + "]";
		} else {
			msg = "   [" + CliHelpers.indeterminateProgressStrings[i] + "]   "
					+ message;
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
