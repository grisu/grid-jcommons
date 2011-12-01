package grisu.jcommons.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.Timer;

import jline.ConsoleReader;
import jline.Terminal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

public class CliHelpers {

	private static SpinUpdater spinUpdater = null;

	static final Logger myLogger = LoggerFactory.getLogger(CliHelpers.class
			.getName());

	private static boolean ENABLE_PROGRESS = true;
	private static Timer timer = null;

	public static final Terminal terminal = Terminal.setupTerminal();
	private static ConsoleReader consoleReader = null;

	public static String[] indeterminateProgressStrings = new String[] { "-",
		"\\", "|", "/" };

	public static final int DURATION = 100;

	private static boolean interrupt_progress = false;

	public static void enableProgressDisplay(boolean enable) {
		ENABLE_PROGRESS = enable;
	}

	public static synchronized ConsoleReader getConsoleReader() {
		if (consoleReader == null) {
			try {
				consoleReader = new ConsoleReader();
				terminal.beforeReadLine(consoleReader, "", (char) 0);
				terminal.afterReadLine(consoleReader, "", (char) 0);
			} catch (final IOException e) {
				myLogger.error(e.getLocalizedMessage(), e);
			}

		}
		return consoleReader;
	}

	private static int getTermwidth() {
		return getConsoleReader().getTermwidth();
	}

	public static String getUserChoice(Collection<String> collection,
			String nonSelectionText) {
		return getUserChoice(collection, null, null, nonSelectionText);
	}

	public static String getUserChoice(Collection<String> collection,
			String prompt, String defaultValue, String nonSelectionText) {

		if (StringUtils.isBlank(prompt)) {
			prompt = "Enter selection";
		}

		int defaultIndex = -1;
		final ImmutableList<String> list = ImmutableList.copyOf(collection
				.iterator());
		for (int i = 0; i < list.size(); i++) {
			System.out.println("[" + (i + 1) + "] " + list.get(i));
			if (list.get(i).equals(defaultValue)) {
				defaultIndex = i + 1;
			}
		}

		if (StringUtils.isNotBlank(nonSelectionText)) {
			System.out.println("\n\n[0] " + nonSelectionText);
			prompt = prompt + "[0]: ";
			defaultIndex = 0;
		} else {
			prompt = prompt + ": ";
		}

		int choice = -1;
		int startIndex = 0;
		if (StringUtils.isBlank(nonSelectionText)) {
			startIndex = 1;
		}
		while ((choice < startIndex) || (choice > list.size())) {
			String input;
			try {
				input = getConsoleReader().readLine(prompt);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}

			if (StringUtils.isBlank(input)) {
				if ((StringUtils.isNotBlank(nonSelectionText) && (defaultIndex >= 0))
						|| (StringUtils.isBlank(nonSelectionText) && (defaultIndex >= 1))) {
					choice = defaultIndex;
				} else {
					continue;
				}
			} else {
				try {
					choice = Integer.parseInt(input);
				} catch (final Exception e) {
					continue;
				}
			}
		}

		if (choice == 0) {
			return null;
		} else {
			return list.get(choice - 1);
		}

	}

	public static void main(String[] args) throws InterruptedException {

		while (true) {

			setIndeterminateProgress("Testing...", true);

			Thread.sleep(4000);

			setIndeterminateProgress(false);

			System.out.println(" xx ");
		}

		// for (int i = 1; i < 100; i = i + 10) {
		//
		// setProgress(i, 100);
		// Thread.sleep(1000);
		//
		// }

	}

	private static String repetition(String string, int progress) {
		final StringBuffer result = new StringBuffer();
		for (int i = 0; i < progress; i++) {
			result.append(string);
		}
		return result.toString();
	}

	public static void setIndeterminateProgress(boolean start) {
		setIndeterminateProgress(null, start);
	}

	public static synchronized void setIndeterminateProgress(
			final String message, boolean start) {

		if (terminal == null) {
			return;
		}

		if (!ENABLE_PROGRESS) {
			System.out.println(start);
			return;
		}

		getConsoleReader().setDefaultPrompt("");

		if (start) {
			if (spinUpdater != null) {
				spinUpdater.setMessage(message);
			} else {
				spinUpdater = new SpinUpdater(message);
				timer = new Timer();
				timer.scheduleAtFixedRate(spinUpdater, 0L, DURATION);
			}
		} else {
			spinUpdater.mute(true);
			timer.cancel();
			spinUpdater = null;
			writeToTerminal("                                                ");
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

	public static void setProgress(int completed, int total) {
		if ((terminal == null) || !ENABLE_PROGRESS) {
			return;
		}

		getConsoleReader().setDefaultPrompt("");

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

	public static void writeToTerminal(String message) {
		writeToTerminal(message, false);
	}

	public static synchronized void writeToTerminal(String message, boolean mute) {
		if (!mute) {
			getConsoleReader().getCursorBuffer().clearBuffer();
			getConsoleReader().getCursorBuffer().write(message);
			try {
				getConsoleReader().setCursorPosition(getTermwidth());
				getConsoleReader().redrawLine();
			} catch (final IOException e) {
				myLogger.error(e.getLocalizedMessage(), e);
			}
		}
	}
}
