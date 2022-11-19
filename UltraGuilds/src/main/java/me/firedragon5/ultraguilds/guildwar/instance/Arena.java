package me.firedragon5.ultraguilds.guildwar.instance;

import me.firedragon5.ultraguilds.filemanager.ConfigManager;
import me.firedragon5.ultraguilds.guildwar.GameState;
import me.firedragon5.ultraguilds.UltraGuilds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {

	private UltraGuilds plugin;

	private int id;
	private Location spawn;

	private GameState state;
	private List<UUID> players;
	private CountDown countDown;
	private Game game;

	public Arena(UltraGuilds plugin, int id, Location spawn) {
		this.id = id;
		this.spawn = spawn;

		this.state = GameState.RECRUITING;
		this.players = new ArrayList<>();
		this.countDown = new CountDown(plugin,this);

	}

	/* Game */
	public void start(){

		game.start();
	}

	public void reset(boolean kickPlayers){
		if (kickPlayers){

			Location loc = ConfigManager.getLobbySpawn();

			for (UUID uuid : players){
				 Bukkit.getPlayer(uuid).teleport(loc);

			}
			players.clear();
		}
		state = GameState.RECRUITING;
		countDown.cancel();
		countDown = new CountDown(plugin, this);
		game = new Game(this);
	}

	/* Tools */
	public void sendTitle(String title, String subtitle) {
		for (UUID uuid : players){
			Bukkit.getPlayer(uuid).sendTitle(title, subtitle);

		}
	}

	public void sendMessage(String message){
		for (UUID uuid : players){
			Bukkit.getPlayer(uuid).sendMessage(message);

		}
	}

     /* Players */

	public void addPlayer(Player player){
		players.add(player.getUniqueId());
		player.teleport(spawn);

		if (state.equals(GameState.RECRUITING) && players.size() >= ConfigManager.getRequiredPlayers()){
				countDown.start();
		}
	}

	public void removePlayer(Player player){
		players.remove(player.getUniqueId());
		player.teleport(ConfigManager.getLobbySpawn());

	}

	/*
	 * Info
	 */

	public int getId() {return id; }

	public GameState getState() {return state; }
	public List<UUID> getPlayers() {return players; }

	public void setState(GameState state) {this.state = state; }


}
