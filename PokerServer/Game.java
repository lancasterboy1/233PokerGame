public class Game{
	private Vector<ClientHandler> players;
	private Vector<ClientHandler> activePlayers;
	private Vector<Card> deck;
	private String gameState; //WAITING or IN PROGRESS
	private int gamePhase; //index of PHASE_ARRAY
}