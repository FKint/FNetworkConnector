package fnetworkconnector;

import java.net.InetAddress;

public interface UInterface {
	
	public Model model = null;
	
	public void exitAsked();
	public void newConnectionAsked();
	public void newConnectionAsked(InetAddress addres, int port);
	public void invite(Connection conn, InetAddress address, int port);
	public void serverSettingsAsked();
	public void aboutAsked();
	public void selectedActivityChanged();
	public void newConnectionAdded(Connection conn);
	public Connection getSelectedConnection();
	public void inviteAsked();
	public void start();
	
	public void setModel(Model model);
	public void makeNewConnectionAsked();
	public void exit();
	public void showAbout(String string);
	public void nameChanged();
	public void hideUI();
	public void setStyle(String name);
	public void setWaiting(boolean b);
}
