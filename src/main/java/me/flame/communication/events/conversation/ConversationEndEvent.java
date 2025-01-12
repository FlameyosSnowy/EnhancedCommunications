package me.flame.communication.events.conversation;

import me.flame.communication.messages.Message;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class ConversationEndEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Message messageData;

    public ConversationEndEvent(final Message messageData) {
        this.messageData = messageData;
    }

    /**
     * Get the message data which was used in the conversation.
     *
     * @return The message data which was used in the conversation.
     */
    public Message getMessageData() {
        return messageData;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @Contract(pure = true)
    @NotNull
    public static HandlerList getHandlersList() {
        return HANDLER_LIST;
    }
}
