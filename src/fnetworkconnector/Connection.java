package fnetworkconnector;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.net.InetAddress;

import javax.swing.JOptionPane;

import fnetworkconnector.messages.InformationMessage;
import fnetworkconnector.messages.InformationRequest;
import fnetworkconnector.messages.InvitationConfirmation;
import fnetworkconnector.messages.InviteRequest;
import fnetworkconnector.messages.InvitedWarning;
import fnetworkconnector.messages.NewConnectionRequest;
import fnetworkconnector.socketset.*;
import fnetworkconnector.connection.*;

public class Connection implements SocketSetListener{
	
	protected String title = Controller.propBundle.getString("conversation");
	protected String ownName;
	
	protected ArrayList<ConnectionHandler> connectionListeners;
	protected ConnectionOwner owner;
	
	protected InetAddress ownAddress;
	protected int ownServerPort;
	
	public ArrayList<SocketSet> socketSets = new ArrayList<SocketSet>();
	protected SocketSet talker;

	public Connection (int ownServerPort, ConnectionOwner owner){
		this.ownServerPort = ownServerPort;
		title = "";
		ownName = "";
		connectionListeners = new ArrayList<ConnectionHandler>();
		
		setOwner(owner);
	}
		
	public Connection(Socket socket, int ownServerPort, ConnectionOwner owner){
		this(ownServerPort, owner);
		newSocketAdd(socket);
	}
	public Connection(ArrayList<Socket> sockets, int ownServerPort, ConnectionOwner owner){
		this(ownServerPort, owner);
		for(Socket socket : sockets){
			newSocketAdd(socket);
		}
	}
	
	
	public void newSocketAdd(Socket socket){
		newSocketAdd(new SocketSet(socket));
		
	}
	public void newSocketAdd(SocketSet socketSet) {
		addSocket(socketSet);
		socketSet.sendData(new NewConnectionRequest());
	}
	public void addSocket(InetAddress address, int port){
		try{
			Socket tmpSocket = new Socket(address, port);
			addSocket(tmpSocket);
		}catch(java.io.IOException ex){
			
			//TODO: write explanation
			//System.out.println("failed to create socket");
			return;
		}
	}
	public void addSocket(Socket socket){
		SocketSet socketSet = new SocketSet(socket, this);
		addSocket(socketSet);
	}
	public void addSocket(SocketSet socketSet){
		socketSets.add(socketSet);
		socketSet.addSocketSetListener(this);
		membersChanged();
	}
	
	
	public void dataSentSocket(Object data, SocketSet socketSet){
		//TODO: write controle
	}
	public void dataSentGlobal(Object data){
		for(ConnectionHandler listener : connectionListeners){
			listener.dataSentGlobal(data, this);
		}
	}
	
	public synchronized void dataReceived(Object data, SocketSet socketSet){
		talker = socketSet;
		if(data instanceof InvitationConfirmation){
			owner.appDataReceived((InvitationConfirmation)data, this);
			return;
		}
		if(data instanceof InformationRequest){
			InformationMessage message;
			message = new InformationMessage(getOwnName(), getOwnServerPort(), owner.getImage());
			socketSet.sendData(message);
			return;
		}
		for(ConnectionHandler listener : connectionListeners){
			listener.dataReceived(data, socketSet, this);
		}
	}
	
	public int getOwnServerPort() {
		// TODO Auto-generated method stub
		return owner.getOwnServerPort();
	}

	public synchronized void sendData(Object data){
		//TODO: create sendData-method
		for(SocketSet socketSet : socketSets){
			socketSet.sendData(data);
		}
		dataSentGlobal(data);
	}
	
