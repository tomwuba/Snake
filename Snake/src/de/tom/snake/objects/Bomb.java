package de.tom.snake.objects;

import java.awt.Color;
import java.awt.Graphics;

import de.tom.snake.Main;

public class Bomb {
	
	
	
	int x;
	int y;
	Main main;
	
	
	public Bomb(int x, int y, Main main) {
		this.x = x;
		this.y = y;
		this.main = main;
	}
	
	
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(x*main.gridSize, y*main.gridSize, main.gridSize, main.gridSize);
	}
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
