package ipBot.bot;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

public class Bot {

	public static String getIp() throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	public static void main(String[] args) throws LoginException, InterruptedException, IOException {
		SettingsLoader settingsLoading = new SettingsLoader("ip_bot_settings.json");
		Settings settings = settingsLoading.getSettings();
		Discord discord = new Discord(settings.token);
		discord.sendMessage("@everyone , the server has started!, IP:"+getIp()+":"+settings.port, settings.channelID);
		discord.disconnect();
	}

	
} class Discord {

	private JDA jda;

	public Discord(String token) throws LoginException, InterruptedException {
		JDABuilder builder = new JDABuilder(token);
		this.jda = builder.build();
		this.jda.setAutoReconnect(true);
		this.jda.awaitReady();
	}

	public void disconnect() {
		this.jda.shutdown();
	}
	
	public void sendMessage(String message, String channelID) {

		if (message.length() > 2000) {
			char[] chars = message.toCharArray();
			int i = 0;
			while (chars.length - i * 2000 > 0) {
				StringBuilder sb = new StringBuilder();
				for (int a = 0; (a + i * 2000 < chars.length) && a < 2000; a++) {
					sb.append(chars[a + i * 2000]);
				}
				sendMessage(sb.toString(), channelID);
				i++;
			}
		} else {
			this.jda.getTextChannelById(channelID).sendTyping();
			this.jda.getTextChannelById(channelID).sendMessage(message).queue();

			Logger.getGlobal().log(Level.INFO, "Discord message sent");
		}

	}

}
		