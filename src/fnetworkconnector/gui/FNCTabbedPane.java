package fnetworkconnector.gui;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fnetworkconnector.*;

public class FNCTabbedPane extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6493024197823617274L;
	
	protected UInterface ownerUI;
	
	public FNCTabbedPane(UInterface tmpOwnerUI) {
		super();
		
		ownerUI = tmpOwnerUI;
		
		addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				//menubar.panelChangeButtonEditor(getSelectedComponent());
				ownerUI.selectedActivityChanged();
			}
		});
		ownerUI.selectedActivityChanged();
	}
}
