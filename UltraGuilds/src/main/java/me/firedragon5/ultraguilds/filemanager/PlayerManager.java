package me.firedragon5.ultraguilds.filemanager;

import me.firedragon5.ultraguilds.UltraGuilds;
import me.firedragon5.ultraguilds.ranks.guildRank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class PlayerManager {


	private UltraGuilds plugin;

	public PlayerManager(UltraGuilds plugin) {
		this.plugin = plugin;
	}

//	Setup
	public void setup() {
		File playerFile = new File("plugins/UltraGuilds/Players/");
		if (!playerFile.exists()) {
			playerFile.getParentFile().mkdirs();
			plugin.saveResource("Players/", false);
		}
	}


	public static FileConfiguration getPlayerData(Player player) {
		File playerFile = new File("plugins/UltraGuilds/Players/" + player.getUniqueId().toString() + ".yml");
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
//		Now we need to check if the file exists
//		If the file does not exist, we will create it
		if (!playerFile.exists()) {
//			We will create the file
			try {
				playerFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
//		Now we need to load the file
		return playerData;
	}





//	reload the player data
	public static void reloadPlayerData(Player player) {
		File playerFile = new File("plugins/UltraGuilds/Players/" + player.getUniqueId().toString() + ".yml");
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
	}



	public static void savePlayerData(Player player) {
		File playerFile = new File("plugins/UltraGuilds/Players/" + player.getUniqueId().toString() + ".yml");
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
		try {
			playerData.save(playerFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	Check if the player file exists
	public static boolean playerFileExists(Player player) {
		File playerFile = new File("plugins/UltraGuilds/Players/" + player.getUniqueId() + ".yml");
		return playerFile.exists();
	}

/*
*
* Setters
*/

//	Set everything about the player
	public static void setFullPlayerGuild(Player player, String guildName, String guildTag, String guildRank
			, int guildClass, int guildScore){

		File playerFile = new File("plugins/UltraGuilds/Players/" + player.getUniqueId().toString() + ".yml");
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);

		playerData.set("Guilds.GuildName", guildName);
		playerData.set("Guilds.Tag", guildTag);
		playerData.set("Guilds.GuildRank", guildRank);
		playerData.set("Guilds.GuildClass", guildClass);
		playerData.set("Guilds.Score", guildScore);

		try{
			playerData.save(playerFile);
		}catch (Exception e){
			e.printStackTrace();
		}


	}

//	Set the player guildName, Tag, GuildRank, GuildClass, Score
	public static void setPlayerGuildName(Player player, String guildName) {
		getPlayerData(player).set("Guilds.GuildName", guildName);
		savePlayerData(player);
	}

	public static void setPlayerGuildTag(Player player, String guildTag) {
		getPlayerData(player).set("Guilds.Tag", guildTag);
		savePlayerData(player);
	}

	public static void setPlayerGuildRank(Player player, String guildRank) {
		getPlayerData(player).set("Guilds.Rank", guildRank);
		savePlayerData(player);
	}

	public static void setPlayerGuildClass(Player player, int guildClass) {
		getPlayerData(player).set("Guilds.Class", guildClass);
		savePlayerData(player);
	}

	public static void setPlayerGuildScore(Player player, int guildScore) {
		getPlayerData(player).set("Guilds.Score", guildScore);
		savePlayerData(player);
	}

/*
Getters
 */

//	Get the player guildName, Tag, GuildRank, GuildClass, Score
	public static String getPlayerGuildName(Player player) {
		return getPlayerData(player).getString("Guilds.GuildName");
	}

	public static String getPlayerGuildTag(Player player) {
		return getPlayerData(player).getString("Guilds.Tag");
	}

	public static String getPlayerGuildRank(Player player) {
		return getPlayerData(player).getString("Guilds.Rank");
	}

	public static int getPlayerGuildClass(Player player) {
		return getPlayerData(player).getInt("Guilds.Class");
	}

	public static int getPlayerGuildScore(Player player) {
		return getPlayerData(player).getInt("Guilds.Score");
	}




/*
Setter for Rank
 */

//	Set the player rankName, RankTag, Cost, NextRank, Material, Command
	public static void setPlayerRankName(Player player, String rankName) {
		getPlayerData(player).set("Rank.RankName", rankName);
	}

	public static void setPlayerRankTag(Player player, String rankTag) {
		getPlayerData(player).set("Rank.RankTag", rankTag);
		savePlayerData(player);
	}

	public static void setPlayerRankCost(Player player, int rankCost) {
		getPlayerData(player).set("Rank.Cost", rankCost);
		savePlayerData(player);
	}

	public static void setPlayerRankNextRank(Player player, String rankNextRank) {
		getPlayerData(player).set("Rank.NextRank", rankNextRank);
		savePlayerData(player);
	}

	public static void setPlayerRankMaterial(Player player, Material rankMaterial) {
		getPlayerData(player).set("Rank.Material", rankMaterial);
		savePlayerData(player);
	}

	public static void setPlayerRankCommand(Player player, String rankCommand) {
		getPlayerData(player).set("Rank.Command", rankCommand);
		savePlayerData(player);
	}



/*
Getters for Rank
 */

//	Get the player rankName, RankTag, Cost, NextRank, Material, Command

	public static String getPlayerRankName(Player player) {
		return getPlayerData(player).getString("Rank.RankName");
	}

	public static String getPlayerRankTag(Player player) {
		return getPlayerData(player).getString("Rank.RankTag");
	}

	public static int getPlayerRankCost(Player player) {
		return getPlayerData(player).getInt("Rank.Cost");
	}

	public static String getPlayerRankNextRank(Player player) {
		return getPlayerData(player).getString("Rank.NextRank");
	}

	public static String getPlayerRankMaterial(Player player) {
		return getPlayerData(player).getString("Rank.Material");
	}

	public static String getPlayerRankCommand(Player player) {
		return getPlayerData(player).getString("Rank.Command");
	}



//	Check if the player rank is null
	public static boolean playerRankIsNull(Player player) {
		return getPlayerData(player).getString("Rank.RankName").equalsIgnoreCase("");
	}







	/*
	Setters for Ranks
	 */


	public static void setNice(Player player, String nice) {
		getPlayerData(player).set("Nice", nice);
//		savePlayerData(player);
	}

//	Set full rank to player file
	public static void setFullRank(Player player, String rankName, String rankTag, int rankCost, String rankNextRank,
								    String rankDescription , String rankMaterial,  String rankCommand) {

		File playerFile = new File("plugins/UltraGuilds/Players/" + player.getUniqueId() + ".yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);

		playerData.set("Rank.RankName", rankName);
		playerData.set("Rank.RankTag", rankTag);
		playerData.set("Rank.Cost", rankCost);
		playerData.set("Rank.NextRank", rankNextRank);
		playerData.set("Rank.Material", rankMaterial);
		playerData.set("Rank.Command", rankCommand);
		playerData.set("Rank.Description", rankDescription);


		try {
			playerData.save(playerFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/*
	Placeholders
	 */



//	Guild placeholders
	public static String getGuildName(Player player) {
		return getPlayerData(player).getString("Guilds.GuildName");
	}

	public static String getGuildTag(Player player) {
		return getPlayerData(player).getString("Guilds.Tag");
	}

	public static String getGuildRank(Player player) {
		return getPlayerData(player).getString("Guilds.GuildRank");
	}

	public static int getGuildClass(Player player) {
		return getPlayerData(player).getInt("Guilds.Class");
	}

	public static int getGuildScore(Player player) {
		return getPlayerData(player).getInt("Guilds.Score");
	}








}
