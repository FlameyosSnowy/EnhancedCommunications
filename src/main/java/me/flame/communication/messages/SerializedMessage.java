package me.flame.communication.messages;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("unused")
public class SerializedMessage {
    private final Player sender;
    private String message;
    private String rawFormat;
    private String serializedFormat;

    public static final SerializedMessage EMPTY = new SerializedMessage(null, "null", "null", "null");

    public SerializedMessage(Player player, String message, String rawFormat, String serializedFormat) {
        this.sender = player;
        this.message = message;
        this.rawFormat = rawFormat;
        this.serializedFormat = serializedFormat;
    }

    private static String checkPlayerString(final Player player) {
        return player == null ? "null" : player.toString();
    }

    public boolean isEmpty() {
        return this.message == null && this.rawFormat == null && this.serializedFormat == null;
    }

    public String getMessage() {
        return message;
    }

    public String getRawFormat() {
        return rawFormat;
    }

    public String getSerializedFormat() {
        return serializedFormat;
    }

    public void setMessage(String message) {
        Objects.requireNonNull(message, "Message cannot be null");
        this.message = message;
    }

    public void setRawFormat(String rawFormat) {
        Objects.requireNonNull(rawFormat, "Raw format cannot be null");
        this.rawFormat = rawFormat;
    }

    public void setSerializedFormat(String serializedFormat) {
        Objects.requireNonNull(serializedFormat, "Serialized format cannot be null");
        this.serializedFormat = serializedFormat;
    }

    @NotNull
    @Contract(value = " -> new", pure = true)
    public static SerializedMessage.Builder builder() {
        return new SerializedMessage.Builder();
    }

    public Player getSender() {
        return sender;
    }

    public static class Builder {
        private Player sender;
        private String message;
        private String rawFormat;
        private String serializedFormat;

        Builder() {}

        public Builder sender(Player sender) {
            this.sender = sender;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder rawFormat(String rawFormat) {
            this.rawFormat = rawFormat;
            return this;
        }

        public Builder serializedFormat(String serializedFormat) {
            this.serializedFormat = serializedFormat;
            return this;
        }

        public SerializedMessage build() {
            Objects.requireNonNull(sender, "Sender cannot be null");
            Objects.requireNonNull(message, "Message cannot be null");
            Objects.requireNonNull(rawFormat, "Raw format cannot be null");
            Objects.requireNonNull(serializedFormat, "Serialized format cannot be null");
            return new SerializedMessage(sender, message, rawFormat, serializedFormat);
        }
    }
}
