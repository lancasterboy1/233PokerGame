import java.io.IOException;

class main{
	
	public static void main(String[] args){
		ServerConnection server = new ServerConnection("127.0.0.1",992);
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