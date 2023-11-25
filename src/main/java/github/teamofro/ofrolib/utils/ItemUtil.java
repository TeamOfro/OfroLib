package github.teamofro.ofrolib.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {

	private ItemUtil() {
	}

	/**
	 * プレイヤーからアイテムを指定された数削除します
	 *
	 * @param player    指定のプレイヤー
	 * @param itemStack アイテム
	 * @param amount    削除する数
	 * @return 指定された数の削除に成功したか
	 */
	public static boolean removeItemStack(Player player, ItemStack itemStack,
			int amount) {
		ItemStack[] inventory = player.getInventory().getContents();
		for (ItemStack item : inventory) {
			if (item != null && item.isSimilar(itemStack)) {
				int iAmount = item.getAmount();
				if (iAmount <= amount) {
					item.setAmount(0);
					amount -= iAmount;
				} else {
					item.setAmount(iAmount - amount);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * プレイヤーの指定したアイテムを持っている数を返します
	 *
	 * @param player    指定のプレイヤー
	 * @param itemStack アイテム
	 * @return 持っているアイテムの数
	 */
	public static int hasItemStack(Player player, ItemStack itemStack) {
		ItemStack[] inventory = player.getInventory().getContents();
		int count = 0;
		for (ItemStack item : inventory) {
			if (item != null && item.isSimilar(itemStack)) {
				count += item.getAmount();
			}
		}
		return count;
	}

	/**
	 * プレイヤーが指定したアイテムを指定の数持っているかを確認します
	 *
	 * @param player    指定のプレイヤー
	 * @param itemStack アイテム
	 * @param amount    持っている数
	 * @return 持っているか
	 */
	public static boolean hasItemStack(Player player, ItemStack itemStack,
			int amount) {
		ItemStack[] inventory = player.getInventory().getContents();
		int count = 0;
		for (ItemStack item : inventory) {
			if (item != null && item.isSimilar(itemStack)) {
				count += item.getAmount();
				if (count >= amount) {
					return true;
				}
			}
		}
		return false;
	}
}
