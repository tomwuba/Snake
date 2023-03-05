package de.tom.snake.leaderboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import de.tom.snake.Main;
import de.tom.snake.api.Difficulty;
import de.tom.snake.api.Language;
import de.tom.snake.api.LanguageString;
import de.tom.snake.objects.Button;

public class LeaderboardInsert {
	
	Main main;
	
	public boolean isOpen = false;
	
	public Button saveButton;
	
	public Button skipButton;
	
	boolean isPlaceEstimated = false;
	
	int estimatedPlace = 0;
	
	int currentHighscore = 0;
	
	int animationCount = 0;
	
	public String currentText = "";
	public LeaderboardInsertExit leaderboardInsertExit;
	
	
	
	
	public LeaderboardInsert(Main main) {
		this.main = main;
		saveButton = new Button(main.jframe.getWidth()/20*13, main.jframe.getHeight()/6*5, LanguageString.leaderboard_button_save[Language.getLanguage(main)], 400, 80);
		skipButton = new Button(main.jframe.getWidth()/20*7, main.jframe.getHeight()/6*5, LanguageString.leaderboard_button_skip[Language.getLanguage(main)], 400, 80, new Color(150, 0, 0));
		leaderboardInsertExit = new LeaderboardInsertExit(main, this);
	}
	
	
	public void draw(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, main.jframe.getWidth(), main.jframe.getWidth());
		g.setColor(Color.WHITE);
		g.setFont(g.getFont().deriveFont(70.0f));
		g.drawString(LanguageString.leaderboard_text_title[Language.getLanguage(main)], main.pauseMenu.helpMenu.getXFromStringLenght(LanguageString.leaderboard_text_title[Language.getLanguage(main)], g), 65);
		g.setFont(main.font);
		g.drawString(LanguageString.leaderboard_insert_new_highscore[Language.getLanguage(main)].replace("%highscore%", "" + currentHighscore), main.pauseMenu.helpMenu.getXFromStringLenght(LanguageString.leaderboard_insert_new_highscore[Language.getLanguage(main)].replace("%highscore%", "" + main.score), g), 120);
		saveButton.changeText(LanguageString.leaderboard_button_save[Language.getLanguage(main)]);
		saveButton.draw(g, !leaderboardInsertExit.isOpen());
		skipButton.changeText(LanguageString.leaderboard_button_skip[Language.getLanguage(main)]);
		skipButton.draw(g, !leaderboardInsertExit.isOpen());
		
		if(!isPlaceEstimated) {
			
			for(int i = 1; i < 6; i++) {
					
					if(!isPlaceEstimated) {
						if(main.leaderBoard.leaderboardAPI.stringList.size() >= i) {
							String nextString = main.leaderBoard.leaderboardAPI.stringList.get(i-1);
							int score = Integer.valueOf(nextString.split(":")[1]);
							if(score < currentHighscore) {
								isPlaceEstimated = true;
								estimatedPlace = i;
							} 
						} else {
							isPlaceEstimated = true;
							estimatedPlace = i;
						}
					}
				
				
			}
		}
		
		for(int i = 1; i < 6; i++) {

			if(main.leaderBoard.leaderboardAPI.stringList.size() >= i-1 || i == estimatedPlace) {

				String nextString = "";
				if(i == estimatedPlace) {
					nextString = ":" + currentHighscore + ":" + main.settings.getDifficulty() + ":" + System.currentTimeMillis();
				} else if(i < estimatedPlace) {
					nextString = main.leaderBoard.leaderboardAPI.stringList.get(i-1);
				} else {
					nextString = main.leaderBoard.leaderboardAPI.stringList.get(i-2);
				}

				String info = nextString.split(":")[0];
				int score = Integer.valueOf(nextString.split(":")[1]);
				String time;
				String difficulty = nextString.split(":")[2];
				if(main.leaderBoard.isDouble(nextString.split(":")[3])) {
					Date date = new Date(Long.valueOf(nextString.split(":")[3]));
					String dd_MM_yyyy = new SimpleDateFormat(LanguageString.leaderboard_date_pattern[Language.getLanguage(main)]).format(date);
					String HH_mm_ss = new SimpleDateFormat(LanguageString.leaderboard_time_pattern[Language.getLanguage(main)]).format(date);
					time = dd_MM_yyyy + " " + HH_mm_ss;
				} else {
					
					time = LanguageString.general_error[Language.getLanguage(main)];
				}
				if(i != estimatedPlace) {
					g.setColor(Color.LIGHT_GRAY);
					g.drawString( i + ": " + info + "; Score: " + score, main.pauseMenu.helpMenu.getXFromStringLenght( i + ": " + info + "; " + score, g), main.jframe.getHeight()/10+100*i);

				} else {
					String animation = " ";
					if(animationCount >= 100) {
						animation = "_";
					}
					g.setColor(Color.WHITE);
					g.drawString( i + ": " + currentText + animation + "; Score: " + score, main.pauseMenu.helpMenu.getXFromStringLenght( i + ": " + currentText + animation + "; Score: " + score, g), main.jframe.getHeight()/10+100*i);

				}
			} else {
					g.setColor(Color.GRAY);
					g.drawString(i + ":      " + LanguageString.leaderboard_text_empty[Language.getLanguage(main)], main.pauseMenu.helpMenu.getXFromStringLenght(i + ":      " + LanguageString.leaderboard_text_empty[Language.getLanguage(main)], g), main.jframe.getHeight()/10+100*i);
					
				
			}
			
		}
		if(leaderboardInsertExit.isOpen()) {
			leaderboardInsertExit.draw(g);
			return;
		}
		animationCount++;
		if(animationCount > 200) {
			animationCount = 0;
		}
		
		
	}
	
	public void open() {
		isPlaceEstimated = false;
		isOpen = true;
		currentHighscore = main.score;
		main.score = 0;
		currentText = "";
	}
	
	public void close(boolean save) {
		isOpen = false;
		if(save) {
			main.leaderBoard.leaderboardAPI.stringList.add(currentText + ":" + currentHighscore + ":" + main.settings.getDifficulty() +":" + System.currentTimeMillis());
			Collections.sort(main.leaderBoard.leaderboardAPI.stringList, new Comparator<String>() {
		        @Override
		        public int compare(String string1, String string2)
		        {

		            return  Integer.valueOf(string2.split(":")[1]).compareTo(Integer.valueOf(string1.split(":")[1]));
		        }
		    });
			if(main.leaderBoard.leaderboardAPI.stringList.size() > 5) {
				main.leaderBoard.leaderboardAPI.stringList = (ArrayList<String>) main.leaderBoard.leaderboardAPI.stringList.stream().limit(5).collect(Collectors.toList());
			}
			main.leaderBoard.leaderboardAPI.saveLeaderboard();
		}
	}
	
	
	
	public boolean isOpen() {
		return isOpen;
	}
	


}
