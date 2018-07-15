

public class Game{
	private Vector<Client> players;
	private Vector<Client> activePlayers;
	private Vector<Card> deck;
	private boolean gameWaiting;
	private int gamePhase; //index of PHASE_ARRAY
	
	public static void Game(Client creator) {
		players = new Vector<Client>();
		deck = new Vector<Card>();
		players.add(creator);
		creator.currentGame=this;
		this.gameWaitin=true;
	}
	
	
	public static void startGame() {
		Iterator<Client> itr = players.iterator();
		while(itr.hasNext()) {
			itr.next().numChips=1000;
		}
		this.gameWaiting=false;
		startRound();
	}
}