package de.tom.snake.api;

import de.tom.snake.Main;

public abstract class Difficulty {
	
	public static final int EASY = 0,
							CLASSIC = 1,
							NORMAL = 2,
							HARD = 3,
							EXTREME = 4;
	public static String toString(int difficulty, Main main) {
		switch(difficulty) {
		case 0:
			return LanguageString.difficulty_easy[Language.getLanguage(main)];
		case 1:
			return LanguageString.difficulty_classic[Language.getLanguage(main)];
		case 2:
			return LanguageString.difficulty_normal[Language.getLanguage(main)];
		case 3:
			return LanguageString.difficulty_hard[Language.getLanguage(main)];
		case 4:
			return LanguageString.difficulty_extreme[Language.getLanguage(main)];
		}
		return LanguageString.general_error[Language.getLanguage(main)];
	}
	
	public static final int[] SNAKE_SPEEDS = {150, 130, 120, 100, 70 },
			                  FRUITS_NUMB = {8, 1 , 2, 1, 1 },
			                  BOMB_NUMBS = {0, 0, 10, 30, 80 };

}
