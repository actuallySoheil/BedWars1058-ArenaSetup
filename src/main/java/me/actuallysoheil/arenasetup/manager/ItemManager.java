package me.actuallysoheil.arenasetup.manager;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import me.actuallysoheil.arenasetup.generator.GeneratorSetupHelper;
import me.actuallysoheil.arenasetup.menu.SelectModeMenu;
import me.actuallysoheil.arenasetup.menu.SelectTeamMenu;
import me.actuallysoheil.arenasetup.menu.api.ItemBuilder;
import me.actuallysoheil.arenasetup.utility.ColorTranslator;
import me.actuallysoheil.arenasetup.utility.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Accessors(fluent = true)
public final class ItemManager {

    @Getter
    private final @NotNull List<CustomItem> customItems;

    private final @NotNull CustomItem waitingSpawnItem;
    private final @NotNull CustomItem spectatorSpawnItem;
    private final @NotNull CustomItem createTeamsItem;
    private final @NotNull CustomItem waitingLocationOneItem;
    private final @NotNull CustomItem waitingLocationTwoItem;

    private final @NotNull CustomItem teamSpawnItem;
    private final @NotNull CustomItem teamGeneratorItem;
    private final @NotNull CustomItem teamKillDropsItem;
    private final @NotNull CustomItem teamShopItem;
    private final @NotNull CustomItem teamUpgradeItem;

    private final @NotNull CustomItem diamondGeneratorItem;
    private final @NotNull CustomItem emeraldGeneratorItem;
    private final @NotNull CustomItem autoSetGeneratorsItem;

    private final @NotNull CustomItem goToSpawnSetupItem;
    private final @NotNull CustomItem goToGeneratorSetupItem;
    private final @NotNull CustomItem saveArenaItem;

    public ItemManager(@NotNull SetupSessionManager setupSessionManager) {
        this.customItems = new ArrayList<>();

        this.waitingSpawnItem = new CustomItem(
                ItemBuilder
                        .of(Material.BEACON)
                        .displayName("&aSet Waiting Spawn")
                        .toItemStack(),
                whoInteracted -> Bukkit.dispatchCommand(
                        whoInteracted, "bw setWaitingSpawn"
                )
        );
        this.spectatorSpawnItem = new CustomItem(
                ItemBuilder
                        .of(Material.SEA_LANTERN)
                        .displayName("&aSet Spectator Spawn")
                        .toItemStack(),
                whoInteracted -> Bukkit.dispatchCommand(
                        whoInteracted, "bw setSpectSpawn"
                )
        );
        this.createTeamsItem = new CustomItem(
                ItemBuilder
                        .of(Material.BOOKSHELF)
                        .displayName("&aCreate Teams")
                        .toItemStack(),
                whoInteracted -> new SelectModeMenu(setupSessionManager)
                        .open(whoInteracted)
        );
        this.waitingLocationOneItem = new CustomItem(
                ItemBuilder
                        .of(Material.STICK)
                        .displayName("&aSet Waiting Location One")
                        .toItemStack(),
                whoInteracted -> Bukkit.dispatchCommand(
                        whoInteracted, "bw waitingPos 1"
                )
        );
        this.waitingLocationTwoItem = new CustomItem(
                ItemBuilder
                        .of(Material.STICK)
                        .displayName("&aSet Waiting Location Two")
                        .toItemStack(),
                whoInteracted -> Bukkit.dispatchCommand(
                        whoInteracted, "bw waitingPos 2"
                )
        );

        this.teamSpawnItem = new CustomItem(
                ItemBuilder
                        .of(Material.BEACON)
                        .displayName("&aSet Team Spawn")
                        .toItemStack(),
                whoInteracted -> new SelectTeamMenu(
                        Objects.requireNonNullElse(
                                setupSessionManager.findSetupModeFor(whoInteracted.getUniqueId()),
                                SetupSessionManager.ArenaSetupModeType.SOLO
                        )
                ).open(whoInteracted)
        );
        this.teamGeneratorItem = new CustomItem(
                ItemBuilder
                        .of(Material.GOLD_BLOCK)
                        .displayName("&aSet Team Generator")
                        .toItemStack(),
                whoInteracted -> {
                    Bukkit.dispatchCommand(whoInteracted, "bw addGenerator Iron");
                    Bukkit.dispatchCommand(whoInteracted, "bw addGenerator Gold");
                }
        );
        this.teamKillDropsItem = new CustomItem(
                ItemBuilder
                        .of(XMaterial.END_PORTAL_FRAME.parseMaterial())
                        .displayName("&aSet Team Kill Drops")
                        .toItemStack(),
                whoInteracted -> Bukkit.dispatchCommand(
                        whoInteracted, "bw setKillDrops"
                )
        );
        this.teamShopItem = new CustomItem(
                ItemBuilder
                        .of(Material.EMERALD)
                        .displayName("&aSet Team Item Shop")
                        .toItemStack(),
                whoInteracted -> Bukkit.dispatchCommand(
                        whoInteracted, "bw setShop"
                )
        );
        this.teamUpgradeItem = new CustomItem(
                ItemBuilder
                        .of(XMaterial.ENCHANTING_TABLE.parseMaterial())
                        .displayName("&aSet Team Upgrade")
                        .toItemStack(),
                whoInteracted -> Bukkit.dispatchCommand(
                        whoInteracted, "bw setUpgrade"
                )
        );

        this.diamondGeneratorItem = new CustomItem(
                ItemBuilder
                        .of(Material.DIAMOND_BLOCK)
                        .displayName("&bSet Diamond Generator")
                        .toItemStack(),
                whoInteracted -> Bukkit.dispatchCommand(
                        whoInteracted, "bw addGenerator Diamond"
                )
        );
        this.emeraldGeneratorItem = new CustomItem(
                ItemBuilder
                        .of(Material.EMERALD_BLOCK)
                        .displayName("&aSet Emerald Generator")
                        .toItemStack(),
                whoInteracted -> Bukkit.dispatchCommand(
                        whoInteracted, "bw addGenerator Emerald"
                )
        );
        this.autoSetGeneratorsItem = new CustomItem(
                ItemBuilder
                        .of(Material.PAPER)
                        .displayName("&aAuto Setup Generators &e&lBETA!")
                        .toItemStack(),
                whoInteracted -> new GeneratorSetupHelper(whoInteracted).setup()
        );

        this.goToSpawnSetupItem = new CustomItem(
                ItemBuilder
                        .of(XMaterial.GREEN_TERRACOTTA.parseItem())
                        .displayName("&aGo to Spawn Setup")
                        .toItemStack(),
                whoInteracted -> giveItems(whoInteracted, ArenaSetupItemType.TEAM)
        );
        this.goToGeneratorSetupItem = new CustomItem(
                ItemBuilder
                        .of(XMaterial.GREEN_TERRACOTTA.parseItem())
                        .displayName("&aGo to Generator Setup")
                        .toItemStack(),
                whoInteracted -> giveItems(whoInteracted, ArenaSetupItemType.GENERATOR)
        );
        this.saveArenaItem = new CustomItem(
                ItemBuilder
                        .of(XMaterial.GREEN_TERRACOTTA.parseItem())
                        .displayName("&aSave Arena")
                        .toItemStack(),
                whoInteracted -> Bukkit.dispatchCommand(
                        whoInteracted, "bw save"
                )
        );

        this.customItems.add(this.waitingSpawnItem);
        this.customItems.add(this.spectatorSpawnItem);
        this.customItems.add(this.createTeamsItem);
        this.customItems.add(this.waitingLocationOneItem);
        this.customItems.add(this.waitingLocationTwoItem);

        this.customItems.add(this.teamSpawnItem);
        this.customItems.add(this.teamGeneratorItem);
        this.customItems.add(this.teamKillDropsItem);
        this.customItems.add(this.teamShopItem);
        this.customItems.add(this.teamUpgradeItem);

        this.customItems.add(this.diamondGeneratorItem);
        this.customItems.add(this.emeraldGeneratorItem);
        this.customItems.add(this.autoSetGeneratorsItem);

        this.customItems.add(this.goToSpawnSetupItem);
        this.customItems.add(this.goToGeneratorSetupItem);
        this.customItems.add(this.saveArenaItem);
    }

