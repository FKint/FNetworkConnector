package fnetworkconnector.socketset;

import java.io.EOFException;
import java.io.IOException;
import java.io.OptionalDataException;
import java.net.SocketException;

import javax.swing.JOptionPane;

import fnetworkconnector.Controller;

public class SocketInputThreadRead extends Thread {
	protected volatile boolean enabled;
	protected volatile boolean reading;
	protected SocketInput socketInput;

	public SocketInputThreadRead(SocketInput socketInput) throws Exception {
		if (socketInput == null) {
			throw new Exception("no_socketinput_to_manage_socketinputthread");
		} else {
			this.socketInput = socketInput;
		}
		enabled = false;
		reading = false;
	}

	public void run() {
		while (enabled) {
			try {

				Object tmpObject = socketInput.objectStream.readObject();
				if (reading) {
					socketInput.dataReceived(tmpObject);
				} else {
				}
			} catch (OptionalDataException ex) {
				System.out.println("lengte van het element = " + ex.length);
				try {
					byte[] myArray = new byte[ex.length];
					socketInput.objectStream.read(myArray);
					for(byte b : myArray){
						socketInput.dataReceived((Byte)b);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ex.printStackTrace();
			} catch (ClassCastException ex) {
				// ex.printStackTrace();
				JOptionPane.showMessageDialog(null, Controller.propBundle
						.getString("unknown_data_received"),
						Controller.propBundle.getString("error"),
						JOptionPane.WARNING_MESSAGE);
			} catch (SocketException ex) {
				// ex.printStackTrace();
				JOptionPane.showMessageDialog(null, Controller.propBundle
						.getString("connection_lost"), Controller.propBundle
						.getString("error"), JOptionPane.WARNING_MESSAGE);
				socketInput.closed();
			} catch (EOFException ex) {
				// ex.printStackTrace();
				JOptionPane.showMessageDialog(null, Controller.propBundle
						.getString("connection_lost"), Controller.propBundle
						.getString("error"), JOptionPane.WARNING_MESSAGE);
				socketInput.closed();
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, Controller.propBundle
						.getString("unknown_error")
						+ "\n"
						+ ex.getMessage()
						+ "\n"
						+ ex.getStackTrace().toString(), Controller.propBundle
						.getString("error"), JOptionPane.WARNING_MESSAGE);
				this.socketInput.close();
			}
		}
	}

	public synchronized void end() {
		setEnabled(false);
	}

	public void setReading(boolean reading) {
		this.reading = reading;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public synchronized void begin() {
		enabled = true;
		setReading(true);
		this.start();
	}

}