package de.tom.snake.leaderboard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.tom.snake.Main;

public class LeaderboardAPI {
	
	
	Main main;
	
	public ArrayList<String> stringList = new ArrayList<>();
	
	
	
	public LeaderboardAPI(Main main) {
		this.main = main;
	}
	
	
	public void loadLeaderboardFile() {
		File file = new File(main.pathToData + "/data/leaderboard.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
			
		}
		stringList.clear();
		try {
			String[] lines = readLines(new File(main.pathToData + "/data/leaderboard.txt"));
			for(String line : lines) {
				stringList.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateLeaderboard() {
		stringList.clear();
		try {
			String[] lines = readLines(new File(main.pathToData + "/data/leaderboard.txt"));
			for(String line : lines) {
				stringList.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteLeaderboard() {
		File file = new File(main.pathToData + "/data/leaderboard.txt");
		if(file.exists()) {
			file.delete();
		}
		stringList.clear();
	}
	
	
	
	
	
	
	
	public void saveLeaderboard() {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(new File(main.pathToData + "/data/leaderboard.txt"));
			for(String line : stringList) {
				fileWriter.write(line + "\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String[] readLines(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
        	if(line.split(":").length == 4 && isInteger(line.split(":")[1])) {
                lines.add(line);
        	}

        }
        bufferedReader.close();
        
        return lines.toArray(new String[lines.size()]);
    }
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public boolean isHighScore(int score) {
		if(stringList.isEmpty()) {
			return true;
		}
		if(stringList.size() < 5) {
			return true;
		}
		for(String string : stringList) {
			if(string.split(":").length == 4) {
				if(isInteger(string.split(":")[1])) {
					if(Integer.valueOf(string.split(":")[1])< score) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
