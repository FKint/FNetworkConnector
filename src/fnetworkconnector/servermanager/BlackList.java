package fnetworkconnector.servermanager;

import java.net.InetAddress;
import java.util.ArrayList;

public class BlackList {
	protected ArrayList<BlackRecord> records;
	
	public BlackList(){
		records = new ArrayList<BlackRecord>();
	}
	
	public boolean block(InetAddress address){
		for(BlackRecord record : records){
			if(record.getAddress().equals(address)){
				return true;
			}
		}
		return false;
	}
}
