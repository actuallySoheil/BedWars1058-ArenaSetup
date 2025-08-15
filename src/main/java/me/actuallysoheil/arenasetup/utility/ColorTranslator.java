package me.actuallysoheil.arenasetup.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class ColorTranslator {

    public @NotNull String translate(@NotNull String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}