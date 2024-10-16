package me.flame.communication.managers;

import me.flame.communication.messages.Message;
import panda.std.Option;

import java.util.UUID;

public interface ConversationManager {
    void sendMessage(Message message);

    Option<Message> getLastMessage(UUID recipient);

    ReplySuccess replyToLastMessage(UUID sender, String content);
}