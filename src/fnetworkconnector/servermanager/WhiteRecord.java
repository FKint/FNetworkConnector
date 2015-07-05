package fnetworkconnector.servermanager;

import java.net.InetAddress;

import fnetworkconnector.Connection;


public class WhiteRecord{
	
	protected InetAddress address;
	private Connection connection;
	
	public WhiteRecord(InetAddress address) {
		// TODO Auto-generated constructor stub
		setAddress(address);
	}
	public WhiteRecord(InetAddress address, Connection conn){
		this(address);
		setConnection(conn);
	}
	
	public void setAddress(InetAddress address){
		this.address = address;
	}
	
	public InetAddress getAddress(){
		return address;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public Connection getConnection() {
		return connection;
	}
}