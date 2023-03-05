package de.tom.snake.leaderboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.tom.snake.Main;
import de.tom.snake.api.Difficulty;
import de.tom.snake.api.Language;
import de.tom.snake.api.LanguageString;
import de.tom.snake.objects.Button;

public class Leaderboard {
	
	
	boolean isOpen = false;
	Main main;
	
	public LeaderboardAPI leaderboardAPI;
	
	public Button exitButton;
	
	public Button clearButton;
	
	public LeaderboardClear leaderboardClear;
	
	
	public Leaderboard(Main main) {
		this.main = main;
		leaderboardAPI = new LeaderboardAPI(main);
		exitButton = new Button(main.jframe.getWidth()/2, 3*main.jframe.getHeight()/4, LanguageString.general_back[Language.getLanguage(main)], 300, 80);
		clearButton = new Button(main.jframe.getWidth()/5*4, 3*main.jframe.getHeight()/4, LanguageString.leaderboard_button_clear[Language.getLanguage(main)], 250, 80, new Color(150, 0, 0));
		leaderboardClear = new LeaderboardClear(main);
	}
	
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, main.jframe.getWidth(), main.jframe.getHeight());
		g.setColor(Color.WHITE);
		g.setFont(g.getFont().deriveFont(70.0f));
		g.drawString(LanguageString.leaderboard_text_title[Language.getLanguage(main)], main.pauseMenu.helpMenu.getXFromStringLenght(LanguageString.leaderboard_text_title[Language.getLanguage(main)], g), 65);
		g.setFont(main.font);
		exitButton.changeText(LanguageString.general_back[Language.getLanguage(main)]);
		clearButton.changeText(LanguageString.leaderboard_button_clear[Language.getLanguage(main)]);
		exitButton.draw(g, !leaderboardClear.isOpen());
		clearButton.draw(g, !leaderboardClear.isOpen());
		Collections.sort(leaderboardAPI.stringList, new Comparator<String>() {
	        @Override
	        public int compare(String string1, String string2)
	        {

	            return  Integer.valueOf(string2.split(":")[1]).compareTo(Integer.valueOf(string1.split(":")[1]));
	        }
	    });
		
		
		for(int i = 1; i < 6; i++) {
			if(leaderboardAPI.stringList.size() >= i) {
				String currentString = leaderboardAPI.stringList.get(i-1);
				if(currentString.split(":").length < 4) {
					g.setColor(Color.WHITE);
					g.drawString( i + ":   " + LanguageString.general_error[Language.getLanguage(main)], main.pauseMenu.helpMenu.getXFromStringLenght(i + ":   " + LanguageString.general_error[Language.getLanguage(main)], g), main.jframe.getHeight()/10+100*i);
					
				} else {
					String info = currentString.split(":")[0];
					if(info.length() > 15) {
						info = info.substring(0, Math.min(info.length(), 15)) + "...";
					}
					
					int score = Integer.valueOf(currentString.split(":")[1]);

					String time;
					
					g.setColor(Color.WHITE);
					g.drawString( i + ": " + info + "; Score: " + score, main.pauseMenu.helpMenu.getXFromStringLenght( i + ": " + info + "; Score: " + score, g), main.jframe.getHeight()/10+100*i);
					
					
				}
				
				
			} else {
				g.setColor(Color.WHITE);
				g.drawString( i + ":   " + LanguageString.leaderboard_text_empty[Language.getLanguage(main)], main.pauseMenu.helpMenu.getXFromStringLenght(i + ":   " + LanguageString.leaderboard_text_empty[Language.getLanguage(main)], g), main.jframe.getHeight()/10+100*i);
			}
			
		}
		
		
		// Hover-Items ------------------------------
		
		
		for(int i = 1; i < 6; i++) {
			if(leaderboardAPI.stringList.size() >= i) {
				String currentString = leaderboardAPI.stringList.get(i-1);
				if(currentString.split(":").length == 4) {
					
					String difficulty = currentString.split(":")[2];
					String time;
					String info = currentString.split(":")[0];
					int score = Integer.valueOf(currentString.split(":")[1]);
					if(isDouble(currentString.split(":")[3])) {
						Date date = new Date(Long.valueOf(currentString.split(":")[3]));
						String dd_MM_yyyy = new SimpleDateFormat(LanguageString.leaderboard_date_pattern[Language.getLanguage(main)]).format(date);
						String HH_mm_ss = new SimpleDateFormat(LanguageString.leaderboard_time_pattern[Language.getLanguage(main)]).format(date);
						time = dd_MM_yyyy + " " + HH_mm_ss;
					} else {
						time = LanguageString.general_error[Language.getLanguage(main)];
					}
					int mouseX = MouseInfo.getPointerInfo().getLocation().x;
					int mouseY = MouseInfo.getPointerInfo().getLocation().y;
					if(mouseX >= main.pauseMenu.helpMenu.getXFromStringLenght( i + ": " + info + "; Score: " + score, g) && mouseX <= (main.jframe.getWidth()/2)+(g.getFontMetrics(g.getFont()).stringWidth( i + ": " + info + "; Score: " + score)/2)) {
						if(mouseY >= (main.jframe.getHeight()/10+100*i)-40 && mouseY <= (main.jframe.getHeight()/10+100*i)+g.getFontMetrics(g.getFont()).getHeight()-10) {
							drawInfoBox(g, time, Integer.valueOf(difficulty));
						}
					}
				}

				
			} 
		}
		
		if(leaderboardClear.isOpen) {
			leaderboardClear.draw(g);
		}

	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	
	public void open() {
		isOpen = true;
		leaderboardAPI.updateLeaderboard();
	}
	
	public void close() {
		isOpen = false;
	}
	
	public boolean isDouble(String s) {
	    try { 
	        Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	
	void drawInfoBox(Graphics g, String time, int difficulty) {
		int mouseX = MouseInfo.getPointerInfo().getLocation().x;
		int mouseY = MouseInfo.getPointerInfo().getLocation().y;
		g.setColor(new Color(255, 255, 255));
		g.fillRect(mouseX, mouseY, 800, 160);
		g.setColor(new Color(0,0,0));
		g.fillRect(mouseX+5, mouseY+5, 790, 150);
		g.setColor(new Color(255,255,255));
		g.drawString(LanguageString.leaderboard_time_text[Language.getLanguage(main)] + time, mouseX+10, mouseY+45);
		g.drawString(LanguageString.leaderboard_difficulty_text[Language.getLanguage(main)] + Difficulty.toString(difficulty, main), mouseX+10, mouseY+115);
		
		
	}
	
	

}
