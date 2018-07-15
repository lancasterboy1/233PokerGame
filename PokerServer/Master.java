import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.io.*;
import java.util.Vector;
import java.util.Enumeration;
import java.lang.Thread;
import java.util.Iterator;

public class Master extends Globals{
	
	private static BufferedReader stdIn;
	private static Thread connectionListener;
	private static ServerSocket serverSocket;
	private static Vector<Client> clientList;
	private static boolean cont;
	
	public static void main(String[] args){
		try{
			clientList = new Vector<Client>(EXPECTED_USERS,USER_VECTOR_CAPACITY_INCREMENT);
			GameHandler.clientList=clientList;
			ConnectionListener.clientList=clientList;
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			serverSocket = new ServerSocket(PORT);
			ConnectionListener.serverSocket=serverSocket;
			connectionListener = new ConnectionListener();
			GameHandler.init();
			System.out.println("Established on port "+PORT);
			connectionListener.start();
			inputLoop();
		}
		catch(IOException e){
			System.out.println("Failed to open a socket.");
			close();
		}
		System.exit(0); //for some reason it doesnt close here, probably the ConnectionListener thread
	}
	
	private static void inputLoop(){
		//CHECKS:
		//STDIN INPUT
		//CONNECTED CLIENT INPUT
		cont=true;
		while(cont){
			//STDIN INPUT CHECK
			try{
				if(stdIn.ready()){
					char[] charBuf = new char[MAX_INPUT_CHARACTERS+1]; //I'M ASSUMING ALL VALUES INIT TO 0
					int numRead = stdIn.read(charBuf,0,MAX_INPUT_CHARACTERS+1);
					if(charBuf[MAX_INPUT_CHARACTERS]!=0){ //too many characters
						while(stdIn.ready()) stdIn.read();
						System.out.println("Message sent had too many characters.");
					}
					else if(numRead<0){
						System.out.println("Console reached EOF.");
					}
					else{ //valid message
						String inputText = cleanInput(charBuf,numRead);
						if(inputText.equals("")){}
						else{
							GameHandler.consoleSentData(inputText);
						}
					}
				}
			}
			catch(IOException e){}
			//CONNECTED CLIENT INPUT CHECK
			for(Enumeration<Client> en=clientList.elements();en.hasMoreElements();){
				Client i = en.nextElement();
				try{
					if(i.in.ready()){
						char[] charBuf = new char[MAX_INPUT_CHARACTERS+1]; //I'M ASSUMING ALL VALUES INIT TO 0
						int numRead = i.in.read(charBuf,0,MAX_INPUT_CHARACTERS+1);
						if(charBuf[MAX_INPUT_CHARACTERS]!=0){ //too many characters
							while(i.in.ready()) i.in.read();
							i.out.println("Message sent had too many characters.");
						}
						else if(numRead<0){ //client disconnected
							System.out.println("Automatic disconnection.");
						}
						else{ //valid message
							String inputText = cleanInput(charBuf,numRead);
							if(inputText.equals("")){}
							else{
								GameHandler.clientSentData(i,inputText);
							}
						}
					}
				}
				catch(SocketException e){System.out.println("SocketException thrown.");}
				catch(IOException e){
					if(!cont){}
					else{
						System.out.println("IOException thrown (probably thread interference).");
					}
				}
				catch(NullPointerException e){}
			}
		}
	}
	
	private static String cleanInput(char[] charBuf, int bufLength){
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<bufLength;i++){
			if(charBuf[i]>=32 && charBuf[i]<=126){
				builder.append(charBuf[i]);
			}
		}
		return builder.toString();
	}
	
	public static void close(){
		System.out.println("Closing server...");
		try{
			cont=false;
			connectionListener.interrupt();
			stdIn.close();
			serverSocket.close();
			for(Enumeration<Client> i=clientList.elements();i.hasMoreElements();){
				i.nextElement().close();
			}
		}
		catch(IOException e){
			System.out.println("Error in shutting down. Forcing shutdown.");
			System.exit(0);
		}
	}
}