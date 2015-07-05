package fnetworkconnector.gui;

import java.io.IOException;
import java.net.InetAddress;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fnetworkconnector.Controller;

public class FNCStatusBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6370958162341835363L;
	protected JLabel statuslabel;

	public FNCStatusBar() {
		super();

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		statuslabel = new JLabel("");
		add(statuslabel);
		add(Box.createHorizontalGlue());
		try {
			add(new JLabel("IP: " + InetAddress.getLocalHost().getHostName()));
		} catch (IOException ex) {
			// ex.printStackTrace();
			JOptionPane.showMessageDialog(this, Controller.propBundle
					.getString("error_determine_own_ip"), Controller.propBundle
					.getString("error"), JOptionPane.WARNING_MESSAGE);
		}
		// TODO: aantal servers, main-server
	}

	public void setStatus(String string) {
		// TODO Auto-generated method stub
		statuslabel.setText(Controller.propBundle.getString(string));
	}
}
