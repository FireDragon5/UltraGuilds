//package me.firedragon5.ultraguilds.guildwar.manager;
//
//import me.firedragon5.ultraguilds.guildwar.instance.Arena;
//import me.firedragon5.ultraguilds.UltraGuilds;
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.configuration.file.FileConfiguration;
//import org.bukkit.entity.Player;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ArenaManager {
//
//	private List<Arena> arenaList = new ArrayList<>();
//
//	public ArenaManager(UltraGuilds plugin){
//		FileConfiguration config = plugin.getConfig();
//
//		for (String str : config.getConfigurationSection("GuildWar.arena-spawn").getKeys(false)){
//
//
//			arenaList.add(new Arena(plugin ,Integer.parseInt(str), new Location(
//					Bukkit.getWorld(config.getString("GuildWar.arena-spawn" + str + ".world")),
//					config.getDouble("GuildWar.arena-spawn " + str + ".x"),
//					config.getDouble("GuildWar.arena-spawn" + str + ".y"),
//					config.getDouble("GuildWar.arena-spawn" + str + ".z"),
//					(float) config.getDouble("GuildWar.arena-spawn" + str + ".yaw"),
//					(float) config.getDouble("GuildWar.arena-spawn" + str + ".pitch"))));
//
//		}
//
//
//
//
//
//	}
//
//
//	public List<Arena> getArenaList() {return arenaList; }
//
//
//	public Arena getArena(Player player) {
//		for (Arena arena : arenaList){
//			if (arena.getPlayers().contains(player.getUniqueId())){
//				return arena;
//			}
//		}
//		return null;
//	}
//
//	public Arena getArena(int id) {
//		for (Arena arena : arenaList){
//			if (arena.getId() == id){
//				return arena;
//			}
//		}
//		return null;
//	}
//
//}
