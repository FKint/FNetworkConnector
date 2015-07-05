package fnetworkconnector.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fnetworkconnector.Controller;
import fnetworkconnector.SocketSet;

public class FNCContactPanel extends JPanel/* implements SocketSetListener */{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2522870700901309825L;
	protected BufferedImage image;
	protected String name;
	protected SocketSet socketSet;
	protected JLabel imageLabel;
	protected int imagewidth;
	protected int imageheight;
	
	/**
	 * @param socketSet
	 */
	public FNCContactPanel(SocketSet socketSet){
		this.socketSet = socketSet;
		
		imagewidth = 50;
		imageheight = 50;

		imageLabel = new JLabel(socketSet.getName(),JLabel.CENTER);
		imageLabel.setVerticalTextPosition(JLabel.BOTTOM);
		imageLabel.setHorizontalTextPosition(JLabel.CENTER);
		
		setImage(socketSet.getImage());
		add(imageLabel);
	}

	public void setImage(BufferedImage image){
		if(image != null){
			this.image = image;
		}else{
			try {
				this.image = (BufferedImage) ImageIO.read(this.getClass().getResource("/resources/defaultContactImage.PNG"));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, Controller.propBundle.getString("error_image_reading"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
				//e.printStackTrace();
			}
		}
		imageLabel.setIcon(new ImageIcon(image.getScaledInstance(imagewidth, imageheight, Image.SCALE_DEFAULT)));
	}
	
	
	public void setName(String name){
		imageLabel.setText(name);
	}

	public synchronized SocketSet getSocketSet() {
		// TODO Auto-generated method stub
		return socketSet;
	}

	public synchronized void socketSetChanged() {
		// TODO Auto-generated method stub
		setImage(socketSet.getImage());
		setName(socketSet.getName());
	}

}
