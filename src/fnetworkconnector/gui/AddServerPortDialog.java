package fnetworkconnector.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fnetworkconnector.Controller;
import fnetworkconnector.Model;

public class AddServerPortDialog extends JDialog{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6920105069382899839L;
	protected JPanel inputPanel;
	protected JTextField serverPortTxt;
	protected JPanel confirmPanel;
	protected JButton okBtn, cancelBtn;
	
	protected Model model;
	
	public AddServerPortDialog(Model model) {
		super();
		
		this.model = model;
		
		getContentPane().setLayout(new BorderLayout());
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		inputPanel.add(new JLabel(Controller.propBundle.getString("serverport")), c);
		c.gridx = 2;
		serverPortTxt = new JTextField();
		inputPanel.add(serverPortTxt, c);
		getContentPane().add(inputPanel, BorderLayout.CENTER);
		
		confirmPanel = new JPanel();
		confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.LINE_AXIS));
		confirmPanel.add(Box.createHorizontalGlue());
		okBtn = new JButton(Controller.propBundle.getString("ok"));
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (addServerPort()) {
					dispose();
				} else {
				}
			}
		});
		confirmPanel.add(okBtn);
		cancelBtn = new JButton(Controller.propBundle.getString("cancel"));
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		confirmPanel.add(cancelBtn);
		getContentPane().add(confirmPanel, BorderLayout.PAGE_END);
		
		setModal(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	protected boolean addServerPort() {
		
		try{
			int port = Integer.parseInt(serverPortTxt.getText());
			if(model.serverExists(port)){
				throw (new Exception(Controller.propBundle.getString("port_already_used_by_program")));
			}
			model.addServer(port);
			dispose();
			return true;
		}catch(NumberFormatException ex){
			JOptionPane.showMessageDialog(getContentPane(), Controller.propBundle.getString("fill_valid_value_serverport"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		}catch(Exception ex){
			JOptionPane.showMessageDialog(getContentPane(), ex.getMessage(), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		}
		return true;
	}

}
