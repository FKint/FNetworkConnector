package fnetworkconnector.messages;

import java.net.InetAddress;
import java.util.ArrayList;

public class InviteRequest implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9049541581756241034L;
	public ArrayList<AddressAndPort> addresses;
	public InviteRequest(){
		addresses = new ArrayList<AddressAndPort>();
		
	}
	
	public void addAddressAndPort(InetAddress address, int port){
		AddressAndPort aap = new AddressAndPort(address, port);
		addresses.add(aap);
	}
	
	public class AddressAndPort implements java.io.Serializable{
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 7328339665344569381L;
		
		public InetAddress address;
		public int port;
		public AddressAndPort(InetAddress address, int port){
			this.address = address;
			this.port = port;
		}
	}
}
