package me.flame.communication.events.conversation;

import me.flame.communication.messages.Message;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class ConversationReplyEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean cancelled = false;

    private final Message messageData;

    public ConversationReplyEvent(final Message messageData) {
        this.messageData = messageData;
    }

    /**
     * Get the message data which is being replied to.
     *
     * @return The message data which is being replied to.
     */
    public Message getMessageData() {
        return messageData;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
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