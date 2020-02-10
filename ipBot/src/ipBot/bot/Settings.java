package ipBot.bot;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

public class Settings {

	public String token = "token";
	public String channelID = "channelID";
	public String port = "25565";
	
	public Settings() {
		// empty cos yeeee
	}

	public void applyDefaults() {
		token = "token";
		channelID = "channelID";
		port = "25565";
	}

	public void saveSettings(String settingsFile) throws JsonIOException, IOException {
		Gson jason = new Gson();

		File file = new File(settingsFile);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}

		PrintWriter writer = new PrintWriter(file);
		writer.println(jason.toJson(this).replace("\",", "\",\n"));
		writer.close();
	}

}
