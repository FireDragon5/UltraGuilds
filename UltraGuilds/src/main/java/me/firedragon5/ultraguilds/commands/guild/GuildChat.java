package me.firedragon5.ultraguilds.commands.guild;

import me.firedragon5.ultraguilds.UltraGuilds;
import me.firedragon5.ultraguilds.Utils;
import me.firedragon5.ultraguilds.filemanager.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class GuildChat implements CommandExecutor {

	private final UltraGuilds plugin;

	public GuildChat(UltraGuilds plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


		Player player = (Player) sender;


		File memberFile = new File("plugins/UltraGuilds/Guilds/Members.yml");
		YamlConfiguration memberData = YamlConfiguration.loadConfiguration(memberFile);


//		/guildchat <message>

		if (PlayerManager.getPlayerGuildName(player).equals("None")) {
			player.sendMessage(Utils.chat("&cYou are not in a guild!"));
			return true;
		}


		if (args.length == 0){
			if (UltraGuilds.guildChat.containsKey(player.getUniqueId())){
				UltraGuilds.guildChat.remove(player.getUniqueId());
				player.sendMessage(Utils.chat(plugin.getConfig().getString("GuildChat.LeaveChat")
						.replace("%player%", player.getName())));
			} else {
				UltraGuilds.guildChat.put(player.getUniqueId(), PlayerManager.getPlayerGuildName(player));
				player.sendMessage(Utils.chat(plugin.getConfig().getString("GuildChat.JoinChat")
						.replace("%player%", player.getName())));
			}
		} else {
			if (UltraGuilds.guildChat.containsKey(player.getUniqueId())){
				String message = "";
				for (int i = 0; i < args.length; i++){
					message = message + args[i] + " ";
				}
				for (Player online : plugin.getServer().getOnlinePlayers()){
					if (UltraGuilds.guildChat.containsKey(online.getUniqueId())){
						if (UltraGuilds.guildChat.get(online.getUniqueId()).equalsIgnoreCase(PlayerManager.getPlayerGuildName(player))){
							online.sendMessage(Utils.chat(plugin.getConfig().getString("GuildChat.Format")
									.replace("%player%", player.getName()).replace("%message%", message)));
						}
					}
				}
			} else {
				player.sendMessage(Utils.chat(plugin.getConfig().getString("GuildChat.NotInChat")));
			}
		}



		return true;
	}
}
