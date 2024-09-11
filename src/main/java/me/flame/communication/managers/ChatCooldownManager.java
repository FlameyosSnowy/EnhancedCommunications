package me.flame.communication.managers;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.utils.Reloadable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatCooldownManager implements Reloadable {
    private final Map<UUID, Instant> cooldowns;
    private boolean chatCooldownsEnabled;

    public ChatCooldownManager() {
        this.cooldowns = new ConcurrentHashMap<>();
        this.chatCooldownsEnabled = EnhancedCommunication.get().getPrimaryConfig().isChatCooldownsEnabled();
    }

    public boolean isChatCooldownsEnabled() {
        return chatCooldownsEnabled;
    }

    public void insertCooldown(@NotNull Player player, Instant cooldown) {
        if (!this.chatCooldownsEnabled) return;
        cooldowns.put(player.getUniqueId(), cooldown);
    }

    public Optional<Instant> getCooldown(@NotNull Player player) {
        if (!this.chatCooldownsEnabled) return Optional.empty();
        return Optional.ofNullable(cooldowns.get(player.getUniqueId()));
    }

    public void removeCooldown(@NotNull Player player) {
        if (!this.chatCooldownsEnabled) return;
        cooldowns.remove(player.getUniqueId());
    }

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
        cooldowns.clear();
        this.chatCooldownsEnabled = EnhancedCommunication.get().getPrimaryConfig().isChatCooldownsEnabled();
    }
}
