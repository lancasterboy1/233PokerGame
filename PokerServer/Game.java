

public class Game{
	private Vector<Client> players;
	private Vector<Client> activePlayers;
	private Vector<Card> deck;
	private String gameState; //WAITING or IN PROGRESS
	private int gamePhase; //index of PHASE_ARRAY
	private Boolean gameIsFull=false;
	
	public static Boolean isFull() {
		return gameIsFull;
	}
	
	public static void Game(Client creator) {
		players = new Vector<Client>();
		deck = new Vector<Card>();
		players.add(creator);
		creator.currentGame=this;
		this.gameState="WAITING";
	}
	
	
	public static void startGame() {
		Iterator<Client> itr = players.iterator();
		while(itr.hasNext()) {
			itr.next().numChips=1000;
		}
		startRound();
	}
}