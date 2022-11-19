package me.firedragon5.ultraguilds.events;

import me.firedragon5.ultraguilds.UltraGuilds;
import me.firedragon5.ultraguilds.filemanager.PlayerManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class JoinEvent implements Listener {


	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		Player player = event.getPlayer();


//		File guildPlayer = new File("plugins/UltraGuilds/Players/" + player.getUniqueId() + ".yml");
//		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(guildPlayer);

		File ranks = new File("plugins/UltraGuilds/Ranks/Ranks.yml");
		YamlConfiguration rankData = YamlConfiguration.loadConfiguration(ranks);

//		If the players file doesn't exist create one

		if (!PlayerManager.playerFileExists(player)){

//			Write to the players file

			PlayerManager.setFullPlayerGuild(player, "None", "None", "None", 0, 0);

		}



		//			Put the player in the guild chat hashmap

		if (!UltraGuilds.guildChat.containsKey(player.getUniqueId())) {
			UltraGuilds.guildChat.put(player.getUniqueId(), PlayerManager.getPlayerGuildName(player));
		}



	}


}
