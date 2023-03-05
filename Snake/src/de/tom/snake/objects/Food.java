package de.tom.snake.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import de.tom.snake.Main;

public class Food {
	
	int x;
	int y;
	Main main;
	boolean isEaten = false;
	
	
	public Food(Main main, int x, int y) {
		this.x = x;
		this.y = y;
		this.main = main;
	}
	
	public void draw(Graphics graphics) {
		graphics.setColor(Color.RED);
		graphics.fillRect(x*main.gridSize, y*main.gridSize, main.gridSize, main.gridSize);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	
	public void eat() {
		// Simply sets the food location to a random, new point
		
		if(main.numbBombs > 0) {
			ArrayList<Integer[]> placesWithoutBombs = new ArrayList<>();
			for(int x = 0; x < main.jframe.getWidth()/main.gridSize; x++) {
				for(int y = 0; y < main.jframe.getHeight()/main.gridSize; y++) {
					for(Segment segment : main.snake.segments) {
						if(segment.getX() != x || segment.getY() != y) {
							Integer[] cords = {x, y};
							placesWithoutBombs.add(cords);
						}
					}
					for(Bomb bomb : main.bombs) {
						if(bomb.getX() != x || bomb.getY() != y) {
							Integer[] cords = {x, y};
							placesWithoutBombs.add(cords);
						}
					}
				}
			}
			
			
			x = placesWithoutBombs.get(new Random().nextInt(placesWithoutBombs.size()))[0];
			y = placesWithoutBombs.get(new Random().nextInt(placesWithoutBombs.size()))[1];
		} else {
			x = new Random().nextInt(main.jframe.getWidth()/main.gridSize);
			y = new Random().nextInt(main.jframe.getHeight()/main.gridSize);
			
		}
		
	}

}
