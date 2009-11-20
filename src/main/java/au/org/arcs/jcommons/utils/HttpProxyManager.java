package au.org.arcs.jcommons.utils;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import javax.swing.JDialog;

import au.org.arcs.jcommons.configuration.CommonArcsProperties;

public class HttpProxyManager {
	
	public static void setDefaultHttpProxy() {
		
		String httpProxy = CommonArcsProperties.getDefault().getArcsProperty(CommonArcsProperties.Property.HTTP_PROXY_HOST);
		if ( httpProxy == null || "".equals(httpProxy) ) {
			return;
		}
		int httpProxyPort = Integer.parseInt(CommonArcsProperties.getDefault().getArcsProperty(CommonArcsProperties.Property.HTTP_PROXY_PORT));
		
		setHttpProxy(httpProxy, httpProxyPort);
		
	}
	
	public static void askForHttpProxyAuthIfNecessarySwing() {
		
		String username = CommonArcsProperties.getDefault().getArcsProperty(CommonArcsProperties.Property.HTTP_PROXY_USERNAME);
		if ( username != null && !"".equals(username) ) {
			HttpProxyAuthDialog dialog = new HttpProxyAuthDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		
	}
	
	public String lastTimeHttpProxyAuthUsername() {
		
		String httproxyusername = CommonArcsProperties.getDefault().getArcsProperty(CommonArcsProperties.Property.HTTP_PROXY_USERNAME);
		
		if ( httproxyusername == null || "".equals(httproxyusername) ) {
			return httproxyusername;
		} else {
			return null;
		}
	}
	
	public static void setHttpProxy(String proxyHost, int proxyPort) {
		
		System.getProperties().put( "proxySet", "true" );
		System.getProperties().put( "proxyHost", proxyHost );
		System.getProperties().put( "proxyPort", new Integer(proxyPort).toString() );
		
		System.setProperty("http.proxyHost", proxyHost);  
		System.setProperty("http.proxyPort", new Integer(proxyPort).toString());  
		
		CommonArcsProperties.getDefault().setArcsProperty(CommonArcsProperties.Property.HTTP_PROXY_HOST, proxyHost);
		CommonArcsProperties.getDefault().setArcsProperty(CommonArcsProperties.Property.HTTP_PROXY_PORT, new Integer(proxyPort).toString());
		
		
	}
	
	public static void setHttpAuth(final String username, final char[] password) {
		
		Authenticator.setDefault(new Authenticator(){
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {  
			        return new PasswordAuthentication(username, password);  
			}  
			
		});
		
		CommonArcsProperties.getDefault().setArcsProperty(CommonArcsProperties.Property.HTTP_PROXY_USERNAME, username);	

	}
	


}
