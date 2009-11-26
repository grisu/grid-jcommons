package au.org.arcs.jcommons.dependencies;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class DownloadingDialog extends JDialog {

	static final long serialVersionUID = -7493900276355066466L;
	private JProgressBar progressBar;
	private JLabel label;
	
	private final String filename;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DownloadingDialog dialog = new DownloadingDialog("Dummy");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		});
	}

	
	
	/**
	 * Create the dialog.
	 */
	public DownloadingDialog(String filename) {
		setModal(false);
		setLocationRelativeTo(null); 
		this.filename = filename;
		setBounds(100, 100, 288, 122);
		getContentPane().setLayout(null);
		getContentPane().add(getProgressBar());
		getContentPane().add(getLabel());

	}
	private JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setIndeterminate(true);
			progressBar.setBounds(12, 30, 254, 25);
		}
		return progressBar;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel(filename);
			label.setBounds(12, 67, 254, 18);
		}
		return label;
	}
	
	public void setMessage(String message) {
		getLabel().setText(message);
	}
}
