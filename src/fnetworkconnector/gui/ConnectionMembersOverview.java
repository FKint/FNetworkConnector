package fnetworkconnector.gui;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fnetworkconnector.Connection;
import fnetworkconnector.Controller;
import fnetworkconnector.SocketSet;
import fnetworkconnector.connection.ConnectionHandler;

public class ConnectionMembersOverview extends JPanel implements ConnectionHandler{//TODO: werk met interface

	/**
	 * 
	 */
	private static final long serialVersionUID = 6206345077598962252L;
	
	protected Connection connection;
	protected ArrayList<FNCContactPanel> contactPanels;
	protected JLabel mainLabel;
	protected OwnInfoPanel ownInfoPanel;

	public ConnectionMembersOverview(Connection connection){
		setConnection(connection);
		mainLabel = new JLabel();
		
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		add(mainLabel);
		contactPanels = new ArrayList<FNCContactPanel>();
		ownInfoPanel = new OwnInfoPanel(connection);
		add(ownInfoPanel);
		connectionMembersChanged(connection);
	}
	
	public void setConnection(Connection connection){
		this.connection = connection;
		connection.addConnectionListener(this);
	}

	@Override
	public void dataReceived(Object data, SocketSet socketSet, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dataSentGlobal(Object data, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dataSentSocket(Object data, SocketSet socketSet, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void connectionMembersChanged(Connection conn) {
		// TODO Auto-generated method stub
		editMembers();
	}
	
	public synchronized void editMembers(){
	
		ArrayList<FNCContactPanel> toRemovePanels = new ArrayList<FNCContactPanel>();
		for(FNCContactPanel p : contactPanels){

			boolean inlist = false;
			for(SocketSet set : connection.socketSets){
				if(p.getSocketSet().equals(set)){
					inlist = true;
					p.socketSetChanged();
				}else{
					

				}
			}
			if(inlist == false){
				toRemovePanels.add(p);
			}
		}
		for(SocketSet set : connection.socketSets){
			boolean inlist = false;
			for(FNCContactPanel panel : contactPanels){
				if(panel.getSocketSet().equals(set)){
					if(inlist == false){
						inlist = true;
					}else{
						toRemovePanels.add(panel);
					}
				}
			}
			if(inlist == false){
				FNCContactPanel tmpPanel = new FNCContactPanel(set);
				contactPanels.add(tmpPanel);
				this.add(tmpPanel, this.getComponentCount() - 1);
				
			}
		}
		for(FNCContactPanel p : toRemovePanels){
			this.remove(p);
			contactPanels.remove(p);
			
		}
		this.repaint();

		mainLabel.setText(Controller.propBundle.getString("chat_members") + ": " + contactPanels.size());
	}

	@Override
	public void connectionDataChanged(Connection conn) {
		// TODO Auto-generated method stub
		
	}
}

