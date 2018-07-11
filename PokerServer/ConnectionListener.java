import java.util.Vector;
import java.util.Enumeration;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;

public class ConnectionListener extends Thread{
	
	public static Vector<ClientHandler> clientList;
	public static ServerSocket serverSocket;
	public boolean cont;
	
	public void run(){
		//CHECKS:
		//NEW CLIENT CONNECTIONS
		System.out.println("Listening for new connections...");
		this.cont=true;
		while(this.cont){
			try{
				Socket newSocket = serverSocket.accept(); //blocks
				
				//Check if they're already on the list, remove duplicates
				Vector<ClientHandler> removalList = new Vector<ClientHandler>(1,1);
				for(Enumeration<ClientHandler> en=clientList.elements();en.hasMoreElements();){
					ClientHandler i = en.nextElement();
					if(newSocket.getInetAddress().toString().equals(i.IP.toString())){
						System.out.println("Instance of client "+i.IP+" already listed, removing duplicate.");
						i.close();
						removalList.add(i);
					}
				}
				for(Enumeration<ClientHandler> en=removalList.elements();en.hasMoreElements();){
					ClientHandler i = en.nextElement();
					clientList.remove(i);
				}
				removalList.clear();
				//Add them to the list
				ClientHandler newClient = new ClientHandler(newSocket);
				clientList.add(newClient);
				GameHandler.newClientConnection(newClient);
			}
			catch(IOException e){}
		}
		System.out.println("Thread process finished.");
		return;
	}
}