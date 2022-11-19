package me.firedragon5.ultraguilds.ranks;

import me.firedragon5.ultraguilds.UltraGuilds;
import me.firedragon5.ultraguilds.Utils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public enum guildRank {




	//	This is the rank system for the guilds
	//	When a player reach a certain score level, they will be able to rank up

	//	First rank member
	E("E", 0, 0),

	//	Second rank D
	D("D", 100, 1),

	//	Third rank C
	C("C", 200, 2),

	//	Fourth rank B
	B("B", 300, 3),

	//	Fifth rank A
	A("A", 400, 4),

	//	Sixth rank S
	S("S", 500, 5),

	//	Seventh rank SS
	SS("SS", 600, 6);

	private String name;
	private int score;
	private int id;

	guildRank(String name, int score, int id) {
		this.name = name;
		this.score = score;
		this.id = id;
	}




	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public int getId() {
		return id;
	}

	public static guildRank getRank(int id) {
		for (guildRank rank : guildRank.values()) {
			if (rank.getId() == id) {
				return rank;
			}
		}
		return null;
	}

//	Get the player rank
	public static guildRank getPlayerRank(Player player) {
		File file = new File("plugins/UltraGuilds/Players/" + player.getUniqueId() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		int id = config.getInt("Guilds.GuildClass");
		return getRank(id);
	}

//	Increase the players id by 1


//	Set the rank to the players.yml file
	public static void setRank(Player player) {

		File guildPlayer = new File("plugins/UltraGuilds/Players/" + player.getUniqueId() + ".yml");
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(guildPlayer);

//		Set the new rank Id to the players.yml file
//		Increase the Id by 1

		playerData.set("Guilds.GuildClass",getPlayerRank(player).getId() + 1);

		try {
			playerData.save(guildPlayer);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

//	If thr player is max rank will send a message



//	Get the next rank of the players current rank id
	public static guildRank getNextRank(Player player) {

//		if player rank is SS return message

			int id = getPlayerRank(player).getId();
			return getRank(id + 1);
	}

//	Get the players score
	public static int getPlayerScore(Player player) {
		File guildPlayer = new File("plugins/UltraGuilds/Players/" + player.getUniqueId() + ".yml");
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(guildPlayer);
		return playerData.getInt("Guilds.Score");
	}

//	Get the next rank score
	public static int getNextRankScore(Player player) {
		return getNextRank(player).getScore();
	}

//	Check if the player score is greater than the next rank score
	public static boolean canRankUp(Player player) {
		return getPlayerScore(player) >= getNextRankScore(player);
	}

// Set the players rank with a message
// Rankup(Player player , Message)
	public static void rankUp(Player player, String message, String Error) {
		if (canRankUp(player)) {
			setRank(player);
			player.sendMessage(Utils.chat(message));
		}else {
				player.sendMessage(Utils.chat(Error));
		}
	}

//	Get the upgrade rank Name
	public static String getUpgradeRankName(Player player) {
		return getNextRank(player).getName();
	}



//	Get the players rank name
	public static String getPlayerRankName(Player player) {
		return getPlayerRank(player).getName();
	}

}
