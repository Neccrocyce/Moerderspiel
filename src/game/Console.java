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
			if (Game.getInstance().getStatus() != 0) {
				System.out.println("Not possible! Game status has to be changed first");
				return;
			}
			Game.getInstance().newGame();
			return;
		}
		//start
		if (cmds[0].equals(commands[1].getCommand())) {
			if (Game.getInstance().getStatus() != 1) {
				System.out.println("Not possible! Game status has to be changed first");
				return;
			}
			Game.getInstance().start();
			if (cmds.length == 2 && cmds[1].equals(commands[1].getParameter())) {
				for (Person p : Game.getInstance().getPersons()) {
					System.out.println(p.getName() + "(" + p.getSeed() + ") -> " + p.getTarget().getName());
				}
			}
			return;
		}
		//pause
		
		
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
		s.close();
	}
}
