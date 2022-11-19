package me.firedragon5.ultraguilds;

//import me.firedragon5.ultraguilds.files.Ranks;
import me.firedragon5.ultraguilds.commands.guild.AdminGuild;
import me.firedragon5.ultraguilds.commands.guild.GuildChat;
import me.firedragon5.ultraguilds.commands.guild.GuildCommand;
import me.firedragon5.ultraguilds.commands.guild.menu.BannerMenu;
import me.firedragon5.ultraguilds.commands.guild.menu.GuildMenus;
import me.firedragon5.ultraguilds.commands.guild.menu.MembersMenu;
import me.firedragon5.ultraguilds.commands.ranks.RankCommand;
import me.firedragon5.ultraguilds.commands.ranks.menu.RankMenu;
//import me.firedragon5.ultraguilds.guildwar.events.ConnectListener;
//import me.firedragon5.ultraguilds.guildwar.events.GameListener;
import me.firedragon5.ultraguilds.events.JoinEvent;
//import me.firedragon5.ultraguilds.guildwar.manager.ArenaManager;
import me.firedragon5.ultraguilds.filemanager.ConfigManager;
import me.firedragon5.ultraguilds.filemanager.GuildManager;
import me.firedragon5.ultraguilds.filemanager.PlayerManager;
import me.firedragon5.ultraguilds.filemanager.RanksManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public final class UltraGuilds extends JavaPlugin {

//	Hashmap for the guild chat
	public static HashMap<UUID, String> guildChat = new HashMap<>();

	private static Economy econ;

	//	Get instance of the plugin
	public static UltraGuilds instance;

	public static Plugin getInstance() {
		return instance;
	}

//	private ArenaManager arenaManager;

//	Get all the managers
	private ConfigManager configManager;
	private RanksManager ranksManager;
	private GuildManager guildManager;
	private PlayerManager playerManager;




	@Override
	public void onEnable() {
//		ConfigManager.setConfig(this);
//		arenaManager =	new ArenaManager(this);
	  PlayerManager playerManager = new PlayerManager(this);
	  playerManager.setup();

		this.CheckGuilds();
		this.CheckRank();
		this.CheckPlayers();

		instance = this;

		setupEconomy();


		this.getConfig().options().copyDefaults();
		this.saveDefaultConfig();


		getCommand("guild").setExecutor(new GuildCommand(this));
		getCommand("guildchat").setExecutor(new GuildChat(this));
		getCommand("grank").setExecutor(new RankCommand(this));
		getCommand("aguild").setExecutor(new AdminGuild(this));



		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new MembersMenu(), this);
		getServer().getPluginManager().registerEvents(new RankMenu(this), this);
//		getServer().getPluginManager().registerEvents(new GameListener(this), this);
//		getServer().getPluginManager().registerEvents(new ConnectListener(this), this);
		getServer().getPluginManager().registerEvents(new GuildMenus(this), this);
		getServer().getPluginManager().registerEvents(new BannerMenu(this) , this);
	}


//	public ArenaManager getArenaManager() {
//		return arenaManager;
//	}

	/**
	 * Getters
	 */
	public static Economy getEconomy() {
		return econ;
	}

	public void CheckRank(){

//	On plugin start make a Rank/ranks.yml folder

//	Add the following to the Rank/ranks.yml file if one does not exist:
		File ranks = new File("plugins/UltraGuilds/Ranks/Ranks.yml");
		YamlConfiguration rankData = YamlConfiguration.loadConfiguration(ranks);



		if (!ranks.exists()) {
//			Also write he comments to the ranks.yml file

			rankData.options().copyDefaults(true);
			rankData.options().header("This is the ranks.yml file for UltraGuilds\n" +
					"Here you can add ranks and change the score needed to get to the next rank\n" +
					"Also you can change the prefix of the rank and the group of the rank\n" +
					"The Group option is the name of the group in LuckPerms.");

//			Ranks:
//  Newbie:
//    RankName: Newbie
//      RankTag: "&7[&fNewbie&7]"
//          Money: 0
//
//  Beginner:
//    RankName: Beginner
//    RankTag: "&7[&aBeginner&7]"
//        Money: 1000

			rankData.set("Ranks.Newbie.RankName", "Newbie");
			rankData.set("Ranks.Newbie.RankTag", "&7[&fNewbie&7]");
			rankData.set("Ranks.Newbie.Cost", 0);
			rankData.set("Ranks.Newbie.NextRank", "Beginner");
//			Add a description to the rank make it a list
			rankData.set("Ranks.Newbie.Description", "This is the description of the rank");
			rankData.set("Ranks.Newbie.Material", "DIAMOND");
			rankData.set("Ranks.Newbie.Command", "lp user %player% parent add default");

			rankData.set("Ranks.Beginner.RankName", "Beginner");
			rankData.set("Ranks.Beginner.RankTag", "&7[&aBeginner&7]");
			rankData.set("Ranks.Beginner.Cost", 1000);
			rankData.set("Ranks.Beginner.NextRank", "");
			rankData.set("Ranks.Beginner.Description", "This is the description of the rank");
			rankData.set("Ranks.Beginner.Material", "DIAMOND");
			rankData.set("Ranks.Beginner.Command", "lp user %player% parent add Beginner");



			try {
				rankData.save(ranks);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}



	}

	private void setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			econ = economyProvider.getProvider();
		} else {
			Bukkit.getLogger().info("Could not find Economy Provider!");
		}
	}




	public void CheckGuilds(){
		File guildFile = new File("plugins/UltraGuilds/Guilds");



//		Create the folder and file if it doesn't exist
		if (!guildFile.exists()) {
			guildFile.mkdirs();
			File guilds = new File("plugins/UltraGuilds/Guilds/Guilds.yml");
			try {
				guilds.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		File Members = new File("plugins/UltraGuilds/Guilds/Members.yml");
		if (!Members.exists()) {

//			Add the following to the Members.yml file if one does not exist:
			YamlConfiguration memberData = YamlConfiguration.loadConfiguration(Members);
			memberData.options().copyDefaults(true);

			memberData.set("Members.Guilds.", null);

			try {
				Members.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void CheckPlayers(){
		File playerFile = new File("plugins/UltraGuilds/Players");

		if (!playerFile.exists()) {
			playerFile.mkdirs();
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}
