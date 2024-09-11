package me.flame.communication.managers;

import java.util.*;

import me.flame.communication.channels.Channel;
import org.bukkit.entity.Player;
import panda.std.Option;

public class PlayerChannelManager {
    private final Map<UUID, Set<Channel>> playerSubscriptions = new HashMap<>();

    public void subscribe(Player player, Channel channel) {
        playerSubscriptions.computeIfAbsent(player, k -> new HashSet<>()).add(channel);
    }

    public void unsubscribe(Player player, Channel channel) {
        Set<Channel> channels = playerSubscriptions.get(player);
        if (channels != null) {
            channels.remove(channel);
        }
    }

    public boolean isSubscribed(Player player, Channel channel) {
        Set<Channel> channels = playerSubscriptions.get(player);
        return channels != null && channels.contains(channel);
    }

    public void sendMessage(Player player, Channel channel, String message) {
        if (channel.canWrite(player)) {
            channel.sendMessage(player, message);
        }
    }

    public Option<Channel> getChannel(final Player player) {
        return null;
    }
}
