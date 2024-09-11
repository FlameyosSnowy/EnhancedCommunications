package me.flame.communication.data;

import org.bukkit.entity.Player;

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
        return new GroupedDataRegistryImpl(player, serializedMessage, words);
    }
}
