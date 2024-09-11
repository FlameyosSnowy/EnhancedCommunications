package me.flame.communication.managers;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.data.GroupedDataRegistry;

import me.flame.communication.data.MessageDataRegistry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import panda.std.Option;

public class MentionsManager {
    public Option<GroupedDataRegistry> getMentions(final Player player, final String serializedMessage) {
        if (!EnhancedCommunication.get().getPrimaryConfig().isMentionsEnabled()) return Option.none();

        String[] words = serializedMessage.split(" ");
        GroupedDataRegistry dataRegistry = MessageDataRegistry.createGrouped(player, serializedMessage, words);

        for (String word : words) {
            Player mention = Bukkit.getPlayer(word);
            if (mention != null) dataRegistry.add(mention);
        }

        return Option.of(dataRegistry);
    }

    public String editMessage(@NotNull final GroupedDataRegistry group) {
        String[] words = group.getWords();
        String message = group.getMessage();


        return message;
    }
}
