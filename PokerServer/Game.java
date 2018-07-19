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
	private int totalChips;

	Iterator<Client> betItr = players.iterator();

	
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
	
	//Xiedong
	public void addPlayer(Client player){}
	private void endRound() {}
	private void endGame(Client player) {}
	
	//Marc
	public void removePlayer(Client player){ //UNTESTED
		if(player.waitingForInput){ //this will get rid of their waitingForInput
			if(PHASE_ARRAY[gamePhase]=="DISCARD"){
				this.userSentData(player,USER_RESP_HOLD); //auto-hold
			}
			else if(PHASE_ARRAY[gamePhase]=="BET"){
				this.userSentData(player,USER_RESP_FOLD); //auto-fold
			}
		}
		
		player.currentGame=null;
		player.hand.clear();
		this.players.remove(player);
		this.activePlayers.remove(player);
		//POSSIBLE GLITCH: During the bet phase, a player leaves, fucking up the iterator and either skipping someone or accessing a nonexistent element of activePlayers
		if(players.size()==1){
			Client lastPlayer = this.players.elementAt(0);
			lastPlayer.waitingForInput = false;
			this.endGame(lastPlayer);
	}
	
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


		/**
		 * tellAllPlayers sends a message to all players to be used throughout the code
		 */
	private void tellAllPlayers(String msg){
		Iterator<Client> itr = players.iterator();
		while(itr.hasNext()) {
			itr.next().println(msg);
		}
	}

	// Austyn
		/**
		 * totalPlayers totals the amount of players in the vector
		 */
	private int totalPlayers(){
		return this.players.size();
	}

	// Austyn
		/**
		 *  betPhase writes to individual player screens depending on the phase of betting that is occuring
		 *  actions are: RAISE [AMT] (ie RAISE 5, RAISE 12, etc), CALL, FOLD, GO ALL IN
		 *  if the first player to bet is betting, they must call a minimum 2 chips
		 *  if every other player is betting in the first round, they can perform all actions
		 *  if the players are betting in the second round, they cannot raise / go all in such as to limit betting to two rounds
		 */
	private void betPhase(){

			int playerCount = totalPlayers();
			// Iterator<Client> itr = players.iterator();
			// ^ made into global variable called betItr


		// First player bet phase (minimum bet is 2 chips)
		if (currentBetTurn == 0) {
			currentBetTurn = 0;
			currentBet = 0;
			consecutiveCalls = 0;

			// not sure we need this boolean..
			allIn = false;

			plr = betItr.next();
			plr.println("The minimum bet is: 2 chips\nYour hand: " + plr.getHand() + "\nYou can:\nRAISE\nFOLD\nCALL\nGO ALL IN");
			plr.waitingForInput = true;

			currentBetTurn = 1;

		}

		// First round, can call, raise, go all in, fold
		else if (currentBetTurn == 1) {


			for (int x = 1; x < playerCount; x++) {
				plr = betItr.next();
				plr.println("The current bet is: " + currentBet + " chips\nYour hand: " + plr.getHand() + "\nYou can:\nRAISE [AMT]\nFOLD\nCALL\nGO ALL IN");
				plr.waitingForInput = true;
			/*betResponse();
			plr.waitingForInput = false;*/
				// betResponse() is called by userSentData only, in the event that a client sends data,
				// their waitingForInput is true, and the current phase is betPhase
			}

			while (betItr.hasPrevious()) {
				plr = betItr.previous();
			}

			currentBetTurn = 2;
		}

		// Last round, cannot raise / go all in
		else if (currentBetTurn == 2) {

			for (int x = 1; x < playerCount; x++) {
				plr = betItr.next();
				plr.println("The current bet is: " + currentBet + " chips\nYour hand: " + plr.getHand() + "\nYou can:\nFOLD\nCALL");
				plr.waitingForInput = true;
			/*betResponse();
			plr.waitingForInput = false;*/
				//same story here
			}

			while (betItr.hasPrevious()) {
				plr = betItr.previous();
			}

			currentBetTurn =0;
		}

	}

	// Austyn
		/**
		 * betResponse chases all variables depending on player response (ie currentBet, totalChips, individual player numChips,
		 */
	private void betResponse(Client player, String cmd){

		// First player bet, minimum call is 2 chips
		if (currentBetTurn == 0) {
			if (cmd == "FOLD")
				removePlayer(player);
			if else (cmd == "CALL"){
				if (player.numChips <= 2) {
					player.out.println("You are all in");
					totalChips += player.numChips;
					player.numChips = 0;
					player.allIn = true;
					currentBet = 2;
				}
				else {
					totalChips = 2;
					player.numChips -= 2;
				}
			}
			if else (cmd == "GO ALL IN"){
				totalChips += player.numChips;
				currentBet = player.numChips;
				player.allIn = true;
				player.numChips = 0;
			}
			else {
				String[] splited = cmd.split("\\s+");
				int result = Integer.parseInt(splitted[1]);

				// didn't put an error message.. will add later
				if (splitted[0] == "RAISE") {
					if (result >= player.numChips) {
						player.out.println("You are all in");
						totalChips += player.numChips;
						currentBet = player.numChips;
						player.numChips = 0;
						player.allIn = true;
					}
						else {
						currentBet = result;
						totalChips += result;
						player.numChips -= result;
					}
				}
				else
					player.out.prinltn("Invalid Entry");
				// what else to write here for error?
			}
		}

		// First round
		else if (currentBetTurn == 1){
			if (cmd == "FOLD")
				removePlayer(player);
			if else (cmd == "CALL"){
				totalChips += currentBet;
				player.numChips -= currentBet;
			}
			if else (cmd == "GO ALL IN"){
				totalChips += player.numChips;
				currentBet = player.numChips;
				player.allIn = true;
				player.numChips = 0;
			}
			else {
				String[] splited = cmd.split("\\s+");
				int result = Integer.parseInt(splitted[1]);

				// didn't put an error message.. will add later
				if (splitted[0] == "RAISE") {
					if (result >= player.numChips){
						player.out.println("You are all in");
						totalChips += player.numChips;
						currentBet = player.numChips;
						player.numChips = 0;
					}
					else {
						currentBet = result;
						totalChips += result;
						player.numChips -= result;
					}
				}
				else
					player.out.prinltn("Invalid Entry");
				// what else to write here for error?
			}
		}

		// Second round, cannot raise / go all in
		else if (currentBetTurn == 2){
			if (cmd == "FOLD")
				removePlayer(player);
			if (cmd == "CALL"){
				totalChips += currentBet;
				player.numChips -= currentBet;
			}
			else
				player.out.prinltn("Invalid Entry");
				// what else to write here for error?
		}


	}

	// Austyn
	private void playerContinueResponse(Client user, String cmd){
		// Fix yes / no response if the answers are incorrect
		if (cmd == "NO")
			removePlayer(player);
	}
}
