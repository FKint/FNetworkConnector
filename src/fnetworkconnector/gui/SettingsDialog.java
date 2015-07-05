package fnetworkconnector.gui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import fnetworkconnector.*;

public class SettingsDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2688556269587645565L;

	public ServerSettingsPanel serverSettingsPanel;
	public GeneralSettingsPanel generalSettingsPanel;

	protected JTabbedPane tabbedPane;
	
	protected Model model;

	public SettingsDialog(Model model) {
		super();
		
		this.model = model;
		
		getContentPane().setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		generalSettingsPanel = new GeneralSettingsPanel(this.model);
		serverSettingsPanel = new ServerSettingsPanel(this.model);
		
		tabbedPane.addTab(Controller.propBundle.getString("general"), generalSettingsPanel);//TODO propBundle
		tabbedPane.addTab(Controller.propBundle.getString("server"), serverSettingsPanel);//TODO pas aan
		
		setModal(true);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}

	public void setSelectedPanel(JPanel panel) {
		// TODO Auto-generated method stub
		if(tabbedPane.isAncestorOf(panel)){
			tabbedPane.setSelectedComponent(panel);
		}
	}
}
