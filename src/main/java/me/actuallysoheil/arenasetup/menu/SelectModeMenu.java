package me.actuallysoheil.arenasetup.menu;

import lombok.val;
import me.actuallysoheil.arenasetup.manager.SetupSessionManager;
import me.actuallysoheil.arenasetup.manager.SetupSessionManager.ArenaSetupModeType;
import me.actuallysoheil.arenasetup.menu.api.ItemBuilder;
import me.actuallysoheil.arenasetup.menu.api.Menu;
import me.actuallysoheil.arenasetup.menu.api.MenuIcon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public final class SelectModeMenu extends Menu {

    private final @NotNull SetupSessionManager setupSessionManager;

    public SelectModeMenu(@NotNull SetupSessionManager setupSessionManager) {
        super("Select a Mode", 3);
        this.setupSessionManager = setupSessionManager;

        addModeIcon(11, "Solo", 1);
        addModeIcon(13, "Doubles", 2);
        addModeIcon(15, "3v3v3v3", 3);
        addModeIcon(17, "4v4v4v4", 4);
    }

    private void addModeIcon(int slot, @NotNull String modeDisplayName, int maxInTeam) {
        addIcon(
                slot,
                new MenuIcon(
                        ItemBuilder
                                .of(Material.SLIME_BALL)
                                .displayName("&a" + modeDisplayName),
                        whoClicked -> {
                            Bukkit.dispatchCommand(
                                    whoClicked, "bw setMaxInTeam " + maxInTeam
                            );
                            whoClicked.closeInventory();
                            val isSoloDoubles = maxInTeam <= 2;
                            this.setupSessionManager.createSetupSession(
                                    whoClicked.getUniqueId(),
                                    isSoloDoubles ? ArenaSetupModeType.SOLO : ArenaSetupModeType.TEAM
                            );
                            Bukkit.dispatchCommand(
                                    whoClicked, "bw createTeam Red Red"
                            );
                            Bukkit.dispatchCommand(
                                    whoClicked, "bw createTeam Blue Blue"
                            );
                            Bukkit.dispatchCommand(
                                    whoClicked, "bw createTeam Green Green"
                            );
                            Bukkit.dispatchCommand(
                                    whoClicked, "bw createTeam Yellow Yellow"
                            );
                            if (isSoloDoubles) {
                                Bukkit.dispatchCommand(
                                        whoClicked, "bw createTeam Aqua Aqua"
                                );
                                Bukkit.dispatchCommand(
                                        whoClicked, "bw createTeam White White"
                                );
                                Bukkit.dispatchCommand(
                                        whoClicked, "bw createTeam Pink Pink"
                                );
                                Bukkit.dispatchCommand(
                                        whoClicked, "bw createTeam Gray Dark_Gray"
                                );
                            }
                        }
                )
        );
    }

}