package fnetworkconnector.servermanager;

import java.net.InetAddress;
import java.util.ArrayList;

import fnetworkconnector.Connection;

public class WhiteList {
	protected ArrayList<WhiteRecord> records;
	
	public WhiteList(){
		records = new ArrayList<WhiteRecord>();
	}
	
	public void addRecord(InetAddress address){
		records.add(new WhiteRecord(address));
	}
	
	public WhiteRecord allow(InetAddress address){
		for(WhiteRecord record : records){
			if(record.getAddress().equals(address)){
				return record;
			}
		}
		return null;
	}
	
	public void removeRecord(WhiteRecord record){
		records.remove(record);
	}

	public void addRecord(InetAddress address, Connection conn) {
		records.add(new WhiteRecord(address, conn));
		
	}
}
