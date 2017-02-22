package game;

import java.util.Scanner;

public class Console {
	private final Command[] commands = new Command[] {
			new Command("newgame", "", "Starts a new game and waits for players"),
			new Command("start", "details", "Starts the game"),
			new Command("pause", "", "Pauses the game and saves it"),
			new Command("continue", "", "Continues the game"),
			new Command("quit", "", "Quits the game"),
			new Command("status", "", "Shows the current status of the game"),
			new Command("info", "details", "Lists information about the players"),			
			new Command("exit", "", "Quits the server"),
			new Command("help", "", "Lists all commands")
	};
	
	private static Console instance = null;
	private boolean exit = false;
	
	private Console () {}
	
	public static Console getInstance () {
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
			printStatus();
			return;
		}
		
		//start
		if (cmds[0].equals(commands[1].getCommand())) {
			if (Game.getInstance().getStatus() != 1) {
				System.out.println("Not possible! Game status has to be changed first");
				return;
			}
			if (Game.getInstance().getPersons().size() == 0) {
				System.out.println("Not possible! No players joined");
				return;
			}
			Game.getInstance().start();
			printStatus();
			//details
			if (cmds.length == 2 && cmds[1].equals(commands[1].getParameter())) {
				for (Person p : Game.getInstance().getPersons()) {
					System.out.println(p.getName() + "(" + p.getSeed() + ") -> " + p.getTarget().getName());
				}
			}
			return;
		}
		
		//pause
		if (cmds[0].equals(commands[2].getCommand())) {
			if (Game.getInstance().getStatus() != 2) {
				System.out.println("Not possible! Game status has to be changed first");
				return;
			}			
			Game.getInstance().pause();
			printStatus();
			return;
		}
		
		//continue
		if (cmds[0].equals(commands[3].getCommand())) {
			if (Game.getInstance().getStatus() != 0 || Game.getInstance().getStatus() != 3) {
				System.out.println("Not possible! Game status has to be changed first");
				return;
			}			
			Game.getInstance().cont();
			printStatus();
			return;
		}
		
		//quit
		if (cmds[0].equals(commands[4].getCommand())) {
			if (Game.getInstance().getStatus() == 0) {
				System.out.println("Not possible! Game status has to be changed first");
				return;
			}			
			Game.getInstance().quit();
			printStatus();
			return;
		}
		
		//status
		if (cmds[0].equals(commands[5].getCommand())) {
			printStatus();
			return;
		}
		
		//info
		if (cmds[0].equals(commands[6].getCommand())) {
			boolean details = false;
			if (Game.getInstance().getStatus() == 0) {
				System.out.println("Not possible! Game status has to be changed first");
				return;
			}
			if (cmds.length == 2 && cmds[1].equals(commands[5].getParameter())) {
				details = true;
			}
			printStatus();
			for (Person person : Game.getInstance().getPersons()) {
				System.out.println(person.getName() + ":\n"
						+ "\tSeed: " + person.getSeed() + "\n"
						+ "\tis " + (person.isAlive() ? 
						"alive"
							+ (details ? "\n\tTarget: " + person.getTarget() : "")
						: "dead\n"
							+ "\tMurdered by " + person.getMurderedByPerson() + " with " + person.getMurderedByObject() + "\n"
							+ "\tTime of death: " + person.TimeOfDeathToString()));
			
			}
			return;
		}
		
		//exit
		if (cmds[0].equals(commands[7].getCommand())) {
			if (Game.getInstance().getStatus() != 0) {
				System.out.println("Not possible! Quit the game first");
				return;
			}
			exit = true;
			return;
		}
		
		//help
		if (cmds[0].equals(commands[8].getCommand())) {
			for (Command command : commands) {
				System.out.println(command.getCommand() + "\t\t" + (command.getCommand().length() < 8 ? "\t" : "") + command.getInformation());
			}
			return;
		}
	}
	
	public void printStatus () {
		String status;
		switch(Game.getInstance().getStatus()) {
		case 0:
			status = "off";
			break;
		case 1:
			status = "waiting for players";
			break;
		case 2:
			status = "running";
			break;
		case 3:
			status = "paused";
			break;
		default:
			System.err.println("Invalid status of game");
			status = "invalid";
			break;
		}
		System.out.println("Gamestatus: " + status);
	}
	
	public void read () {
		Scanner s = new Scanner(System.in);
		while (!exit) {
			String msg = s.nextLine();
			runCmd(msg);
		}
		s.close();
	}
}
