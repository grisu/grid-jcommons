package au.org.arcs.jcommons.utils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import au.org.arcs.jcommons.configuration.CommonArcsProperties;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HttpProxyPanel extends JPanel {
	private JLabel label;
	private JTextField proxyHostTextField;
	private JLabel label_1;
	private JTextField httpProxyPortField;
	private JCheckBox authCheckbox;
	private JLabel label_2;
	private JLabel label_3;
	private JTextField httpProxyUsernameTextField;
	private JPasswordField httpProxyPasswordField;
	private JButton applyButton;
	private Action action;
	private JButton btnApply;

	/**
	 * Create the panel.
	 */
	public HttpProxyPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{14, 0, 0, 12};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0};
		setLayout(gridBagLayout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(getLabel(), gbc);
		GridBagConstraints gbc_1 = new GridBagConstraints();
		gbc_1.insets = new Insets(0, 0, 5, 5);
		gbc_1.fill = GridBagConstraints.BOTH;
		gbc_1.gridx = 2;
		gbc_1.gridy = 0;
		add(getProxyHostTextField(), gbc_1);
		GridBagConstraints gbc_2 = new GridBagConstraints();
		gbc_2.anchor = GridBagConstraints.EAST;
		gbc_2.insets = new Insets(0, 0, 5, 5);
		gbc_2.gridx = 1;
		gbc_2.gridy = 1;
		add(getLabel_1(), gbc_2);
		GridBagConstraints gbc_3 = new GridBagConstraints();
		gbc_3.insets = new Insets(0, 0, 5, 5);
		gbc_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_3.gridx = 2;
		gbc_3.gridy = 1;
		add(getHttpProxyPortField(), gbc_3);
		GridBagConstraints gbc_4 = new GridBagConstraints();
		gbc_4.insets = new Insets(0, 0, 5, 5);
		gbc_4.anchor = GridBagConstraints.WEST;
		gbc_4.gridwidth = 2;
		gbc_4.gridx = 1;
		gbc_4.gridy = 2;
		add(getAuthCheckbox(), gbc_4);
		GridBagConstraints gbc_5 = new GridBagConstraints();
		gbc_5.anchor = GridBagConstraints.EAST;
		gbc_5.insets = new Insets(0, 0, 5, 5);
		gbc_5.gridx = 1;
		gbc_5.gridy = 3;
		add(getLabel_2(), gbc_5);
		GridBagConstraints gbc_7 = new GridBagConstraints();
		gbc_7.insets = new Insets(0, 0, 5, 5);
		gbc_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_7.gridx = 2;
		gbc_7.gridy = 3;
		add(getHttpProxyUsernameTextField(), gbc_7);
		GridBagConstraints gbc_6 = new GridBagConstraints();
		gbc_6.anchor = GridBagConstraints.EAST;
		gbc_6.insets = new Insets(0, 0, 5, 5);
		gbc_6.gridx = 1;
		gbc_6.gridy = 4;
		add(getLabel_3(), gbc_6);
		GridBagConstraints gbc_8 = new GridBagConstraints();
		gbc_8.insets = new Insets(0, 0, 5, 5);
		gbc_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_8.gridx = 2;
		gbc_8.gridy = 4;
		add(getHttpProxyPasswordField(), gbc_8);
		GridBagConstraints gbc_10 = new GridBagConstraints();
		gbc_10.anchor = GridBagConstraints.EAST;
		gbc_10.insets = new Insets(0, 0, 0, 5);
		gbc_10.gridx = 2;
		gbc_10.gridy = 5;
		add(getBtnApply(), gbc_10);
		GridBagConstraints gbc_9 = new GridBagConstraints();
		gbc_9.insets = new Insets(0, 0, 0, 5);
		gbc_9.anchor = GridBagConstraints.EAST;
		gbc_9.gridx = 2;
		gbc_9.gridy = 5;

	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Http proxy host");
		}
		return label;
	}
	private JTextField getProxyHostTextField() {
		if (proxyHostTextField == null) {
			proxyHostTextField = new JTextField();
			proxyHostTextField.setColumns(10);
			proxyHostTextField.setText(CommonArcsProperties.getDefault().getArcsProperty(CommonArcsProperties.Property.HTTP_PROXY_HOST));
		}
		return proxyHostTextField;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("Http proxy port");
		}
		return label_1;
	}
	private JTextField getHttpProxyPortField() {
		if (httpProxyPortField == null) {
			httpProxyPortField = new JTextField();
			httpProxyPortField.setColumns(10);
			httpProxyPortField.setText(CommonArcsProperties.getDefault().getArcsProperty(CommonArcsProperties.Property.HTTP_PROXY_PORT));
		}
		return httpProxyPortField;
	}
	private JCheckBox getAuthCheckbox() {
		if (authCheckbox == null) {
			authCheckbox = new JCheckBox("Authentication required");
			authCheckbox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					
					getHttpProxyUsernameTextField().setEnabled(getAuthCheckbox().isSelected());
					getHttpProxyPasswordField().setEnabled(getAuthCheckbox().isSelected());
					
				}
			});
			
			String username = CommonArcsProperties.getDefault().getArcsProperty(CommonArcsProperties.Property.HTTP_PROXY_USERNAME);
			if ( username != null && !"".equals(username) ) {
				authCheckbox.setSelected(true);
			}
		}
		return authCheckbox;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("Http proxy username");
		}
		return label_2;
	}
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel("Http proxy password");
		}
		return label_3;
	}
	private JTextField getHttpProxyUsernameTextField() {
		if (httpProxyUsernameTextField == null) {
			httpProxyUsernameTextField = new JTextField();
			httpProxyUsernameTextField.setEnabled(false);
			httpProxyUsernameTextField.setColumns(10);
			String username = CommonArcsProperties.getDefault().getArcsProperty(CommonArcsProperties.Property.HTTP_PROXY_USERNAME);
			httpProxyUsernameTextField.setText(username);
		}
		return httpProxyUsernameTextField;
	}
	private JPasswordField getHttpProxyPasswordField() {
		if (httpProxyPasswordField == null) {
			httpProxyPasswordField = new JPasswordField();
			httpProxyPasswordField.setEnabled(false);
		}
		return httpProxyPasswordField;
	}
	
	public boolean isAuthSelected() {
		return getAuthCheckbox().isSelected();
	}
	
	public String getUsername() {
		return getHttpProxyUsernameTextField().getText();
	}
	
	public String getProxyHost() {
		return getProxyHostTextField().getText();
	}
	
	public int getProxyPort() {
		try {
			return new Integer(getHttpProxyPortField().getText());
		} catch (Exception e) {
			return 0;
		}
	}
	
	public char[] getPassword() {
		return getHttpProxyPasswordField().getPassword();
	}


	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton("Apply");
			btnApply.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String host = getProxyHost();
					int port = getProxyPort();
					
					if ( host != null ) {
						if ( isAuthSelected() ) {
							String username = getUsername();
							char[] password = getPassword();
							HttpProxyManager.setHttpProxy(host, port, username, password);
						} else {
							HttpProxyManager.setHttpProxy(host, port, null, null);
						}
					}

						
					
				}
			});
		}
		return btnApply;
	}
}
