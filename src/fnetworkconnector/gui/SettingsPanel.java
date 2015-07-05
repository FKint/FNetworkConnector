package fnetworkconnector.gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import fnetworkconnector.Controller;
import fnetworkconnector.Model;

public class SettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2908690139836741093L;
	protected JButton applyButton;
	protected JButton resetButton;
	
	protected Model model;
	
	
	public SettingsPanel(Model model) {
		super();
		
		setModel(model);
		
		setLayout(new GridBagLayout());
		applyButton = new JButton(Controller.propBundle.getString("apply"));
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				apply();
			}
		});
		resetButton = new JButton(Controller.propBundle.getString("reset"));
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
	}
	public void apply() {
	}
	public void reset() {
	}
	

	
	public void setModel(Model model){
		this.model = model;
	}
}
