package me.actuallysoheil.arenasetup.menu.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@Accessors(fluent = true)
@AllArgsConstructor
@RequiredArgsConstructor
public final class MenuIcon {

    @Getter
    private final @NotNull ItemBuilder itemBuilder;
    private @Nullable Consumer<Player> playerClickConsumer;

    public void click(@NotNull Player whoClicked) {
        if (this.playerClickConsumer == null) return;
        this.playerClickConsumer.accept(whoClicked);
    }

}