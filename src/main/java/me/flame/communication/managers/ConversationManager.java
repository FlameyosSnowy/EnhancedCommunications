package me.flame.communication.managers;

import me.flame.communication.messages.Message;

import java.util.Optional;
import java.util.UUID;

public interface ConversationManager {
    /**
     * Sends a message to a player.
     * <p>
     * This should be considered the entry point for any outgoing message.
     * <p>
     * The event {@link me.flame.communication.events.conversation.ConversationStartEvent}
     * is thrown if the recipient is not already in a conversation.
     *
     * @param message the message
     */
    ReplySuccess sendMessage(Message message);

    /**
     * Returns the last message a player has sent or received.
     * <p>
     * The returned option is empty if the player has not sent or received any message.
     *
     * @param recipient the player to get the last message for
     * @return the last message
     */
    Optional<Message> getLastMessage(UUID recipient);

    /**
     * Replies to the last message the sender has sent or received.
     * <p>
     * If the sender has not sent or received any message, the returned
     * {@link ReplySuccess} is {@link ReplySuccess#NOT_MESSAGED}.
     *
     * @param sender the sender of the reply
     * @param content the reply content
     * @return the reply success
     */
    ReplySuccess replyToLastMessage(UUID sender, String content);
}