	public synchronized void addConnectionListener(ConnectionHandler connectionHandler) {
		// TODO Auto-generated method stub
		for(ConnectionHandler tmpHandler : connectionListeners){
			if(tmpHandler.equals(connectionHandler)){
				return;
			}
		}
		connectionListeners.add(connectionHandler);
	}
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}
	
	public String getOwnName(){
		//TODO: schrijf volledige naam
		return owner.getOwnName();
	}
	public BufferedImage getOwnImage(){
		return owner.getImage();
	}
	public void invite(InetAddress address, int port) {
		// TODO Auto-generated method stub
		try{
			InviteRequest req = new InviteRequest();
			for(SocketSet tmpSet : socketSets){
				req.addAddressAndPort(tmpSet.getAddress(), tmpSet.getServerPort());
				tmpSet.sendData(new InvitationConfirmation(address));
			}
			SocketSet socketSet = new SocketSet(address, port);
			socketSet.sendData(req);
			addSocket(socketSet);
		}catch(IOException ex){
			//ex.printStackTrace();
			JOptionPane.showMessageDialog(null, Controller.propBundle.getString("error_sending_data")+":\n"+ex.getStackTrace().toString(), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		}
	}
	public void connectionMembersAdd(InviteRequest req) {
		// TODO Auto-generated method stub MANIER OM DUIDELIJK TE MAKEN WAAR HIJ IS UITGENODIGD
		for(int x = 0; x < req.addresses.size(); x++){
			try{
				SocketSet socketSet = new SocketSet(req.addresses.get(x).address, req.addresses.get(x).port);
				socketSet.sendData(new InvitedWarning());
				addSocket(socketSet);
			}catch(IOException ex){
				//ex.printStackTrace();
				JOptionPane.showMessageDialog(null, Controller.propBundle.getString("error_sending_data")+":\n"+ex.getStackTrace().toString(), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}
	
	public void invitingSocketAdd(SocketSet socketSet) {
		// TODO Auto-generated method stub
		addSocket(socketSet);
	}
	public void invitedSocketAdd(SocketSet socketSet) {
		// TODO Auto-generated method stub
		addSocket(socketSet);
	}
	
	public void setOwner(ConnectionOwner owner){
		this.owner = owner;
	}
	@Override
	public void socketSetInfoChanged(SocketSet socketSet) {
		// TODO Auto-generated method stub
		membersChanged();
		
	}
	public ArrayList<SocketSet> getSocketSets() {
		// TODO Auto-generated method stub
		return socketSets;
	}
	public synchronized void membersChanged(){
		for(ConnectionHandler handler : connectionListeners){
			//System.out.println("handler voor memberschanged");
			handler.connectionMembersChanged(this);
		}
	}
	public synchronized void removeConnectionListener(ConnectionHandler listener) {
		// TODO Auto-generated method stub
		if(connectionListeners.contains(listener)){
			connectionListeners.remove(listener);
		}
	}

	public synchronized void ownInfoChanged(){
		InformationMessage infomessage = new InformationMessage(owner.getOwnName(), owner.getOwnServerPort(), owner.getImage());
		for(SocketSet ss : socketSets){
			ss.sendData(infomessage);
		}
		//membersChanged();
		for(ConnectionHandler h : connectionListeners){
			h.connectionDataChanged(this);
		}
	}
	
	public synchronized void exit(){
		ArrayList<SocketSet> tmpSocketSets = new ArrayList<SocketSet>();
		for(SocketSet ss : socketSets){
			tmpSocketSets.add(ss);
		}
		//System.out.println("tmpSocketSets.size = " + tmpSocketSets.size());
		for(SocketSet ss : tmpSocketSets){
			ss.closeSocketSet();
		}
		//owner.removeConnection(this);
	}

	@Override
	public synchronized void socketClosed(SocketSet socketSet) {
		//System.out.println("socketset closed, socketsets voor:" + socketSets.size());
		// TODO Auto-generated method stub
		socketSets.remove(socketSet);
		//System.out.println("socketsets achter: " + socketSets.size());
		//socketSet.removeSocketSetListener(this);
		this.membersChanged();
	}
	

}
