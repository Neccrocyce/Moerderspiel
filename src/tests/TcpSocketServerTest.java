package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import game.TCPSocket;

public class TcpSocketServerTest {

	@Test
	public void test() {
		try {
			TCPSocket socket = new TCPSocket();
			TCPSocket.setPort(16333);
			socket.openSocket();
			socket.closeServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
