package au.org.arcs.jcommons.utils;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import au.org.arcs.jcommons.configuration.CommonArcsProperties;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HttpProxyAuthPanel extends JPanel {
	private JLabel label;
	private JTextField textField;
	private JLabel label_1;
	private JPasswordField passwordField;
	private JButton button;

	/**
	 * Create the panel.
	 */
	public HttpProxyAuthPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(getLabel(), gbc);
		GridBagConstraints gbc_1 = new GridBagConstraints();
		gbc_1.insets = new Insets(0, 0, 5, 5);
		gbc_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_1.gridx = 2;
		gbc_1.gridy = 1;
		add(getTextField(), gbc_1);
		GridBagConstraints gbc_2 = new GridBagConstraints();
		gbc_2.anchor = GridBagConstraints.EAST;
		gbc_2.insets = new Insets(0, 0, 5, 5);
		gbc_2.gridx = 1;
		gbc_2.gridy = 2;
		add(getLabel_1(), gbc_2);
		GridBagConstraints gbc_3 = new GridBagConstraints();
		gbc_3.insets = new Insets(0, 0, 5, 5);
		gbc_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_3.gridx = 2;
		gbc_3.gridy = 2;
		add(getPasswordField(), gbc_3);
		GridBagConstraints gbc_4 = new GridBagConstraints();
		gbc_4.insets = new Insets(0, 0, 5, 5);
		gbc_4.anchor = GridBagConstraints.EAST;
		gbc_4.gridx = 2;
		gbc_4.gridy = 3;

	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Http proxy username");
		}
		return label;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setColumns(10);
			String username = CommonArcsProperties.getDefault().getArcsProperty(CommonArcsProperties.Property.HTTP_PROXY_USERNAME);
			if ( username != null && !"".equals(username) ) {
				textField.setText(username);
			}
		}
		return textField;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("Http proxy password");
		}
		return label_1;
	}
	private JPasswordField getPasswordField() {
		if (passwordField == null) {
			passwordField = new JPasswordField();
		}
		return passwordField;
	}
	
	public String getUsername() {
		return getTextField().getText();
	}
	
	public char[] getPassword() {
		return getPasswordField().getPassword();
	}
}
