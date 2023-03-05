package de.tom.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import de.tom.snake.api.Language;
import de.tom.snake.api.LanguageString;
import de.tom.snake.api.SoundAPI;
import de.tom.snake.objects.Bomb;
import de.tom.snake.objects.Button;
import de.tom.snake.objects.Food;
import de.tom.snake.objects.Snake;


public class DrawingPanel extends JPanel{
	
	private Main main;
	
	public DrawingPanel(Main main) {
		this.main = main;
	}
	
	long timeInFuture = 0L;
	public static long gameSpeed = 200L;
	

	
	
	
	// This is the method for the drawing on the canvas, however, it's not a canvas.
	@Override
	protected void paintComponent(Graphics g) {
		//super.paintComponent(g);
		g.setFont(main.font);
		// checks if settings are open.
		if(main.settings.isOpen()) {
			main.settings.drawSettings(g);
			return;
		}
		if(main.leaderBoard.isOpen()) {
			main.leaderBoard.draw(g);
			return;
		}
		if(!main.isGame) {
			if(main.leaderboardInsert.isOpen()) {
				main.leaderboardInsert.draw(g);
				return;
			}
			// Draws the waiting menu
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, main.jframe.getWidth(), main.jframe.getHeight());
			
			// This is for the text "Snake" in Atari font.
			g.setColor(Color.white);
			Font newFont = main.font.deriveFont(100.0f);
			drawCenteredString(g, "Snake", new Rectangle(0, 0, main.jframe.getWidth(), main.jframe.getHeight()/2-main.jframe.getHeight()/3), newFont);
			g.setFont(main.font);
			if(!main.status.equalsIgnoreCase("")) {
				g.setColor(Color.RED);
				
				drawCenteredString(g, main.status, new Rectangle(0, 0, main.jframe.getWidth(), 350), g.getFont());
				g.setColor(Color.WHITE);
				drawCenteredString(g, LanguageString.menu_text_score[Language.getLanguage(main)] + main.oldScore, new Rectangle(0, 0, main.jframe.getWidth(), 450), g.getFont());
			}
			g.setColor(Color.white);
			g.drawString(LanguageString.menu_text_author[Language.getLanguage(main)], 30, main.jframe.getHeight()-10);
			g.setColor(Color.gray);
			main.startButton.changeText(LanguageString.menu_button_play_name[Language.getLanguage(main)]);
			main.startButton.draw(g);
			main.settingsButton.changeText(LanguageString.menu_button_settings_name[Language.getLanguage(main)]);
			main.settingsButton.draw(g);
			main.leaderBoardButton.changeText(LanguageString.menu_button_leaderboard_name[Language.getLanguage(main)]);
			main.leaderBoardButton.draw(g);
			main.exitButton.changeText(LanguageString.menu_button_exit_name[Language.getLanguage(main)]);
			main.exitButton.draw(g);
			g.setColor(Color.WHITE);
			g.drawString(main.version, main.jframe.getWidth()-300, main.jframe.getHeight()-20);
			return;
		}
		
