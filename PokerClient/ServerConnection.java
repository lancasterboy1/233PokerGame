import java.net.Socket;
import java.net.InetAddress;
import java.io.*;

public class ServerConnection{
	//public Socket sSocket;
	public int[] sSocket;
	public InetAddress IP;
	public BufferedReader in;
	//public PrintWriter out;
	public PrintStream out;
	
	
	public ServerConnection(String address, int port){
		this.sSocket = new int[1];
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.out = System.out;
	}
	/*public ServerConnection(String address, int port){
		try{
			this.IP=InetAddress.getByName(address);
			this.sSocket = new Socket(this.IP, port);
			this.in = new BufferedReader(new InputStreamReader(sSocket.getInputStream()));
			this.out = new PrintWriter(sSocket.getOutputStream(), true);
		}
		catch(IOException e){
			System.out.println("Failed to gain connection to server.");
		}
	}*/
}