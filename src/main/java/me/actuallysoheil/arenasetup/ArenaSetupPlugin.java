package me.actuallysoheil.arenasetup;

import com.andrei1058.bedwars.api.BedWars;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import me.actuallysoheil.arenasetup.command.BedWarsSetupCommand;
import me.actuallysoheil.arenasetup.listener.bedwars.SetupSessionCloseListener;
import me.actuallysoheil.arenasetup.listener.bedwars.SetupSessionStartListener;
import me.actuallysoheil.arenasetup.listener.player.PlayerActiveSessionListener;
import me.actuallysoheil.arenasetup.manager.ItemManager;
import me.actuallysoheil.arenasetup.manager.SetupSessionManager;
import me.actuallysoheil.arenasetup.menu.api.MenuListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

@Accessors(fluent = true)
@Getter
public final class ArenaSetupPlugin extends JavaPlugin {

    @Getter
    private static ArenaSetupPlugin instance;
    @Getter
    private static BedWars bedwarsAPI;

    private SetupSessionManager setupSessionManager;
    private ItemManager itemManager;

    @Override
    public void onEnable() {
        instance = this;
        initializeBedWars1058();

        this.setupSessionManager = new SetupSessionManager();
        this.itemManager = new ItemManager(this.setupSessionManager);

        registerListeners(
                new MenuListener(),

                new SetupSessionCloseListener(this.setupSessionManager),
                new SetupSessionStartListener(this.setupSessionManager, this.itemManager),

                new PlayerActiveSessionListener(this.setupSessionManager, this.itemManager)
        );
        getCommand("bwsetup").setExecutor(
                new BedWarsSetupCommand(this.itemManager)
        );
    }

    @Override
    public void onDisable() {
        this.itemManager.customItems().clear();

        instance = null;
    }

    private void initializeBedWars1058() {
        val pluginManager = getServer().getPluginManager();
        if (pluginManager.getPlugin("BedWars1058") == null) {
            getLogger().severe("BedWars1058 was not found. Disabling the plugin...");
            pluginManager.disablePlugin(this);
            return;
        }

        bedwarsAPI = Objects.requireNonNull(
                getServer().getServicesManager().getRegistration(BedWars.class),
                "BedWars1058 was not found."
        ).getProvider();
    }

    private void registerListeners(@NotNull Listener... listeners) {
        val pluginManager = getServer().getPluginManager();
        Arrays.stream(listeners)
                .forEach(listener -> pluginManager.registerEvents(listener, this));
    }

}