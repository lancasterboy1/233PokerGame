import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Vector;

public class Client{
	private Socket cSocket;
	public InetAddress IP;
	public BufferedReader in;
	public PrintWriter out;
	
	public int numChips;
	public Vector<Card> hand;
	public Game currentGame;
	public boolean waitingForInput;
	public boolean allIn = false;
	
	public Client(Socket clientSocket){
		this.cSocket=clientSocket;
		this.IP=cSocket.getInetAddress();
		try{
			this.in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);
		}
		catch(IOException e){
			System.out.println("Failed to communicate with client "+IP);
		}
		this.waitingForInput=false;
	}
	
	public String getHand(){ //UNTESTED
		int num = 1;
		String retString="";
		Iterator<Client> itr = hand.iterator();
		while(itr.hasNext()) {
			retString += ""+num+": "+itr.next().getName()+"\n";
			num+=1;
		}
		return retString;
	}
	
	public void close(){
		try{
			this.in.close();
			this.out.close();
			this.cSocket.close();
		}
		catch(IOException e){}
	}
}