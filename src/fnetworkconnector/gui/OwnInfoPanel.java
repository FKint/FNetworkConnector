package fnetworkconnector.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fnetworkconnector.Connection;
import fnetworkconnector.Controller;
import fnetworkconnector.SocketSet;
import fnetworkconnector.connection.ConnectionHandler;

public class OwnInfoPanel extends JPanel implements ConnectionHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8998498187021081292L;

	protected Connection connection;
	protected BufferedImage image;
	protected String name;
	protected JLabel imageLabel;
	protected int imagewidth;
	protected int imageheight;
	
	
	public OwnInfoPanel(Connection conn){
		connection = conn;
		connection.addConnectionListener(this);
		
		imagewidth = 50;
		imageheight = 50;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new JLabel("---"));
		imageLabel = new JLabel(connection.getOwnName(),JLabel.CENTER);
		imageLabel.setVerticalTextPosition(JLabel.BOTTOM);
		imageLabel.setHorizontalTextPosition(JLabel.CENTER);
		
		setImage(connection.getOwnImage());
		add(imageLabel);
		
	}
	
	
	public void setImage(BufferedImage image){
		if(image != null){
			this.image = image;
		}else{
			try {
				this.image = (BufferedImage) ImageIO.read(this.getClass().getResource("/resources/defaultContactImage.PNG"));
			} catch (IOException e) {
				//e.printStackTrace();
				JOptionPane.showMessageDialog(this, Controller.propBundle.getString("error_image_reading"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			}
		}
		imageLabel.setIcon(new ImageIcon(image.getScaledInstance(imagewidth, imageheight, Image.SCALE_DEFAULT)));
	}
	
	
	public void setName(String name){
		imageLabel.setText(name);
	}


	@Override
	public void connectionMembersChanged(Connection conn) {
		// TODO Auto-generated method stub
		
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
	public void connectionDataChanged(Connection conn) {
		// TODO Auto-generated method stub
		setName(conn.getOwnName());
		setImage(conn.getOwnImage());
		
	}
}
