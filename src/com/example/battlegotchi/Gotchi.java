package com.example.battlegotchi;

public class Gotchi {
	
	int health;
		
	public Gotchi(){
		health = 100;
	}
	
	public int getHealth(){
		return health;
	}
	
	public void takeDamage(){
		health = health - 10;
	}
		
		

}
