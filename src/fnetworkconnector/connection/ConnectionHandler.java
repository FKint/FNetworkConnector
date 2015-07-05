package fnetworkconnector.connection;

import fnetworkconnector.*;

public interface ConnectionHandler {
	public void dataReceived(Object data, SocketSet socketSet, Connection conn);

	public void dataSentGlobal(Object data, Connection conn);

	void dataSentSocket(Object data, SocketSet socketSet, Connection conn);
	
	public void connectionMembersChanged(Connection conn);
	
	public void connectionDataChanged(Connection conn);
}
