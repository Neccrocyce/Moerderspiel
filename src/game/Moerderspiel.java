package game;

public class Moerderspiel {
	private static final String folder = "moerderspiel/";
	
	public static void main (String[] args) {
		TCPSocket.setPort(16333);
		Console.getInstance().read();
	}
	
	public static String getFolder() {
		return folder;
	}
	
	
}
