package de.tom.snake.leaderboard;

import java.awt.Color;
import java.awt.Graphics;

import de.tom.snake.Main;
import de.tom.snake.api.Language;
import de.tom.snake.api.LanguageString;
import de.tom.snake.objects.Button;

public class LeaderboardClear {
	private Main main;
	
	public Button yesButton;
	
	public Button noButton;
	
	boolean isOpen = false;
	
	
	
	public LeaderboardClear(Main main) {
		this.main = main;
		yesButton = new Button(main.jframe.getWidth()/20*13, main.jframe.getHeight()/5*3, LanguageString.leaderboard_button_yes[Language.getLanguage(main)], 350, 80);
		noButton = new Button(main.jframe.getWidth()/20*7, main.jframe.getHeight()/5*3, LanguageString.leaderboard_button_no[Language.getLanguage(main)], 350, 80);
		
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 100));
		g.fillRect(0, 0, main.jframe.getWidth(), main.jframe.getHeight());
		
		g.setColor(Color.WHITE);
		g.fillRoundRect(main.jframe.getWidth()/4, main.jframe.getHeight()/5, main.jframe.getWidth()/2, main.jframe.getHeight()/2, 20, 20);
		g.setColor(Color.BLACK);
		g.fillRoundRect(main.jframe.getWidth()/4+5, main.jframe.getHeight()/5+5, main.jframe.getWidth()/2-10, main.jframe.getHeight()/2-10, 20, 20);
		g.setColor(Color.WHITE);
		g.drawString(LanguageString.leaderboard_clear_text[Language.getLanguage(main)], main.pauseMenu.helpMenu.getXFromStringLenght(LanguageString.leaderboard_insert_exit_text[Language.getLanguage(main)], g), main.jframe.getHeight()/3);
		noButton.draw(g);
		yesButton.draw(g);
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
}
