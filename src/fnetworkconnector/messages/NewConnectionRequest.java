package fnetworkconnector.messages;

import java.util.Random;

public class NewConnectionRequest implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7332007164927277470L;
	protected int id213 = new Random().nextInt();
	
	public NewConnectionRequest(){
		
	}
}
