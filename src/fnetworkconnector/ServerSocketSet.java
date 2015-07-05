package fnetworkconnector;

import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JOptionPane;

import fnetworkconnector.serversocketset.*;

public class ServerSocketSet {

	protected ServerSocketAcceptorThread acceptorThread;
	public ServerSocket serverSocket;
	protected ServerSocketManager manager;
	
	public ServerSocketSet(){
		serverSocket = null;
		acceptorThread = null;
	}
	public ServerSocketSet(ServerSocket serverSocket, ServerSocketManager manager){
		this.manager = manager;
		this.serverSocket = serverSocket;
		acceptorThread = new ServerSocketAcceptorThread(this);
		acceptorThread.begin();
	}
	public ServerSocketSet(int serverport, ServerSocketManager manager)throws java.io.IOException{
		this(new ServerSocket(serverport), manager);
	}
	public void newIncomingSocket(SocketSet tmpSocket) {
		// TODO Auto-generated method stub
		manager.newIncomingSocket(tmpSocket);
		
	}
	

	public int getPort(){
		return serverSocket.getLocalPort();
	}
	public boolean close() {
		// TODO Auto-generated method stub
		try{
			acceptorThread.end();
			serverSocket.close();
			return true;
		}catch(IOException ex){
			//ex.printStackTrace();
			JOptionPane.showMessageDialog(null, Controller.propBundle.getString("socket_close_failed")+":\n"+ex.getStackTrace().toString(), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
}
