package fnetworkconnector.serversocketset;

import java.net.Socket;
import fnetworkconnector.*;

public class ServerSocketAcceptorThread extends Thread{

	protected ServerSocketSet serverSocketSet;
	
	protected boolean working;
	
	public ServerSocketAcceptorThread(ServerSocketSet serverSocketSet){
		super();
		this.serverSocketSet = serverSocketSet;
	}
	
	public void run(){
		while(working){
			try{
				Socket tmpSocket = serverSocketSet.serverSocket.accept();
				SocketSet tmpSocketSet = new SocketSet(tmpSocket);
				serverSocketSet.newIncomingSocket(tmpSocketSet);
			}catch(Exception ex){
				serverSocketSet.close();//CHECK
				//ex.printStackTrace();
				//end();
			}
		}
	}
	
	public void begin(){
		setWorking(true);
		this.start();
	}
	public void end(){
		setWorking(false);
	}
	
	public void setWorking(boolean tmpWorking){
		working = tmpWorking;
	}
}