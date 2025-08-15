package me.actuallysoheil.arenasetup.listener.bedwars;

import com.andrei1058.bedwars.api.events.server.SetupSessionCloseEvent;
import lombok.RequiredArgsConstructor;
import lombok.val;
import me.actuallysoheil.arenasetup.manager.SetupSessionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class SetupSessionCloseListener implements Listener {

    private final @NotNull SetupSessionManager setupSessionManager;

    @EventHandler
    private void onPlayerCloseSession(@NotNull SetupSessionCloseEvent event) {
        val player = event.getSetupSession().getPlayer();
        player.getInventory().clear();
        this.setupSessionManager.removeSetupSession(player.getUniqueId());
    }

}