import java.util.Vector;

public class Game{
	private Vector<Client> players;
	private Vector<Client> activePlayers;
	private Vector<Card> deck;
	private boolean gameWaiting;
	private int gamePhase; //index of PHASE_ARRAY
	private boolean gameIsFull=false;

	
	public boolean isFull() {
		return gameIsFull;
	}
	
	public void Game(Client creator) {
		players = new Vector<Client>();
		deck = new Vector<Card>();
		players.add(creator);
		creator.currentGame=this;
		this.gameWaiting=true;
	}
	
	
	public void startGame() {
		Iterator<Client> itr = players.iterator();
		while(itr.hasNext()) {
			itr.next().numChips=1000;
		}
		this.gameWaiting=false;
		startRound();
	}

	private void betResponse(Client user, String cmd){}
	private void playerContinueResponse(Client user, String cmd){}
	
	public void userSentData(Client user, String cmd){
		if(this.gameWaiting){}
		else{
			String currentPhase = PHASE_ARRAY[gamePhase];
			if(currentPhase=="DISCARD") this.discardResponse(user,cmd);
			else if(currentPhase=="BET") this.betResponse(user,cmd);
			else if(currentPhase=="FINISH") this.playerContinueResponse(user,cmd);
		}
	}

	private void discardPhase(){

		for(int x = 0; x < players.size(); x++){

			[players(x)].out.println("Please ")

		}
	}

	private void discardResponse(Client player, String response){



	}
}