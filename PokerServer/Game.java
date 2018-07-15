import java.util.Vector;

public class Game{
	private Vector<Client> players;
	private Vector<Client> activePlayers;
	private Vector<Card> deck;
	private boolean gameWaiting;
	private int gamePhase; //index of PHASE_ARRAY
	private boolean gameIsFull=false;
	
	private int currentBetTurn=0;
	private int currentBet=0;
	private int consecutiveCalls=0;
	private boolean allIn=false;

	
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
	
	public void addPlayer(Client player){}
	
	//Marc
	public void removePlayer(Client player){}
	
	public void startGame() {
		Iterator<Client> itr = players.iterator();
		while(itr.hasNext()) {
			itr.next().numChips=1000;
		}
		this.gameWaiting=false;
		startRound();
	}

	// Austyn - Marc
	public void userSentData(Client user, String cmd){
		if(this.gameWaiting){}
		else{
			String currentPhase = PHASE_ARRAY[gamePhase];
			if(currentPhase=="DISCARD") this.discardResponse(user,cmd);
			else if(currentPhase=="BET") this.betResponse(user,cmd);
			else if(currentPhase=="FINISH") this.playerContinueResponse(user,cmd);
		}
	}

	// Austyn - Marc
	private void discardPhase(){
		Iterator<Client> itr = players.iterator();
		while(itr.hasNext()) {
			plr = itr.next();
			plr.println("Your hand: "+plr.getHand()+"\nYou can:\nHOLD\nDISCARD [the numbers of the cards to discard, ie 1, 2, 3, 4, or 5, separated by spaces]");
			plr.waitingForInput=true;
		}
	}

	//Marc
	private void discardResponse(Client player, String response){
		
	}
	
	private void tellAllPlayers(String msg){
		Iterator<Client> itr = players.iterator();
		while(itr.hasNext()) {
			itr.next().println(msg);
		}
	}

	// Austyn
	private void betResponse(Client user, String cmd){


	}

	// Austyn
	private void playerContinueResponse(Client user, String cmd){


	}
}