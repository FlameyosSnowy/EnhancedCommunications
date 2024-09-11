package me.flame.communication.modifier;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.actions.ActionsRegistry;
import me.flame.communication.data.GroupedDataRegistry;
import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.managers.ActionsManager;
import me.flame.communication.managers.ChatManager;
import me.flame.communication.managers.MentionsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import panda.std.Option;

import java.util.Objects;

public class MentionInsertionMessageModifier implements MessageModifier {
    private final ChatManager chatManager = EnhancedCommunication.get().getChatManager();
    private final MentionsManager mentionsManager = this.chatManager.getMentionsManager();
    private final ActionsManager actionsManager = this.chatManager.getActionsManager();

    @Override
    public EventPriority getPriority() {
        return EventPriority.NORMAL;
    }

    @Override
    public String modify(final RawDataRegistry dataRegistry) {
        String message = dataRegistry.getMessage();
        Option<GroupedDataRegistry> mentions = this.mentionsManager.getMentions(
                dataRegistry.getPlayer(),
                dataRegistry.getMessage()
        );

        if (mentions.isEmpty()) return message;

        GroupedDataRegistry verifiedData = mentions.orNull();
        Objects.requireNonNull(verifiedData);

        this.actionsManager.createMentionActions(verifiedData);
        return this.mentionsManager.editMessage(verifiedData);
    }
}
