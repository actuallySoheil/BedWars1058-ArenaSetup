package me.actuallysoheil.arenasetup.manager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public final class SetupSessionManager {

    private final @NotNull HashMap<UUID, ArenaSetupModeType> setupModeSession;

    public SetupSessionManager() {
        this.setupModeSession = new HashMap<>();
    }

    public void createSetupSession(@NotNull UUID playerId,
                                   @NotNull ArenaSetupModeType arenaSetupModeType) {
        this.setupModeSession.put(playerId, arenaSetupModeType);
    }

    public void removeSetupSession(@NotNull UUID playerId) {
        this.setupModeSession.remove(playerId);
    }

    public boolean isInSession(@NotNull UUID playerId) {
        return this.setupModeSession.containsKey(playerId);
    }

    public @Nullable ArenaSetupModeType findSetupModeFor(@NotNull UUID playerId) {
        return this.setupModeSession.get(playerId);
    }

    public enum ArenaSetupModeType {
        SOLO, TEAM
    }

}