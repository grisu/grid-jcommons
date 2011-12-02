package grisu.jcommons.view.cli;

import java.io.IOException;
import java.util.Collection;

import jline.ConsoleReader;
import jline.Terminal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

public class CliHelpers {


	static final Logger myLogger = LoggerFactory.getLogger(CliHelpers.class
			.getName());

	public static final Terminal terminal = Terminal.setupTerminal();
	private static ConsoleReader consoleReader = null;

	// private static ProgressDisplay progressSingleton = new
	// MuteProgressDisplay();
	private static ProgressDisplay progressSingleton = new LineByLineProgressDisplay();

	// private static ProgressDisplay progressSingleton = new
	// MuteProgressDisplay();


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

			setIndeterminateProgress("Testing 1 11111111111111111111111111",
					true);
			Thread.sleep(2000);
			// setIndeterminateProgress("Testing 2 222222222222222222222",
			// true);
			// Thread.sleep(2000);
			// setIndeterminateProgress("Testing 3 33333333333333", true);
			// Thread.sleep(2000);
			// setIndeterminateProgress("Testing 4 444444", true);
			// Thread.sleep(2000);
			setIndeterminateProgress("Testing 5", true);
			Thread.sleep(2000);

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

	public static void setIndeterminateProgress(boolean start) {
		progressSingleton.setIndeterminateProgress(start);
	}

	public static synchronized void setIndeterminateProgress(
			final String message, boolean start) {

		progressSingleton.setIndeterminateProgress(message, start);

	}

	public static void setProgress(int completed, int total) {
		progressSingleton.setProgress(completed, total);

	}

	public static synchronized void setProgressDisplay(ProgressDisplay pg) {
		progressSingleton = pg;
	}

	public static void writeToTerminal(String message) {

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
