package fnetworkconnector.servermanager;

public interface ServerManagerListener {
	public void ownServerPortChanged(int ownServerPort);
	public void serversChanged();
}
