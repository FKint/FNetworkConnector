package fnetworkconnector.servermanager;

import java.net.InetAddress;

public class BlackRecord {
	
	protected InetAddress address;
	
	public BlackRecord(){
		
	}
	public BlackRecord(InetAddress address){
		this.address = address;
	}

	public Object getAddress() {
		// TODO Auto-generated method stub
		return address;
	}
}
