package fnetworkconnector.connection;

import java.awt.image.BufferedImage;

import fnetworkconnector.messages.*;
import fnetworkconnector.*;

public interface ConnectionOwner{
	public int getOwnServerPort();
	public String getOwnName();
	public void appDataReceived(Message message, Connection conn);
	public BufferedImage getImage();
	public void removeConnection(Connection connection);
}