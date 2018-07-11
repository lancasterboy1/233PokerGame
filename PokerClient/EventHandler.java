

public class EventHandler implements Globals{
	private static ServerConnection server;
	
	public static void consoleSentData(String cmd){
		if(cmd.equals("join")){
			server = new ServerConnection(ADDRESS,PORT);
			if(server.sSocket==null){
				System.out.println("Failed to connect.");
				server=null;
			}
		}
		else if(server != null){
			server.out.println(cmd);
		}
	}
	public static void serverSentData(String cmd){
		System.out.println(cmd);
	}
}