package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class TCPSocket extends Thread implements Runnable {
	private static int port;
	private ServerSocket server;
	
	public TCPSocket () {
	}
	
	public void openSocket () throws IOException {
		server = new ServerSocket(port);
		while (true) {
			try {
				Socket client = null;
				client = server.accept();
				receive(client);
				if (client != null) {
					client.close();					
				}
			} catch (SocketException e) {
				break;
			}
		}
	}
	
	public void receive (Socket client) {
		BufferedReader in = null;
		String msg = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//			client.setSoTimeout(5000);			
			msg = in.readLine();
			Game.getInstance().receivePacket(msg, client);
		} 
		catch (SocketTimeoutException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	public boolean send (Socket client, String msg) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			out.write(msg + "\n");
			out.flush();
			return true;
		} catch (IOException e) {
			return false;
		}		
	}
	
	public void closeConnection (Socket client) {
		try {
			client.close();
		} catch (IOException e) {
		}
	}
	
	public void run () {
		try {
			openSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setPort (int port) {
		TCPSocket.port = port;
	}
	
	public boolean closeServer () {
		try {
			server.close();
		}
		catch (NullPointerException e) {
			return true;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return server.isClosed();
	}
	
	
}
