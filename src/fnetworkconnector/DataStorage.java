package fnetworkconnector;

import java.util.Date;

public class DataStorage {
	protected Object data;
	protected long date;
	public DataStorage(Object data){
		this.data = data;
		this.date = (new Date()).getTime();
	}
	public Object getData() {
		// TODO Auto-generated method stub
		return data;
	}
}
