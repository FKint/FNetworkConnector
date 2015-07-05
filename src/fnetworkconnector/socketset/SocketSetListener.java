package fnetworkconnector.socketset;

import fnetworkconnector.SocketSet;

public interface SocketSetListener{
	public void dataSentSocket(Object data, SocketSet socketSet);
	public void dataReceived(Object data, SocketSet socketSet);
	public void socketSetInfoChanged(SocketSet socketSet);
	public void socketClosed(SocketSet socketSet);
}