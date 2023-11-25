package github.teamofro.ofrolib.listeners;

import github.teamofro.ofrolib.utils.Util;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChange implements Listener {

	@EventHandler
	public void change(PlayerChangedWorldEvent e) {
		Player player = e.getPlayer();
		World world = player.getWorld();
		String worldName = world.getName();
		String sendWorldName = switch (worldName) {
			case "newworld" -> "lobby";
			default -> worldName;
		};
		player.sendActionBar(Util.mm("<gold><bold>現在のワールド:<yellow>" + sendWorldName));
		player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
		player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
	}
}
