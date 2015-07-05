package fnetworkconnector;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import fnetworkconnector.servermanager.*;
import fnetworkconnector.serversocketset.ServerSocketManager;
import fnetworkconnector.socketset.SocketSetListener;
import fnetworkconnector.messages.*;


public class ServerManager implements SocketSetListener, ServerSocketManager{
	
	protected ArrayList<ServerSocketSet> serverSocketSets = new ArrayList<ServerSocketSet>();
	protected WhiteList whiteList;
	protected BlackList blackList;
	
	protected ArrayList<ServerManagerListener>listeners;
	protected Model model;
	
	protected ArrayList<SocketSet> quarantaineList;
	
	public ServerManager(Model model){
		setModel(model);
		listeners = new ArrayList<ServerManagerListener>();
		listeners.add(model);
		
		whiteList = new WhiteList();
		blackList = new BlackList();
		serverSocketSets = new ArrayList<ServerSocketSet>();
		quarantaineList = new ArrayList<SocketSet>();
		//PAS AAN AAN GOEDE POORTEN//TODO
		int boundto = -1;
		for(int x = 10120; x < 10130; x++){
			try{
				serverSocketSets.add(new ServerSocketSet(x, this));
				boundto = x;
				break;
			}catch(IOException ex){
				
			}
		}
		String msg;
		if(boundto != -1){
			msg = Controller.propBundle.getString("bound_to")+": "+boundto;
		}else{
			msg = Controller.propBundle.getString("standard_ports_not_available_self_specify");
		}
		JOptionPane.showMessageDialog(null, msg, Controller.propBundle.getString("info"), JOptionPane.INFORMATION_MESSAGE);
		serversChanged();
	}
	
	private void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model = model;
		model.setServerManager(this);
	}

	public void addServer(int port){
		try{
			ServerSocketSet server = new ServerSocketSet(port, this);
			serverSocketSets.add(server);
			serversChanged();
		}catch(IOException ex){
			//ex.printStackTrace();
			JOptionPane.showMessageDialog(null, Controller.propBundle.getString("error_serversocket_creating_failed"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void newIncomingSocket(SocketSet socketSet){
		//TODO: write
		controlSocket(socketSet);
	}
	
	public synchronized void controlSocket(SocketSet socketSet){
		InetAddress address = socketSet.socket.getInetAddress();
		WhiteRecord whiteRecord = whiteList.allow(address);
		if(whiteRecord != null){
			if(whiteRecord.getConnection() != null){
				whiteRecord.getConnection().addSocket(socketSet);
				//TODO:OPTIMALISEER
			}else{
				//TODO: write connectionadder
				model.newIncomingSocket(socketSet);
			}
			whiteList.removeRecord(whiteRecord);
		}else if(blackList.block(address)){
		}else{
			addQuarantaineSocketSet(socketSet);
		}
		
	}
	
	public void createNewConnection(SocketSet socketSet){
		
	}

	@Override
	public synchronized void dataReceived(Object data, SocketSet socketSet) {
		// TODO Auto-generated method stub
		Object data2;
		if(data instanceof DataStorage){
			data2 = ((DataStorage)data).getData();
		}else{
			data2 = data;
		}
		handleData(data2, socketSet);
		
	}
	public synchronized void handleData(Object data, SocketSet socketSet){
		if(data instanceof NewConnectionRequest){
			//TODO: vraag toestemming aan gebruiker
			if(model == null){
			}
			removeQuarantaineSocketSet(socketSet);
			model.newIncomingSocket(socketSet);
		}else if(data instanceof InviteRequest){
			//TODO: write inviteaccept bv: controleer of het aanvaard moet worden
			if(model == null){}
			removeQuarantaineSocketSet(socketSet);
			model.invitedIncomingSocket(socketSet, (InviteRequest)data);
			
		}else if(data instanceof InformationRequest){
			InformationMessage info = new InformationMessage(model.getOwnName(), getServerPort(), model.getImage());
			//TODO: write informationmessage, do also in connection
			socketSet.sendData(info);
		}
	}

	@Override
	public void dataSentSocket(Object data, SocketSet socketSet) {
		// TODO Auto-generated method stub
		
	}
	
	public int getServerPort(){
		if(serverSocketSets.size() > 0){
			return serverSocketSets.get(0).getPort();
		}else{
			return -1;
		}
	}
	
	public void serversChanged(){
		for(ServerManagerListener listener : listeners){
			listener.serversChanged();
		}
	}

	public void addWhiteRecord(InetAddress address, Connection conn) {
		// TODO Auto-generated method stub
		
		whiteList.addRecord(address, conn);
		whiteListChanged();
	}
	
	public synchronized void whiteListChanged(){
		for(SocketSet socketSet : quarantaineList){
			WhiteRecord rec = whiteList.allow(socketSet.getAddress());
			if(rec != null){
				removeQuarantaineSocketSet(socketSet);
				if(rec.getConnection() != null){
					rec.getConnection().invitedSocketAdd(socketSet);
				}else{
					model.newIncomingSocket(socketSet);
					
				}
				whiteList.removeRecord(rec);
				
			}
		}
	}
	
	public synchronized void addQuarantaineSocketSet(SocketSet socketSet){
//TODO werk verder
		quarantaineList.add(socketSet);
		socketSet.addSocketSetListener(this);
	}
	public synchronized void removeQuarantaineSocketSet(SocketSet socketSet){
		if(quarantaineList.contains(socketSet)){
			quarantaineList.remove(socketSet);
			socketSet.removeSocketSetListener(this);
		}else{
		}
	}

	public int getServerCount() {
		// TODO Auto-generated method stub
		return serverSocketSets.size();
	}

	public Integer[] getServerPorts() {
		// TODO Auto-generated method stub
		Integer[] tmpPorts = new Integer[getServerCount()];
		for(int x = 0; x < getServerCount(); x++){
			tmpPorts[x] = serverSocketSets.get(x).getPort();
		}
		return tmpPorts;
	}

	public boolean removeServerOnPort(int port) {
		// TODO Auto-generated method stub
		for(ServerSocketSet sss : serverSocketSets){
			if(sss.getPort() == port){
				boolean result = sss.close();
				serverSocketSets.remove(sss);
				return result;
			}
		}
		return false;
	}

	public void addServerManagerListener(ServerManagerListener listener2) {
		// TODO Auto-generated method stub
		listeners.add(listener2);
	}

	public boolean serverExists(int port) {
		// TODO Auto-generated method stub
		for(ServerSocketSet sss : serverSocketSets){
			if(sss.getPort() == port){
				return true;
			}
		}
		return false;
	}

	@Override
	public void socketSetInfoChanged(SocketSet socketSet) {
		// TODO Auto-generated method stub
		
	}

	public void removeServerManagerListener(ServerManagerListener listener) {
		// TODO Auto-generated method stub
		if(this.listeners.contains(listener)){
			listeners.remove(listener);
		}
		
	}

	@Override
	public void socketClosed(SocketSet socketSet) {
		// TODO Auto-generated method stub
		this.removeQuarantaineSocketSet(socketSet);
	}
}