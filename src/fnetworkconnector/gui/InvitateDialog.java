package fnetworkconnector.gui;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import fnetworkconnector.Connection;
import fnetworkconnector.Controller;
import fnetworkconnector.GUI;
import fnetworkconnector.Tools;

public class InvitateDialog extends ConnectionDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 389226852022042029L;

	public InvitateDialog(GUI owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}
	public String getTitle(){
		return Controller.propBundle.getString("invite");
	}
	
	public void doAction() {
		// TODO: vervang
		try {
			Connection conn = ownerGUI.getSelectedConnection();
			ownerGUI.invite(conn, InetAddress.getByName(Tools.modifyInetAddress(hostText.getText())), Integer.parseInt(portText.getText()));
			dispose();
		} catch (NumberFormatException ex) {
			// TODO Auto-generated catch block
			//ex.printStackTrace();
			JOptionPane.showMessageDialog(getContentPane(), Controller.propBundle.getString("invalid_value"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		} catch (UnknownHostException ex) {
			// TODO Auto-generated catch block
			//ex.printStackTrace();
			JOptionPane.showMessageDialog(getContentPane(), Controller.propBundle.getString("filled_host_not_available"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		}
	}
}
