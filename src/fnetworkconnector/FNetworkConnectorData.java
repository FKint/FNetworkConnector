package fnetworkconnector;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import fnetworkconnector.fnetworkconnectordata.FNetworkConnectorDataListener;

public class FNetworkConnectorData {
	protected String name;
	protected ArrayList<FNetworkConnectorDataListener> listeners;
	
	
	public FNetworkConnectorData(String name){
		this.name = name;
		listeners = new ArrayList<FNetworkConnectorDataListener>();
	}
	public FNetworkConnectorData(){
		//this(InetAddress.getLocalHost().getHostName());
		try{
			name = InetAddress.getLocalHost().getHostName();
		}catch(UnknownHostException ex){
			//ex.printStackTrace();
			JOptionPane.showMessageDialog(null, Controller.propBundle.getString("filled_host_not_available"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		}

		listeners = new ArrayList<FNetworkConnectorDataListener>();
	}
	
	public void setName(String name){
		boolean original;
		if(!name.equals(this.name)){
			original = false;
		}else{
			original = true;
		}
		this.name = name;
		if(!original){
			nameChanged();
		}
	}
	
	public String getName(){
		return name;
	}
	
	public void addDataListener(FNetworkConnectorDataListener listener){
		listeners.add(listener);
	}
	public void removeDataListener(FNetworkConnectorDataListener listener){
		if(listeners.contains(listener)){
			listeners.remove(listener);
		}
	}
	public void nameChanged() {
		for(FNetworkConnectorDataListener listener : listeners){
			listener.nameChanged();
		}
	}
}
