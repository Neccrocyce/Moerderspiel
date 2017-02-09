package game;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shuffle.MergeShuffle;
import shuffle.SeedGenerator;

public class Game {
	private static Game instance = null;
	/**
	 * status of game:
	 * 0: off, 1: newgame initiated, 2: running, 3: paused, 4: quit 
	 */
	private int status;
	private List<Person> persons;
	
	private Game () {
		status = 0;
		persons = new ArrayList<>();
	}
	
	public static Game getInstance () {
		if (instance == null) {
			instance = new Game ();
		}
		return instance;
	}
	
	public void newGame () {
		status = 1;
		TCPSocket.getInstance().start();
	}
	
	
	public void start () {
		int[] seeds = new int[persons.size()];
		for (int i = 0; i < seeds.length; i++) {
			seeds[i] = persons.get(i).getSeed();
		}
		Person[] pArray = persons.toArray(new Person[persons.size()]);
		MergeShuffle.shuffle(pArray, new SeedGenerator(seeds).generateSeed());
		persons = Arrays.asList(pArray);
		
		//set target
		persons.get(persons.size() - 1).setTarget(persons.get(0));
		for (int i = 0; i < persons.size() - 1; i++) {
			persons.get(i).setTarget(persons.get(i + 1));
		}
		
		//set status
		status = 2;
	}
	
	public Person joinPlayer (String name, int seed) {
		Person p = new Person(name, seed);
		persons.add(p);
		System.out.println(name + "joined");
		return p;
	}
	
	public void receivePacket (String msg, Socket client) {
		String[] login, cmds;
		int seed;
		try {
			login = msg.split("\n")[0].split(" ");
			cmds = msg.split("\n")[1].split(" ");
			seed = Integer.parseInt(login[1]);
		} 
		catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
			TCPSocket.getInstance().send(client, "error invalid message format");
			System.err.println("Received: Invalid Message format");
			return;
		}
		 
		switch (cmds[0]) {
		case "join":
			if (status == 1) {
				if (!existPerson(login[0])) {
					Person p = joinPlayer(login[0], seed);
					TCPSocket.getInstance().send(client, "OK");
					
					//wait until the game started
					while (status == 1) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {}
					}
					TCPSocket.getInstance().send(client, p.getTarget().getName());
				}
				//if game started already
				else {
					TCPSocket.getInstance().send(client, "FAIL gamestatus");
				 }
			}
			//if the name exists already
			else {
				TCPSocket.getInstance().send(client, "FAIL name");
			}
			break;
		}
		
	}
	
	public boolean save () {
		try {
			if (!new File(Moerderspiel.getFolder()).exists()) {
				new File(Moerderspiel.getFolder()).mkdirs();
			}
			Path src = Paths.get(Moerderspiel.getFolder() + "autosave.saves");
			Path dst = Paths.get(Moerderspiel.getFolder() + "autosave.saves.1");
			if (new File(Moerderspiel.getFolder() + "autosave.saves").exists()) {
				Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
				Files.delete(src);
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(Moerderspiel.getFolder() + "autosave.saves"));
			for (Person person : persons) {
				out.writeObject(person);
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("Saved Game");
		return true;
	}
	
	public boolean load () {
		ObjectInputStream in = null;
		try {
			persons = new ArrayList<>();
			in = new ObjectInputStream(new FileInputStream(Moerderspiel.getFolder() + "autosave.saves"));
			while (true) {
				Person p = (Person) in.readObject();
				persons.add(p);
			}			
		} catch (EOFException ef) {
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			try {
				in.close();
			} catch (Exception e2) {
			}
		}
		System.out.println("Loaded Game");
		return true;
	}
	
	public int getStatus() {
		return status;
	}
	
	public List<Person> getPersons() {
		return persons;
	}
	
	private boolean existPerson (String name) {
		boolean existP = false;
		for (Person person : persons) {
			existP |= name.equals(person.getName());
		}
		return existP;
	}
}
