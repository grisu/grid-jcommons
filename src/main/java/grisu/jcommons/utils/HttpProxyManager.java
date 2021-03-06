package grisu.jcommons.utils;

import grisu.jcommons.configuration.CommonGridProperties;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.bushe.swing.event.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpProxyManager {

	static final Logger myLogger = LoggerFactory
			.getLogger(HttpProxyManager.class.getName());

	private static String currentHttpProxyHost = null;
	private static int currentHttpProxyPort = 80;

	public static String getCurrentHttpProxyHost() {
		return currentHttpProxyHost;
	}

	public static int getCurrentHttpProxyPort() {
		return currentHttpProxyPort;
	}

	public static String lastTimeHttpProxyAuthUsername() {

		String httproxyusername = CommonGridProperties.getDefault()
				.getGridProperty(
						CommonGridProperties.Property.HTTP_PROXY_USERNAME);

		if ((httproxyusername == null) || "".equals(httproxyusername)) {
			return httproxyusername;
		} else {
			return null;
		}
	}

	// public static void askForHttpProxyAuthIfNecessarySwing() {
	//
	// String username =
	// CommonGridProperties.getDefault().getArcsProperty(CommonGridProperties.Property.HTTP_PROXY_USERNAME);
	// if ( username != null && !"".equals(username) ) {
	// HttpProxyAuthDialog dialog = new HttpProxyAuthDialog();
	// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	// dialog.setVisible(true);
	// }
	//
	// }

	public static String lastTimeHttpProxyHost() {
		return CommonGridProperties.getDefault().getGridProperty(
				CommonGridProperties.Property.HTTP_PROXY_HOST);
	}

	public static Integer lastTimeHttpProxyPort() {

		return Integer
				.parseInt(CommonGridProperties.getDefault().getGridProperty(
						CommonGridProperties.Property.HTTP_PROXY_PORT));
	}

	public static void setDefaultHttpProxy() {

		myLogger.debug("Setting default http proxy if necessary...");

		String httpProxy = lastTimeHttpProxyHost();
		if ((httpProxy == null) || "".equals(httpProxy)) {

			// check whether there is a proxy setting from getdown
			myLogger.debug("Checking getdown proxy settings...");
			File proxySettings = new File(new File("."), "proxy.txt");
			if (!proxySettings.exists()) {
				myLogger.debug(
						"No proxy settings at: {}, not setting http proxy.",
						proxySettings.getAbsolutePath());
				return;
			}
			Properties prop = new Properties();

			try {
				// load a properties file
				prop.load(new FileInputStream(proxySettings));

				String host = prop.getProperty("host");
				Integer port = Integer.parseInt(prop.getProperty("port"));

				myLogger.debug(
						"Successfully read properties file, http proxy host: {}, port: {}",
						host, port.toString());

				setHttpProxy(host, port, null, null);
				return;

			} catch (Exception ex) {
				myLogger.debug("Failed reading {}: {}",
						proxySettings.getAbsolutePath(),
						ex.getLocalizedMessage());
				return;
			}

		}
		int httpProxyPort = lastTimeHttpProxyPort();

		myLogger.debug("Setting http proxy to: {} : {}", httpProxy,
				httpProxyPort);
		setHttpProxy(httpProxy, httpProxyPort, null, null);

	}

	private static void setHttpAuth(final String username, final char[] password) {

		Authenticator.setDefault(new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}

		});

		CommonGridProperties.getDefault().setGridProperty(
				CommonGridProperties.Property.HTTP_PROXY_USERNAME, username);

	}

	private static void setHttpProxy(String proxyHost, int proxyPort) {

		if (StringUtils.isBlank(proxyHost)) {
			System.getProperties().put("proxySet", "false");
			System.getProperties().put("proxyHost", "");
			System.getProperties().put("proxyPort", 80);
			System.setProperty("http.proxyHost", "");
			System.setProperty("http.proxyPort", "80");
			CommonGridProperties.getDefault().setGridProperty(
					CommonGridProperties.Property.HTTP_PROXY_HOST, "");
			CommonGridProperties.getDefault().setGridProperty(
					CommonGridProperties.Property.HTTP_PROXY_PORT, "");
			currentHttpProxyHost = null;
			currentHttpProxyPort = 80;
		} else {
			System.getProperties().put("proxySet", "true");
			System.getProperties().put("proxyHost", proxyHost);
			System.getProperties().put("proxyPort",
					new Integer(proxyPort).toString());
			System.setProperty("http.proxyHost", proxyHost);
			System.setProperty("http.proxyPort",
					new Integer(proxyPort).toString());
			CommonGridProperties.getDefault().setGridProperty(
					CommonGridProperties.Property.HTTP_PROXY_HOST, proxyHost);
			CommonGridProperties.getDefault().setGridProperty(
					CommonGridProperties.Property.HTTP_PROXY_PORT,
					new Integer(proxyPort).toString());
			currentHttpProxyHost = proxyHost;
			currentHttpProxyPort = proxyPort;
			;
		}

	}

	public static void setHttpProxy(String proxyHost, int proxyPort,
			String username, char[] password) {

		try {
			Class shibClass = Class.forName("grith.sibboleth.Shibboleth");
			Method m = shibClass.getMethod("setHttpProxy", String.class,
					int.class, String.class, char[].class);
			m.invoke(null, proxyHost, proxyPort, username, password);
		} catch (Exception e) {
			myLogger.debug("Not setting http proxy for Shibboleth class");
			// probably not in classpath
		}

		setHttpProxy(proxyHost, proxyPort);
		if (StringUtils.isNotBlank(username)) {
			setHttpAuth(username, password);
			// for jython
			System.getProperties().put(
					"http_proxy",
					"http://" + username + ":" + new String(password) + "@"
							+ proxyHost + ":" + proxyPort + "/");
			System.getProperties().put(
					"https_proxy",
					"http://" + username + ":" + new String(password) + "@"
							+ proxyHost + ":" + proxyPort + "/");
		} else {
			// for jython
			System.getProperties().put("http_proxy",
					"http://" + proxyHost + ":" + proxyPort + "/");
			System.getProperties().put("https_proxy",
					"http://" + proxyHost + ":" + proxyPort + "/");
			CommonGridProperties.getDefault().setGridProperty(
					CommonGridProperties.Property.HTTP_PROXY_USERNAME, "");

		}

		EventBus.publish(new NewHttpProxyEvent(proxyHost, proxyPort, username,
				password));
	}

	public static void useSystemHttpProxy() {
		System.setProperty("java.net.useSystemProxies", "true");
	}

}
