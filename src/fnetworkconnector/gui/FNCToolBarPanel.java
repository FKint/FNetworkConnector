package fnetworkconnector.gui;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import fnetworkconnector.*;

public class FNCToolBarPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8742047157543008656L;
	
	protected UInterface ownerUI;
	
	protected JPanel mainToolbarPanel;
	protected FNCConnectionToolBar mainToolbar;
	
	public FNCToolBarPanel(UInterface tmpOwnerUI) {
		super();
		
		ownerUI = tmpOwnerUI;
		
		setLayout(new BorderLayout());
		mainToolbarPanel = new JPanel();
		mainToolbarPanel.setLayout(new BoxLayout(mainToolbarPanel, BoxLayout.LINE_AXIS));
		mainToolbar = new FNCConnectionToolBar(ownerUI);
		mainToolbarPanel.add(mainToolbar);
		this.add(mainToolbarPanel);
	}
}
