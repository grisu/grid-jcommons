package grisu.jcommons.utils;

import java.security.KeyStore;
import java.security.Provider;
import java.security.cert.X509Certificate;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509TrustManager;

public class DefaultGridSecurityProvider extends Provider {

	protected static class MyTrustManagerFactory extends TrustManagerFactorySpi {
		public MyTrustManagerFactory() {
		}

		@Override
		protected TrustManager[] engineGetTrustManagers() {
			return new TrustManager[] { new MyX509TrustManager() };
		}

		@Override
		protected void engineInit(KeyStore keystore) {
		}

		@Override
		protected void engineInit(ManagerFactoryParameters mgrparams) {
		}
	}

	protected static class MyX509TrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}

	private static final long serialVersionUID = 1L;

	public DefaultGridSecurityProvider() {
		super("DefaultGridSecurityProvider", 1.0, "Trust certificates");
		put("TrustManagerFactory.TrustAllCertificates",
				MyTrustManagerFactory.class.getName());
	}

}
