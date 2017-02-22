package tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import game.Game;
import game.Person;

public class TestPlayer extends Thread {
	private Person person;
	private Socket client;
	/**
	 * status of player:
	 * 0: created, 1: joined, 11: join failed, 2: in game, 3: kicked  
	 */
	private int status;
	
	public TestPlayer (String name, int seed) {
		person = new Person(name, seed);
		status = 0;
	}
	
	public void joinGame () {
		try {
			client = new Socket("localhost", 16333);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		send(person.getName() + " "  + person.getSeed() + "\tjoin");
		String s =  receive();
		if (s.equals("OK")) {
			status = 1;
			s = receive();
			if (s.startsWith("FAIL")) {
				System.err.println("Client: " + s);
				status = 11;
			}
			else {
				person.setTarget(new Person(s));
			}
		}
		else {
			System.err.println("Client: " + s);
			status = 11;
		}
		try {
			client.close();
		} catch (IOException e) {
			
		}
	}
	
	public void run () {
		joinGame();
	}
	
	private void send (String msg) {
		try {			
			BufferedWriter out;
			out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			out.write(msg + "\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String receive () {
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			client.setSoTimeout(10 *60000);			
			return in.readLine();
		} 
		catch (SocketTimeoutException e) {
			e.printStackTrace();
			return "Timeout";
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public int getStatus() {
		return status;
	}
}
