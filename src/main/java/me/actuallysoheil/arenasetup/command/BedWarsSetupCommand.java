package me.actuallysoheil.arenasetup.command;

import lombok.RequiredArgsConstructor;
import lombok.val;
import me.actuallysoheil.arenasetup.ArenaSetupPlugin;
import me.actuallysoheil.arenasetup.manager.ItemManager;
import me.actuallysoheil.arenasetup.utility.ColorTranslator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public final class BedWarsSetupCommand implements TabExecutor {

    private final @NotNull ItemManager itemManager;

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] arguments) {
        if (!(sender instanceof Player player)) return false;

        if (!player.hasPermission("bwsetup.admin")) {
            player.sendMessage(
                    ColorTranslator.translate("&cYou don't have permission to use this command!")
            );
            return true;
        }

        if (!ArenaSetupPlugin.bedwarsAPI().isInSetupSession(player.getUniqueId())) {
            player.sendMessage(
                    ColorTranslator.translate("&cYou are not in setup session!")
            );
            return true;
        }

        if (arguments.length == 0) {
            player.sendMessage(
                    ColorTranslator.translate("&cUsage: /bwsetup <waiting|team|generator>")
            );
            return true;
        }

        val arenaSetupItemType = switch (arguments[0].toLowerCase()) {
            case "team" -> ItemManager.ArenaSetupItemType.TEAM;
            case "generator" -> ItemManager.ArenaSetupItemType.GENERATOR;
            default -> ItemManager.ArenaSetupItemType.WAITING;
        };

        this.itemManager.giveItems(player, arenaSetupItemType);

        return false;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender,
                                               @NotNull Command command,
                                               @NotNull String alias,
                                               @NotNull String[] args) {
        if (args.length != 1 || !sender.hasPermission("bwsetup.admin"))
            return Collections.emptyList();
        return List.of("waiting", "team", "generator");
    }

}