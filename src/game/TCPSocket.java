package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPSocket {
	
	private TCPSocket () {
	}
	
	public static void openSocket (int port) throws IOException {
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
	
	public static void receive (Socket client) {
		try {
			client.setSoTimeout(5000);
		} catch (SocketException e) {
			
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = "";
		
		while (true) {
			String tmp = null;
			try {
				tmp = in.readLine();
			} catch (IOException e) {
				break;
			}
			msg += tmp + "\n";
		}
		System.out.println(msg);
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
//	public static boolean send (Socket client, byte[] packet) {
//		BufferedOutputStream out;
//		try {
//			out = new BufferedOutputStream(client.getOutputStream(), packet.length);
//			out.write(packet, 0, packet.length);
//			return true;
//		} catch (IOException e) {
//			MyLogger.logError(e.getStackTrace().toString());
//			return false;
//		}
//		
//	}
}
