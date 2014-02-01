package com.example.battlegotchi;

import android.content.SharedPreferences;

public class Gotchi {

	int hunger;
	int strength;
	int stage;
	int age;
	int weight;
	int energy;
	int foodCounter = 0;
	boolean madePoo;
	boolean isAngry;

	public Gotchi() {
		hunger = 100;
		strength = 1;
		stage = 1;
		weight = 1;
		madePoo = false;
		isAngry = false;
	}

	public int getHunger() {
		return hunger;
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

	public void setHunger(int hunger) {
		this.hunger = hunger;
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
		this.energy = energy - 10;
	}

	public int getStage() {
		return this.stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public long getAge(SharedPreferences settings) {
		return (System.currentTimeMillis() - settings.getLong(
				"firstRunTimestamp", 0))/1000/60/60;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getMaxWeight() {
		return stage * stage;
	}
}
