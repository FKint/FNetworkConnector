package fnetworkconnector;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Storage {
	
	protected Model model;
	
	protected ServerManager serverManager;
	protected ArrayList<Connection> connections;
	protected FNetworkConnectorData fnetworkConnectorData;
	protected BufferedImage image;
	
	public Storage(){
		connections = new ArrayList<Connection>();

		fnetworkConnectorData = new FNetworkConnectorData();

		try {
			setImage(ImageIO.read(this.getClass().getResource("/resources/defaultContactImage.PNG")));
		} catch (IOException e) {
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, Controller.propBundle.getString("error_image_reading"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void setModel(Model model){
		this.model = model;
		model.setStorage(this);
		
	}
	public void start(){
		serverManager = new ServerManager(model);

	}

	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		if(image == null){
			return new BufferedImage(0,0,0);
		}else{
			return image;
		}
	}
	public void setImage(BufferedImage image){
		this.image = image;
	}
}
