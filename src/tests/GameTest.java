package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import game.Game;
import game.Person;

public class GameTest {

	@Test
	public void testSaveAndLoad() {
		Person[] p = new Person[] {
				new Person("player1", 0),
				new Person("player2", 100),
				new Person("player3", 500)
		};
		Game.getInstance().joinPlayer("player1", 0);
		Game.getInstance().joinPlayer("player2", 100);
		Game.getInstance().joinPlayer("player3", 500);
		assertEquals(true, Game.getInstance().save());
		assertEquals(true, Game.getInstance().load());
		for (int i = 0; i < p.length; i++) {
			equalsPerson(p[i], Game.getInstance().getPersons().get(i));
		}
		
	}
	
	public void equalsPerson (Person exp, Person act) {
		assertEquals(exp.getName(), act.getName());
		assertEquals(exp.getSeed(), act.getSeed());
		assertEquals(exp.getTarget(), act.getTarget());
		assertEquals(exp.isAlive(), act.isAlive());
		assertEquals(exp.getTimeOfDeath(), act.getTimeOfDeath());
		assertEquals(exp.getMurderedByPerson(), act.getMurderedByPerson());
		assertEquals(exp.getMurderedByObject(), act.getMurderedByObject());
	}

}
