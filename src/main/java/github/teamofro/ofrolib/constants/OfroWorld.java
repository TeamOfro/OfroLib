package github.teamofro.ofrolib.constants;

import github.teamofro.ofrolib.OfroLib;
import github.teamofro.ofrolib.utils.FormatterUtil;
import github.teamofro.ofrolib.utils.Util;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * lobbyとかcasualとかそこらへんの不変的なワールド
 */
public class OfroWorld {

	@NotNull
	public static final World LOBBY = Objects.requireNonNull(Bukkit.getWorld("newworld"));

	@NotNull
	public static final World SHOP = Objects.requireNonNull(Bukkit.getWorld("shop"));

	public static void teleportLobby(Player player) {
		player.teleport(OfroLib.getCore()
				.getMVWorldManager()
				.getMVWorld(LOBBY.getName())
				.getSpawnLocation());
	}

	public static boolean isLobby(World world) {
		return LOBBY.equals(world);
	}

	public static final String savageName = "savage";

	public static String getNaturalWorld(String world) {
		if (world.endsWith("_nether")) {
			return world.replace("_nether", "");
		}
		// endはまだ未定
		/*
		 * if (world.endsWith("_end")) {
		 * return world.replace("_end", "");
		 * }
		 */
		return world;
	}

	public static World getNaturalWorld(World world) {
		return Bukkit.getWorld(getNaturalWorld(world.getName()));
	}

	public static boolean isSavageWorld(String worldName) {
		return worldName.startsWith(savageName);
	}

	public static boolean isSavageWorld(World world) {
		return isSavageWorld(world.getName());
	}

	public static class Savage {
		public static void dropMoney(OfflinePlayer player, Location location) {
			Economy economy = OfroLib.getEconomy();
			double balance = economy.getBalance(player);
			ThreadLocalRandom current = ThreadLocalRandom.current();
			ItemStack money = null;
			int moneyInteger = 0;
			if (balance >= 100000) {
				if (current.nextBoolean()) {
					moneyInteger = 10000;
					money = Items.get10000Yen();
				}
			} else if (balance >= 50000) {
				if (current.nextInt(3) == 0) {
					moneyInteger = 1000;
					money = Items.get1000Yen();
				}
			} else if (balance >= 10000) {
				if (current.nextInt(4) == 0) {
					moneyInteger = 100;
					money = Items.get100Yen();
				}
			}
			World world = location.getWorld();
			if (money != null) {
				Util.cast(FormatterUtil.format(
						"<gray>{player}は{money}円を落とした",
						Map.of("player", player.getName(), "money", moneyInteger)));
				economy.withdrawPlayer(player, moneyInteger);
				world.dropItemNaturally(location, money);
			}
		}
	}
}
