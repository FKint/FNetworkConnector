package fnetworkconnector.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import fnetworkconnector.*;

public class FNCConnectionToolBar extends JToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6574142947374910131L;
	
	protected UInterface ownerUI;
	
	protected JButton mainToolbarNewConnectionButton;
	
	public FNCConnectionToolBar(UInterface tmpOwnerUI) {
		super();
		
		ownerUI = tmpOwnerUI;
		
		mainToolbarNewConnectionButton = new JButton(Controller.propBundle.getString("new_connection"));
		mainToolbarNewConnectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ownerUI.makeNewConnectionAsked();
			}
		});
		this.add(mainToolbarNewConnectionButton);
	}
}
