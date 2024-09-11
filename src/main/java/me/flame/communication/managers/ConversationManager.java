package me.flame.communication.managers;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.events.ConversationEndEvent;
import me.flame.communication.events.ConversationReplyEvent;
import me.flame.communication.events.ConversationStartEvent;
import me.flame.communication.messages.Message;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.util.*;

public class ConversationManager {
    private final Map<UUID, Message> lastMessageMap = new HashMap<>();
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public void sendMessage(Message message) {
        if (!lastMessageMap.containsKey(message.recipient())) {
            Bukkit.getPluginManager().callEvent(new ConversationStartEvent(message));
        }
        lastMessageMap.put(message.recipient(), message);

        Player sender = Bukkit.getPlayer(message.sender());
        Player recipient = Bukkit.getPlayer(message.recipient());
        Objects.requireNonNull(sender);
        Objects.requireNonNull(recipient);

        sender.sendMessage(this.miniMessage.deserialize(
                EnhancedCommunication.get().getPrimaryConfig()
                        .getMessagingSenderFormat()
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
    }

    public Option<Message> getLastMessage(UUID recipient) {
        return Option.of(lastMessageMap.get(recipient));
    }

    public ReplySuccess replyToLastMessage(UUID sender, String content) {
        Message lastMessage = this.lastMessageMap.get(sender);
        if (lastMessage == null) return ReplySuccess.NOT_MESSAGED;

        Message messageData = new Message(sender, lastMessage.sender(), content);
        this.sendMessage(messageData);
        Bukkit.getPluginManager().callEvent(new ConversationReplyEvent(messageData));
        return ReplySuccess.FOUND;
    }
}