package fnetworkconnector.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import fnetworkconnector.connection.ConnectionHandler;
import fnetworkconnector.*;

public class ConnectionGUI extends JPanel implements ConnectionHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = -590866294419161183L;
	
	protected UInterface ownerUI;
	
	protected Connection connection;
	protected ArrayList<FNCContactPanel> contactpanels;
	protected int counter = 0;
	protected String talkerName;
	//TODO pas aan
	
	protected ConnectionMembersOverview leftPanel;
	protected JScrollPane overviewScrollPane;
	protected JEditorPane overviewPane;
	protected JPanel inputPanel;
	protected JScrollPane inputTextScrollPane;
	protected JTextArea inputTextPane;
	protected JButton inputSendButton;
	
	
	public ConnectionGUI(Connection tmpConnection){
		super();
		talkerName = "";
		
		this.setConnection(tmpConnection);
		setLayout(new BorderLayout());
		
		leftPanel = new ConnectionMembersOverview(connection);
		
		
		this.add(new JScrollPane(leftPanel), BorderLayout.LINE_START);
		
		overviewPane = new JEditorPane("text/html", "<html><body><p id='txt_0' class='txt_input'></p></body></html>");
		overviewPane.setEditable(false);
		overviewPane.setAutoscrolls(true);
		((javax.swing.text.html.HTMLDocument) overviewPane.getDocument()).getStyleSheet().addRule(".txt_input{padding:1px 1px 1px 1px; margin:1px 1px 1px 1px;}");
		overviewScrollPane = new JScrollPane(overviewPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		overviewScrollPane.setAutoscrolls(true);
		overviewScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(overviewScrollPane, BorderLayout.CENTER);
		inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS));
		inputPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		inputTextPane = new JTextArea();
		inputTextPane.setLineWrap(true);
		inputTextPane.setWrapStyleWord(true);
		inputTextPane.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					inputSendButton.doClick();
				} else if (e.getKeyCode() == KeyEvent.VK_TAB) {
					inputSendButton.requestFocusInWindow();
				}
			}
		});
		inputTextScrollPane = new JScrollPane(inputTextPane);
		inputPanel.add(inputTextScrollPane);
		inputSendButton = new JButton(Controller.propBundle.getString("send"));
		inputSendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = inputTextPane.getText();
				inputSend(text);
				inputTextPane.requestFocus();
				inputTextPane.setText("");
			}
		});
		inputPanel.add(inputSendButton);
		add(inputPanel, BorderLayout.PAGE_END);
	}
	
	public void setConnection(Connection tmpConnection){
		if(connection != null){
			connection.removeConnectionListener(this);
		}
		connection = tmpConnection;
		connection.addConnectionListener(this);
	}

	public String getTitle() {
		return connection.getTitle();
	}
	
	public String getTalker(){
		return connection.getOwnName();
	}
	
	public Connection getConnection(){
		return connection;
	}
	
	
	public void inputSend(Object toSend){
		connection.sendData(toSend);
	}
	
	public void handleText(String text, String talker){
		try {
			if (!talker.equals(talkerName)) {// TODO: talker
				text = "<b>" + talker + " " + Controller.propBundle.getString("says") + ": " + "</b><br>" + text;// TODO:
				talkerName = talker;
				// talker
			}
			((javax.swing.text.html.HTMLDocument) (overviewPane.getDocument())).insertAfterEnd(((javax.swing.text.html.HTMLDocument) (overviewPane.getDocument())).getElement("txt_" + counter), "<p id='txt_" + (counter + 1) + "' class='txt_input'>" + text + "</p>");
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					overviewScrollPane.getVerticalScrollBar().setValue(overviewScrollPane.getVerticalScrollBar().getMaximum());
				}
			});
			counter++;
		} catch (Exception ex) {
			//ex.printStackTrace();
			JOptionPane.showMessageDialog(this, Controller.propBundle.getString("error_text_handling")+":\n"+ex.getMessage().toString(), Controller.propBundle.getString("error"), JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public void dataSentSocket(Object data, SocketSet socketSet, Connection conn) {
		// TODO Auto-generated method stub
	}
	@Override
	public void dataSentGlobal(Object data, Connection conn){
		if(data instanceof String){
			handleText((String)data, connection.getOwnName());
		}
	}
	
	@Override
	public void dataReceived(Object data, SocketSet socketSet, Connection conn){
		Object tmpInput = data;
		if (tmpInput instanceof String) {
			handleText((String)tmpInput, socketSet.getName());
		}
	}

	@Override
	public void connectionMembersChanged(Connection conn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionDataChanged(Connection conn) {
		// TODO Auto-generated method stub
		
	}
}
