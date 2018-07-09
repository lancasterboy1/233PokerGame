import java.net.Socket;
import java.net.InetAddress;
import java.io.*;

public class ServerConnection{
	public Socket sSocket;
	public InetAddress IP;
	public BufferedReader in;
	public PrintWriter out;
	
	public ServerConnection(String address, int port){
		try{
			this.IP=InetAddress.getByName(address);
			this.sSocket = new Socket(this.IP, port);
			this.in = new BufferedReader(new InputStreamReader(sSocket.getInputStream()));
			this.out = new PrintWriter(sSocket.getOutputStream(), true);
		}
		catch(IOException e){
			System.out.println("Failed to gain connection to server.");
		}
	}
}