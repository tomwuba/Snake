package de.tom.snake.listener;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.tom.snake.DrawingPanel;
import de.tom.snake.Main;
import de.tom.snake.api.Difficulty;
import de.tom.snake.api.SnakeDirection;
import de.tom.snake.api.SoundAPI;
import de.tom.snake.objects.Bomb;
import de.tom.snake.objects.Food;

public class SnakeDirectionEvent implements KeyListener{
		
	Main main;
	
	public SnakeDirectionEvent(Main main) {
		this.main = main;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(main.leaderboardInsert.isOpen()) {
			if(main.leaderboardInsert.currentText.toCharArray().length < 15 || e.getExtendedKeyCodeForChar(e.getKeyChar()) == 8) {
				if(e.getExtendedKeyCodeForChar(e.getKeyChar()) != 27) {
					if(e.getExtendedKeyCodeForChar(e.getKeyChar()) != 8) {
						
						main.leaderboardInsert.currentText = main.leaderboardInsert.currentText + e.getKeyChar();
					} else {
						if(main.leaderboardInsert.currentText.length() > 0) {
							main.leaderboardInsert.currentText = main.leaderboardInsert.currentText.substring(0, main.leaderboardInsert.currentText.toCharArray().length-1);

						}
					}
				}
				
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(main.leaderboardInsert.isOpen() && e.getExtendedKeyCode() != 27) {
			
			return;
		}
		if(main.isPauseMenu) {
			if(main.pauseMenu.helpMenu.isOpen()) {
				main.pauseMenu.helpMenu.close();
				return;
			}
		
		}
		if(!main.isGame) {
			if(main.leaderboardInsert.isOpen()) {
				if(e.getKeyCode() == 27) {
					if(main.leaderboardInsert.leaderboardInsertExit.isOpen()) {
						main.leaderboardInsert.leaderboardInsertExit.close();
						return;
					}
					main.leaderboardInsert.close(true);
					return;
				}
				
			}
			if(e.getKeyCode() != 32 && e.getKeyCode() != 80) {
				return;
			}
			if(main.settings.getMusicVolume() > 0) {
				main.musicClip.setFramePosition(0);
				SoundAPI.setVolume(main.musicClip, main.settings.getMusicVolume());
				main.musicClip.start();
				main.musicClip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			main.isGame = true;
			main.numbFoods = Difficulty.FRUITS_NUMB[main.settings.getDifficulty()];
			main.numbBombs = Difficulty.BOMB_NUMBS[main.settings.getDifficulty()];
			DrawingPanel.gameSpeed = Difficulty.SNAKE_SPEEDS[main.settings.getDifficulty()];
			
			
			for(int i = 0; i < main.numbBombs; i++) {
				main.bombs.add(new Bomb(new Random().nextInt(main.jframe.getWidth()/main.gridSize), new Random().nextInt(main.jframe.getHeight()/main.gridSize), main));
				
			
			}
			if(main.numbBombs > 0) {
				ArrayList<Integer[]> placesWithoutBombs = new ArrayList<>();
				for(int x = 0; x < main.jframe.getWidth()/main.gridSize; x++) {
					for(int y = 0; y < main.jframe.getHeight()/main.gridSize; y++) {
						for(Bomb bomb : main.bombs) {
							if(bomb.getX() != x || bomb.getY() != y) {
								Integer[] cords = {x, y};
								placesWithoutBombs.add(cords);
							}
						}
					}
				}
				
				for(int i = 0; i < main.numbFoods; i++) {
					main.foods.add(new Food(main, placesWithoutBombs.get(new Random().nextInt(placesWithoutBombs.size()))[0], placesWithoutBombs.get(new Random().nextInt(placesWithoutBombs.size()))[1]));
					
				}
			} else {
				for(int i = 0; i < main.numbFoods; i++) {
					main.foods.add(new Food(main, new Random().nextInt(main.jframe.getWidth()/main.gridSize), new Random().nextInt(main.jframe.getHeight()/main.gridSize)));
					
				}
			}
			
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
			main.jframe.getContentPane().setCursor(blankCursor);
			return;
		}
					
		
		

		
		 
		switch(e.getKeyCode()) {
		case 69:
			System.exit(0);
		case 38:
			main.snake.setDirection(SnakeDirection.NORTH);
			break;
		case 39:
			main.snake.setDirection(SnakeDirection.EAST);
			break;
		case 40:
			main.snake.setDirection(SnakeDirection.SOUTH);
			break;
		case 37:
			main.snake.setDirection(SnakeDirection.WEST);
			break;
		case 80:
			if(main.isPauseMenu) {
				if(main.settings.getMusicVolume() > 0) {
					SoundAPI.setVolume(main.musicClip, main.settings.getMusicVolume());
					main.musicClip.start();
					main.musicClip.loop(Clip.LOOP_CONTINUOUSLY);
				}
				main.isPauseMenu = false;
				BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
				main.jframe.getContentPane().setCursor(blankCursor);

			} else {
				if(main.settings.getMusicVolume() > 0) main.musicClip.stop(); 
				
				main.jframe.getContentPane().setCursor(main.normalCursor);
				main.isPauseMenu = true;
			}
			break;
		case 27:
			
			if(main.isPauseMenu) {
				if(main.settings.getMusicVolume() > 0) {
					SoundAPI.setVolume(main.musicClip, main.settings.getMusicVolume());
					main.musicClip.start();
					main.musicClip.loop(Clip.LOOP_CONTINUOUSLY);
				}
				BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
				main.jframe.getContentPane().setCursor(blankCursor);
				main.isPauseMenu = false;
			} else {
				if(main.settings.getMusicVolume() > 0) main.musicClip.stop();
				main.jframe.getContentPane().setCursor(main.normalCursor);
				main.isPauseMenu = true;
			}
			break;
		}				
	}
	@Override
	public void keyReleased(KeyEvent e) {	
		
	}
}
