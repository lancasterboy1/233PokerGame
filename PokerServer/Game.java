

public class Game{
	private Vector<Client> players;
	private Vector<Client> activePlayers;
	private Vector<Card> deck;
	private boolean gameWaiting;
	private int gamePhase; //index of PHASE_ARRAY
	private Boolean gameIsFull=false;
	
<<<<<<< HEAD
	public static Boolean isFull() {
		return gameIsFull;
	}
	
=======
>>>>>>> 5885324e1309d75ead9bef91bb0991a599d3890a
	public static void Game(Client creator) {
		players = new Vector<Client>();
		deck = new Vector<Card>();
		players.add(creator);
		creator.currentGame=this;
		this.gameWaiting=true;
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