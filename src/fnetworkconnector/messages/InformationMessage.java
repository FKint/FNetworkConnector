package fnetworkconnector.messages;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import fnetworkconnector.Controller;

public class InformationMessage implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6108515432402733494L;

	public String name;
	public int serverPort;
	public Byte[] imagedata;

	public InformationMessage(String name, int serverPort, BufferedImage image) {
		imagedata = new Byte[]{};
		this.name = name;
		this.serverPort = serverPort;
		setImage(image);
	}

	public String getName() {
		return name;
	}

	public int getServerPort() {
		return serverPort;
	}

	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		BufferedImage image;
		byte[] tmpData = new byte[imagedata.length];
		for(int x = 0; x < imagedata.length; x ++){
			tmpData[x] = imagedata[x];
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(tmpData);
		
		//imagedata = (Byte[])tmpData;
		try {
			image = ImageIO.read(bais);
			if (image != null && image.getWidth() != 0
					&& image.getHeight() != 0) {
				return image;
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, Controller.propBundle.getString("error_image_reading"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		}
		return (BufferedImage)(new ImageIcon().getImage());
	}

	public void setImage(BufferedImage image) {
		byte[] tmpData = new byte[]{};
		ByteArrayOutputStream baus = new ByteArrayOutputStream();
		if (image != null && image.getWidth() != 0 && image.getHeight() != 0) {
			//this.image = image;
			try {
				ImageIO.write(image,"png", baus);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				JOptionPane.showMessageDialog(null, Controller.propBundle.getString("error_image_saving"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			}
		} else {
			try {
				ImageIO.write(image, "png", baus);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				JOptionPane.showMessageDialog(null, Controller.propBundle.getString("error_image_saving"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			}
		}
		tmpData = baus.toByteArray();
		imagedata = new Byte[tmpData.length];
		for(int x = 0; x<tmpData.length; x++){
			imagedata[x] = (Byte)tmpData[x];
		}
	}
}
