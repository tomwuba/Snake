package de.tom.snake.objects;

import java.awt.Color;
import java.awt.Graphics;

import de.tom.snake.Main;

public class Segment {
	
	int x;
	int y;
	int health;
	Main main;
	Snake snake;
	
	public Segment(Snake snake, Main main, int x, int y) {
		this.x = x;
		this.y = y;
		health = 0;
		this.main = main;
		this.snake = snake;
		
	}
	
	public void draw(Graphics graphics) {
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(x*main.gridSize, y*main.gridSize, main.gridSize, main.gridSize);
		
	}
	
	public void removeHealth() {
		health++;
	}
	
	boolean isRemovable() {
		// Explained in "Snake.java"
		if(health >= snake.getLength()) {
			return true;
		}
		return false;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
