public class Card extends Globals{
	
	private int faceVal;
	private int suitVal;
	
	public Card(int face,int suit){
		//Check if face is an index of FACE_VALUES and suit is an index of SUIT_VALUES
		this.faceVal=face;
		this.suitVal=suit;
	}
	
	public String getName(){
		return FACE_VALUES[face]+" of "+SUITS[suit];
	}
}