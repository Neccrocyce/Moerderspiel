package tests;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameInteractiveTest {

	@Test
	public void test1 () {
		int number = (int) (Math.random() * 10000);
		TestPlayer pl = new TestPlayer("player" + number, 100);
		pl.joinGame();
	}
	
//	@Test
	public void testjoinPlayers () {
		TestPlayer[] pl = new TestPlayer[5];
		for (int i = 0; i < pl.length; i++) {
			pl[i] = new TestPlayer("player" + i, 100 * i);
			pl[i].joinGame();
//			assertEquals(1, pl[i].getStatus());
		}
	}
}
