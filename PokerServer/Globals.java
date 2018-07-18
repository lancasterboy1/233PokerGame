public class Globals{
	//Constants
	public final static int PORT = 992;
	public final static String ADDRESS = "127.0.0.1";
	final static int EXPECTED_USERS=10;
	final static int USER_VECTOR_CAPACITY_INCREMENT=5;
	final static int MAX_INPUT_CHARACTERS=1024;
	final static String[] FACE_VALUES=["2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"];
	final static String[] SUITS=["diamonds","clubs","spades","hearts"];
	final static String[] PHASE_ARRAY=["DISCARD","BET","FINISH"]; //must end with FINISH
	
	//Commands
	final static String USER_CMD_JOIN="JOIN";
	final static String USER_CMD_START="START";
	final static String USER_CMD_EXIT="EXIT";
	final static String USER_RESP_HOLD="HOLD";
	final static String USER_RESP_DISCARD="DISCARD";
	final static String USER_RESP_FOLD="FOLD";
	final static String USER_RESP_CALL="CALL";
	final static String USER_RESP_RAISE="RAISE";
	
	//General use functions
	protected static String cleanInput(char[] charBuf, int bufLength){
		if(bufLength>charBuf.length) return "";
		
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<bufLength;i++){
			if(charBuf[i]>=32 && charBuf[i]<=126){
				builder.append(charBuf[i]);
			}
		}
		return builder.toString();
	}
}