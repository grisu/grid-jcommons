package grisu.jcommons.utils;


import grisu.jcommons.view.cli.FancyCliProgress;

import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Strings;

public class SpinUpdater extends TimerTask {

	private volatile String message = null;
	private volatile int lastMessage = 0;
	private int i = 0;

	private volatile boolean mute = false;

	private final FancyCliProgress progress;

	public SpinUpdater(String message, FancyCliProgress p) {
		this.message = message;
		this.progress = p;
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
			msg = "   [" + FancyCliProgress.indeterminateProgressStrings[i]
					+ "]";
		} else {
			msg = "   [" + FancyCliProgress.indeterminateProgressStrings[i]
					+ "]   "
					+ message;
		}

		progress.writeToTerminal(msg, mute);


		i = i + 1;
		if (i >= FancyCliProgress.indeterminateProgressStrings.length) {
			i = 0;
		}
	}

	public void setMessage(String message) {
		this.message = message;
	}



}
