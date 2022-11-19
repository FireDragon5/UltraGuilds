package me.firedragon5.ultraguilds.filemanager;

import me.firedragon5.ultraguilds.UltraGuilds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

	private static FileConfiguration config;

	public static void setConfig(UltraGuilds plugin) {
		ConfigManager.config = plugin.getConfig();
		plugin.saveDefaultConfig();
	}




	public static int getRequiredPlayers() {
		return config.getInt("GuildWar.requiredPlayers");
	}

	public static int getCountDownSeconds() {
		return config.getInt("GuildWar.countdown-seconds");
	}

	public static Location getLobbySpawn(){
		return new Location(

				Bukkit.getWorld(config.getString("GuildWar.lobbySpawn.world")),
				config.getDouble("GuildWar.lobbySpawn.x"),
				config.getDouble("GuildWar.lobbySpawn.y"),
				config.getDouble("GuildWar.lobbySpawn.z"),
				(float) config.getDouble("GuildWar.lobbySpawn.yaw"),
				(float) config.getDouble("GuildWar.lobbySpawn.pitch")

		) ;
	}



}
