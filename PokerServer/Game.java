import java.util.Vector;

public class Game{
	private Vector<Client> players;
	private Vector<Client> activePlayers;
	private Vector<Card> deck;
	private boolean gameWaiting;
	private int gamePhase; //index of PHASE_ARRAY
	private boolean gameIsFull=false;
	
	private int currentBetTurn=0; // 0 == first player bet, 1 == every other player bet in first round, 2 == second round of bet (can't raise)
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

	// Phases / Responses
	// These define the writing to players / handling player responses for each of discarding, betting,

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
	private int totalPlayers(){
		return this.players.length();
	}

	// Austyn
	private void betPhase(){

		currentBetTurn=0;
		currentBet=0;
		consecutiveCalls=0;
		allIn=false;

		int playerCount = totalPlayers();

		Iterator<Client> itr = players.iterator();

		// First player must call minimum call of 2 chips
		plr = itr.next();
		plr.println("The minimum bet is: 2 chips\nYour hand: " + plr.getHand() + "\nYou can:\nRAISE\nFOLD\nCALL\nGO ALL IN");
		plr.waitingForInput = true;
		betResponse();
		plr.waitingForInput = false;

		currentBetTurn = 1;
		for(int x = 1; x < playerCount; x++) {
			plr = itr.next();
			plr.println("The current bet is: " + currentBet + " chips\nYour hand: " + plr.getHand() + "\nYou can:\nRAISE\nFOLD\nCALL\nGO ALL IN");
			plr.waitingForInput = true;
			/*betResponse();
			plr.waitingForInput = false;*/
			// betResponse() is called by userSentData only, in the event that a client sends data,
			// their waitingForInput is true, and the current phase is betPhase
		}

		while(itr.hasPrevious()) {
			plr = itr.previous();
		}

		currentBetTurn = 2;
		for(int x = 1; x < playerCount; x++) {
			plr = itr.next();
			plr.println("The current bet is: " + currentBet + " chips\nYour hand: " + plr.getHand() + "\nYou can:\nFOLD\nCALL");
			plr.waitingForInput = true;
			/*betResponse();
			plr.waitingForInput = false;*/
			//same story here
		}

		while(itr.hasPrevious()) {
			plr = itr.previous();
		}

	}

	// Austyn
	private void betResponse(Client user, String cmd){

		if (currentBetTurn == 0) {

		}
		else if (currentBetTurn == 1){

		}
		else if (currentBetTurn == 2){

		}


	}

	// Austyn
	private void playerContinueResponse(Client user, String cmd){


	}
}