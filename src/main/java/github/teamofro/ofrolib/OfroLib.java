package github.teamofro.ofrolib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseNetherPortals.MultiverseNetherPortals;
import com.onarandombox.multiverseinventories.MultiverseInventories;
import github.teamofro.ofrolib.api.serializer.ItemStackSerializer;
import github.teamofro.ofrolib.api.serializer.LocationSerializer;
import github.teamofro.ofrolib.listeners.EventListeners;
import github.teamofro.ofrolib.listeners.WorldChange;
import github.teamofro.ofrolib.multiverse.CommandDestination;
import github.teamofro.ofrolib.utils.PrefixUtil;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class OfroLib extends JavaPlugin {

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(ItemStack.class, new ItemStackSerializer())
            .registerTypeAdapter(Location.class, new LocationSerializer())
            .create();

    public static final String prefix = "<gray>[<aqua>Ofro<gray>]";
    public static final PrefixUtil prefixUtil = new PrefixUtil(prefix);

    private static MultiverseNetherPortals netherPortals;
    private static MultiverseInventories inventories;
    private static MultiverseCore core;
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    public static MultiverseNetherPortals getNetherPortals() {
        return netherPortals;
    }

    public static MultiverseInventories getInventories() {
        return inventories;
    }

    public static MultiverseCore getCore() {
        return core;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }

    public static OfroLib getPlugin() {
        return getPlugin(OfroLib.class);
    }

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            prefixUtil.logInfo("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();

        loadMultiverse();

        registerEvent(new EventListeners());
        registerEvent(new WorldChange());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * {@link Listener} を登録するメゾット
     *
     * @param listener instance
     */
    public void registerEvent(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    private void loadMultiverse() {
        netherPortals = (MultiverseNetherPortals) Bukkit.getServer()
                .getPluginManager()
                .getPlugin("Multiverse-NetherPortals");
        inventories = (MultiverseInventories) Bukkit.getServer().getPluginManager().getPlugin(
                "Multiverse-Inventories");
        core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin(
                "Multiverse-Core");
        core.getDestFactory().registerDestinationType(CommandDestination.class,
                "cmd");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp == null) {
            return false;
        }
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        }
        perms = rsp.getProvider();
        return perms != null;
    }

    public static String getOfroMark() {
        return "♨";
    }
}
