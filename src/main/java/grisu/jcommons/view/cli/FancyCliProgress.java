package grisu.jcommons.view.cli;

import grisu.jcommons.utils.SpinUpdater;

import java.io.IOException;
import java.util.Timer;

import jline.ConsoleReader;
import jline.Terminal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class FancyCliProgress implements ProgressDisplay {

	public static final int DURATION = 100;

	public static String[] indeterminateProgressStrings = new String[] { "-",
			"\\", "|", "/" };

	private static Timer timer = null;

	private final Terminal terminal = null;
	private final ConsoleReader consoleReader = null;

	static final Logger myLogger = LoggerFactory
			.getLogger(FancyCliProgress.class.getName());

	private SpinUpdater spinUpdater = null;

	private static String lastMessage = null;

	private String repetition(String string, int progress) {
		final StringBuffer result = new StringBuffer();
		for (int i = 0; i < progress; i++) {
			result.append(string);
		}
		return result.toString();
	}

	@Override
	public void setIndeterminateProgress(boolean start) {
		setIndeterminateProgress(null, start);
	}

	@Override
	public void setIndeterminateProgress(String message, boolean start) {
		if (terminal == null) {
			return;
		}

		consoleReader.setDefaultPrompt("");

		if (start) {
			if (spinUpdater != null) {
				if ((message == null)
						|| ((message != null) && !message.equals(lastMessage))) {
					spinUpdater.setMessage(message);
				}
			} else {
				spinUpdater = new SpinUpdater(message, this);
				timer = new Timer(true);
				timer.scheduleAtFixedRate(spinUpdater, 0L, DURATION);
			}
			lastMessage = message;
		} else {
			spinUpdater.mute(true);
			timer.cancel();

			spinUpdater = null;
			// if (StringUtils.isNotBlank(lastMessage)) {
			if (lastMessage == null) {
				lastMessage = "";
			}

			String pad = Strings.padEnd("", lastMessage.length() + 9, ' ');
			// System.out.println("PAD: " + pad.length());
			writeToTerminal(pad);
			// } else {
			// writeToTerminal("                    ");
			// }
			lastMessage = null;
			// try {
			// consoleReader.clearLine();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			if (!StringUtils.isBlank(message)) {
				writeToTerminal(message);
				System.out.println(message);
			} else {
				writeToTerminal("");
			}

		}
	}

	@Override
	public void setProgress(int completed, int total) {
		if ((terminal == null)) {
			return;
		}

		consoleReader.setDefaultPrompt("");

		int progress = completed;
		if (total != 0) {
			progress = (completed * 20) / total;
		}

		final String totalStr = String.valueOf(total);
		final String percent = String.format(
				"%" + totalStr.length() + "d/%s [", completed, totalStr);
		final String result = percent + repetition("-", progress)
				+ repetition(" ", 20 - progress) + "]";

		writeToTerminal(result);
	}

	public void writeToTerminal(String message) {
		writeToTerminal(message, false);
	}

	public synchronized void writeToTerminal(String message, boolean mute) {
		if (!mute) {
			consoleReader.getCursorBuffer().clearBuffer();
			consoleReader.getCursorBuffer().write(message);
			try {
				consoleReader.setCursorPosition(consoleReader.getTermwidth());
				consoleReader.redrawLine();
			} catch (final IOException e) {
				myLogger.error(e.getLocalizedMessage(), e);
			}
		} else {
			myLogger.debug("Muting: " + message);
		}

	}

}
