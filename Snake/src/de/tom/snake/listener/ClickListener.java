package de.tom.snake.listener;

import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
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
import de.tom.snake.api.Language;
import de.tom.snake.api.LanguageString;
import de.tom.snake.api.SoundAPI;
import de.tom.snake.leaderboard.Leaderboard;
import de.tom.snake.objects.Bomb;
import de.tom.snake.objects.Food;
import de.tom.snake.objects.Snake;
import de.tom.snake.pause.PauseMenu;

public class ClickListener implements MouseListener{
	
	Main main;
	
	public static boolean isLeftMouseDraggedOverMusicButton = false;
	public static boolean isLeftMouseDraggedOverSoundButton = false;
	
	public static int mouseXWhenClicked = 0;
	
	
	public ClickListener(Main main) {
		this.main = main;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(main.settings.musicButton.isMouseAboveSlider()) {
			isLeftMouseDraggedOverMusicButton = true; 
			mouseXWhenClicked = MouseInfo.getPointerInfo().getLocation().x;
		}
		if(main.settings.soundEffectButton.isMouseAboveSlider()) {
			isLeftMouseDraggedOverSoundButton = true; 
			mouseXWhenClicked = MouseInfo.getPointerInfo().getLocation().x;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if(e.getButton()==MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) {
			return;
		}
		if(isLeftMouseDraggedOverMusicButton) isLeftMouseDraggedOverMusicButton = false;
		if(isLeftMouseDraggedOverSoundButton) isLeftMouseDraggedOverSoundButton = false;
		if(main.leaderboardInsert.isOpen()) {
			
			if(main.leaderboardInsert.leaderboardInsertExit.isOpen()) {
				
				if(main.leaderboardInsert.leaderboardInsertExit.yesButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
					main.leaderboardInsert.leaderboardInsertExit.close();
					main.leaderboardInsert.close(false);
					if(main.settings.getSoundVolume() > 0) {
						new SoundAPI().playSound(main.highscoreSaveSoundFile, (int) main.settings.getSoundVolume());
						new SoundAPI().playSound(main.buttonClickSoundFile, (int) main.settings.getSoundVolume());
					}
					
					
				} else if(main.leaderboardInsert.leaderboardInsertExit.noButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
					main.leaderboardInsert.leaderboardInsertExit.close();
					new SoundAPI().playSound(main.backSoundFile, (int) main.settings.getSoundVolume());
				}
				return;
			}
			if(main.leaderboardInsert.saveButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
				main.leaderboardInsert.close(true);
				if(main.settings.getSoundVolume() > 0) {
					new SoundAPI().playSound(main.highscoreSaveSoundFile, (int) main.settings.getSoundVolume());
				}
				return;
			}
			if(main.leaderboardInsert.skipButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
				if(main.settings.getSoundVolume() > 0) {
					new SoundAPI().playSound(main.buttonClickSoundFile, (int) main.settings.getSoundVolume());
				}
				main.leaderboardInsert.leaderboardInsertExit.open();
			}
			return;
		}
		if(main.leaderBoard.isOpen()) {
			if(main.leaderBoard.leaderboardClear.isOpen()) {
				if(main.leaderBoard.leaderboardClear.noButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
					main.leaderBoard.leaderboardClear.close();
					if(main.settings.getSoundVolume() > 0) {
						new SoundAPI().playSound(main.backSoundFile, (int) main.settings.getSoundVolume());
					}
				}
				if(main.leaderBoard.leaderboardClear.yesButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
					main.leaderBoard.leaderboardClear.close();
					main.leaderBoard.close();
					main.leaderBoard.leaderboardAPI.deleteLeaderboard();
					if(main.settings.getSoundVolume() > 0) {
						new SoundAPI().playSound(main.buttonSelectFile, (int) main.settings.getSoundVolume());
					}
				}
				return;
			}
			if(main.leaderBoard.exitButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
				main.leaderBoard.close();
				if(main.settings.getSoundVolume() > 0) {
					new SoundAPI().playSound(main.backSoundFile, (int) main.settings.getSoundVolume());
				}
			}
			
			if(main.leaderBoard.clearButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
				main.leaderBoard.leaderboardClear.open();
				if(main.settings.getSoundVolume() > 0) {
					new SoundAPI().playSound(main.buttonClickSoundFile, (int) main.settings.getSoundVolume());
				}
			}
			return;
		}
		if(main.isPauseMenu) {
			if(main.pauseMenu.helpMenu.isOpen()) {
				if(main.pauseMenu.helpMenu.okayButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
					main.pauseMenu.helpMenu.close();
					if(main.settings.getSoundVolume() > 0) {
						new SoundAPI().playSound(main.backSoundFile, (int) main.settings.getSoundVolume());
					}
				}
				
				
				return;
			}
			if(main.pauseMenu.leaderboardButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
				main.leaderBoard.open();
				if(main.settings.getSoundVolume() > 0) {
					new SoundAPI().playSound(main.buttonClickSoundFile, (int) main.settings.getSoundVolume());
				}
			}
			if(main.pauseMenu.backToGameButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
				if(main.settings.getMusicVolume() > 0) {
					SoundAPI.setVolume(main.musicClip, main.settings.getMusicVolume());
					main.musicClip.start();
					main.musicClip.loop(Clip.LOOP_CONTINUOUSLY);
				}
				if(main.settings.getSoundVolume() > 0) {
					new SoundAPI().playSound(main.buttonClickSoundFile, (int) main.settings.getSoundVolume());
				}
				main.jframe.requestFocusInWindow();
				BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
				main.jframe.getContentPane().setCursor(blankCursor);
				main.isPauseMenu = false;
				
			}
			
			if(main.pauseMenu.endGameButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
				if(main.settings.getSoundVolume() > 0) {
					new SoundAPI().playSound(main.deadSoundFile, (int) main.settings.getSoundVolume());
				}
				main.isPauseMenu = false;
				main.isGame = false;
				main.snake = new Snake(main, main.jframe.getWidth()/main.gridSize/2, main.jframe.getHeight()/main.gridSize/2, 0);
				main.oldScore = main.score;
				main.score = 0;
				main.isGame = false;
				// Activate the cursor
				main.jframe.getContentPane().setCursor(main.normalCursor);
				main.status = LanguageString.dead_text_game_ended[Language.getLanguage(main)];
				main.foods.clear();
				main.bombs.clear();
			}
			if(main.pauseMenu.helpButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
				main.pauseMenu.helpMenu.open();
				if(main.settings.getSoundVolume() > 0) {
					new SoundAPI().playSound(main.buttonClickSoundFile, (int) main.settings.getSoundVolume());
				}
			}
			return;
		}
		if(main.isGame) return;
		if(main.settings.isOpen()) {
			
			
			if(main.settings.backbutton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
				main.settings.close();
				if(main.settings.getSoundVolume() > 0) {
					new SoundAPI().playSound(main.backSoundFile, (int) main.settings.getSoundVolume());
				}
				return;
			}
			
			
			if(main.settings.difficultyButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
				if(main.settings.getSoundVolume() > 0) {
					new SoundAPI().playSound(main.buttonSelectFile, (int) main.settings.getSoundVolume());
				}
				if(e.isShiftDown() && (main.settings.getDifficulty() == Difficulty.HARD || main.settings.getDifficulty() == Difficulty.EXTREME)) {
					if(main.settings.getDifficulty() == Difficulty.HARD) {
						main.settings.setDifficulty(Difficulty.EXTREME);
					} else if(main.settings.getDifficulty() == Difficulty.EXTREME) {
						main.settings.setDifficulty(Difficulty.HARD);
					}
					return;
				}
				switch(main.settings.getDifficulty()) {
				case Difficulty.EASY:
					main.settings.setDifficulty(Difficulty.CLASSIC);
					break;
				case Difficulty.CLASSIC:
					main.settings.setDifficulty(Difficulty.NORMAL);
					break;
				case Difficulty.NORMAL:
					main.settings.setDifficulty(Difficulty.HARD);
					break;
				case Difficulty.HARD:
					main.settings.setDifficulty(Difficulty.EASY);
					break;
					
				}
			}
			if(main.settings.languageButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
				if(main.settings.getSoundVolume() > 0) {
					new SoundAPI().playSound(main.buttonSelectFile, (int) main.settings.getSoundVolume());
				}
				switch(main.settings.getLanguage()) {
				case Language.EN:
					main.settings.setLanguage(Language.DE);
					break;
				case Language.DE:
					main.settings.setLanguage(Language.EN);
					break;
				
				}
			}
			
			return;
		}
		if(main.leaderBoardButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
			main.leaderBoard.open();
			if(main.settings.getSoundVolume() > 0) {
				new SoundAPI().playSound(main.buttonClickSoundFile, (int) main.settings.getSoundVolume());
			}
			
		}
		if(main.startButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
			main.isGame = true;
			if(main.settings.getSoundVolume() > 0) {
				new SoundAPI().playSound(main.buttonClickSoundFile, (int) main.settings.getSoundVolume());
			}
			if(main.settings.getMusicVolume() > 0) {
				main.musicClip.setFramePosition(0);
				SoundAPI.setVolume(main.musicClip, main.settings.getMusicVolume());
				main.musicClip.start();
				main.musicClip.loop(Clip.LOOP_CONTINUOUSLY);
			}

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
		if(main.exitButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
			if(main.settings.getSoundVolume() > 0) {
				new SoundAPI().playSound(main.buttonClickSoundFile, (int) main.settings.getSoundVolume());
			}
			System.exit(0);
		}
		if(main.settingsButton.isMouseAbove(e.getXOnScreen(), e.getYOnScreen())) {
			main.settings.open();
			if(main.settings.getSoundVolume() > 0) {
				new SoundAPI().playSound(main.buttonClickSoundFile, (int) main.settings.getSoundVolume());
			}
			
			return;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
