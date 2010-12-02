package au.org.arcs.jcommons.utils;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import org.apache.commons.lang.StringUtils;
import org.bushe.swing.event.EventBus;

import au.org.arcs.jcommons.configuration.CommonArcsProperties;

public class HttpProxyManager {

	private static String currentHttpProxyHost = null;
	private static int currentHttpProxyPort = 80;

	public static String getCurrentHttpProxyHost() {
		return currentHttpProxyHost;
	}

	public static int getCurrentHttpProxyPort() {
		return currentHttpProxyPort;
	}

	public static String lastTimeHttpProxyAuthUsername() {

		String httproxyusername = CommonArcsProperties.getDefault()
				.getArcsProperty(
						CommonArcsProperties.Property.HTTP_PROXY_USERNAME);

		if ((httproxyusername == null) || "".equals(httproxyusername)) {
			return httproxyusername;
		} else {
			return null;
		}
	}

	// public static void askForHttpProxyAuthIfNecessarySwing() {
	//
	// String username =
	// CommonArcsProperties.getDefault().getArcsProperty(CommonArcsProperties.Property.HTTP_PROXY_USERNAME);
	// if ( username != null && !"".equals(username) ) {
	// HttpProxyAuthDialog dialog = new HttpProxyAuthDialog();
	// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	// dialog.setVisible(true);
	// }
	//
	// }

	public static String lastTimeHttpProxyHost() {
		return CommonArcsProperties.getDefault().getArcsProperty(
				CommonArcsProperties.Property.HTTP_PROXY_HOST);
	}

	public static Integer lastTimeHttpProxyPort() {

		return Integer
				.parseInt(CommonArcsProperties.getDefault().getArcsProperty(
						CommonArcsProperties.Property.HTTP_PROXY_PORT));
	}

	public static void setDefaultHttpProxy() {

		String httpProxy = lastTimeHttpProxyHost();
		if ((httpProxy == null) || "".equals(httpProxy)) {
			return;
		}
		int httpProxyPort = lastTimeHttpProxyPort();

		setHttpProxy(httpProxy, httpProxyPort, null, null);

	}

	private static void setHttpAuth(final String username, final char[] password) {

		Authenticator.setDefault(new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}

		});

		CommonArcsProperties.getDefault().setArcsProperty(
				CommonArcsProperties.Property.HTTP_PROXY_USERNAME, username);

	}

	private static void setHttpProxy(String proxyHost, int proxyPort) {

		if (StringUtils.isBlank(proxyHost)) {
			System.getProperties().put("proxySet", "false");
			System.getProperties().put("proxyHost", "");
			System.getProperties().put("proxyPort", 80);
			System.setProperty("http.proxyHost", "");
			System.setProperty("http.proxyPort", "80");
			CommonArcsProperties.getDefault().setArcsProperty(
					CommonArcsProperties.Property.HTTP_PROXY_HOST, "");
			CommonArcsProperties.getDefault().setArcsProperty(
					CommonArcsProperties.Property.HTTP_PROXY_PORT, "");
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
			CommonArcsProperties.getDefault().setArcsProperty(
					CommonArcsProperties.Property.HTTP_PROXY_HOST, proxyHost);
			CommonArcsProperties.getDefault().setArcsProperty(
					CommonArcsProperties.Property.HTTP_PROXY_PORT,
					new Integer(proxyPort).toString());
			currentHttpProxyHost = proxyHost;
			currentHttpProxyPort = proxyPort;
			;
		}

	}

	public static void setHttpProxy(String proxyHost, int proxyPort,
			String username, char[] password) {

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
			CommonArcsProperties.getDefault().setArcsProperty(
					CommonArcsProperties.Property.HTTP_PROXY_USERNAME, "");

		}

		EventBus.publish(new NewHttpProxyEvent(proxyHost, proxyPort, username,
				password));
	}

	public static void useSystemHttpProxy() {
		System.setProperty("java.net.useSystemProxies", "true");
	}

}
