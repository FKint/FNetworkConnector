package fnetworkconnector;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fnetworkconnector.gui.*;

public class GUI extends JFrame implements UInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1418862175232715857L;
	protected Model model;
	
	protected FNCMenuBar menubar;
	protected FNCContainer container;
	protected TrayIcon trayIcon;
	
	public GUI(Model tmpModel) {
		super();
		
		setModel(tmpModel);

		setTitle(model.getOwnName());
		
		menubar = new FNCMenuBar(this);
		this.setJMenuBar(menubar);
		
		container = new FNCContainer(this);
		this.setContentPane(container);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				exitAsked();
			}
		});
		setSize(500, 500);
		setLocationRelativeTo(null);
		
		if(SystemTray.isSupported()){
			//http://java.sun.com/developer/technicalArticles/J2SE/Desktop/javase6/systemtray/
			SystemTray tray = SystemTray.getSystemTray();
			Image im = new ImageIcon(this.getClass().getResource("/resources/blank.png")).getImage();
			MouseListener listener = new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					if(isVisible()){
						setVisible(false);
					}else{
						if(e.getClickCount() == 11 || (e.getButton() == MouseEvent.BUTTON2 && e.getClickCount() == 2)){
							unTray();
						}
					}
				}
			};
			trayIcon = new TrayIcon(im, "", null);
			trayIcon.setImageAutoSize(true);
			trayIcon.addMouseListener(listener);
			try{
				tray.add(trayIcon);
			}catch(AWTException ex){
				
			}
			
		}
	}
	
	
	@Override
	public void aboutAsked() {
		model.aboutAsked();
	}
	
	@Override
	public void exitAsked() {
		model.exitAsked();
	}
	
	@Override
	public void newConnectionAsked() {
		model.newConnectionAsked();
		
	}
	
	@Override
	public void newConnectionAsked(InetAddress address, int port) {
		model.newConnectionAsked(address, port);
	}
	
	@Override
	public void newConnectionAdded(Connection conn) {
		// TODO Auto-generated method stub
		ConnectionGUI connGUI = new ConnectionGUI(conn);
		container.mainTabPane.addTab(connGUI.getTitle(), connGUI);
	}
	
	@Override
	public void makeNewConnectionAsked() {
		// TODO Auto-generated method stub
		new ConnectionDialog(this);
	}
	
	@Override
	public void serverSettingsAsked() {
		// TODO Auto-generated method stub
		SettingsDialog dialog = new SettingsDialog(model);
		dialog.setSelectedPanel(dialog.serverSettingsPanel);
	}
	
	@Override
	public void selectedActivityChanged() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Connection getSelectedConnection() {
		// TODO Auto-generated method stub
		Component comp = getSelectedConnectionGUI();
		if(comp instanceof ConnectionGUI){
			return ((ConnectionGUI)comp).getConnection();
		}else{
			return null;
		}
	}
	public ConnectionGUI getSelectedConnectionGUI(){
		Component comp = container.mainTabPane.getSelectedComponent();
		if(comp instanceof ConnectionGUI){
			return (ConnectionGUI)comp;
		}else{
			return null;
		}
	}

	@Override
	public void invite(Connection conn, InetAddress address, int port) {
		// TODO Auto-generated method stub
		conn.invite(address, port);
	}

	@Override
	public void inviteAsked() {
		// TODO Auto-generated method stub
		new InvitateDialog(this);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		setVisible(true);
	}


	@Override
	public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model = model;
		model.setInterface(this);
	}


	@Override
	public void exit() {
		// TODO Auto-generated method stub
		System.exit(0);
	}


	@Override
	public void showAbout(String string) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(getContentPane(), string);
	}


	@Override
	public void nameChanged() {
		// TODO Auto-generated method stub
		setTitle(model.getOwnName());
	}
	
	public void toSysTray(){
		setVisible(false);
		
	}
	public void unTray(){
		setVisible(true);
	}


	@Override
	public void hideUI() {
		// TODO Auto-generated method stub
		toSysTray();
	}


	@Override
	public void setStyle(String name) {
		// TODO Auto-generated method stub
		if(name.equals("MS ACCESS")){
			this.setIconImage(new ImageIcon(this.getClass().getResource("/resources/msaccess.gif")).getImage());
			trayIcon.setImage(new ImageIcon(this.getClass().getResource("/resources/msaccess.gif")).getImage());
			trayIcon.setToolTip("Microsoft Access 2003 Help");
		}
	}


	@Override
	public void setWaiting(boolean b) {
		// TODO Auto-generated method stub
		if(b){
			container.statusbar.setStatus("waiting");
			this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		}else{
			container.statusbar.setStatus("ready");
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
}