    public void giveItems(@NotNull Player player, @NotNull ArenaSetupItemType arenaSetupItemType) {
        val playerInventory = player.getInventory();
        playerInventory.clear();
        switch (arenaSetupItemType) {
            case WAITING -> {
                playerInventory.setItem(1, this.waitingSpawnItem.itemStack());
                playerInventory.setItem(2, this.spectatorSpawnItem.itemStack());
                playerInventory.setItem(4, this.createTeamsItem.itemStack());
                playerInventory.setItem(6, this.waitingLocationOneItem.itemStack());
                playerInventory.setItem(7, this.waitingLocationTwoItem.itemStack());
                playerInventory.setItem(8, this.goToSpawnSetupItem.itemStack());
            }
            case TEAM -> {
                player.sendMessage(ColorTranslator.translate(
                        "&aUse &eSet Team Spawn&a item to setup a different team."
                ));
                playerInventory.setItem(0, this.teamSpawnItem.itemStack());
                playerInventory.setItem(1, this.teamGeneratorItem.itemStack());
                playerInventory.setItem(2, this.teamKillDropsItem.itemStack());
                playerInventory.setItem(6, this.teamShopItem.itemStack());
                playerInventory.setItem(7, this.teamUpgradeItem.itemStack());
                playerInventory.setItem(8, this.goToGeneratorSetupItem.itemStack());
            }
            case GENERATOR -> {
                playerInventory.setItem(1, this.diamondGeneratorItem.itemStack());
                playerInventory.setItem(2, this.emeraldGeneratorItem.itemStack());
                playerInventory.setItem(4, this.autoSetGeneratorsItem.itemStack());
                playerInventory.setItem(7, this.saveArenaItem.itemStack());
            }
        }
    }

    public record CustomItem(@NotNull ItemStack itemStack,
                             @NotNull Consumer<Player> playerClickConsumer) {
    }

    public enum ArenaSetupItemType {
        WAITING, TEAM, GENERATOR
    }

}