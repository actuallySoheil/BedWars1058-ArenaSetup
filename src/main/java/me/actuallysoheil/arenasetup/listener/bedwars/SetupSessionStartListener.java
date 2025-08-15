package me.actuallysoheil.arenasetup.listener.bedwars;

import com.andrei1058.bedwars.api.events.server.SetupSessionStartEvent;
import com.andrei1058.bedwars.api.server.SetupType;
import lombok.RequiredArgsConstructor;
import lombok.val;
import me.actuallysoheil.arenasetup.manager.ItemManager;
import me.actuallysoheil.arenasetup.manager.SetupSessionManager;
import me.actuallysoheil.arenasetup.utility.ColorTranslator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class SetupSessionStartListener implements Listener {

    private final @NotNull SetupSessionManager setupSessionManager;
    private final @NotNull ItemManager itemManager;

    @EventHandler
    private void onStartSession(@NotNull SetupSessionStartEvent event) {
        val setupSession = event.getSetupSession();
        val player = setupSession.getPlayer();
        if (setupSession.getSetupType().equals(SetupType.ASSISTED)) {
            player.sendMessage(ColorTranslator.translate(
                    "&cIn order to use arena setup addon, you must be in &6Advanced Mode&c!"
            ));
            return;
        }

        player.sendMessage(ColorTranslator.translate(
                "&aSetup session has been started. Use items or do /bwsetup command for more info."
        ));
        val maxInTeam = setupSession.getConfig().getInt("maxInTeam");
        this.setupSessionManager.createSetupSession(
                player.getUniqueId(),
                maxInTeam <= 2 ?
                        SetupSessionManager.ArenaSetupModeType.SOLO :
                        SetupSessionManager.ArenaSetupModeType.TEAM
        );
        this.itemManager.giveItems(player, ItemManager.ArenaSetupItemType.WAITING);
    }

}