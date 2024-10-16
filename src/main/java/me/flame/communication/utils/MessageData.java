package me.flame.communication.utils;

import me.flame.communication.EnhancedCommunication;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record MessageData(String content, String route) {
    @NotNull
    @Contract("_ -> new")
    public static MessageDataBuilder builder(String route) {
        return new MessageDataBuilder(EnhancedCommunication.get().getMessagesConfig().get(route), route);
    }

    public static class MessageDataBuilder {
        private String content;
        private final String route;

        MessageDataBuilder(String content, String route) {
            this.content = content;
            this.route = route;
        }

        public MessageDataBuilder replace(CharSequence sequence, CharSequence otherSequence) {
            this.content = this.content.replace(sequence, otherSequence);
            return this;
        }

        public MessageData build() {
            return new MessageData(content, route);
        }
    }
}
