import java.util.Vector;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class Game extends Globals{
	private Vector<Client> players;
	private Vector<Client> activePlayers;
	private Map<Client,String> playerResponses;
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
		this.playerResponses = new HashMap<Client,String>(this.players.size()); //please leave this here i need it
		
		Iterator<Client> itr = players.iterator();
		while(itr.hasNext()) {
			itr.next().numChips=1000;
		}
		// fun tip: Collections.shuffle(this.deck) shuffles the order of the items in the deck
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
		this.playerResponses.clear();
		Iterator<Client> itr = this.activePlayers.iterator();
		while(itr.hasNext()) {
			Client plr = itr.next();
			plr.println("Your hand: \n"+plr.getHand()+"\nYou can:\nHOLD\nDISCARD [the numbers of the cards to discard, ie 1, 2, 3, 4, or 5, separated by spaces]");
			plr.waitingForInput=true;
		}
	}

	//Marc
	private void discardResponse(Client player, String response){ //UNTESTED
		String[] cmds = response.split(" ");
		boolean success = false;
		if(cmds[0] == USER_RESP_HOLD){
			success = true;
		}
		else if(cmds[0] == USER_RESP_DISCARD){
			success = true;
			if(cmds.length==1) success = false;
			else{
				try{
					Vector<Integer> discardList = new Vector<Integer>();
					for(int i=1;i<cmds.length;i++){
						int cardNum = Integer.parseInt(cmds[i]);
						if(cardNum>=1 && cardNum<=player.hand.size())
							discardList.add(new Integer(cardNum));
						else
							success = false;
					}
					if(success){
						Iterator<Integer> itr = discardList.iterator();
						while(itr.hasNext()){
							int handIndex = itr.next() - 1; //Players will give a number 1 - 5, the card vector will have indexes 0 - 4
							//Swap out all the cards the player wants discarded for cards from the top of the deck,
							//moving the discarded cards to the bottom of the deck
							this.deck.add(0,player.hand.elementAt(handIndex));
							player.hand.remove(handIndex);
							player.hand.add(handIndex,this.deck.lastElement());
							this.deck.remove(this.deck.lastElement());
						}
						//Collections.shuffle(this.deck) OPTIONAL SHUFFLING STEP
					}
				}
				catch(NumberFormatException e) success = false;
				catch(IndexOutOfBoundsException e) success = false;
			}
		}
		if(!success) player.out.println("Your options are:\nHOLD\nDISCARD [the numbers of the cards to discard, ie 1, 2, 3, 4, or 5, separated by spaces]");
		else{
			this.playerResponses.put(player,response);
			player.waitingForInput = false;
			
			//Check if everyone responded
			if(this.playerResponses.size()==this.activePlayers.size()){
				Iterator<Client> itr = this.activePlayers.iterator();
				while(itr.hasNext()) {
					Client plr = itr.next();
					plr.println("Your new hand: \n"+plr.getHand());
				}
				this.nextPhase();
			}
			else{
				player.out.println("Please wait for the other players...");
			}
		}
	}
	
	private void tellAllPlayers(String msg){
		Iterator<Client> itr = players.iterator();
		while(itr.hasNext()) {
			itr.next().println(msg);
		}
	}

	// Austyn
	private int totalPlayers(){
		return this.players.size();
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