package me.flame.communication.data;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MessageDataRegistryImpl extends RawDataRegistryImpl implements MessageDataRegistry, AutoCloseable {
    private final String[] words;

    protected MessageDataRegistryImpl(final Player player,
                                      final String message,
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
    }
}
