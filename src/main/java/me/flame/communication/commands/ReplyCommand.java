package me.flame.communication.commands;

import dev.velix.imperat.BukkitSource;
import dev.velix.imperat.annotations.*;

import me.flame.communication.managers.ConversationManager;
import me.flame.communication.managers.ReplySuccess;
import me.flame.communication.settings.MessagesSettings;

import net.kyori.adventure.text.minimessage.MiniMessage;

import org.jetbrains.annotations.NotNull;

@Command(value = { "reply", "r" })
@Permission("enchancedcommunications.user.reply")
@Description("Reply to someone you were talking to!")
public class ReplyCommand {
    @Dependency
    private ConversationManager conversationManager;

    @Dependency
    private MessagesSettings settings;

    @Usage
    public void onReplyCommand(@NotNull BukkitSource sender,
                               @Named("message") @Greedy String message) {
        ReplySuccess success = this.conversationManager.replyToLastMessage(sender.asPlayer().getUniqueId(), message);
        if (success == ReplySuccess.FOUND) return;

        this.settings.send(sender.asPlayer(), "server.no-messages-to-reply-to");
    }

    @Usage
    public void onDefaultUsage(@NotNull BukkitSource sender) {
        sender.reply(MiniMessage.miniMessage().deserialize("<red>/reply <player> <messsage>"));
    }
}
