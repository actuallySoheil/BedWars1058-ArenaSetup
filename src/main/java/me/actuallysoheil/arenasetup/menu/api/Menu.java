package me.actuallysoheil.arenasetup.menu.api;

import lombok.Getter;
import lombok.val;
import me.actuallysoheil.arenasetup.utility.ColorTranslator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.HashMap;
import java.util.Map;

public class Menu implements InventoryHolder {

    @Getter
    private final @NotNull Inventory inventory;

    private final @NotNull Map<Integer, MenuIcon> menuIcons;

    public Menu(@NotNull String title, @Range(from = 1, to = 6) int rows) {
        this.inventory = Bukkit.createInventory(
                this,
                rows * 9,
                ColorTranslator.translate("&8" + title)
        );

        this.menuIcons = new HashMap<>();
    }

    public void addIcon(@Range(from = 1, to = 54) int slot, @NotNull MenuIcon menuIcon) {
        if (this.menuIcons.containsKey(slot)) return;

        this.menuIcons.put(slot, menuIcon);
        this.inventory.setItem(slot - 1, menuIcon.itemBuilder().toItemStack());
    }

    public void open(@NotNull Player player) {
        player.openInventory(this.inventory);
    }

    protected void clickIcon(@Range(from = 1, to = 54) int slot, @NotNull Player whoClicked) {
        @Nullable val menuIcon = this.menuIcons.get(slot);
        if (menuIcon == null) return;

        menuIcon.click(whoClicked);
    }

}