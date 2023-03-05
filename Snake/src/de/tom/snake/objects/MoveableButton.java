package de.tom.snake.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.util.Random;

import de.tom.snake.Main;

public class MoveableButton {
	
	int x;
	
	int y;
	
	float value;
	
	String text;
	
	int width;
	
	Main main;
	
	public MoveableButton(Main main, String text, int middleX, int y, float value, int width) {
		this.x = middleX-width/2;
		this.y = y-40;
		this.value = value;
		this.text = text;
		this.width = width;
		this.main = main;
	}
	
	
	public void draw(Graphics g) {

		
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, width, 80);
		g.setColor(Color.BLACK);
		g.fillRect(x+4, y+4, width-8, 72);
		g.setColor(Color.gray);
		g.drawString(text + (int) value + "%", main.jframe.getWidth()/2-g.getFontMetrics().stringWidth(text + (int) value + "%")/2, y+70-g.getFontMetrics().getHeight()/2);
		// Draw the moveable rectangle
		if(isMouseAboveSlider()) g.setColor(Color.WHITE);
		g.fillRect((int)((main.jframe.getWidth()/2-width/2)+(width-20)*((float)value/100)), y+1, 20, 78);
		g.setColor(Color.BLACK);
		g.fillRect((int)((main.jframe.getWidth()/2-width/2)+(width-20)*((float)value/100))+5, y+6, 10, 68);
		
	}
	
	public float getValue() {
		return value;
	}
	
	public void setValue(float newValue) {
		if(newValue >= 0 && newValue <= 100) value = newValue;
		
		
		
	}
	
	public boolean isMouseAboveSlider() {
		int mouseX = MouseInfo.getPointerInfo().getLocation().x;
		int mouseY = MouseInfo.getPointerInfo().getLocation().y;
		
		int sliderX = (int) ((main.jframe.getWidth()/2-width/2)+(width-20)*((float)value/100));
		int sliderY = y;
		
		if(mouseX >= sliderX && mouseX < sliderX+20 && mouseY >= sliderY && mouseY < sliderY+80) return true;
		return false;
	}
	
	
	
	public void changeText(String newText) {
		text = newText;
	}

}
