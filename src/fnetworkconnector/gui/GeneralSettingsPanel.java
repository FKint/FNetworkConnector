package fnetworkconnector.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import fnetworkconnector.Controller;
import fnetworkconnector.Model;

public class GeneralSettingsPanel extends SettingsPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9205943477956634967L;

	protected JTextField nameTxt;
	protected JButton imageBtn;

	public GeneralSettingsPanel(Model tmpModel) {
		super(tmpModel);

		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 1;

		add(new JLabel(Controller.propBundle.getString("name")), c);
		c.gridx = 1;
		nameTxt = new JTextField(model.getOwnName());
		add(nameTxt, c);

		c.gridy = 2;
		c.gridx = 0;
		add(new JLabel(Controller.propBundle.getString("image") + ":"), c);
		c.gridx = 1;
		imageBtn = new JButton(Controller.propBundle.getString("choose_image"),
				new ImageIcon(model.getImage().getScaledInstance(20, 20,
						Image.SCALE_FAST)));

		// imageBtn.setIcon(new
		// ImageIcon(model.getImage().getScaledInstance(20,20,
		// Image.SCALE_FAST)));
		imageBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseImage();
			}
		});
		add(imageBtn, c);

		c.gridx = 0;
		c.gridy = 10;
		add(resetButton, c);
		c.gridx = 1;
		add(applyButton, c);

	}

	public void apply() {
		model.setOwnName(nameTxt.getText());

	}

	public void chooseImage() {
		final JFileChooser filechooser = new JFileChooser();
		filechooser.addChoosableFileFilter(new FileNameExtensionFilter(
				Controller.propBundle.getString("images"), "png", "jpg",
				"jpeg", "gif", "bmp"));
		int result = filechooser.showOpenDialog(this);
		final Component container = this;
		if (result == JFileChooser.APPROVE_OPTION) {
			// String filename = filechooser.getSelectedFile().getPath();
			new Thread(new Runnable() {
				public void run() {
					try {
						BufferedImage iicon = ImageIO.read(filechooser
								.getSelectedFile());

						if (iicon.getHeight() <= 0 && iicon.getWidth() <= 0) {
							JOptionPane.showMessageDialog(container,
									Controller.propBundle
											.getString("invalid_image"));
							return;
						} else {
							model.setImage(iicon);
							Image icon = iicon.getScaledInstance(20, 20,
									Image.SCALE_FAST);
							imageBtn.setIcon(new ImageIcon(icon));
							// TODO: werk af
						}
					} catch (IOException e) {
						// e.printStackTrace();
						JOptionPane.showMessageDialog(container,
								Controller.propBundle
										.getString("error_image_reading"),
								Controller.propBundle.getString("error"),
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}).start();

		}

	}
}
