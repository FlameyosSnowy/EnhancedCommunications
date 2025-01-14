package me.flame.communication.managers.impl;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.events.conversation.ConversationReplyEvent;
import me.flame.communication.events.conversation.ConversationStartEvent;
import me.flame.communication.managers.ConversationManager;
import me.flame.communication.managers.ReplySuccess;
import me.flame.communication.messages.Message;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ConversationManagerImpl implements ConversationManager {
    private final Map<UUID, Message> lastMessageMap = new HashMap<>();
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public ReplySuccess sendMessage(@NotNull Message message) {
        boolean cancelled = this.executeEventsAndCheckCancelled(message);
        if (cancelled) return ReplySuccess.CANCELLED;

        lastMessageMap.put(message.recipient(), message);

        Player sender = Bukkit.getPlayer(message.sender());
        Player recipient = Bukkit.getPlayer(message.recipient());
        Objects.requireNonNull(sender);
        Objects.requireNonNull(recipient);

        sender.sendMessage(this.miniMessage.deserialize(
                EnhancedCommunication.get().getPrimaryConfig().getMessagingSenderFormat()
                        .replace("%sender%", this.miniMessage.serialize(sender.displayName()))
                        .replace("%recipient%", this.miniMessage.serialize(recipient.displayName()))
                        .replace("%message%", message.content())
        ));

        recipient.sendMessage(this.miniMessage.deserialize(
                EnhancedCommunication.get().getPrimaryConfig().getMessagingRecipientFormat()
                        .replace("%sender%", this.miniMessage.serialize(sender.displayName()))
                        .replace("%recipient%", this.miniMessage.serialize(recipient.displayName()))
                        .replace("%message%", message.content())
        ));
        return ReplySuccess.FOUND;
    }

    private boolean executeEventsAndCheckCancelled(final @NotNull Message message) {
        if (!lastMessageMap.containsKey(message.recipient())) {
            return callEvent(new ConversationStartEvent(message));
        } else {
            return callEvent(new ConversationReplyEvent(message));
        }
    }

    private static <T extends Event & Cancellable> boolean callEvent(final @NotNull T event) {
        Bukkit.getPluginManager().callEvent(event);
        return event.isCancelled();
    }

    @Override
    public Optional<Message> getLastMessage(UUID recipient) {
        return Optional.ofNullable(lastMessageMap.get(recipient));
    }


    @Override
    public ReplySuccess replyToLastMessage(UUID sender, String content) {
        Message lastMessage = this.lastMessageMap.get(sender);
        if (lastMessage == null) return ReplySuccess.NOT_MESSAGED;

        Message messageData = new Message(sender, lastMessage.sender(), content);
        this.sendMessage(messageData);
        return ReplySuccess.FOUND;
    }
}