		// Just the background, not very interesting ig.
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, main.jframe.getWidth(), main.jframe.getHeight());
		
		
		
		
		// PAUSED
		if(main.isPauseMenu) {
			
			main.snake.draw(g);
			for(Food food : main.foods) {
				food.draw(g);
			}
			
			g.setColor(Color.gray);
			
			drawCenteredString(g, "" + main.score, new Rectangle(0, 0, main.jframe.getWidth(), 50), g.getFont());
			for(Bomb bomb : main.bombs) {
				if(main.snake.getX() == bomb.getX() && main.snake.getY() == bomb.getY()) {
					Random random = new Random();
					String reason = LanguageString.dead_text_bomb[Language.getLanguage(main)];
					if(random.nextInt(2)==0) {
						if(random.nextInt(1)==0) {
							reason = LanguageString.dead_text_bomb_alternate_1[Language.getLanguage(main)];
						} else {
							reason = LanguageString.dead_text_bomb_alternate_2[Language.getLanguage(main)];
						}
					} 
					die(reason);
					return;
				}
				bomb.draw(g);
			}
			
			main.pauseMenu.drawMenu(g);
			
			
			
			
			return;
		
		}
		
		
		
		// IF SNAKE RAN INTO ITSELF
		if(main.snake.isDead()) {
			// This method triggers only when there's a dead case IN the Snake class.
			die(LanguageString.dead_text_bomb[Language.getLanguage(main)]);
			return;
		}
		// The snake moves foward, and then gets drawn.
		

		
		main.snake.draw(g);
		
		if(timeInFuture <= System.currentTimeMillis()) {
			timeInFuture = System.currentTimeMillis() + gameSpeed;
			main.snake.move();

		}
		
		// DRAWS THE OTHER OBJECTS
		// Draws the food on its newest location.
		for(Food food : main.foods) {
			if(main.snake.getX() == food.getX() && main.snake.getY() == food.getY()) {
				food.eat();
				if(main.settings.getSoundVolume() > 0) {
					new SoundAPI().playSound(main.fruitEatSoundFile, (int) main.settings.getSoundVolume());
				}
				main.snake.grow();
				main.score++;
			} 
			food.draw(g);
		}
		
		for(Bomb bomb : main.bombs) {
			if(main.snake.getX() == bomb.getX() && main.snake.getY() == bomb.getY()) {
				Random random = new Random();
				String reason = LanguageString.dead_text_bomb[Language.getLanguage(main)];
				if(random.nextInt(2)==0) {
					if(random.nextInt(1)==0) {
						reason = LanguageString.dead_text_bomb_alternate_1[Language.getLanguage(main)];
					} else {
						reason = LanguageString.dead_text_bomb_alternate_2[Language.getLanguage(main)];
					}
				} 
				die(reason);
				return;
			}
			bomb.draw(g);
		}
		//main.food.draw(g);
		// Player dies when touching one of the borders:
		
		// CHECKS IF SNAKE TOUCHES ONE OF THE BORDERS
		if(main.snake.getX() >= main.jframe.getWidth()/main.gridSize || main.snake.getX() < 0) {
			die(LanguageString.dead_text[Language.getLanguage(main)]);
			return;
		}
		if(main.snake.getY() >= main.jframe.getHeight()/main.gridSize || main.snake.getY() < 0) {
			die(LanguageString.dead_text[Language.getLanguage(main)]);
			return;
		}
		// The score:
		g.setColor(Color.gray);
		g.setFont(main.font);
		
		// DRAWS THE SCORE
		drawCenteredString(g, "" + main.score, new Rectangle(0, 0, main.jframe.getWidth(), 50), g.getFont());
	}
	
	public void die(String reason) {
		if(main.settings.getMusicVolume() > 0) main.musicClip.stop();
		main.snake = new Snake(main, main.jframe.getWidth()/main.gridSize/2, main.jframe.getHeight()/main.gridSize/2, 0);
		main.oldScore = main.score;
		
		if(main.settings.getSoundVolume() > 0) {
			new SoundAPI().playSound(main.deadSoundFile, (int) main.settings.getSoundVolume());
		}
		// Activate the cursor
		main.jframe.getContentPane().setCursor(main.normalCursor);
		main.status = reason;
		main.foods.clear();
		main.bombs.clear();
		main.isGame = false;
		if(main.leaderBoard.leaderboardAPI.isHighScore(main.score) && main.score > 0) {
			main.leaderboardInsert.open();
			if(main.settings.getSoundVolume() > 0) {
				new SoundAPI().playSound(main.highscoreSoundFile, (int) main.settings.getSoundVolume());
			}
		} else {
			main.score = 0;
		}
	}
	
	
	// This methode basically centers a String by his width and height inside of a rectangle.
	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    
	    g.setFont(font);
	    
	    g.drawString(text, x, y);
	}
	

}
