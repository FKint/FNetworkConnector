package fnetworkconnector.socketset;

import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import fnetworkconnector.*;

public class SocketOutput {

	protected OutputStream stream;
	protected ObjectOutputStream objectStream;
	protected SocketSet socketSet;
	//protected SocketOutputThread thread;
	protected boolean writing;
	protected ArrayList<Object> queue;
	public SocketOutput(SocketSet socketSet) throws IOException {
		stream = socketSet.socket.getOutputStream();
		createObjectOutputStream();
		this.socketSet = socketSet;
		queue = new ArrayList<Object>();
		//thread = new SocketOutputThread(this);
	}

	public void createObjectOutputStream() {
		if (stream != null) {
			try {
				objectStream = new ObjectOutputStream(stream);
			} catch (IOException e) {
				// e.printStackTrace();
				JOptionPane.showMessageDialog(null, Controller.propBundle
						.getString("error_opening_output_stream")+":\n"+e.getStackTrace().toString(),
						Controller.propBundle.getString("error"),
						JOptionPane.WARNING_MESSAGE);
			}
		} else {
		}
	}

	public void sendData(Object data) {
		if (objectStream == null) {
			createObjectOutputStream();
		}
		queue.add(data);
		//thread.addToQueu(data);
		if(!writing){
			new SocketOutputThread(this).start();
			
		}
		/*try {
			// System.out.println("write object: " + data);
			final Object myData = data;
			new Thread(new Runnable() {
				public void run() {
					try {
						objectStream.writeObject((Object) myData);
					} catch (IOException ex) {
						// ex.printStackTrace();
						JOptionPane.showMessageDialog(null, Controller.propBundle
								.getString("error_sending_data")+":\n"+ex.getStackTrace().toString(), Controller.propBundle
								.getString("error"), JOptionPane.WARNING_MESSAGE);
						closed();
					} catch (Exception ex) {
						// ex.printStackTrace();
						JOptionPane.showMessageDialog(null, Controller.propBundle
								.getString("unknown_error")
								+ ": " + ex.getMessage()+":\n"+ex.getStackTrace().toString(), Controller.propBundle
								.getString("error"), JOptionPane.WARNING_MESSAGE);
						closed();
					}
				}
			}).start();

			this.dataSent(data);
		} catch (Exception ex) {
			// ex.printStackTrace();
			JOptionPane.showMessageDialog(null, Controller.propBundle
					.getString("unknown_error")
					+ ": " + ex.getMessage()+"\n"+ex.getStackTrace().toString(), Controller.propBundle
					.getString("error"), JOptionPane.WARNING_MESSAGE);
			closed();
		}*/
	}

	public void dataSent(Object data) {
		if (socketSet != null) {
			socketSet.dataSent(data);
		} else {
		}
	}

	public void close() {
		// TODO Auto-generated method stub

		try {
			objectStream.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}

		try {
			objectStream.close();
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	public void closed() {
		// System.out.println("socketoutput closed");
		socketSet.socketClosed();
	}

	
	
}