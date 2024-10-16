package me.flame.communication.renderers;

import io.papermc.paper.chat.ChatRenderer;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MergedChatRenderer implements ChatRenderer {
    private final ChatRenderer globalRenderer;
    private final ChatRenderer perViewerRenderer;

    public MergedChatRenderer(ChatRenderer globalRenderer, ChatRenderer perViewerRenderer) {
        this.globalRenderer = globalRenderer;
        this.perViewerRenderer = perViewerRenderer;
    }

    @NotNull
    @Override
    public Component render(@NotNull Player source, @NotNull Component displayName, @NotNull Component message, @NotNull Audience viewer) {
        return this.perViewerRenderer.render(
                source,
                displayName,
                this.globalRenderer.render(source, displayName, message, viewer),
                viewer
        );
    }
}
