package me.actuallysoheil.arenasetup.generator.task;

import lombok.RequiredArgsConstructor;
import lombok.val;
import me.actuallysoheil.arenasetup.utility.ColorTranslator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public final class DiamondGeneratorSetterTask extends BukkitRunnable {

    private final @NotNull List<Location> diamondGeneratorLocations;
    private final @NotNull Player player;
    private final @NotNull Consumer<Player> onFinish;

    private int index;

    @Override
    public void run() {
        if (this.index == this.diamondGeneratorLocations.size() - 1) {
            this.player.sendMessage(ColorTranslator.translate(
                    "&aDiamond generator setup task has been finished."
            ));
            this.onFinish.accept(this.player);
            cancel();
            return;
        }

        val diamondGeneratorLocation = this.diamondGeneratorLocations.get(this.index);
        if (diamondGeneratorLocation == null) return;

        this.player.teleport(diamondGeneratorLocation.clone().add(0.5, 0, 0.5));
        Bukkit.dispatchCommand(this.player, "bw addGenerator Diamond");

        this.index++;
    }

}