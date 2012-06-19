package grisu.jcommons.utils;

import java.lang.Thread.UncaughtExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultExceptionHandler implements UncaughtExceptionHandler {

	static final Logger myLogger = LoggerFactory
			.getLogger(DefaultExceptionHandler.class.getName());

	public void uncaughtException(Thread t, Throwable e) {
		myLogger.error(e.getLocalizedMessage(), e);

		System.err.println("unexpected error occurred : "
				+ e.getLocalizedMessage());
	}

}
