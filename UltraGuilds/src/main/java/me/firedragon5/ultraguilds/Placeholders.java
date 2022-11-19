package me.firedragon5.ultraguilds;


import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class Placeholders extends  PlaceholderExpansion {


	@Override
	public @NotNull String getIdentifier() {
		return "ultraguilds";
	}

	@Override
	public @NotNull String getAuthor() {
		return UltraGuilds.getInstance().getDescription().getAuthors().toString();
	}

	@Override
	public @Nullable String getRequiredPlugin() {
		return "UltraGuilds";
	}

	@Override
	public @NotNull String getVersion() {
//		Get the version of the plugin
		return UltraGuilds.getInstance().getDescription().getVersion();
	}

	@Override
	public boolean canRegister() {
		return true;
	}

	@Override
	public boolean persist() {
		return true;
	}


	@Override
	public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {

		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);

		File memberFile = new File("plugins/UltraGuilds/Guilds/Members.yml");
		YamlConfiguration memberData = YamlConfiguration.loadConfiguration(memberFile);

		File guildPlayer = new File("plugins/UltraGuilds/Players/" + player.getUniqueId() + ".yml");
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(guildPlayer);

		File rankFile = new File("plugins/UltraGuilds/Ranks/Ranks.yml");
		YamlConfiguration rankData = YamlConfiguration.loadConfiguration(rankFile);

		if (player == null) {
			return "";
		}
		switch (params){
			case "guild_list":
				return guildData.getConfigurationSection("Guilds").getKeys(false).toString();

			case "player_guild_tag":
				return guildData.getString("Guilds." + player.getUniqueId() + ".Tag");

			case "player_guild_name":
				return guildData.getString("Guilds." + player.getUniqueId() + ".Name");

			case "player_rank":
				return playerData.getString("Ranks.Rank.RankName");

			case "player_rank_tag":
				return playerData.getString("Ranks.Rank.RankTag");

			case "player_next_rank":
				if (playerData.getString("Ranks.Rank.NextRank").equals("")){
					return "Max Rank";
				}else {
					return playerData.getString("Ranks.Rank.NextRank");
				}
			case "ranks_list":
				return rankData.getConfigurationSection("Ranks").getKeys(false).toString();

			default:
				return null;
		}
	}
}
