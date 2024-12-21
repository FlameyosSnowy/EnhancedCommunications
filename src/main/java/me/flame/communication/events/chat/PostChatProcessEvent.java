package me.flame.communication.events.chat;

import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.messages.SerializedMessage;
import org.bukkit.entity.Player;

import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PostChatProcessEvent extends PlayerEvent {
    private final String message;
    private final SerializedMessage dataRegistry;

    private static final HandlerList HANDLERS = new HandlerList();

    public PostChatProcessEvent(Player player, String message, SerializedMessage dataRegistry) {
        super(player);
        this.message = message;
        this.dataRegistry = dataRegistry;
    }

    /**
     * Get the original message which was sent by the player.
     *
     * @return the original message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the {@link RawDataRegistry} which contains all the data associated with the processed message.
     *
     * @return the raw data registry
     */
    public SerializedMessage getData() {
        return dataRegistry;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Gets the {@link HandlerList} for this event.
     *
     * @return the handler list
     */
    @SuppressWarnings("unused")
    @Contract(pure = true)
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
