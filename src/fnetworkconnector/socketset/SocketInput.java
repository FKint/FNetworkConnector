package fnetworkconnector.socketset;

import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

import fnetworkconnector.*;

public class SocketInput{

	protected ObjectInputStream objectStream;
	protected SocketSet socketSet;
	protected SocketInputThreadRead readThread;

	public SocketInput(SocketSet socketSet) throws Exception{
		if(socketSet == null){
			throw new Exception("invalid_socketset_as_argument");
		}else{
			this.socketSet = socketSet;
			createObjectInputStream();
			try{
				readThread = new SocketInputThreadRead(this);
			}catch(Exception ex){
				//ex.printStackTrace();
				JOptionPane.showMessageDialog(null, Controller.propBundle.getString(ex.getMessage())+":\n"+ex.getStackTrace().toString(), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	public synchronized void createObjectInputStream(){
		try {
			if(socketSet.socket.getInputStream() != null){
				objectStream = new ObjectInputStream(socketSet.socket.getInputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, Controller.propBundle.getString("error_stream_creating")+":\n"+e.getStackTrace().toString(), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public synchronized void startReading(){
		readThread.begin();
	}
	public synchronized void stopReading(){
		readThread.end();
	}
	
	public synchronized void dataReceived(Object data){
		if(socketSet != null){
			socketSet.dataReceived(data);
		}else{
			//System.out.println("No socketset available to warn that data has been received");
		}
	}

	public void closed() {
		// TODO Auto-generated method stub
		//System.out.println("socketinput.closed()");
		socketSet.socketClosed();
	}

	public void close() {
		// TODO Auto-generated method stub
		try {
			readThread.end();
			objectStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
}