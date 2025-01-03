package me.flame.communication.data;

import me.flame.communication.data.impl.GroupedDataRegistryImpl;
import me.flame.communication.data.impl.MessageDataRegistryImpl;
import me.flame.communication.data.impl.RawDataRegistryImpl;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@SuppressWarnings("unused")
public interface MessageDataRegistry<D> extends RawDataRegistry<D> {
    /**
     * Retrieves the array of words associated with this message data registry.
     *
     * @return an array of words
     */
    String[] getWords();

    /**
     * Creates a message data registry from a player, serialized message and words.
     *
     * @param player the player associated with the message
     * @param serializedMessage the serialized message
     * @param words the words of the message
     * @return the message data registry
     */
    static <D> MessageDataRegistry<D> create(final Player player,
                                      final D serializedMessage,
                                      final String[] words) {
        return new MessageDataRegistryImpl<>(player, serializedMessage, words);
    }

    /**
     * Creates a raw data registry from the given player and message.
     * <p>
     * This method creates a raw data registry from the given player and message. The raw data
     * registry will not contain any words or grouped data registry.
     * <p>
     *
     * @param player the player associated with the message
     * @param message the message associated with the player
     * @return the created raw data registry
     */
    static <D> RawDataRegistry<D> createRaw(Player player,
                                     D message) {
        return new RawDataRegistryImpl<>(player, message);
    }

    /**
     * Creates a grouped data registry from a single player.
     * <p>
     * This method creates a grouped data registry from a single player. The grouped data registry
     * will contain only the given player.
     * <p>
     * This method is useful when you want to create a grouped data registry from a single player.
     * <p>
     * This method is equivalent to calling
     * {@link #fromSet(Player, D, String[], Set)} with a set containing only the given player.
     * <p>
     * This method is more efficient than calling
     * {@link #fromSet(Player, D, String[], Set)} with a set containing only the given player.
     *
     * @param player the player associated with the message
     * @param serializedMessage the serialized message
     * @param words the words of the message
     * @return the grouped data registry
     */
    @NotNull
    @Contract("_, _, _ -> new")
    static <D> GroupedDataRegistry<D> createGrouped(final Player player,
                                             final D serializedMessage,
                                             final String[] words) {
        return new GroupedDataRegistryImpl<>(player, serializedMessage, words, null);
    }

    /**
     * Creates a grouped data registry from a set of players.
     * <p>
     * This method creates a grouped data registry from a set of players. The set of players provided
     * must be a set of unique players.
     * <p>
     * This method is useful when you want to create a grouped data registry from an existing set of
     * players.
     * <p>
     * This method is equivalent to calling
     * {@link #createGrouped(Player, D, String[])} and then calling
     * {@link GroupedDataRegistry#add(Player)} for each player in the set.
     * <p>
     * This method is more efficient than calling
     * {@link #createGrouped(Player, D, String[])} and then calling
     * {@link GroupedDataRegistry#add(Player)} for each player in the set.
     *
     * @param player the player associated with the message
     * @param serializedMessage the serialized message
     * @param words the words of the message
     * @param players the set of players to add to the grouped data registry
     * @return the grouped data registry
     */
    @NotNull
    @Contract("_, _, _, _ -> new")
    static <D> GroupedDataRegistry<D> fromSet(final Player player,
                                       final D serializedMessage,
                                       final String[] words,
                                       final Set<Player> players) {
        return new GroupedDataRegistryImpl<>(player, serializedMessage, words, players);
    }
}