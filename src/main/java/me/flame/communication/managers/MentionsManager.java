package me.flame.communication.managers;

import me.flame.communication.data.GroupedDataRegistry;
import org.bukkit.entity.Player;
import panda.std.Option;

public interface MentionsManager {
    /**
     * Processes the given message and looks for any player mentions in it.
     * If any are found, the message is modified to include the player's display name instead of their username.
     * The modified message is then returned within a {@link GroupedDataRegistry}.
     *
     * @param player the player the message is from
     * @param message the message to look for mentions in
     * @return an {@link Option} containing the modified message and players, or none if no modifications were made
     */
    Option<GroupedDataRegistry> changeMentionsLook(final Player player, final String message);
}
