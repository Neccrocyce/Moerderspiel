package tests;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.junit.Test;

import game.TCPSocket;

public class TcpSocketClientTest{

	@Test
	public void test() {
		Socket socket = null;
		try {
			socket = new Socket("localhost", 16333);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			out.write("test1");
			out.write("test2\n");
			out.write("test3\n");
			out.flush();
			socket.close();
//			while (socket.isConnected()) {}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
