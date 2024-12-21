package me.flame.communication.renderers;

import io.papermc.paper.chat.ChatRenderer;

import me.flame.communication.messages.SerializedMessage;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record DefaultChatRenderer(SerializedMessage dataRegistry) implements ChatRenderer {
    @Override
    public @NotNull Component render(@NotNull final Player source, @NotNull final Component sourceDisplayName, @NotNull final Component message, @NotNull final Audience viewer) {
        return MiniMessage.miniMessage().deserialize(dataRegistry.getSerializedFormat());
    }
}
