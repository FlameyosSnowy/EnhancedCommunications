package me.flame.communication.managers;

import me.flame.communication.utils.Reloadable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Optional;

public interface ChatCooldownManager extends Reloadable {
    boolean isChatCooldownsEnabled();

    void insertCooldown(@NotNull Player player, Instant cooldown);

    Optional<Instant> getCooldown(@NotNull Player player);

    Double getCooldownRemainder(@NotNull Player player);

    void removeCooldown(@NotNull Player player);

    boolean hasCooldown(final Player player);
}
