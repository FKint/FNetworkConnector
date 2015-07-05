package fnetworkconnector.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fnetworkconnector.*;
import fnetworkconnector.servermanager.ServerManagerListener;

public class ServerPortSettingsDialog extends JDialog implements ServerManagerListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2446769473850456008L;

	protected JPanel listPanel;
	protected JList portsList;
	protected JPanel buttonPanel;
	protected JButton addBtn, removeBtn;
	
	protected JPanel confirmPanel;
	protected JButton closeBtn;
	
	protected Model model;
	protected Integer[] ports;
	
	public ServerPortSettingsDialog(Model model) {
		super();
		
		setModel(model);
		
		getContentPane().setLayout(new BorderLayout());
		listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		portsList = new JList();
		reloadServerList();
		listPanel.add(portsList);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		addBtn = new JButton(Controller.propBundle.getString("add"));
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addServer();
			}
		});
		removeBtn = new JButton(Controller.propBundle.getString("remove"));
		removeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeServer();
			}
		});

		buttonPanel.add(addBtn);
		buttonPanel.add(removeBtn);

		listPanel.add(buttonPanel, BorderLayout.LINE_END);
		
		getContentPane().add(listPanel, BorderLayout.CENTER);
		
		confirmPanel = new JPanel();
		confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.LINE_AXIS));
		
		closeBtn = new JButton(Controller.propBundle.getString("close"));
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		confirmPanel.add(Box.createHorizontalGlue());
		confirmPanel.add(closeBtn);
		getContentPane().add(confirmPanel, BorderLayout.PAGE_END);
		
		setModal(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void reloadServerList() {
		//TODO werk bij

		Integer[] ports = model.getServerPorts();
		this.ports = ports;
		portsList.setListData(ports);
	}
	
	private void removeServer() {
		// TODO: afwerken
		if(JOptionPane.showConfirmDialog(getContentPane(), Controller.propBundle.getString("sure_delete_server")) == JOptionPane.YES_OPTION){
			try{
				try{
					int selected = Integer.parseInt(portsList.getModel().getElementAt(portsList.getSelectedIndex()).toString());
					//int selected = Integer.parseInt((String)portsList.getSelectedValue());
					if(model.removeServerOnPort(selected)){
						reloadServerList();
					}else{
						JOptionPane.showMessageDialog(getContentPane(), Controller.propBundle.getString("server_close_failed"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
					}
				}catch(NumberFormatException ex1){
					JOptionPane.showMessageDialog(getContentPane(), Controller.propBundle.getString("invalid_value"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
				}
			}catch(NumberFormatException ex){
				//ex.printStackTrace();
				JOptionPane.showMessageDialog(getContentPane(), Controller.propBundle.getString("invalid_value"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private void addServer() {
		new AddServerPortDialog(model);
		reloadServerList();
	}
	
	public void setModel(Model model){
		this.model = model;
		model.addServerManagerListener(this);
	}


	@Override
	public void ownServerPortChanged(int ownServerPort) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serversChanged() {
		// TODO Auto-generated method stub
		reloadServerList();
	}
	
	public void finalize(){
		model.removeServerManagerListener(this);
		try {
			super.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			JOptionPane.showMessageDialog(getContentPane(), Controller.propBundle.getString("error_destroying_objects"), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		}
	}
}
