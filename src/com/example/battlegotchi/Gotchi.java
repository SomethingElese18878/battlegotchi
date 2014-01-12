package com.example.battlegotchi;

public class Gotchi {

	int health;
	int strength;
	int stage;
	boolean madePoo;
	boolean isAngry;

	public Gotchi() {
		health = 100;
		strength = 1;
		madePoo = false;
		isAngry = false;
	}

	public int getHealth() {
		return health;
	}

	public int getStrength() {
		return strength;
	}

	public boolean getMadePoo() {
		return madePoo;
	}

	public boolean getIsAngry() {
		return isAngry;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public void setMadePoo(boolean madePoo) {
		this.madePoo = madePoo;
	}

	public void setIsAngry(boolean isAngry) {
		this.isAngry = isAngry;
	}

	public void takeDamage() {
		health = health - 10;
	}

	public int getStage() {
		return this.stage;
	}
	
	public void setStage(int stage) {
		this.stage = stage;
	}

}
