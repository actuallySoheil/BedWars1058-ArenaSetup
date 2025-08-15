package me.actuallysoheil.arenasetup.menu;

import me.actuallysoheil.arenasetup.manager.SetupSessionManager.ArenaSetupModeType;
import me.actuallysoheil.arenasetup.menu.api.ItemBuilder;
import me.actuallysoheil.arenasetup.menu.api.Menu;
import me.actuallysoheil.arenasetup.menu.api.MenuIcon;
import me.actuallysoheil.arenasetup.utility.XMaterial;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public final class SelectTeamMenu extends Menu {

    public SelectTeamMenu(@NotNull ArenaSetupModeType arenaSetupModeType) {
        super(
                "Select a Team",
                arenaSetupModeType.equals(ArenaSetupModeType.TEAM) ? 3 : 5
        );

        addTeamIcon(11, XMaterial.RED_WOOL, "Red", "&cRed Team");
        addTeamIcon(13, XMaterial.BLUE_WOOL, "Blue", "&bBlue Team");
        addTeamIcon(15, XMaterial.GREEN_WOOL, "Green", "&aGreen Team");
        addTeamIcon(17, XMaterial.YELLOW_WOOL, "Yellow", "&eYellow Team");

        if (arenaSetupModeType.equals(ArenaSetupModeType.SOLO)) {
            addTeamIcon(29, XMaterial.CYAN_WOOL, "Aqua", "&3Aqua Team");
            addTeamIcon(31, XMaterial.WHITE_WOOL, "White", "&fWhite Team");
            addTeamIcon(33, XMaterial.PINK_WOOL, "Pink", "&dPink Team");
            addTeamIcon(35, XMaterial.GRAY_WOOL, "Gray", "&7Gray Team");
        }
    }

    private void addTeamIcon(int slot,
                             @NotNull XMaterial xMaterial,
                             @NotNull String teamName,
                             @NotNull String teamDisplayName) {
        addIcon(
                slot,
                new MenuIcon(
                        ItemBuilder
                                .of(xMaterial.parseItem())
                                .displayName(teamDisplayName),
                        whoClicked -> {
                            Bukkit.dispatchCommand(whoClicked, "bw setSpawn " + teamName);
                            whoClicked.closeInventory();
                        }
                )
        );
    }

}