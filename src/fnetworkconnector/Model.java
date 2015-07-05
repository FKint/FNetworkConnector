package fnetworkconnector;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import fnetworkconnector.connection.*;
import fnetworkconnector.fnetworkconnectordata.FNetworkConnectorDataListener;
import fnetworkconnector.messages.*;
import fnetworkconnector.servermanager.ServerManagerListener;

public class Model implements ServerManagerListener, ConnectionOwner,
		FNetworkConnectorDataListener {

	protected Storage storage;

	protected String name = "";
	protected int ownServerPort = -1;
	protected UInterface uInterface;

	protected ServerManager serverManager;
	protected ArrayList<Connection> connections;
	protected FNetworkConnectorData fnetworkConnectorData = new FNetworkConnectorData();

	public Model(Storage storage) {
		super();
		this.storage = storage;
	}

	public void setStorage(Storage storage) {
		setServerManager(storage.serverManager);
		setConnections(storage.connections);
		setFNetworkConnectorData(storage.fnetworkConnectorData);
		this.storage = storage;
	}

	public void setInterface(UInterface tmpUInterface) {
		uInterface = tmpUInterface;
	}

	public void createNewConnection(InetAddress address, int port) {
		final InetAddress myAddress = address;
		final int myPort = port;
		final Model model = this;
		new Thread(new Runnable() {
			public void run() {
				uInterface.setWaiting(true);
				try {
					Connection tmpConnection = new Connection(
							getOwnServerPort(), model);
					SocketSet socketSet = new SocketSet(myAddress, myPort);
					tmpConnection.newSocketAdd(socketSet);
					newConnectionAdded(tmpConnection);
				} catch (IOException ex) {
					// ex.printStackTrace();
					JOptionPane.showMessageDialog(null, Controller.propBundle
							.getString("connection_create_failed")+":\n"+ex.getStackTrace().toString(),
							Controller.propBundle.getString("error"),
							JOptionPane.WARNING_MESSAGE);
				}
				uInterface.setWaiting(false);
			}
		}).start();

	}

	public void addServer(int port) {
		serverManager.addServer(port);
	}

	public void newIncomingSocket(SocketSet socketSet) {
		Connection conn = new Connection(getOwnServerPort(), this);
		conn.invitingSocketAdd(socketSet);
		newConnectionAdded(conn);
	}

	public void invitedIncomingSocket(SocketSet socketSet, InviteRequest req) {

		Connection conn = new Connection(getOwnServerPort(), this);
		conn.invitingSocketAdd(socketSet);
		conn.connectionMembersAdd(req);
		newConnectionAdded(conn);

	}

	protected void newConnectionAdded(Connection conn) {
		connections.add(conn);
		uInterface.newConnectionAdded(conn);
	}

	@Override
	public int getOwnServerPort() {
		return ownServerPort;
	}

	@Override
	public void ownServerPortChanged(int ownServerPort) {
		// TODO Auto-generated method stub
		this.ownServerPort = ownServerPort;
	}

	@Override
	public String getOwnName() {
		return fnetworkConnectorData.getName();
		// return name;
	}

	@Override
	public void appDataReceived(Message message, Connection conn) {
		if (message instanceof InvitationConfirmation) {
			serverManager.addWhiteRecord(((InvitationConfirmation) message)
					.getAddress(), conn);
		}
	}

	public void start() {
		// TODO Auto-generated method stub$
		storage.setModel(this);
		storage.start();
	}

	public void aboutAsked() {
		// TODO Auto-generated method stub
		uInterface.showAbout("Floris Kint (c)");
	}

	public synchronized void exitAsked() {
		// TODO Auto-generated method stub
		for (Connection con : connections) {
			con.exit();
		}
		// connections.clear();
		uInterface.exit();
	}

	public void newConnectionAsked() {
		// TODO Auto-generated method stub

	}

	public void newConnectionAsked(InetAddress address, int port) {
		// TODO Auto-generated method stub
		createNewConnection(address, port);
	}

	public Integer[] getServerPorts() {
		// TODO Auto-generated method stub
		return serverManager.getServerPorts();
	}

	public boolean removeServerOnPort(int port) {
		// TODO Auto-generated method stub
		return serverManager.removeServerOnPort(port);
	}

	public void addServerManagerListener(ServerManagerListener listener) {
		// TODO Auto-generated method stub
		serverManager.addServerManagerListener(listener);
	}

	@Override
	public void serversChanged() {
		// TODO Auto-generated method stub
		try {
			ownServerPortChanged(serverManager.getServerPort());
		} catch (Exception ex) {

		}
	}

	public void setServerManager(ServerManager serverManager) {
		// TODO Auto-generated method stub
		this.serverManager = serverManager;
	}

	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}

	public boolean serverExists(int port) {
		// TODO Auto-generated method stub
		return serverManager.serverExists(port);
	}

	public void setOwnName(String text) {
		// TODO Auto-generated method stub
		fnetworkConnectorData.setName(text);

	}

	@Override
	public void nameChanged() {
		// TODO Auto-generated method stub
		if (uInterface != null) {
			this.uInterface.nameChanged();
		}
		for (Connection conn : connections) {
			conn.ownInfoChanged();
		}
	}

	public void setFNetworkConnectorData(FNetworkConnectorData data) {
		fnetworkConnectorData.removeDataListener(this);
		data.addDataListener(this);
		fnetworkConnectorData = data;
		fnetworkConnectorData.nameChanged();
	}

	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		if (storage != null) {
			return storage.getImage();
		} else {
			return (BufferedImage) (new ImageIcon().getImage());
		}
	}

	public void setImage(BufferedImage image) {
		if (storage != null) {
			storage.setImage(image);
			for (Connection conn : connections) {
				conn.ownInfoChanged();
			}
		} else {
			// TODO: write explanation
		}
	}

	public void removeServerManagerListener(ServerManagerListener listener) {
		// TODO Auto-generated method stub
		serverManager.removeServerManagerListener(listener);
	}

	@Override
	public void removeConnection(Connection connection) {
		// TODO Auto-generated method stub
		connections.remove(connection);
	}

}
