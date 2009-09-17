package au.org.arcs.jcommons.utils;

import java.security.KeyStore;
import java.security.Provider;
import java.security.cert.X509Certificate;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509TrustManager;


public class ArcsSecurityProvider extends Provider {

	public ArcsSecurityProvider() {
		super("ArcsSecurityProvider", 1.0, "Trust certificates");
		put("TrustManagerFactory.TrustAllCertificates",
				MyTrustManagerFactory.class.getName());
	}

	protected static class MyTrustManagerFactory extends TrustManagerFactorySpi {
		public MyTrustManagerFactory() {
		}

		protected void engineInit(KeyStore keystore) {
		}

		protected void engineInit(ManagerFactoryParameters mgrparams) {
		}

		protected TrustManager[] engineGetTrustManagers() {
			return new TrustManager[] { new MyX509TrustManager() };
		}
	}

	protected static class MyX509TrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

}
