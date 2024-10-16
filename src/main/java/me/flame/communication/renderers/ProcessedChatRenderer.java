package me.flame.communication.renderers;

import io.papermc.paper.chat.ChatRenderer;
import me.flame.communication.data.RawDataRegistry;

@FunctionalInterface
public interface ProcessedChatRenderer {
    ChatRenderer createChatRenderer(RawDataRegistry rawDataRegistry);
}
