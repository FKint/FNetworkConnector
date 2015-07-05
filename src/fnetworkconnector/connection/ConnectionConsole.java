package fnetworkconnector.connection;

import fnetworkconnector.*;

public class ConnectionConsole implements ConnectionHandler {
	
	public ConnectionConsole(){
	}
	
	public void dataReceived(Object data, SocketSet socketSet, Connection conn){}

	@Override
	public void dataSentGlobal(Object data, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dataSentSocket(Object data, SocketSet socketSet, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionMembersChanged(Connection conn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionDataChanged(Connection conn) {
		// TODO Auto-generated method stub
		
	}
}
