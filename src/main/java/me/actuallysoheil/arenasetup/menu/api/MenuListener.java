package me.actuallysoheil.arenasetup.menu.api;

import lombok.val;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public final class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        val clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) return;
        if (!(clickedInventory.getHolder() instanceof Menu menu)) return;

        event.setCancelled(true);
        menu.clickIcon(event.getSlot() + 1, ((Player) event.getWhoClicked()));
    }

}