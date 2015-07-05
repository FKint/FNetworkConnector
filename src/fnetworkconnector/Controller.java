package fnetworkconnector;
/*
import java.io.IOException;*/
import java.util.ResourceBundle;

public class Controller {
	final static int GUI = 3546;
	final static int CONSOLE = 3461;
	
	protected static int kind;
	
	public static final ResourceBundle propBundle = ResourceBundle.getBundle("properties/fnetworkconnector");
	
	protected Storage storage;
	protected Model model;
	protected UInterface uinterface;
	
	
	public Controller(int kind){
		storage = new Storage();
		model = new Model(storage);
		Controller.kind = kind;
		switch(kind){
			case GUI:
				uinterface = new GUI(model);
				break;
			case CONSOLE:
				uinterface = new ConsoleInterface();
				break;
			default:
				uinterface = null;
			//	System.out.println("Geen geldige interface gekozen");
				break;
		}
		
		model.start();
		uinterface.start();

	}
	
	public Controller(){
		this(GUI);
	}
	
	public static int getKind(){
		return kind;
	}

}
