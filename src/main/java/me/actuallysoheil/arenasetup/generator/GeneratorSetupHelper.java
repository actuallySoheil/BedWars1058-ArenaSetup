package me.actuallysoheil.arenasetup.generator;

import lombok.RequiredArgsConstructor;
import lombok.val;
import me.actuallysoheil.arenasetup.ArenaSetupPlugin;
import me.actuallysoheil.arenasetup.generator.task.DiamondGeneratorSetterTask;
import me.actuallysoheil.arenasetup.generator.task.EmeraldGeneratorSetterTask;
import me.actuallysoheil.arenasetup.utility.ColorTranslator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@RequiredArgsConstructor
public final class GeneratorSetupHelper {

    private final @NotNull Player player;

    public void setup() {
        this.player.sendMessage(ColorTranslator.translate(
                "&8Finding generators..."
        ));
        val diamondGeneratorLocations = new ArrayList<Location>();
        val emeraldGeneratorLocations = new ArrayList<Location>();

        val playerWorld = this.player.getWorld();
        for (val chunk : playerWorld.getLoadedChunks())
            for (var x = 0; x < 16; x++)
                for (var y = 0; y < 256; y++)
                    for (var z = 0; z < 16; z++) {
                        val block = chunk.getBlock(x, y, z);
                        if (!isPotentialGenerator(block)) continue;
                        val blockLocation = block.getLocation()
                                .clone().add(0, 1, 0);
                        if (block.getType().equals(Material.DIAMOND_BLOCK))
                            diamondGeneratorLocations.add(blockLocation);
                        else
                            emeraldGeneratorLocations.add(blockLocation);
                    }

        if (diamondGeneratorLocations.isEmpty() && emeraldGeneratorLocations.isEmpty()) {
            this.player.sendMessage(ColorTranslator.translate(
                    "&cNo generators were found!"
            ));
            return;
        }

        this.player.sendMessage(ColorTranslator.translate(
                "&c&lNOTE: &cThis is a beta feature. " +
                        "If you encounter to any issues, please report it."
        ));

        new DiamondGeneratorSetterTask(
                diamondGeneratorLocations,
                this.player,
                player -> new EmeraldGeneratorSetterTask(
                        emeraldGeneratorLocations,
                        player
                ).runTaskTimer(ArenaSetupPlugin.instance(), 0L, 10L)
        ).runTaskTimer(ArenaSetupPlugin.instance(), 0L, 10L);
    }

    private boolean isPotentialGenerator(@NotNull Block block) {
        val blockType = block.getType();
        if (blockType != Material.DIAMOND_BLOCK
                && blockType != Material.EMERALD_BLOCK) return false;
        if (block.getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) return false;

        for (int i = 1; i <= 3; i++)
            if (block.getRelative(BlockFace.UP, i).getType() != Material.AIR)
                return false;

        val up = block.getRelative(BlockFace.UP, 1);
        val north = up.getRelative(BlockFace.NORTH);
        val south = up.getRelative(BlockFace.SOUTH);
        val east = up.getRelative(BlockFace.EAST);
        val west = up.getRelative(BlockFace.WEST);

        return isStair(north) && isStair(south) && isStair(east) && isStair(west);
    }

    private boolean isStair(@NotNull Block block) {
        return block.getType().toString().contains("STAIRS");
    }

}