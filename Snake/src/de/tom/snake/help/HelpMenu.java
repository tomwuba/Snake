package de.tom.snake.help;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.MouseInfo;

import de.tom.snake.Main;
import de.tom.snake.api.Language;
import de.tom.snake.api.LanguageString;
import de.tom.snake.objects.Button;

public class HelpMenu {
	
	
	boolean isOpen = false;
	
	Main main;
	
	public Button okayButton;
	
	
	public HelpMenu(Main main) {
		this.main = main;
		okayButton = new Button(main.jframe.getWidth()/2, main.jframe.getHeight()/5*3, LanguageString.general_back[Language.getLanguage(main)], 250, 60);
		
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 100));
		g.fillRect(0, 0, main.jframe.getWidth(), main.jframe.getHeight());
		g.setColor(Color.WHITE);
		g.fillRoundRect(main.jframe.getWidth()/4, main.jframe.getHeight()/5, main.jframe.getWidth()/2, main.jframe.getHeight()/2, 20, 20);
		g.setColor(Color.BLACK);
		g.fillRoundRect(main.jframe.getWidth()/4+5, main.jframe.getHeight()/5+5, main.jframe.getWidth()/2-10, main.jframe.getHeight()/2-10, 20, 20);
		
		g.setFont(g.getFont().deriveFont(60.0f));
		g.setColor(Color.WHITE);
		g.drawString("?", getXFromStringLenght("?", g), main.jframe.getHeight()/4+40);
		
		
		g.setFont(g.getFont().deriveFont(21.0f));
		String[] helpText = LanguageString.help_text[Language.getLanguage(main)].split("\n");
		for(int i = 0; i < helpText.length; i++) {
			g.drawString(helpText[i], getXFromStringLenght(helpText[i], g), main.jframe.getHeight()/4+50*(i+2));
			
			
		}
		g.setFont(main.font);
		okayButton.changeText(LanguageString.general_back[Language.getLanguage(main)]);
		okayButton.draw(g);
		
		
		
		
	}
	
	public void open() {
		isOpen = true;
	}
	
	public void close() {
		isOpen = false;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	
	public int getXFromStringLenght(String string, Graphics graphics) {
		FontMetrics fontMetrics = graphics.getFontMetrics(graphics.getFont());
		
		return main.jframe.getWidth()/2-(fontMetrics.stringWidth(string)/2);
	}

}
