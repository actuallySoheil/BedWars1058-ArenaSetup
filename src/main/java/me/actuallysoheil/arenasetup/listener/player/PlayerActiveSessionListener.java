package me.actuallysoheil.arenasetup.listener.player;

import lombok.RequiredArgsConstructor;
import lombok.val;
import me.actuallysoheil.arenasetup.manager.ItemManager;
import me.actuallysoheil.arenasetup.manager.SetupSessionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public final class PlayerActiveSessionListener implements Listener {

    private final @NotNull SetupSessionManager setupSessionManager;
    private final @NotNull ItemManager itemManager;

    @EventHandler
    public void onPlayerInteract(@NotNull PlayerInteractEvent event) {
        @Nullable val itemStack = event.getItem();
        if (itemStack == null) return;

        val player = event.getPlayer();
        if (!this.setupSessionManager.isInSession(player.getUniqueId())) return;
        this.itemManager.customItems().stream()
                .filter(customItem -> customItem.itemStack().isSimilar(itemStack))
                .findFirst()
                .ifPresent(customItem -> {
                    event.setCancelled(true);
                    customItem.playerClickConsumer().accept(player);
                });
    }

    @EventHandler
    public void onPlayerDropItem(@NotNull PlayerDropItemEvent event) {
        if (!this.setupSessionManager.isInSession(event.getPlayer().getUniqueId())) return;
        this.itemManager.customItems().stream()
                .map(ItemManager.CustomItem::itemStack)
                .filter(itemStack -> itemStack.isSimilar(event.getItemDrop().getItemStack()))
                .findFirst()
                .ifPresent(itemStack -> event.setCancelled(true));
    }

    @EventHandler
    public void onBlockPlace(@NotNull BlockPlaceEvent event) {
        if (!this.setupSessionManager.isInSession(event.getPlayer().getUniqueId())) return;
        this.itemManager.customItems().stream()
                .map(ItemManager.CustomItem::itemStack)
                .filter(itemStack -> itemStack.isSimilar(event.getItemInHand()))
                .findFirst()
                .ifPresent(itemStack -> event.setCancelled(true));
    }

    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        this.setupSessionManager.removeSetupSession(event.getPlayer().getUniqueId());
    }

}