package me.firedragon5.ultraguilds.commands.ranks;

import me.firedragon5.ultraguilds.UltraGuilds;
import me.firedragon5.ultraguilds.Utils;
import me.firedragon5.ultraguilds.commands.ranks.menu.RankMenu;
import me.firedragon5.ultraguilds.filemanager.PlayerManager;
import me.firedragon5.ultraguilds.filemanager.RanksManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RankCommand implements CommandExecutor, TabCompleter {


	private final UltraGuilds plugin;

	public RankCommand(UltraGuilds plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {



//		/granks

		Player player = (Player) sender;


//		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
//		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);

		File memberFile = new File("plugins/UltraGuilds/Guilds/Members.yml");
		YamlConfiguration memberData = YamlConfiguration.loadConfiguration(memberFile);

		if (args.length == 0) {

			RankMenu rankMenu = new RankMenu(plugin);
			rankMenu.createMenu(player);


		}else if (args[0].equalsIgnoreCase("upgrade")){
//			Check if the player can upgrade their rank

			Economy econ = UltraGuilds.getEconomy();

			String rankName = PlayerManager.getPlayerRankName(player);
			String nextRank = RanksManager.getNextRank(rankName);
			String nextRankName = RanksManager.getRankName(nextRank);
			int nextRankCost = RanksManager.getRankCost(nextRank);
			String nextRankTag = RanksManager.getRankPrefix(nextRank);
			String nextRankDescription = RanksManager.getRankDescription(nextRank);
			String nextRankIcon = RanksManager.getRankMaterial(nextRank);
			String nextRankCommand = RanksManager.getRankCommandConsole(nextRank);
			String nextRankNextRank = RanksManager.getNextRank(nextRank);



//			Check what rank the player clicks on

				if (PlayerManager.getPlayerRankNextRank(player).equals("")) {
					player.sendMessage(Utils.chat("&cYou have reached the max rank!"));
					return true;
				}

			if (econ.getBalance(player) >= RanksManager.getRankCost(nextRank)) {
				econ.withdrawPlayer(player, RanksManager.getRankCost(nextRank));

				PlayerManager.setFullRank(player, nextRank, nextRankTag, nextRankCost, nextRankNextRank,
						nextRankDescription, nextRankIcon, nextRankCommand);

				player.sendMessage(Utils.chat("&aYou have upgraded your rank to " + nextRank + "!"));
			}else {
				player.sendMessage(Utils.chat("&cYou do not have enough money to upgrade your rank!"));
			}


			}



		return true;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		List<String> tab = new ArrayList<>();

		if (args.length == 1) {
			tab.add("upgrade");
		}

		tab.removeIf(s -> !s.toLowerCase().startsWith(args[0].toLowerCase()));

		return tab;
	}
}
