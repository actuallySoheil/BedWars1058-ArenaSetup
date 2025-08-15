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

@RequiredArgsConstructor
public final class EmeraldGeneratorSetterTask extends BukkitRunnable {

    private final @NotNull List<Location> emeraldGeneratorLocations;
    private final @NotNull Player player;

    private int index;

    @Override
    public void run() {
        if (this.index == this.emeraldGeneratorLocations.size() - 1) {
            this.player.sendMessage(ColorTranslator.translate(
                    "&aEmerald generator setup task has been finished."
            ));
            cancel();
            return;
        }

        val emeraldGeneratorLocation = this.emeraldGeneratorLocations.get(this.index);
        if (emeraldGeneratorLocation == null) return;

        this.player.teleport(emeraldGeneratorLocation.clone().add(0.5, 0, 0.5));
        Bukkit.dispatchCommand(this.player, "bw addGenerator Emerald");

        this.index++;
    }

}