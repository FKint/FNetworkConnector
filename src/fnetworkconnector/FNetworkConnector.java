package fnetworkconnector;

public class FNetworkConnector {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		if(args.length >= 1){
			if(args[0].equals("gui")){
				new Controller(Controller.GUI);
			}else if(args[0].equals("console")){
				new Controller(Controller.CONSOLE);
			}else{
			//	System.out.println("Invalid argument(s)");
			}
		}else{
			new Controller(Controller.GUI);
		}
	}
	
}
