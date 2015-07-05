package fnetworkconnector.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import fnetworkconnector.*;

public class FNCContainer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1921309363402438273L;
	
	protected UInterface ownerUI;
	
	protected FNCToolBarPanel toolbarPanel;
	//protected FNCLeftPanel leftPanel;
	public FNCTabbedPane mainTabPane;
	public FNCStatusBar statusbar;
	
	public FNCContainer(UInterface tmpOwnerUI){
		ownerUI = tmpOwnerUI;
		
		setLayout(new BorderLayout());
		toolbarPanel = new FNCToolBarPanel(ownerUI);
		this.add(toolbarPanel, BorderLayout.PAGE_START);
		
		/*leftPanel = new FNCLeftPanel();
		this.add(leftPanel, BorderLayout.LINE_START);*/
		
		mainTabPane = new FNCTabbedPane(ownerUI);
		this.add(mainTabPane, BorderLayout.CENTER);
		
		statusbar = new FNCStatusBar();
		this.add(statusbar, BorderLayout.PAGE_END);
	}
}
