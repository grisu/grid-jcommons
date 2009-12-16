package au.org.arcs.common.desktop;

import java.util.Locale;

public class Test {

	public static final String OS_NAME = System.getProperty("os.name")
			.toLowerCase(Locale.US);

	public static final String OS_ARCH = System.getProperty("os.arch")
			.toLowerCase(Locale.US);

	public static final String OS_VERSION = System.getProperty("os.version")
			.toLowerCase(Locale.US);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println(OS_NAME);
		System.out.println(OS_ARCH);
		System.out.println(OS_VERSION);
	}

}
