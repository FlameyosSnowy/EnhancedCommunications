package me.flame.communication.commands;

import dev.velix.imperat.BukkitSource;
import dev.velix.imperat.annotations.*;
import me.flame.communication.EnhancedCommunication;
import me.flame.communication.events.conversation.ConversationEndEvent;
import me.flame.communication.managers.ConversationManager;
import me.flame.communication.messages.Message;
import me.flame.communication.settings.PrimarySettings;
import me.flame.communication.utils.MessageData;
import me.flame.communication.utils.Messages;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

@Command(value = { "msg", "message" })
public class MessageCommand {
    @Dependency
    private ConversationManager conversationManager;

    @Dependency
    private PrimarySettings settings;

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Usage
    public void onMessageCommand(@NotNull BukkitSource sender,
                                 @Named("target") @NotNull Player target,
                                 @Named("message") @Greedy String message) {
        UUID uniqueId = sender.asPlayer().getUniqueId();
        this.conversationManager.getLastMessage(uniqueId)
                .filter((messageData) -> this.settings.isCountingMessageOtherAsConversationEnd())
                .peek((oldMessageData) -> {
                    Player recipient = Objects.requireNonNull(Bukkit.getPlayer(oldMessageData.recipient()));
                    Messages.sendServer(sender.asPlayer(), MessageData.builder("server.recipient-discussion-ended")
                            .replace("%recipient%", this.miniMessage.serialize(recipient.displayName()))
                            .build());
                    Bukkit.getPluginManager().callEvent(new ConversationEndEvent(oldMessageData));
                });


        Message messageData = new Message(uniqueId, target.getUniqueId(), message);
        conversationManager.sendMessage(messageData);
    }
}