package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import game.TCPSocket;

public class TcpSocketServerTest {

	@Test
	public void test() {
		try {
			TCPSocket.openSocket(16333);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
