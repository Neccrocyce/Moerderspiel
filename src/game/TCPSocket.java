package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPSocket extends Thread {
	private static TCPSocket instance = null;
	private int port;
	
	private TCPSocket () {
	}
	
	public void openSocket (int port) throws IOException {
		ServerSocket server = new ServerSocket(port);
		while (true) {
			Socket client = null;
			
			try {
				client = server.accept();
				receive(client);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (client != null) {
					client.close();
				}
			}
		}
	}
	
	public void receive (Socket client) {
		BufferedReader in = null;
		String msg = "";
		
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			client.setSoTimeout(5000);
			
			while (true) {
				String tmp = null;
				tmp = in.readLine();
				
				if (tmp == null) {
					break;
				}
				msg += tmp + "\n";
			}
			
		} 
		catch (SocketTimeoutException se) {
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Game.getInstance().receivePacket(msg, client);
		}			
	}
	
	public boolean send (Socket client, String msg) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			out.write(msg);
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			return false;
		}
		
	}
	
	public void run () {
		try {
			openSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static TCPSocket getInstance () {
		if (instance == null) {
			instance = new TCPSocket();
		}
		return instance;
	}
	
	public void setPort (int port) {
		this.port = port;
	}
	
	
}
