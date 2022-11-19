package me.firedragon5.ultraguilds.commands.guild.menu;

import me.firedragon5.ultraguilds.Utils;
import me.firedragon5.ultraguilds.filemanager.PlayerManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GuildSettings implements Listener {


	public Inventory inv ;

	public GuildSettings(Player player){

//		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
//		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);

		File memberFile = new File("plugins/UltraGuilds/Guilds/Members.yml");
		YamlConfiguration memberData = YamlConfiguration.loadConfiguration(memberFile);

//		File guildPlayer = new File("plugins/UltraGuilds/Players/" + player.getUniqueId() + ".yml");
//		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(guildPlayer);

		String guildName = PlayerManager.getPlayerGuildName(player);


		inv = player.getServer().createInventory(null, 54, Utils.chat("&6&lGuild Settings"));


		ItemStack GuildName = new ItemStack(Material.NAME_TAG);
		ItemMeta GuildNameMeta = GuildName.getItemMeta();
		GuildNameMeta.setDisplayName(Utils.chat("&6&lGuild Name: " + guildName));
		List<String> guildNameLore = new ArrayList<String>();
		guildNameLore.add(Utils.chat("&7Click to change the guild name"));
		GuildNameMeta.setLore(guildNameLore);
		GuildName.setItemMeta(GuildNameMeta);







	}

//	If player clicks on a item get the string from the chat and set it to the item
//	Then save the file
//	Then update the inventory
//	Then open the inventory


@EventHandler
public void onClick(InventoryClickEvent event) {
	Player player = (Player) event.getWhoClicked();
	if (event.getView().getTitle().equals(Utils.chat("&6&lGuild Settings"))) {
		event.setCancelled(true);
		if (event.getCurrentItem() == null) {
			return;
		}
		if (event.getCurrentItem().getType() == Material.NAME_TAG) {
			player.closeInventory();
			player.sendMessage(Utils.chat("&6&lGuilds &8>> &7Please type the new guild name in chat"));
//			Get the message the players has typed
//			Set the item to the message







		}
	}

	}




}
