package fnetworkconnector.gui;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import fnetworkconnector.*;

public class ServerSettingsPanel extends SettingsPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4873366089814701802L;
	protected JButton serverPortBtn;
	
	public ServerSettingsPanel(Model tmpModel) {
		super(tmpModel);
		
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridheight = 1;
		c.gridwidth = 1;
		
		c.gridx = 0;
		c.gridy = 1;
		add(new JLabel(Controller.propBundle.getString("serverport")), c);
		c.gridx = 1;
		serverPortBtn = new JButton(Controller.propBundle.getString("edit") + " " + Controller.propBundle.getString("serverport"));
		serverPortBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(model == null){
				}
				new ServerPortSettingsDialog(model);
			}
		});
		add(serverPortBtn, c);
		
		c.gridx = 0;
		c.gridy = 10;
		add(resetButton, c);
		c.gridx = 1;
		add(applyButton, c);
	}
	public void apply() {
	}
}
