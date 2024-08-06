package de.tom.snake;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JFrame;

import de.tom.snake.api.Difficulty;
import de.tom.snake.api.Language;
import de.tom.snake.help.HelpMenu;
import de.tom.snake.leaderboard.Leaderboard;
import de.tom.snake.leaderboard.LeaderboardInsert;
import de.tom.snake.listener.ClickListener;
import de.tom.snake.listener.SnakeDirectionEvent;
import de.tom.snake.objects.Bomb;
import de.tom.snake.objects.Button;
import de.tom.snake.objects.Food;
import de.tom.snake.objects.Snake;
import de.tom.snake.pause.PauseMenu;
import de.tom.snake.settings.Settings;

public class Main {
	
	
	public JFrame jframe;
	// How many milliseconds between an update.
	public int tickSpeed = 2;
	
	// There's only 1 player, what did you expect?
	public Snake snake;
	
	// The scale of the game.
	public int gridSize = 20;
	
	
	// The public atari-font
	public Font font;
	
	// You wouldn't change that, would you?
	public int score = 0;
	
	
	public int oldScore = 0;
	
	
	// This tells the individual tasks, if the player is in the wait-menu or is already moving
	public boolean isGame = false;
	public boolean isPauseMenu = false;
	public Cursor normalCursor;
	
	
	// The sketch-patch
	public String pathToData;
	public Button startButton;
	public Button settingsButton;
	public Button leaderBoardButton;
	public Button exitButton;
	
	public List<Food> foods = new ArrayList<>();
	
	public List<Bomb> bombs = new ArrayList<>();
	
	public int numbFoods = 1;
	
	public int numbBombs = 0;
	
	public Settings settings;
	
	public PauseMenu pauseMenu;
	
	
	
	public String status = "";
	
	public String version = "v.1.3.2.1";
	
	public String latest = "fixed death bug";
	
	public Leaderboard leaderBoard;
	
	public LeaderboardInsert leaderboardInsert;
	
	public AudioInputStream deadSoundStream;
	
	public boolean canActivateSounds = true;
	
	public File deadSoundFile;
	
	public File backSoundFile;
	
	public File highscoreSoundFile;
	
	public File highscoreSaveSoundFile;
	
	public File fruitEatSoundFile;
	
	public File buttonClickSoundFile;
	
	public File buttonSelectFile;
	
	public File musicSoundFile;
	
	public boolean canActivateMusic = true;
	
