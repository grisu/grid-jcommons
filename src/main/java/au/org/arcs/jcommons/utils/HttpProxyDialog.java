package au.org.arcs.jcommons.utils;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class HttpProxyDialog extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			HttpProxyDialog dialog = new HttpProxyDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	private final JPanel contentPanel = new JPanel();

	private HttpProxyPanel httpProxyPanel;

	/**
	 * Create the dialog.
	 */
	public HttpProxyDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			httpProxyPanel = new HttpProxyPanel();
			contentPanel.add(httpProxyPanel, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						String host = httpProxyPanel.getProxyHost();
						int port = httpProxyPanel.getProxyPort();

						if (host != null) {
							if (httpProxyPanel.isAuthSelected()) {
								String username = httpProxyPanel.getUsername();
								char[] password = httpProxyPanel.getPassword();
								HttpProxyManager.setHttpProxy(host, port,
										username, password);
							} else {
								HttpProxyManager.setHttpProxy(host, port, null,
										null);
							}
						}

						HttpProxyDialog.this.dispose();

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
