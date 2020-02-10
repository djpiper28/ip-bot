package ipBot.bot;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class SettingsLoader {

	private Settings settings;

	public SettingsLoader(String settingsFile) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson jason = new Gson();
		this.settings = jason.fromJson(new FileReader(settingsFile), Settings.class);
	}

	public Settings getSettings() {
		return this.settings;
	}
}
