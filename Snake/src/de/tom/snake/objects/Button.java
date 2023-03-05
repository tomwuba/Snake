package de.tom.snake.objects;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.PointerInfo;

public class Button {
	
	int middleX;
	int middleY;
	int width;
	int height;
	String text;
	
	Color color;
	
	
	
	public Button(int middleX, int middleY, String text, int width, int height) {
		this.middleX = middleX;
		this.middleY = middleY;
		this.text = text;
		this.width = width;
		this.height = height;
	}
	
	public Button(int middleX, int middleY, String text, int width, int height, Color color) {
		this.middleX = middleX;
		this.middleY = middleY;
		this.text = text;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public void draw(Graphics g) {
		Color color = Color.gray;
		if(isMouseAbove(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y)) {
			color = Color.WHITE;
		} 
		if(this.color != null) {
			color = this.color;
			if(isMouseAbove(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y)) {
				
				color = color.brighter().brighter();
			}
			
		} 		
		FontMetrics fontMetrics = g.getFontMetrics();		
		g.setColor(color);
		g.fillRect(middleX-width/2, middleY-height/2, width, height);		
		g.setColor(Color.BLACK);
		g.fillRect((middleX-width/2)+5, (middleY-height/2)+5, width-10, height-10);
		g.setColor(color);
		g.drawString(text, middleX-fontMetrics.stringWidth(text)/2, middleY-(fontMetrics.getHeight()/2)+fontMetrics.getAscent());		
	}
	
	
	// The boolean decides, if the button changes when the mouse is above. This method is additional.
	public void draw(Graphics g, boolean mouseHoveringChange) {
		Color color = Color.gray;
		if(isMouseAbove(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y) && mouseHoveringChange) {
			color = Color.WHITE;
		} 
		if(this.color != null) {
			color = this.color;
			if(isMouseAbove(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y) && mouseHoveringChange) {
				
				color = color.brighter().brighter();
			}
			
		} 		
		FontMetrics fontMetrics = g.getFontMetrics();		
		g.setColor(color);
		g.fillRect(middleX-width/2, middleY-height/2, width, height);		
		g.setColor(Color.BLACK);
		g.fillRect((middleX-width/2)+5, (middleY-height/2)+5, width-10, height-10);
		g.setColor(color);
		g.drawString(text, middleX-fontMetrics.stringWidth(text)/2, middleY-(fontMetrics.getHeight()/2)+fontMetrics.getAscent());		
	}
	
	
	// Methode that checks if the cursor is above the button
	public boolean isMouseAbove(int mouseX, int mouseY) {
	
		if(mouseX <= (middleX+width/2)+5 && mouseX >= (middleX-width/2)-5 && mouseY <= (middleY+height/2)+5 && mouseY >= (middleY-height/2)-5) {
			return true;
		}

			
		return false;
	}
	
	public void changeText(String newText) {
		text = newText;
	}
	
}
