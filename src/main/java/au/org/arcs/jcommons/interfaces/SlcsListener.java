package au.org.arcs.jcommons.interfaces;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public interface SlcsListener {

	public void slcsLoginComplete(X509Certificate cert, PrivateKey privateKey);

	public void slcsLoginFailed(String message, Exception optionalException);

}
