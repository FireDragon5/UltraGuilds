package me.firedragon5.ultraguilds.filemanager;

import me.firedragon5.ultraguilds.UltraGuilds;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class RanksManager {

	private static FileConfiguration ranksConfig;
	private UltraGuilds plugin;

	public RanksManager(UltraGuilds plugin) {
		this.plugin = plugin;
	}



	public static FileConfiguration getRanksConfig() {
		File ranksFile = new File("plugins/UltraGuilds/Ranks/Ranks.yml");
		if (!ranksFile.exists()) {
			ranksFile.getParentFile().mkdirs();
			UltraGuilds.getInstance().saveResource("Ranks/Ranks.yml", false);
		}
		return	ranksConfig = YamlConfiguration.loadConfiguration(ranksFile);
	}

	public static void saveRanksConfig() {
		try {
			ranksConfig.save("plugins/UltraGuilds/Ranks/Ranks.yml");
		} catch (Exception e) {
			System.out.println("Could not save ranks data");
		}
	}

	public static boolean rankExists(String rank) {
		if (ranksConfig.contains("Ranks." + rank)) {
			return true;
		}
		return false;
	}

//	Get a full rank
	public static String getFullRank(String rankName, String rankTag, int cost, String nextRank,
									 String Description, Material material, String commandConsole) {


		rankName = getRanksConfig().getString("Ranks." + rankName + ".Name");
		rankTag = getRanksConfig().getString("Ranks." + rankName + ".Tag");
		cost = getRanksConfig().getInt("Ranks." + rankName + ".Cost");
		nextRank = getRanksConfig().getString("Ranks." + rankName + ".NextRank");
		Description = getRanksConfig().getString("Ranks." + rankName + ".Description");
		material = Material.getMaterial(getRanksConfig().getString("Ranks." + rankName + ".Material"));
		commandConsole = getRanksConfig().getString("Ranks." + rankName + ".CommandConsole");


		return rankName + rankTag + cost + nextRank + Description + material + commandConsole;



	}

	public static String getRankName(String rank) {
		return getRanksConfig().getString("Ranks." + rank + ".RankName");
	}

	public static String getRankPrefix(String rank) {
		return getRanksConfig().getString("Ranks." + rank + ".RankTag");
	}

	public static int getRankCost(String rank) {
		return getRanksConfig().getInt("Ranks." + rank + ".Cost");
	}

	public static String getNextRank(String rank) {
		return getRanksConfig().getString("Ranks." + rank + ".NextRank");
	}

	public static String getRankDescription(String rank) {
		return getRanksConfig().getString("Ranks." + rank + ".Description");
	}

	public static String getRankMaterial(String rank) {
		return getRanksConfig().getString("Ranks." + rank + ".Material");
	}

	public static String getRankCommandConsole(String rank) {
		return getRanksConfig().getString("Ranks." + rank + ".Command");
	}






}
