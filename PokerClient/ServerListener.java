

public class ServerListener extends Thread{
	public ServerConnection server;
	private boolean cont;
	
	public void run(){
		this.cont=true;
		while(cont){
			//CHECK FOR SERVER MESSAGES
			if(server.in.ready()){
				char[] charBuf = new char[MAX_INPUT_CHARACTERS+1]; //I'M ASSUMING ALL VALUES INIT TO 0
				int numRead = server.in.read(charBuf,0,MAX_INPUT_CHARACTERS+1);
				if(charBuf[MAX_INPUT_CHARACTERS]!=0){ //too many characters
					while(server.in.ready()) server.in.read();
					System.out.println("Message sent by server had too many characters.");
				}
				else if(numRead<0){
					System.out.println("Console reached EOF.");
				}
				else{ //valid message
					String inputText = Master.cleanInput(charBuf,numRead);
					if(inputText.equals("")){}
					else{
						EventHandler.serverSentData(inputText);
					}
				}
			}
		}
	}
}