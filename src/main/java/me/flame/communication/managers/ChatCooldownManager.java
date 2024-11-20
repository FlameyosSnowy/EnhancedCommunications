package me.flame.communication.managers;

import me.flame.communication.utils.Reloadable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Optional;

public interface ChatCooldownManager extends Reloadable {
    /**
     * Checks if chat cooldowns are enabled.
     *
     * @return true if chat cooldowns are enabled, false otherwise.
     */
    boolean isChatCooldownsEnabled();

    /**
     * Inserts or updates the cooldown for the specified player.
     *
     * <p>This method sets the cooldown time for a player, which determines
     * how long the player must wait before sending another chat message.
     *
     * @param player the player for whom the cooldown is being set
     * @param cooldown the time until which the player's cooldown is valid
     */
    void insertCooldown(@NotNull Player player, Instant cooldown);

    /**
     * Retrieves the cooldown expiration time for the specified player.
     *
     * <p>This method provides the time until which the player must wait before
     * sending another chat message. If no cooldown is set for the player or if
     * chat cooldowns are disabled, an empty Optional is returned.
     *
     * @param player the player whose cooldown time is being retrieved
     * @return an Optional containing the cooldown expiration time if set, otherwise empty
     */
    Optional<Instant> getCooldown(@NotNull Player player);

    /**
     * Calculates the remaining time for a player's cooldown.
     *
     * <p>This method provides the remaining time in seconds until a player's
     * cooldown is over. If no cooldown is set for the player or if chat
     * cooldowns are disabled, zero is returned.
     *
     * @param player the player whose cooldown time is being retrieved
     * @return the remaining cooldown time in seconds, or zero if no cooldown is set
     */
    Double getCooldownRemainder(@NotNull Player player);

    /**
     * Removes the cooldown for the specified player.
     *
     * <p>This method removes the cooldown associated with the specified player,
     * allowing them to send chat messages again without restriction.
     *
     * @param player the player whose cooldown is being removed
     */
    void removeCooldown(@NotNull Player player);

    /**
     * Determines if the specified player has a cooldown.
     *
     * <p>This method checks if a cooldown is set for the specified player.
     * If a cooldown is set, the method returns true. Otherwise, false is returned.
     *
     * @param player the player whose cooldown status is being checked
     * @return true if the player has a cooldown, false otherwise
     */
    boolean hasCooldown(final Player player);
}
