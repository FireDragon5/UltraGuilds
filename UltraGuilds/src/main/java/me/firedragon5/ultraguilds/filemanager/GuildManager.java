package me.firedragon5.ultraguilds.filemanager;

import me.firedragon5.ultraguilds.UltraGuilds;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class GuildManager {


	private static FileConfiguration guildConfig;
	private UltraGuilds plugin;

	public GuildManager(UltraGuilds plugin) {
		this.plugin = plugin;
	}


	public static FileConfiguration getGuildConfig() {
		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);
		if (!guildFile.exists()) {
//			We will create the file
			try {
				guildFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return guildData;
	}

//	Save the guild data
	public static void saveGuildConfig() {

		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);

		try {
			guildData.save(guildFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	Loop through all the guilds and check if the guild exists
	public static boolean listGuild(String guild) {

		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);

		for (String guilds : guildData.getConfigurationSection("Guilds").getKeys(false)) {
			if (guilds.equalsIgnoreCase(guild)) {
				return true;
			}
		}
		return false;
	}




	/*
	Getter
	 */

//	Check if guild exists
	public static boolean guildExists(String guildName){
		return getGuildConfig().contains("Guilds." + guildName);
	}

	public static String getGuildName(String guild) {
		return getGuildConfig().getString("Guilds." + guild + ".Name");
	}

	public static String getGuildLeader(String guild) {
		return getGuildConfig().getString("Guilds." + guild + ".Leader");
	}

	public static String getGuildBanner(String guild) {
		return getGuildConfig().getString("Guilds." + guild + ".Banner");
	}

//	Tag
	public static String getGuildTag(String guild) {
		return getGuildConfig().getString("Guilds." + guild + ".Tag");
	}

//	Creator
	public static String getGuildCreator(String guild) {
		return getGuildConfig().getString("Guilds." + guild + ".Creator");
	}

//	Score
	public static int getGuildScore(String guild) {
		return getGuildConfig().getInt("Guilds." + guild + ".Score");
	}

//	Visibility = true or false
	public static boolean getGuildVisibility(String guild) {
		return getGuildConfig().getBoolean("Guilds." + guild + ".Visibility");
	}

//Spawn Location
	public static String getGuildSpawnLocation(String guild) {
		return getGuildConfig().getString("Guilds." + guild + ".SpawnLocation");
	}

//	Teleport the player to the spawn location
	public static void teleportToSpawn(Player player, String guild){

		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);

		String spawnLocation = guildData.getString("Guilds." + guild + ".SpawnLocation");

		player.teleport(Location.deserialize(guildData.getConfigurationSection("Guilds." + guild + ".SpawnLocation").getValues(true)));

	}

//	Check if spawn was set
	public static boolean spawnSet(String guild){
		return getGuildSpawnLocation(guild).equals("None");
	}

//	Get a list of guilds that visibility is set to true
	public static String getGuildList(){
		String guildList = "";
		for(String guild : getGuildConfig().getConfigurationSection("Guilds").getKeys(false)){
			if(getGuildVisibility(guild)){
				guildList += guild;
			}
		}
		return guildList;
	}

	/*
	Setter
	 */

/*
Setter
 */

//	Full setter guilds
	public static void setFullGuildData(String guildName, String guildTag, String guildLeader, String guildCreator, int guildScore, boolean guildVisibility, String guildSpawnLocation){

		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);

		guildData.set("Guilds." + guildName + ".Name", guildName);
		guildData.set("Guilds." + guildName + ".Tag", guildTag);
		guildData.set("Guilds." + guildName + ".Leader", guildLeader);
		guildData.set("Guilds." + guildName + ".Creator", guildCreator);
		guildData.set("Guilds." + guildName + ".Score", guildScore);
		guildData.set("Guilds." + guildName + ".Visibility", guildVisibility);
		guildData.set("Guilds." + guildName + ".SpawnLocation", guildSpawnLocation);


		try {
			guildData.save(guildFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	Name
	public static void setGuildName(String guild, String name) {
		getGuildConfig().set("Guilds." + guild + ".Name", name);
		saveGuildConfig();
	}

//	Leader
	public static void setGuildLeader(String guild, Player player) {
		getGuildConfig().set("Guilds." + guild + ".Leader", player.getName());
		saveGuildConfig();
	}

//	Banner
	public static void setGuildBanner(String guild, String banner) {
		getGuildConfig().set("Guilds." + guild + ".Banner", banner);
		saveGuildConfig();
	}

//	Tag
	public static void setGuildTag(String guild, String tag) {
		getGuildConfig().set("Guilds." + guild + ".Tag", tag);
		saveGuildConfig();
	}

//	Creator
	public static void setGuildCreator(String guild, Player player) {
		getGuildConfig().set("Guilds." + guild + ".Creator", player.getName());
		saveGuildConfig();
	}

//	Score
	public static void setGuildScore(String guild, int score) {
		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);

		getGuildConfig().set("Guilds." + guild + ".Score", score);

		try {
			getGuildConfig().save(guildFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	Visibility
	public static void setGuildVisibility(String guild, boolean visibility) {
		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);
		guildData.set("Guilds." + guild + ".Visibility", visibility);

		try {
			guildData.save(guildFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	Spawn Location
	public static void setGuildSpawnLocation(String guild, Player player) {

		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);

		guildData.set("Guilds." + guild + ".SpawnLocation", player.getLocation());

		try {
			guildData.save(guildFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	Set new Guild leader
	public static void setNewGuildLeader(String guild, Player target){

		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);

		guildData.set("Guilds." + guild + ".Leader", target.getName());

		try {
			guildData.save(guildFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
