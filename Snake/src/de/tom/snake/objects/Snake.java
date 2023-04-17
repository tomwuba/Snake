package de.tom.snake.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import de.tom.snake.DrawingPanel;
import de.tom.snake.Main;
import de.tom.snake.api.SnakeDirection;

public class Snake {
	
	
	int x;
	int y;
	int length;
	int direction = SnakeDirection.NORTH;
	int nextdirection = SnakeDirection.NORTH;
	Main main;
	
	ArrayList<Segment> segments = new ArrayList<>();
	boolean isDead = false;
	
	
	public Snake(Main main, int x, int y, int length) {
		this.x = x;
		this.y = y;
		this.length = length;
		this.main = main;
	}
	
	public void draw(Graphics graphics) {
		direction = nextdirection;
		// Remove segments if they are not part of the snake anymore
		
		// This removes the Health of each segment, in fact it adds 1 to every segment, till the segment number is the lenght of the snake.
		// Then .isRemovable() is true and that happends to be the tail of the snake.
		for(Segment segment : segments) {
			segment.draw(graphics);
			
		}
		
		// This simply draws the snake head
		// You can change the Color if you want to visualize the head of the snake different than the segments.
		graphics.setColor(Color.WHITE);
		graphics.fillRect(x*main.gridSize, y*main.gridSize, main.gridSize, main.gridSize);
	}
	
	public void move() {
		segments.add(new Segment(this, main, x, y));
		switch (direction) {
		case SnakeDirection.NORTH:
			y--;
			break;
		case SnakeDirection.SOUTH:
			y++;
			break;
		case SnakeDirection.EAST:
			x++;
			break;
		case SnakeDirection.WEST:
			x--;
			break;
		}
		Iterator<Segment> it = segments.iterator();
		while(it.hasNext()) {
			Segment segment = it.next();
			
			if(segment.isRemovable()) {
				if(!main.isPauseMenu) {
					it.remove();
				}
				
			}
			// Checks if the snake ran into a segment (= Dead)
			if(segment.getX() == x && segment.getY() == y) {
				isDead = true;
			}
		}
		for(Segment segment : segments) {
			if(!main.isPauseMenu) {
				segment.removeHealth();
			}
			
		}
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getLength() {
		return length;
	}
	
	public void grow() {
		length++;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public void setDirection(int newDirection) {
		if(main.isPauseMenu) return;
		// The snake can't go in the opposite direction, at least, it shouldn't. Those heaps of code simply checks that.
		switch(direction) {
		case SnakeDirection.NORTH:
			if(newDirection == SnakeDirection.SOUTH) {
				return;
			}
			break;
		case SnakeDirection.SOUTH:
			if(newDirection == SnakeDirection.NORTH) {
				return;
			}
			break;
		case SnakeDirection.WEST:
			if(newDirection == SnakeDirection.EAST) {
				return;
			}
			break;
		case SnakeDirection.EAST:
			if(newDirection == SnakeDirection.WEST) {
				return;
			}
			break;
		}
		nextdirection = newDirection;
	}
	
	
	
	

}
