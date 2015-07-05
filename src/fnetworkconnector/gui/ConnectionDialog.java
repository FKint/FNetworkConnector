package fnetworkconnector.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import fnetworkconnector.Controller;
import fnetworkconnector.GUI;
import fnetworkconnector.Tools;

public class ConnectionDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2190790110500467516L;
	
	protected GUI ownerGUI;
	
	protected JButton okButton, cancelButton;
	protected JTextField hostText, portText;
	
	public ConnectionDialog(GUI owner) {
		super(owner);
		this.ownerGUI = owner;
		getContentPane().setLayout(new GridBagLayout());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		
		c.gridx = 0;
		c.gridy = 1;
		add(new JLabel(Controller.propBundle.getString("hostname")), c);
		c.gridx = 1;
		hostText = new JTextField();
		add(hostText, c);
		c.gridx = 0;
		c.gridy = 2;
		add(new JLabel(Controller.propBundle.getString("portnumber")), c);
		c.gridx = 1;
		portText = new JTextField(""/* + connectionStandardPort */);// TODO:
																	// edit
		add(portText, c);
		c.gridx = 0;
		c.gridy = 3;
		cancelButton = new JButton(Controller.propBundle.getString("cancel"));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		add(cancelButton, c);
		c.gridx = 1;
		okButton = new JButton(Controller.propBundle.getString("ok"));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAction();
				
			}
		});
		add(okButton, c);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setModal(true);
	}
	
	public String getTitle(){
		return Controller.propBundle.getString("new_connection");
	}
	
	public void doAction() {
		// TODO: vervang
		try {
			ownerGUI.newConnectionAsked(InetAddress.getByName(Tools.modifyInetAddress(hostText.getText())), Integer.parseInt(portText.getText()));
			dispose();
		} catch (NumberFormatException ex) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(getContentPane(), Controller.propBundle.getString("invalid_value"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			//ex.printStackTrace();
		} catch (UnknownHostException ex) {
			// TODO Auto-generated catch block
			//ex.printStackTrace();
			JOptionPane.showMessageDialog(getContentPane(), Controller.propBundle.getString("filled_host_not_available"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		}
	}
}
