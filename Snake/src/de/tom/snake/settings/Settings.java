package de.tom.snake.settings;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.tom.snake.Main;
import de.tom.snake.api.Difficulty;
import de.tom.snake.api.Language;
import de.tom.snake.api.LanguageString;
import de.tom.snake.listener.ClickListener;
import de.tom.snake.objects.Button;
import de.tom.snake.objects.MoveableButton;

public class Settings {
	
	boolean isOpen = false;
	
	// Settings (Please change ingame.
	float soundVolume = 50.0f;
	float music = 50.0f;
	int difficulty = Difficulty.CLASSIC;
	int language = Language.EN;
	
	public Button backbutton;
	public MoveableButton soundEffectButton;
	public Button difficultyButton;
	public Button languageButton;
	
	public MoveableButton musicButton;
	
	Main main;
	
	
	

	
	
	
	public Settings(Main main) {
		this.main = main;
		backbutton = new Button(main.jframe.getWidth()/2, (main.jframe.getHeight()/5)*4, "", 500, 75);
		//soundEffectButton = new Button(main.jframe.getWidth()/2, (main.jframe.getHeight()/6), "", 800, 75);
		soundEffectButton = new MoveableButton(main, "", main.jframe.getWidth()/2, (main.jframe.getHeight()/6), soundVolume, 800);
		
		musicButton = new MoveableButton(main, "", main.jframe.getWidth()/2, (main.jframe.getHeight()/6)*2, music, 800);
		difficultyButton = new Button(main.jframe.getWidth()/2, (main.jframe.getHeight()/6)*3, "", 800, 75);
		languageButton = new Button(main.jframe.getWidth()/2, (main.jframe.getHeight()/6)*4, "", 800, 75);
		
	}
	
	public void loadSettingsFile() {
		File file = new File(main.pathToData + "/data/settings.txt");
		if(!file.exists()) {
			createFile();
		}
	}
	
	public void createFile() {
		File file = new File(main.pathToData + "/data/settings.txt");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FileWriter fileWriter = new FileWriter(file);
			
			fileWriter.write("soundeffects: " + soundVolume + "\n");
			fileWriter.write("music: " + music + "\n");
			fileWriter.write("difficulty: " + difficulty + "\n");
			fileWriter.write("language: " + language);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}
	
	
	
	
	public void open() {
		
		loadSettings();
		soundEffectButton.setValue(soundVolume);
		musicButton.setValue(music);
		isOpen = true;
		

	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public void close() {
		saveSettings();
		isOpen = false;
		
	}
	
	public void setSoundEffect(int volume) {
		soundVolume = volume;

		
	}
	
	public float getSoundVolume() {
		if(!main.canActivateSounds) {
			return 0;
		}
		return soundVolume;
	}
	
	public void setMusic(int volume) {
		
		music = volume;
	}
	
	public float getMusicVolume() {
		return (float)(music/100.0f);
	}
	
	
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public void setLanguage(int language) {
		this.language = language;
	}
	
	public int getLanguage() {
		return language;
	}
	
	
	public void drawSettings(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, main.jframe.getWidth(), main.jframe.getHeight());
		
		g.setColor(Color.WHITE);
		backbutton.changeText(LanguageString.general_back[Language.getLanguage(main)]);
		backbutton.draw(g);
		
		
		soundEffectButton.draw(g);
		soundEffectButton.changeText(LanguageString.settings_text_soundeffects[Language.getLanguage(main)]);
		if(ClickListener.isLeftMouseDraggedOverSoundButton) {
			soundEffectButton.setValue(((ClickListener.mouseXWhenClicked-(ClickListener.mouseXWhenClicked-(MouseInfo.getPointerInfo().getLocation().x)))-main.jframe.getWidth()/2+400)*49/(400-20));
		}
//		musicButton.changeText(LanguageString.settings_text_music[Language.getLanguage(main)] + booleanString(music));
		musicButton.draw(g);
		musicButton.changeText(LanguageString.settings_text_music[Language.getLanguage(main)]);
		if(ClickListener.isLeftMouseDraggedOverMusicButton) {
			musicButton.setValue(((ClickListener.mouseXWhenClicked-(ClickListener.mouseXWhenClicked-(MouseInfo.getPointerInfo().getLocation().x)))-main.jframe.getWidth()/2+400)*49/(400-20));
		}
		difficultyButton.changeText(LanguageString.settings_text_difficulty[Language.getLanguage(main)] + Difficulty.toString(difficulty, main));
		difficultyButton.draw(g);
		languageButton.changeText(LanguageString.settings_text_language[Language.getLanguage(main)] + Language.toString(language));
		languageButton.draw(g);
		music = (int) musicButton.getValue();
		soundVolume = (int) soundEffectButton.getValue();
	}
	
	
	
    public String[] readLines(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }
    
    public void saveSettings() {
		File file = new File(main.pathToData + "/data/settings.txt");
		
		try {
			String[] lines = readLines(file);
			String[] newLines = new String[lines.length];
			int i = 0;
			for(String line : lines) {
				
				if(line.startsWith("soundeffects: ")) {
					newLines[i] = "soundeffects: " + soundVolume + "\n";
				} else if(line.startsWith("music: ")) {
					newLines[i] = "music: " + music + "\n";				
				} else if(line.startsWith("difficulty: ")) {
					newLines[i] = "difficulty: " + difficulty + "\n";					
				} else if(line.startsWith("language: ")) {
					newLines[i] = "language: " + language + "\n";					
				} else {
					newLines[i] = line + "\n";
				}
				i++;
			}
			FileWriter fileWriter = new FileWriter(file);
			for(String line : newLines) {
				fileWriter.write(line);
			}
			fileWriter.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }
    
    public void loadSettings() {
		File file = new File(main.pathToData + "/data/settings.txt");
		try {
			String[] lines = readLines(file);
			String[] newLines = new String[lines.length];
			int i = 0;
			for(String line : lines) {
				
				if(line.startsWith("soundeffects: ")) {
					soundVolume = Float.valueOf(line.split(": ")[1]);
				} else if(line.startsWith("music: ")) {
					music = Float.valueOf(line.split(": ")[1]);		
				} else if(line.startsWith("difficulty: ")) {
					difficulty = Integer.valueOf(line.split(": ")[1]);			
				} else if(line.startsWith("language: ")) {
					language = Integer.valueOf(line.split(": ")[1]);				
				} else {
					newLines[i] = line + "\n";
				}
				i++;
			}
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }
	
	
	

}
