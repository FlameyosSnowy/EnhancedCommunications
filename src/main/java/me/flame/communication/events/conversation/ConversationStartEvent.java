package me.flame.communication.events.conversation;

import me.flame.communication.messages.Message;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class ConversationStartEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Message messageData;

    public ConversationStartEvent(final Message messageData) {
        this.messageData = messageData;
    }

    /**
     * Get the {@link Message} data of this event.
     *
     * @return Message data
     */
    public Message getMessageData() {
        return messageData;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @NotNull
    public static HandlerList getHandlersList() {
        return HANDLER_LIST;
    }
}
