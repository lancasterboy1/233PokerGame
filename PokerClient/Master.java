

class Master implements Globals{
	private BufferedReader stdIn;
	private static cont;
	
	public static void main(String[] args){
		stdIn = new BufferedReader(new InputStreamReader(System.in));
		mainLoop(); //This is a standin; our real main loop will be the GUI's main loop
	}
	
	private static void mainLoop(){
		cont=true;
		while(cont){
			//CHECK STDIN
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
							EventHandler.consoleSentData(inputText);
						}
					}
				}
			}
			catch(IOException e){}
			catch(ArrayIndexOutOfBoundsException e){}
		}
	}
	
	public static String cleanInput(char[] charBuf, int bufLength){
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<bufLength;i++){
			if(charBuf[i]>=32 && charBuf[i]<=126){
				builder.append(charBuf[i]);
			}
		}
		return builder.toString();
	}
}