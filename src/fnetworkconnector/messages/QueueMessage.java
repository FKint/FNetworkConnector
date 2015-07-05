package fnetworkconnector.messages;

import java.util.Date;

public class QueueMessage implements Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 503692710648826987L;
	protected Object data;
	protected long time;

	public QueueMessage(Object data){
		time = new Date().getTime();
		this.data = data;
	}
}
