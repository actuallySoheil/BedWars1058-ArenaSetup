package me.actuallysoheil.arenasetup.menu.api;

import lombok.val;
import me.actuallysoheil.arenasetup.utility.ColorTranslator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@CheckReturnValue
public final class ItemBuilder {

    private final @NotNull ItemStack itemStack;
    private final @NotNull ItemMeta itemMeta;

    @Contract(pure = true, value = "_ -> new")
    public static @NotNull ItemBuilder of(@NotNull ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    @Contract(pure = true, value = "_ -> new")
    public static @NotNull ItemBuilder of(@NotNull Material material) {
        return of(material, 1);
    }

    @Contract(pure = true, value = "_, _ -> new")
    public static @NotNull ItemBuilder of(@NotNull Material material,
                                          int amount) {
        return new ItemBuilder(material, amount);
    }

    public static @NotNull ItemBuilder of(@NotNull Material material,
                                          int amount,
                                          int data) {
        return new ItemBuilder(material, amount, data);
    }

    private ItemBuilder(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = Objects.requireNonNull(itemStack.getItemMeta());
    }

    @SuppressWarnings("deprecation") // Legacy support.
    private ItemBuilder(@NotNull Material material, int amount, int data) {
        this.itemStack = new ItemStack(material, amount, (short) data);
        this.itemMeta = Objects.requireNonNull(this.itemStack.getItemMeta());
    }

    private ItemBuilder(@NotNull Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = Objects.requireNonNull(this.itemStack.getItemMeta());
    }

    @Contract("_ -> this")
    public @NotNull ItemBuilder displayName(@NotNull String displayName) {
        this.itemMeta.setDisplayName(ColorTranslator.translate(displayName));
        return this;
    }

    @Contract("_ -> this")
    public @NotNull ItemBuilder lore(@NotNull String... lore) {
        val colorTranslatedLore = Arrays.stream(lore)
                .map(ColorTranslator::translate)
                .collect(Collectors.toCollection(ArrayList::new));
        this.itemMeta.setLore(colorTranslatedLore);
        return this;
    }

    @Contract("-> new")
    public @NotNull ItemStack toItemStack() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }

}