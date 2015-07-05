package fnetworkconnector;

import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JOptionPane;

public class Tools {
	public static String modifyInetAddress(String address){
		if(address.equals("localhost") || address.equals("127.0.0.1")){
			try{
				address = InetAddress.getLocalHost().getHostName();
			}catch(IOException ex){
				//ex.printStackTrace();
				JOptionPane.showMessageDialog(null, Controller.propBundle.getString("error_determine_own_ip"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			}
		}
		return address;
	}
}
