import java.io.PrintWriter;
import java.util.Vector;
import java.util.Enumeration;

public class GameHandler{
	
<<<<<<< HEAD
	public static Vector<ClientHandler> clientList; //Auto updates as people join and leave
	public static Vector<Game> gameList;
=======
	public static Vector<Client> clientList; //Auto updates as people join and leave
	
	private static Vector<Game> gameList;
>>>>>>> 5885324e1309d75ead9bef91bb0991a599d3890a
	
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
<<<<<<< HEAD
	}
	
	public static void clientSendData(Client user, String cmd) {
		if(cmd=="JOIN") {
			Iterator<Game> gameIterator = gameList.iterator();
			while(gameIterator.hasNext()) {
				if(!gameIterator.next().isFull()) {
					gameIterator.next().addPlayer(user);
					break;
				}
			}
			Game newGame = new Game(user);
			gameList.add(newGame);
		}
		else if(cmd=="START") {
			if(user.currentGame!=null)
				user.currentGame.startGame();
		}
		else if(cmd=="EXIT") {
			if(user.currentGame!=null)
				user.currentGame.removePlayer(user);
		}
		if(user.waitingForInput)
=======
		//todo
		else if(user.waitingForInput)
>>>>>>> 5885324e1309d75ead9bef91bb0991a599d3890a
			clientSentData(user, cmd);
	}
}