	public Clip musicClip;
	
	
	

	
	
	

	
	
	
	public Main() {
		ProcessHandle currentProcess = ProcessHandle.current();

	    // Get the PID of the current process
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", "SnakeUpdater.jar", version, "" + ProcessHandle.current().pid());
        try {
			Process process = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			pathToData = new File(URLDecoder.decode(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/..", "UTF-8")).getAbsolutePath();
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		
		new File(pathToData + "/info").mkdirs();
		new File(pathToData + "/data").mkdirs();
		new File(pathToData + "/data/sounds").mkdirs();
		createChangeLogFile();
		try {
	        Clip clip = AudioSystem.getClip();
	        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(pathToData + "/data/sounds/debugsound.wav"));
	        clip.open(inputStream);
	        clip.start(); 
	      } catch (Exception e) {
	        System.err.println(e.getMessage());
	        System.out.println("WARNING: You are running without the debugsound.wav. There may be bugs with the sound.");
	    }
		deadSoundFile = new File(pathToData + "/data/sounds/dead.wav");
		backSoundFile = new File(pathToData + "/data/sounds/back.wav");
		highscoreSoundFile = new File(pathToData + "/data/sounds/highscore.wav");
		highscoreSaveSoundFile = new File(pathToData + "/data/sounds/highscore_save.wav");
		fruitEatSoundFile = new File(pathToData + "/data/sounds/fruit_eat.wav");
		buttonClickSoundFile = new File(pathToData + "/data/sounds/button_click.wav");
		buttonSelectFile = new File(pathToData + "/data/sounds/button_select.wav");
		musicSoundFile = new File(pathToData + "/data/sounds/snake_music.wav");
		if(!deadSoundFile.exists() || !highscoreSaveSoundFile.exists() || !highscoreSoundFile.exists() || !backSoundFile.exists() || !fruitEatSoundFile.exists() || !buttonClickSoundFile.exists() || !buttonSelectFile.exists()) {
			canActivateSounds = false;
			System.out.println("Line 158, Main! ERROR: Seems like one of the soundfiles got missing. Sound cannot be enabled.");
		}
		
		if(!musicSoundFile.exists()) {
			System.out.println("Line 162, Main! ERROR: Seems like the music file got missing. Music cannot be enabled.");
			canActivateMusic = false;
		}
	    
		
		// Atari-textfont load (so it looks more like the 90's ;))
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(pathToData + "/data/AtariClassicChunky-PxXP.ttf")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		try {
			FileInputStream fileIn = new FileInputStream(pathToData + "/data/AtariClassicChunky-PxXP.ttf");
			
			font = Font.createFont(Font.TRUETYPE_FONT, fileIn);
			font = new Font(font.getName(), font.getStyle(),30);
		} catch (FontFormatException | IOException e) {
			
			e.printStackTrace();
		}
		
		// Initializing the JFrame.
		
		jframe = new JFrame("Snake");
		jframe.setContentPane(new DrawingPanel(this));
		jframe.setUndecorated(true);
		jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		
		// This is for not-fullscreenmode
		//jframe.setSize(16*gridSize, 16*gridSize);
		
		normalCursor = jframe.getContentPane().getCursor();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		
		
		// Setup the Food and the snake.
		
	
		snake = new Snake(this, jframe.getWidth()/gridSize/2, jframe.getHeight()/gridSize/2, 0);
		
		// The Movelistener of the snake
		jframe.addKeyListener(new SnakeDirectionEvent(this));
		jframe.addMouseListener(new ClickListener(this));
		// Setup the buttons
		startButton = new Button(jframe.getWidth()/2, (jframe.getHeight()/2)-jframe.getHeight()/6, "Start", 500, 80);
		settingsButton = new Button(jframe.getWidth()/2, (jframe.getHeight()/2), "Settings", 500, 80);
		leaderBoardButton = new Button(jframe.getWidth()/2, (jframe.getHeight()/2)+jframe.getHeight()/6, "Leaderboard", 500, 80);
		exitButton = new Button((jframe.getWidth()/2)+jframe.getWidth()/3, (jframe.getHeight()/2)+jframe.getHeight()/3, "Exit", 300, 50);
		
		// Loads the settings
		settings = new Settings(this);
		settings.loadSettingsFile();
		settings.loadSettings();
		
		pauseMenu = new PauseMenu(this);
		leaderBoard = new Leaderboard(this);
		leaderBoard.leaderboardAPI.loadLeaderboardFile();
		leaderboardInsert = new LeaderboardInsert(this);
		
		// starts the scheduler
		if(canActivateMusic) {
			try {
				AudioInputStream sound = AudioSystem.getAudioInputStream(musicSoundFile);
				DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
				musicClip = (Clip) AudioSystem.getLine(info);
				musicClip.open(sound);
				
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		startGame();
	}
	
	public static void main(String[] args) {
		// Just to avoid the static methods ;)
		new Main();
	}
	
	
	public void startGame() {
		// The gameticker itself.
		
		long timeInFuture = System.currentTimeMillis();
		/*
		 * The basic idea is that we set a time in the future, e.g. now but in 250 milliseconds, and we just loop every millisecond, if the time in 
		 * future turned into a time in the past (so 250 milliseconds are over), and then we set a new time in future, again in 250 milliseconds and so on...
		 */
		while(true) {
			if(timeInFuture <= System.currentTimeMillis()) {
				timeInFuture = System.currentTimeMillis() + tickSpeed;
				jframe.getContentPane().repaint();
				
			}
		}
	}
	
	
	public void createChangeLogFile() {
		File file = new File(pathToData + "/info/changelogs.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fileWriter = new FileWriter(file);
			
			fileWriter.write("Changelogs:\nPlease note: The first changes haven't been recorded. Also, minor changes are being ignored as well.\n\n");
			fileWriter.write("v.1.0.1.2  Fixed bomb-food-bug\n");
			fileWriter.write("v.1.0.2.0 Added leaderboard\n");
			fileWriter.write("v.1.0.2.1 Fixed difficulty-bug\n");
			fileWriter.write("v.1.0.2.2 Changed changelogs path\n");
			fileWriter.write("v.1.0.2.3 Changed data's path\n");
			fileWriter.write("v.1.0.2.4 Fixed cursor when pausemenu is closed\n");
			fileWriter.write("v.1.0.2.5 Improved performance of pausemenu\n");
			fileWriter.write("+o[---v.1.1---]o+\n");
			fileWriter.write("v.1.1.0.0 Added leaderboard-insert\n");
			fileWriter.write("v.1.1.0.1 Fixed leaderboard-insert-bug\n");
			fileWriter.write("v.1.1.0.2 Food doenst spawn in segments anymore\n");
			fileWriter.write("v.1.1.1.0_a Added first sound effect (alpha)\n");
			fileWriter.write("v.1.1.1.0 Fixed soundbug\n");
			fileWriter.write("v.1.1.1.1 Fixed buttonclickbug\n");
			fileWriter.write("v.1.1.1.2 Better leaderboard (time is in hover-event)\n");
			fileWriter.write("v.1.1.1.3 Added difficulty to leaderboard\n");
			fileWriter.write("+o[---v.1.2---]o+\n");
			fileWriter.write("v.1.2.0.0 Added all Sounds\n");
			fileWriter.write("+o[---v.1.3---]o+\n");
			fileWriter.write("v.1.3.0.0 Added music\n");
			fileWriter.write("v.1.3.0.1 fixed musicglitches\n");
			fileWriter.write("v.1.3.0.2 fixed soundglitch\n");
			fileWriter.write("v.1.3.0.3 fixed bombglitch on exit\n");
			fileWriter.write("v.1.3.1.0 added more sounds\n");
			fileWriter.write("v.1.3.2.0 edited hotkeys\n");
			fileWriter.write("v.1.3.2.1 fixed deathbug\n");			
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
