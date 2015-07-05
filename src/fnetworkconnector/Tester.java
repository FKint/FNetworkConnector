package fnetworkconnector;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import fnetworkconnector.connection.ConnectionOwner;
import fnetworkconnector.messages.Message;
import fnetworkconnector.socketset.SocketSetListener;

public class Tester implements SocketSetListener, ConnectionOwner{

	/**
	 * @param args
	 */
	
	protected SocketSet inS;
	protected ServerManager man;
	protected Model mod;
	protected Connection conn;
	protected Storage stor;
	protected UInterface ui;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Tester();
	}
	
	public Tester(){
		try{
			stor = new Storage();
			mod = new Model(stor);
			ui = new GUI(mod);
			mod.addServer(23232);

			inS = new SocketSet(new Socket(InetAddress.getLocalHost(), 23232), this);
			try{
				//System.out.println("create new connection");
				Connection tmpConnection = new Connection(/*Controller.getKind(), */23232, this);
				//System.out.println("connection created");
				SocketSet socketSet = new SocketSet(InetAddress.getLocalHost(), 23232);
				//System.out.println("socketSet created");
				tmpConnection.newSocketAdd(socketSet);
				//System.out.println("newsocketAdd voltooid");
				//socketSet.output.sendData(new NewConnectionRequest());
				//socketSet.output.sendData(new NewConnectionRequest());
				//uInterface.newConnectionAdded(tmpConnection);
				mod.newConnectionAdded(tmpConnection);
				//storage.connections.add(tmpConnection);
				//TODO: handel verder af
				//System.out.println("end new connection create");
			}catch(IOException ex){
				ex.printStackTrace();
			}
			inS.sendData("heylaba");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void dataReceived(Object data, SocketSet socketSet) {
		// TODO Auto-generated method stub
		//System.out.println("data received: " + data);
	}

	@Override
	public void dataSentSocket(Object data, SocketSet socketSet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void socketSetInfoChanged(SocketSet socketSet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void appDataReceived(Message message, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOwnName() {
		// TODO Auto-generated method stub
		return "me";
	}

	@Override
	public int getOwnServerPort() {
		// TODO Auto-generated method stub
		return 23232;
	}

	@Override
	public void socketClosed(SocketSet socketSet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeConnection(Connection connection) {
		// TODO Auto-generated method stub
		
	}

}
