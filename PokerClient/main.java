import java.io.IOException;

class main extends Globals{
	
	public static void main(String[] args){
		ServerConnection server = new ServerConnection(ADDRESS,PORT);
		if(server.sSocket==null){}
		else{
			try{
				server.out.println("sup");
				String inputText = server.in.readLine();
				System.out.println("[DEBUG] Received from server: "+inputText);
			}
			catch(IOException e){}
		}
	}
}