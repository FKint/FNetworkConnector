package fnetworkconnector.messages;

import java.net.InetAddress;

public class InvitationConfirmation implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6830248641413279939L;
	
	protected InetAddress address;
	
	public InvitationConfirmation(InetAddress address){
		this.address = address;
	}
	public InetAddress getAddress(){
		return address;
	}
}
