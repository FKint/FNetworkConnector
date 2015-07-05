package fnetworkconnector.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import fnetworkconnector.*;

public class FNCMenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6702388732483069660L;
	
	protected UInterface owner;
	
	protected JMenu fileMenu;
	protected JMenuItem fileExitMenuItem;
	protected JMenu connectionMenu;
	protected JMenuItem newConnectionMenuItem;
	protected JMenuItem inviteConnectionMenuItem;
	protected JMenu settingsMenu;
	protected JMenuItem serverSettingsMenuItem;
	protected JMenuItem hideMenuItem;
	protected JMenu stylesSettingsMenu;
	protected JMenuItem msAccessStyleMenuItem;
	protected JMenu helpMenu;
	protected JMenuItem aboutHelpMenuItem;
	
	public FNCMenuBar(UInterface tmpOwner){
		super();
		this.owner = tmpOwner;
		
		fileMenu = new JMenu(Controller.propBundle.getString("file"));
		fileMenu.setMnemonic(Controller.propBundle.getString("file_afk").charAt(0));
		fileExitMenuItem = new JMenuItem(Controller.propBundle.getString("exit"));
		fileExitMenuItem.setMnemonic(Controller.propBundle.getString("exit_afk").charAt(0));
		fileExitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.exitAsked();
			}
		});
		fileMenu.add(fileExitMenuItem);
		this.add(fileMenu);
		connectionMenu = new JMenu(Controller.propBundle.getString("connection"));
		connectionMenu.setMnemonic(Controller.propBundle.getString("connection_afk").charAt(0));
		newConnectionMenuItem = new JMenuItem(Controller.propBundle.getString("new_connection"));
		newConnectionMenuItem.setMnemonic(Controller.propBundle.getString("new_connection_afk").charAt(0));
		newConnectionMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.newConnectionAsked();
			}
		});
		inviteConnectionMenuItem = new JMenuItem(Controller.propBundle.getString("invite"));
		inviteConnectionMenuItem.setMnemonic(Controller.propBundle.getString("invite_afk").charAt(0));
		inviteConnectionMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.inviteAsked();
			}
		});
		connectionMenu.add(inviteConnectionMenuItem);
		connectionMenu.add(newConnectionMenuItem);
		this.add(connectionMenu);
		settingsMenu = new JMenu(Controller.propBundle.getString("settings"));
		settingsMenu.setMnemonic(Controller.propBundle.getString("settings_afk").charAt(0));
		serverSettingsMenuItem = new JMenuItem(Controller.propBundle.getString("settings"));
		serverSettingsMenuItem.setMnemonic(Controller.propBundle.getString("settings_afk").charAt(0));
		serverSettingsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.serverSettingsAsked();
			}
		});
		settingsMenu.add(serverSettingsMenuItem);
		hideMenuItem = new JMenuItem(Controller.propBundle.getString("hide"));
		hideMenuItem.setMnemonic(Controller.propBundle.getString("hide_afk").charAt(0));
		hideMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		hideMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				owner.hideUI();
			}
		});
		settingsMenu.add(hideMenuItem);
		stylesSettingsMenu = new JMenu(Controller.propBundle.getString("styles"));
		msAccessStyleMenuItem = new JMenuItem("MS ACCESS");
		msAccessStyleMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				owner.setStyle("MS ACCESS");
			}
		});
		stylesSettingsMenu.add(msAccessStyleMenuItem);
		settingsMenu.add(stylesSettingsMenu);
		this.add(settingsMenu);
		helpMenu = new JMenu(Controller.propBundle.getString("help"));
		helpMenu.setMnemonic(Controller.propBundle.getString("help_afk").charAt(0));
		aboutHelpMenuItem = new JMenuItem(Controller.propBundle.getString("about"));
		aboutHelpMenuItem.setMnemonic(Controller.propBundle.getString("about_afk").charAt(0));
		aboutHelpMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				owner.aboutAsked();
			}
		});
		helpMenu.add(aboutHelpMenuItem);
		this.add(helpMenu);
	}
	
	//TODO: create method to check which items must be visible/enabled or unvisible/disabled
}
