package me.flame.communication.renderers;

import io.papermc.paper.chat.ChatRenderer;

import me.flame.communication.messages.SerializedMessage;

@FunctionalInterface
public interface ProcessedChatRenderer {
    /**
     * Creates a new ChatRenderer instance from the given RawDataRegistry.
     *
     * @param rawDataRegistry The RawDataRegistry to create a ChatRenderer from.
     * @return A new ChatRenderer.
     */
    ChatRenderer createChatRenderer(SerializedMessage rawDataRegistry);
}
