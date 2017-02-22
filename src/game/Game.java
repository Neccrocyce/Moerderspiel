package game;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
	private TCPSocket tcpSocket;
	
	/**
	 * status of game:
	 * 0: off, 1: newgame initiated, 2: running, 3: paused
	 */
	private int status;
	private List<Person> persons;
	
	private Game () {
		status = 0;
	}
	
	public static Game getInstance () {
		if (instance == null) {
			instance = new Game ();
		}
		return instance;
	}
	
	public void newGame () {
		persons = new ArrayList<>();
		tcpSocket = new TCPSocket();
		status = 1;
		tcpSocket.start();
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
	
	public void pause() {
		save();
		status = 3;
	}
	
	public void cont () {
		if (status == 0) {
			load();
		}
		status = 2;
	}
	
	public void quit () {
		tcpSocket.closeServer();
		status = 0;
	}
	
	public Person joinPlayer (String name, int seed) {
		Person p = new Person(name, seed);
		persons.add(p);
		System.out.println(name + " joined");
		return p;
	}
	
	public void receivePacket (String msg, Socket client) {
		String[] login, cmds;
		int seed;
		try {
			login = msg.split("\t")[0].split(" ");
			cmds = msg.split("\t")[1].split(" ");
			seed = Integer.parseInt(login[1]);
		} 
		catch (ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
			tcpSocket.send(client, "error invalid message format");
			System.err.println("Received: Invalid Message format");
			try {
				client.close();
			} catch (IOException e2) {
				
			}
			return;
		}
		 
		switch (cmds[0]) {
		case "join":
			if (status == 1) {
				if (!existPerson(login[0])) {
					Person p = joinPlayer(login[0], seed);
					tcpSocket.send(client, "OK");
					
					//wait until the game started
					while (status == 1) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {}
					}
					if (status == 2) {
						tcpSocket.send(client, p.getTarget().getName());
					}
					else {
						sendFailStatus(client);
					}
					tcpSocket.closeConnection(client);
				}
				//if game started already
				else {
					sendFailStatus(client);
				 }
			}
			//if the name exists already
			else {
				tcpSocket.send(client, "FAIL name");
			}
			break;
		}
		
	}
	
	public boolean save () {
		try {
			if (!new File(Moerderspiel.getFolder()).exists()) {
				new File(Moerderspiel.getFolder()).mkdirs();
			}
			Path src = Paths.get(Moerderspiel.getFolder() + "autosave.save");
			Path dst = Paths.get(Moerderspiel.getFolder() + "autosave.save.1");
			if (new File(Moerderspiel.getFolder() + "autosave.save").exists()) {
				Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
				Files.delete(src);
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(Moerderspiel.getFolder() + "autosave.save"));
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
			in = new ObjectInputStream(new FileInputStream(Moerderspiel.getFolder() + "autosave.save"));
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
	
	public void sendFailStatus (Socket socket) {
		tcpSocket.send(socket, "FAIL gamestatus" + status);
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
