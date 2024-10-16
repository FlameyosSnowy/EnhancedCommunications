package me.flame.communication.managers;

import me.flame.communication.data.GroupedDataRegistry;
import org.bukkit.entity.Player;
import panda.std.Option;

public interface MentionsManager {
    Option<GroupedDataRegistry> changeMentionsLook(final Player player, final String message);
}
