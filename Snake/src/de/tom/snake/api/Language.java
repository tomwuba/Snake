package de.tom.snake.api;

import de.tom.snake.Main;
import de.tom.snake.settings.Settings;

public abstract class Language {
	
	
	
	
	
	public static final int EN = 0,
						    DE = 1;
	
	public static String toString(int language) {
		switch(language) {
		case 0:
			return LanguageString.language_names[language];
		case 1:
			return LanguageString.language_names[language];
		}
		return LanguageString.general_error[0];
	}
	
	public static int getLanguage(Main main) {
		return main.settings.getLanguage();
	}
	
	

}
