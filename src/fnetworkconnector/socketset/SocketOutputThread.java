package fnetworkconnector.socketset;

import java.io.IOException;
import java.io.Serializable;

import javax.swing.JOptionPane;

import fnetworkconnector.Controller;

public class SocketOutputThread extends Thread {
	SocketOutput socketOutput;
	//protected ArrayList<Object> queue;
	//protected boolean writing = false;

	public SocketOutputThread(SocketOutput socketOutput) {
		this.socketOutput = socketOutput;
		//start();
	}

	public void run() {
		socketOutput.writing = true;
		/*try {
			//queue.wait();
			this.wait(0);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}*/
		/*System.out.println("working = "+socketOutput.socketSet.getWorking());
		while (socketOutput.socketSet.getWorking()) {
			
			if (queue.size() > 0) {
				try {
					socketOutput.objectStream.writeObject(queue.get(0));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, Controller.propBundle
							.getString("error_sending_data")
							+ ":\n"
							+ e.getMessage()
							+ "\n"
							+ e.getStackTrace().toString(),
							Controller.propBundle.getString("error"),
							JOptionPane.WARNING_MESSAGE);
				}
			}else{
				/*try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					JOptionPane.showMessageDialog(null, Controller.propBundle
							.getString("unknown_error")
							+ ":\n"
							+ e.getMessage()
							+ "\n"
							+ e.getStackTrace().toString(),
							Controller.propBundle.getString("error"),
							JOptionPane.WARNING_MESSAGE);
				}*//*
				try {
					Thread.sleep(500);
					System.out.println("sleep");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					//start();
				}
			}
		}*/
		if(this.socketOutput.socketSet.getSelfReady()){
			while(socketOutput.queue.size() > 0){
				Object theObject = socketOutput.queue.get(0);
				socketOutput.queue.remove(socketOutput.queue.get(0));
				try {
					if(!(theObject instanceof Serializable)){
						System.out.println(theObject + " is geen instantie van Serializable");
					}
					socketOutput.objectStream.writeObject(theObject);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, Controller.propBundle
							.getString("error_sending_data")
							+ ":\n"
							+ e.getMessage()
							+ "\n"
							+ e.getStackTrace(),
							Controller.propBundle.getString("error"),
							JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		socketOutput.writing = false;
	}

	/*public void send(){
		if(queue.size() > 0){
			try {
				this.socketOutput.objectStream.writeObject(queue.get(0));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}*/
}
