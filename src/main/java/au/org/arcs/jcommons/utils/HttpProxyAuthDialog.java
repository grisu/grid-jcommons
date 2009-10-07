package au.org.arcs.jcommons.utils;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HttpProxyAuthDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private HttpProxyAuthPanel httpProxyAuthPanel = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			HttpProxyAuthDialog dialog = new HttpProxyAuthDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public HttpProxyAuthDialog() {
		setModal(true);
		setBounds(100, 100, 450, 163);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			httpProxyAuthPanel = new HttpProxyAuthPanel();
			contentPanel.add(httpProxyAuthPanel, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
								
						String username = httpProxyAuthPanel.getUsername();
						char[] password = httpProxyAuthPanel.getPassword();
								
						HttpProxyManager.setHttpAuth(username, password);
						
						HttpProxyAuthDialog.this.dispose();
					}
				});
				okButton.setActionCommand("Apply");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
