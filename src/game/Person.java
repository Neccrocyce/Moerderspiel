package game;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Person {
	private String name;
	private Person target;
	private boolean alive;
	private Date timeOfDeath;
	private int seed;
	private Person murderedByPerson;
	private String murderedByObject;
	
	public Person(String name) {
		this.name = name;
		alive = true;
	}
	
	public String getName() {
		return name;
	}
	
	public Person getTarget() {
		return target;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public Date getTimeOfDeath() {
		return timeOfDeath;
	}
	
	public String TimeOfDeathToString () {
		Format formatter = new SimpleDateFormat("dd.MM HH:mm");
		String time = formatter.format(timeOfDeath);
		return time;
	}
	
	public int getSeed() {
		return seed;
	}
	
	public Person getMurderedByPerson() {
		return murderedByPerson;
	}
	
	public String getMurderedByObject() {
		return murderedByObject;
	}	
	
	public void setTarget(Person target) {
		this.target = target;
	}
	
	public void setSeed(int seed) {
		this.seed = seed;
	}
	
	public void getMurdered (Person murderer, String obj) {
		alive = false;
		murderedByPerson = murderer;
		murderedByObject = obj;
		timeOfDeath = new Date();
	}
	
	

}
