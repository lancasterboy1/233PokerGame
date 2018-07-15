import java.io.PrintWriter;
import java.util.Vector;
import java.util.Enumeration;

public class GameHandler{
	
	public static Vector<Client> clientList; //Auto updates as people join and leave
	
	private static Vector<Game> gameList;
	
	/*
	* This function is called when the server first begins
	*/
	public static void init(){
		
	}
	
	/*
	* Every time a new client connects, this function is called
	* (RUNS ON A THREAD)
	* @param client The client who connected
	*/
	public static void newClientConnection(Client client){
		//todo
	}
	
	/*
	* Every time a server command is entered, this function is called
	* @param cmd The command entered
	*/
	public static void consoleSentData(String cmd){
		if(cmd.equals("quit")) Master.close();
	}
	
	/*
	* Every time a client sends a message, this function is called
	* @param client The client who sent a message
	* @param cmd The message they sent
	*/
	public static void clientSentData(Client client, String cmd){
		
		if(cmd.equals("exit")){
			client.close();
			clientList.remove(client);
			System.out.println(client.IP+" disconnected.");
			for(Enumeration<ClientHandler> en=clientList.elements();en.hasMoreElements();){
				ClientHandler i = en.nextElement();
				i.out.println(client.IP+" disconnected.");
			}
		}
		//todo
		else if(user.waitingForInput)
			clientSentData(user, cmd);
	}
}