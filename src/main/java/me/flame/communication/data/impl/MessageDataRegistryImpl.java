package me.flame.communication.data.impl;

import me.flame.communication.data.MessageDataRegistry;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MessageDataRegistryImpl<D> extends RawDataRegistryImpl<D> implements MessageDataRegistry<D>, AutoCloseable {
    private final String[] words;

    public MessageDataRegistryImpl(final Player player,
                                   final D message,
                                   final String[] words) {
        super(player, message);
        this.words = words;
    }

    public String[] getWords() {
        return words;
    }

    @Override
    public void close() throws Exception {
        Arrays.fill(words, null);
        this.setData(null);
    }
}