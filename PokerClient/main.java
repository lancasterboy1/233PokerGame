class main{
	
	public static void main(String[] args){
		ServerConnection server = new ServerConnection("127.0.0.1",992);
		if(server.sSocket==null){}
		else{
			server.out.println("sup");
			String inputText = server.in.readLine();
			System.out.println("Received from server: "+inputText);
		}
	}
}