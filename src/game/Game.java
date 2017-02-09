package game;

public class Game {
	private static Game instance = null;
	
	private Game () {}
	
	public static Game getInstance () {
		if (instance == null) {
			instance = new Game ();
		}
		return instance;
	}
	
	public void startGame () {
		TCPSocket.getInstance().start();
	}
	
	public void joinPlayer () {
		
	}
	
	public void receivePacket (String msg) {
		
	}
}
