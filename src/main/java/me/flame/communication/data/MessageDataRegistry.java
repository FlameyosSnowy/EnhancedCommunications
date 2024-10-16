package me.flame.communication.data;

import me.flame.communication.data.impl.GroupedDataRegistryImpl;
import me.flame.communication.data.impl.MessageDataRegistryImpl;
import me.flame.communication.data.impl.RawDataRegistryImpl;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public interface MessageDataRegistry extends RawDataRegistry {
    String[] getWords();

    default boolean isEmpty() {
        return false;
    }

    static MessageDataRegistry create(final Player player,
                                      final String serializedMessage,
                                      final String[] words) {
        return new MessageDataRegistryImpl(player, serializedMessage, words);
    }

    static RawDataRegistry createRaw(Player player,
                                     String message) {
        return new RawDataRegistryImpl(player, message);
    }

    static GroupedDataRegistry createGrouped(final Player player,
                                             final String serializedMessage,
                                             final String[] words) {
        return new GroupedDataRegistryImpl(player, serializedMessage, words, null);
    }

    static GroupedDataRegistry fromSet(final Player player,
                                       final String serializedMessage,
                                       final String[] words,
                                       final Set<Player> players) {
        return new GroupedDataRegistryImpl(player, serializedMessage, words, players);
    }
}
