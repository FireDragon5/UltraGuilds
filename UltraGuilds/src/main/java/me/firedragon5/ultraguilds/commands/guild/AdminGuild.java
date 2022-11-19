package me.firedragon5.ultraguilds.commands.guild;

import me.firedragon5.ultraguilds.UltraGuilds;
import me.firedragon5.ultraguilds.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdminGuild implements CommandExecutor, TabCompleter {

	private final UltraGuilds plugin;

	public AdminGuild(UltraGuilds plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player){


			Player player = (Player) sender;

			File warFolder = new File("plugins/UltraGuilds/War/GuildWar.yml");
			YamlConfiguration warData = YamlConfiguration.loadConfiguration(warFolder);


//			/aguild <reloadconfig|warspawn1|warspawn2|warbegin>

			if (args.length == 1){
				if (args[0].equalsIgnoreCase("reloadconfig")){
					//reload config

					UltraGuilds.getInstance().reloadConfig();
					player.sendMessage(Utils.chat("&a&lReloaded config"));

				}
//				if (args[0].equalsIgnoreCase("warspawn1")){
//					//set war spawn 1
//
//					warData.set("WarSpawn1", player.getLocation());
//
//					player.sendMessage(Utils.chat("&a&lSet war spawn 1"));
//
//				}
//				if (args[0].equalsIgnoreCase("warspawn2")){
//					//set war spawn 2
//
//					warData.set("WarSpawn2", player.getLocation());
//
//					player.sendMessage(Utils.chat("&a&lSet war spawn 2"));
//
//				}
//				if (args[0].equalsIgnoreCase("warbegin")){
//					//start war
//
////					Check if the 2 warspawn was set
//					if (warData.get("WarSpawn1") == null || warData.get("WarSpawn2") == null){
//						player.sendMessage(Utils.chat("&c&lYou need to set the 2 war spawn first"));
//						return true;
//					}
//
//					//start war
////					plugin.war.startWar();
//
//
//				}
			}


		}else {
			sender.sendMessage("You must be a player to use this command!");
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

		List<String> tab = new ArrayList<>();

		if (args.length == 1){
			tab.add("reloadconfig");
//			tab.add("warspawn1");
//			tab.add("warspawn2");
//			tab.add("warbegin");
		}

		for (int i = 0; i < tab.size(); i++) {
			if (!tab.get(i).toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
				tab.remove(i);
				i--;
			}
		}

		return tab;



	}
}
