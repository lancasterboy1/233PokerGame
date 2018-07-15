import java.util.Vector;

public class Game{
	private Vector<ClientHandler> players;
	private Vector<ClientHandler> activePlayers;
	private Vector<Card> deck;
	private String gameState; //WAITING or IN PROGRESS
	private int gamePhase; //index of PHASE_ARRAY
	
	public static void Game(ClientHandler creator) {
		players = new Vector<ClientHandler>();
		deck = new Vector<Card>();
		players.add(creator);
		creator.currentGame=this;
		this.gameState="WAITING";
	}
	
	
	public static void startGame() {
		Iterator<ClientHandler> itr = players.iterator();
		while(itr.hasNext()) {
			itr.next().numChips=1000;
		}
		startRound();
	}

	private void discardPhase(){

		for(int x = 0; x < players.size(); x++){

			[players(x)].out.println("Please ")

		}
	}

	private void discardResponse(Client player, String response){



	}



	private void userSentData(Client user, String cmd){

		if (!this.gameWaiting|) {
			if (PHASE_ARRAY(gamePhase) == "DISCARD") // discard phase
				discardResponse(user, cmd);
			else if (PHASE_ARRAY(gamePhase) == "BET") // bet phase
				betResponse(user, cmd);
			else if (PHASE_ARRAY(gamePhase) == "FINISH") // finish
				playerContinueResponse(user, cmd);
		}
	}


}