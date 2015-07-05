package fnetworkconnector;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.net.InetAddress;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import fnetworkconnector.socketset.*;
import fnetworkconnector.messages.*;

public class SocketSet {

	public Socket socket;
	protected SocketOutput output;
	protected SocketInput input;
	protected int serverPort;
	protected String name;
	protected InetAddress address;
	protected BufferedImage image;

	protected boolean infoGot = false;
	protected boolean selfReady = false;
	protected boolean remoteReady = false;
	

	protected ArrayList<SocketSetListener> socketSetListeners = new ArrayList<SocketSetListener>();

	protected ArrayList<DataStorage> stack;
	protected ArrayList<DataStorage> outStack;

	public SocketSet() {
		socket = null;
		output = null;
		input = null;
		serverPort = -1;
		name = "";
	}

	public SocketSet(Socket socket, SocketSetListener listener) {
		this(socket);
		addSocketSetListener(listener);
	}

	public SocketSet(Socket socket) {
		this.socket = socket;
		stack = new ArrayList<DataStorage>();
		outStack = new ArrayList<DataStorage>();
		name = "";
		image = (BufferedImage) (new ImageIcon().getImage());
		address = socket.getInetAddress();
		createStreams();
		askInfo();
		ready();
		
	}

	public SocketSet(InetAddress address, int connectionport)
			throws IOException {
		this(new Socket(address, connectionport));
	}

	public SocketSet(InetAddress address, int connectionport,
			SocketSetListener listener) throws IOException {
		this(new Socket(address, connectionport), listener);
	}

	public void createStreams() {
		createOutputStream();
		createInputStream();
	}

	public synchronized void addSocketSetListener(SocketSetListener listener) {

		if (!socketSetListeners.contains(listener)) {
			socketSetListeners.add(listener);
			flushInStack();
			askInfo();

		}
	}

	public void flushInStack() {
		ArrayList<DataStorage> stack = getStack();
		for (DataStorage stor : stack) {
			dataReceived(stor.getData());
		}
	}

	public synchronized void removeSocketSetListener(SocketSetListener listener) {

		if (socketSetListeners.contains(listener)) {
			socketSetListeners.remove(listener);
		}

	}

	public void createOutputStream() {
		try {
			output = new SocketOutput(this);

		} catch (IOException e) {
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, Controller.propBundle.getString("error_opening_output_stream")+":\n"+e.getStackTrace().toString(), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		}
	}

	public void createInputStream() {
		try {
			input = new SocketInput(this);
			input.startReading();
		} catch (IOException e) {
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, Controller.propBundle.getString("error_opening_input_stream")+":\n"+e.getStackTrace().toString(), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Controller.propBundle.getString("unknown_error")+": "+e.getMessage()+":\n"+e.getStackTrace().toString(), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			//e.printStackTrace();
		}
	}

	public void askInfo() {
		sendData(new InformationRequest());
	}

	public void infoGot(InformationMessage info) {
		setName(info.getName());
		setServerPort(info.getServerPort());
		setImage(info.getImage());
		infoGot = true;
		socketChanged();
	}

	protected synchronized void socketChanged() {
		// TODO Auto-generated method stub
		//System.out.println("in socketchanged");
		for (SocketSetListener l : socketSetListeners) {
			l.socketSetInfoChanged(this);
		}
	}

	protected void setServerPort(int serverPort) {
		// TODO Auto-generated method stub
		this.serverPort = serverPort;
	}

	protected void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	public void dataSent(Object data) {// warning from output

		for (SocketSetListener listener : socketSetListeners) {
			listener.dataSentSocket(data, this);

		}
	}

	public synchronized void dataReceived(Object data) {// warning from input
		if (data instanceof InformationMessage) {
			infoGot((InformationMessage) data);
			return;
		}
		if (data instanceof ReadyMessage) {
			readyGot((ReadyMessage) data);
			return;
		}
		if (data instanceof SocketClosedMessage) {
			socketClosed();
			return;
		}

		if (socketSetListeners.size() > 0 && selfReady) {
			for (SocketSetListener listener : socketSetListeners) {
				listener.dataReceived(data, this);
			}
		} else {
			stack.add(new DataStorage(data));
		}
	}

	protected void readyGot(ReadyMessage data) {
		// TODO Auto-generated method stub
		if (selfReady) {
			if (!remoteReady) {
				ready();
			}

			remoteReady = true;
			flushOutStack();
		}

	}

	protected void flushOutStack() {
		// TODO Auto-generated method stub
		ArrayList<DataStorage> tmpStor = getOutStack();
		for (DataStorage stor : tmpStor) {
			sendData(stor.getData());
		}
	}

	protected synchronized ArrayList<DataStorage> getOutStack() {
		ArrayList<DataStorage> tmpStack = new ArrayList<DataStorage>();
		for (DataStorage stor : outStack) {
			tmpStack.add(stor);
		}
		outStack.clear();
		return tmpStack;
	}

	public void sendData(Object data) {// try to send data
		if (remoteReady && selfReady) {
		
			if (output != null) {
				output.sendData(data);
			} else {
				// TODO: write explanation
				//System.out.println("No available streams to send data to");
			}
			dataSent(data);
		} else {
			addToOutStack(data);
			dataSent(new QueueMessage(data));
		}
	}

	protected void addToOutStack(Object data) {
		// TODO Auto-generated method stub
		outStack.add(new DataStorage(data));
	}

	public String getName() {
		return name;
	}

	public InetAddress getAddress() {
		return address;
	}

	public synchronized void closeSocketSet() {
		//System.out.println("in socketSet - closeSocketSet");
		sendData(new SocketClosedMessage());
		//System.out.println("na sendData");
		socketClosed();
	}

	public synchronized void socketClosed() {
		input.close();
		output.close();
		this.selfReady = false;
		try{
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//System.out.println("ioexception bij sluiten van socket");
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, Controller.propBundle.getString("socket_close_failed")+":\n"+e.getStackTrace().toString(), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		}
		for (SocketSetListener l : socketSetListeners) {
			l.socketClosed(this);
		}
		//this.socketSetListeners.clear();

	}

	public int getServerPort() {
		return serverPort;
	}

	protected synchronized ArrayList<DataStorage> getStack() {
		ArrayList<DataStorage> tmpStack = new ArrayList<DataStorage>();
		for (DataStorage data : stack) {
			tmpStack.add(data);
		}
		stack.clear();
		return tmpStack;
	}

	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		if (image == null) {
			try {
				image = ImageIO.read(this.getClass().getResource(
						"/resources/defaultContactImage.PNG"));
			} catch (IOException ex) {
				image = (BufferedImage) (new ImageIcon().getImage());
			}
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		//System.out.println("image set");
		this.image = image;
	}

	public void ready() {
		selfReady = true;
		output.sendData(new ReadyMessage());
	}

	public boolean getSelfReady() {
		// TODO Auto-generated method stub
		return selfReady;
	}
}
