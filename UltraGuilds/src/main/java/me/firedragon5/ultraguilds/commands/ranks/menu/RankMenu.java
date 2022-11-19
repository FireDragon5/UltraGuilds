package me.firedragon5.ultraguilds.commands.ranks.menu;


import me.firedragon5.ultraguilds.UltraGuilds;
import me.firedragon5.ultraguilds.Utils;
import me.firedragon5.ultraguilds.filemanager.PlayerManager;
import me.firedragon5.ultraguilds.filemanager.RanksManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RankMenu implements Listener {

	private final UltraGuilds plugin;

	public RankMenu(UltraGuilds plugin) {
		this.plugin = plugin;
	}


	public Inventory inv ;

	public void createMenu(Player player) {

//		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
//		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);

		File memberFile = new File("plugins/UltraGuilds/Guilds/Members.yml");
		YamlConfiguration memberData = YamlConfiguration.loadConfiguration(memberFile);

//		File guildPlayer = new File("plugins/UltraGuilds/Players/" + player.getUniqueId() + ".yml");
//		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(guildPlayer);
//
//		File rankFile = new File("plugins/UltraGuilds/Ranks/Ranks.yml");
//		YamlConfiguration rankData = YamlConfiguration.loadConfiguration(rankFile);


		inv = Bukkit.createInventory(null, 54, Utils.chat("&8&lRanks"));

//		Player head that show their rank
		ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
		playerHeadMeta.setOwner(player.getName());
		playerHeadMeta.setDisplayName(Utils.chat("&6&l" + player.getName()));
		List<String> playerHeadLore = new ArrayList<>();
		playerHeadLore.add(Utils.chat("&7Rank: " + PlayerManager.getPlayerRankName(player)));

		playerHeadMeta.setLore(playerHeadLore);
		playerHead.setItemMeta(playerHeadMeta);

		inv.setItem(4, playerHead);

		int ranksBSlot = 9;

		for (String rank : RanksManager.getRanksConfig().getConfigurationSection("Ranks").getKeys(false)) {

			ranksBSlot++;

			Material material = Material.valueOf(RanksManager.getRankMaterial(rank));

			Material currentRank = Material.valueOf(plugin.getConfig().getString("Ranks-Settings.CurrentRankItem"));

			if (PlayerManager.getPlayerRankName(player).equalsIgnoreCase(rank)) {
				material = currentRank;
			}

			ItemStack item = new ItemStack(material);
			ItemMeta meta = item.getItemMeta();

			meta.setDisplayName(Utils.chat("&l&6" + RanksManager.getRankName(rank)));
			List<String> lore = new ArrayList<>();
			lore.add(Utils.chat("&l&bRank Name: &f" + RanksManager.getRankName(rank)));
			lore.add(Utils.chat("&l&bRank Prefix: &f" + RanksManager.getRankPrefix(rank)));
			lore.add(Utils.chat("&l&bCost: &f" + RanksManager.getRankCost(rank)));
//			lore.add(Utils.chat("&l&bRank Description: &f" + rankData.getString("Ranks." + rank + ".Description")));

			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(ranksBSlot ,item);
		}




//		Check if slot is null and if it is, set it to a glass pane
		for (int i = 0; i < inv.getSize(); i++) {
			if (inv.getItem(i) == null) {
				ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(" ");
				item.setItemMeta(meta);
				inv.setItem(i, item);
			}
		}


//		Close button
		ItemStack close = new ItemStack(Material.BARRIER);
		ItemMeta closeMeta = close.getItemMeta();
		closeMeta.setDisplayName(Utils.chat("&c&lClose"));
		close.setItemMeta(closeMeta);
		inv.setItem(49, close);

		player.openInventory(inv);

	}

	@EventHandler
	public void onClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();

//		File guildFile = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
//		YamlConfiguration guildData = YamlConfiguration.loadConfiguration(guildFile);
//
//		File memberFile = new File("plugins/UltraGuilds/Guilds/Members.yml");
//		YamlConfiguration memberData = YamlConfiguration.loadConfiguration(memberFile);
//
//		File guildPlayer = new File("plugins/UltraGuilds/Players/" + player.getUniqueId() + ".yml");
//		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(guildPlayer);
//
//		File rankFile = new File("plugins/UltraGuilds/Ranks/Ranks.yml");
//		YamlConfiguration rankData = YamlConfiguration.loadConfiguration(rankFile);

		if(event.getView().getTitle().equals(Utils.chat("&8&lRanks"))){
			event.setCancelled(true);


			if (clicked.getType() == Material.BARRIER) {
				player.closeInventory();
			}

		}
	}

}
