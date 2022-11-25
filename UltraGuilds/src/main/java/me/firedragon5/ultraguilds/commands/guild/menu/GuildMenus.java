package me.firedragon5.ultraguilds.commands.guild.menu;

import me.firedragon5.ultraguilds.UltraGuilds;
import me.firedragon5.ultraguilds.Utils;
import me.firedragon5.ultraguilds.filemanager.GuildManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuildMenus implements Listener {

	private final UltraGuilds plugin;

	public GuildMenus(UltraGuilds plugin) {
		this.plugin = plugin;
	}


	public Inventory inv;


	public void GuildMenu(Player player) {

		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);

		File memberFile = new File("plugins/UltraGuilds/Guilds/Members.yml");
		YamlConfiguration memberData = YamlConfiguration.loadConfiguration(memberFile);
//
//		File guildPlayer = new File("plugins/UltraGuilds/Players/" + player.getUniqueId() + ".yml");
//		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(guildPlayer);



		inv = Bukkit.createInventory(null, 54, Utils.chat("&8Guild Menu"));

//		Create a menu that displays all the guilds on the server

		/*
		* TODO
		*  - Create guild custom item
		*
		* */



		for (String guild : GuildManager.getGuildConfig().getKeys(false)) {

			String guildTag = GuildManager.getGuildTag(guild);
			int guildScore = GuildManager.getGuildScore(guild);
			String guildLeader = GuildManager.getGuildLeader(guild);
			String guildCreator = GuildManager.getGuildCreator(guild);


			String guildMembersCount = memberData.getString("Members.Guilds." + guild + ".Counter");

/*

			TODO
			 Fixes:
			 -	Guild Leader gives an error. Error: Cannot invoke "String.length()" because "name" is null

 */

			String guildLeaderName = Bukkit.getOfflinePlayer(UUID.fromString(guildLeader)).getName();
			String guildCreatorName = Bukkit.getOfflinePlayer(UUID.fromString(guildCreator)).getName();



			ItemStack guildItem = new ItemStack(Material.END_ROD);
			ItemMeta guildMeta = guildItem.getItemMeta();
			guildMeta.setDisplayName(Utils.chat("&8Guild: &b" + guild));
			List<String> guildLore = new ArrayList<>();
			guildLore.add(Utils.chat("&8Tag: &b" + guildTag));
			guildLore.add(Utils.chat("&8Score: &b" + guildScore));
			guildLore.add(Utils.chat("&8Leader: &b" + guildLeaderName));
			guildLore.add(Utils.chat("&8Creator: &b" + guildCreatorName));
			guildLore.add(Utils.chat("&8Members: &b" + guildMembersCount));
			guildMeta.setLore(guildLore);
			guildItem.setItemMeta(guildMeta);


			inv.addItem(guildItem);


		}

//		if slot is null place a glass panel
		for (int i = 0; i < inv.getSize(); i++) {
			if (inv.getItem(i) == null) {
				ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
				ItemMeta glassMeta = glass.getItemMeta();
				glassMeta.setDisplayName(Utils.chat("&8"));
				glass.setItemMeta(glassMeta);
				inv.setItem(i, glass);
			}
		}

		player.openInventory(inv);

	}



	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		ItemStack clicked = e.getCurrentItem();
		if (e.getView().getTitle().equals(Utils.chat("&8Guild Menu"))) {
			e.setCancelled(true);
			if (clicked.getType() == Material.END_ROD) {
				player.closeInventory();
			}
		}
	}






}
