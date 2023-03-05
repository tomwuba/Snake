package de.tom.snake.pause;

import java.awt.Color;
import java.awt.Graphics;

import de.tom.snake.Main;
import de.tom.snake.api.Language;
import de.tom.snake.api.LanguageString;
import de.tom.snake.help.HelpMenu;
import de.tom.snake.objects.Button;

public class PauseMenu {
	

	Main main;
	
	public Button backToGameButton;
	public Button helpButton;
	public Button leaderboardButton;
	public Button endGameButton;
	public HelpMenu helpMenu;

	
	
	public PauseMenu(Main main) {
		this.main = main;
		backToGameButton = new Button(main.jframe.getWidth()/2, main.jframe.getHeight()/6, LanguageString.pause_button_back_to_game[Language.getLanguage(main)], 700, 80);
		helpButton = new Button(main.jframe.getWidth()/2, main.jframe.getHeight()/6*3, LanguageString.pause_button_help[Language.getLanguage(main)], 700, 80);
		leaderboardButton = new Button(main.jframe.getWidth()/2, main.jframe.getHeight()/6*4, LanguageString.menu_button_leaderboard_name[Language.getLanguage(main)], 700, 80);
		endGameButton = new Button(main.jframe.getWidth()/2, main.jframe.getHeight()/6*5, LanguageString.pause_button_end_the_game[Language.getLanguage(main)], 700, 80, new Color(150, 0, 0));
		helpMenu = new HelpMenu(main);
		
	}
	
	public void drawMenu(Graphics graphics) {
		graphics.setColor(new Color(0, 0, 0, 150));
		graphics.fillRect(0, 0, main.jframe.getWidth(), main.jframe.getHeight());
		backToGameButton.draw(graphics, !helpMenu.isOpen());
		backToGameButton.changeText(LanguageString.pause_button_back_to_game[Language.getLanguage(main)]);
		helpButton.draw(graphics, !helpMenu.isOpen());
		helpButton.changeText(LanguageString.pause_button_help[Language.getLanguage(main)]);
		leaderboardButton.draw(graphics, !helpMenu.isOpen());
		leaderboardButton.changeText(LanguageString.menu_button_leaderboard_name[Language.getLanguage(main)]);
		endGameButton.draw(graphics, !helpMenu.isOpen());
		endGameButton.changeText(LanguageString.pause_button_end_the_game[Language.getLanguage(main)]);
		if(helpMenu.isOpen()) {
			helpMenu.draw(graphics);
		} 
	}
	
	
	
	
	

}
