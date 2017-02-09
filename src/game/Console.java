package game;

import java.util.Scanner;

public class Console {
	private final Command[] commands = new Command[] {
			new Command("newgame", "", "Starts a new game and waits for players"),
			new Command("start", "details", "Starts the game"),
			new Command("pause", "", "pauses the game and saves it"),
			new Command("continue", "", "continues the game"),
			new Command("quit", "", "Quits the game"),
			new Command("info", "details", "lists information about the players"),
			new Command("help", "", "lists all commands")			
	};
	
	private static Console instance = null;
	
	private Console () {}
	
	public Console getInstance () {
		if (instance == null) {
			instance = new Console();
		}
		return instance;
	}
	
	public void runCmd (String cmd) {
		String[] cmds = cmd.split(" ");
		//newgame
		if (cmds[0].equals(commands[0].getCommand())) {
			
		}
	}
	
	public void read () {
		Scanner s = new Scanner(System.in);
		while (true) {
			String msg = s.nextLine();
			runCmd(msg);
			if (msg.equals(commands[4].getCommand())) {
				break;
			}
		}
	}
}
