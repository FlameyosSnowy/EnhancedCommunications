package me.flame.communication.managers.impl;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.managers.ChatCooldownManager;
import me.flame.communication.utils.Reloadable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatCooldownManagerImpl implements Reloadable, ChatCooldownManager {
    private final Map<UUID, Instant> cooldowns;
    private boolean chatCooldownsEnabled;

    public ChatCooldownManagerImpl() {
        this.cooldowns = new ConcurrentHashMap<>();
        this.chatCooldownsEnabled = EnhancedCommunication.get().getPrimaryConfig().isChatCooldownsEnabled();
    }

    @Override
    public boolean isChatCooldownsEnabled() {
        return chatCooldownsEnabled;
    }

    @Override
    public void insertCooldown(@NotNull Player player, Instant cooldown) {
        if (!this.chatCooldownsEnabled) return;
        cooldowns.put(player.getUniqueId(), cooldown);
    }

    @Override
    public Optional<Instant> getCooldown(@NotNull Player player) {
        if (!this.chatCooldownsEnabled) return Optional.empty();
        return Optional.ofNullable(cooldowns.get(player.getUniqueId()));
    }

    @Override
    public Double getCooldownRemainder(@NotNull final Player player) {
        return this.getCooldown(player)
                .map((cooldown) -> {
                    long millis = Duration.between(Instant.now(), cooldown).toMillis();
                    if (millis <= 0) return 0.0;
                    return millis / 1000.0;
                })
                .orElse(0.0);
    }

    @Override
    public void removeCooldown(@NotNull Player player) {
        if (!this.chatCooldownsEnabled) return;
        cooldowns.remove(player.getUniqueId());
    }

    @Override
    public boolean hasCooldown(final Player player) {
        if (!this.chatCooldownsEnabled) return false;

        UUID uniqueId = player.getUniqueId();
        Instant duration = cooldowns.get(uniqueId);

        if (duration == null) return false;
        if (Instant.now().isAfter(duration)) {
            cooldowns.remove(uniqueId);
            return false;
        }

        return true;
    }

    @Override
    public void reload() {
        this.cooldowns.clear();
        this.chatCooldownsEnabled = EnhancedCommunication.get().getPrimaryConfig().isChatCooldownsEnabled();
    }
}
