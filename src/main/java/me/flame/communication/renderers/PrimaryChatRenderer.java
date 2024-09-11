package me.flame.communication.renderers;

import io.papermc.paper.chat.ChatRenderer;
import me.flame.communication.EnhancedCommunication;
import me.flame.communication.channels.Channel;
import me.flame.communication.managers.ChatManager;
import me.flame.communication.managers.PlayerChannelManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import panda.std.Option;

public class PrimaryChatRenderer implements ChatRenderer {
    private final PlayerChannelManager channelManager;
    private final ChatManager chatManager;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public PrimaryChatRenderer() {
        this.chatManager = EnhancedCommunication.get().getChatManager();
        this.channelManager = this.chatManager.getChannelManager();
    }

    @Override
    public @NotNull Component render(@NotNull Player player, @NotNull Component displayName, @NotNull Component message, @NotNull Audience viewer) {
        Option<Channel> optionalChannel = channelManager.getChannel(player);
        Component channelFormattedMessage = optionalChannel
                .filter(channel -> channel.canReceive(player))
                .map((channel) -> miniMessage.deserialize(this.chatManager.getChatProvider().getFormat(miniMessage.serialize(message), channel.getFormat(), player)))
                .orNull();


        if (optionalChannel.isPresent()) {
            Channel channel = optionalChannel.get();

            if (channel.canReceive(player)) {
                String formattedMessage = MiniMessage.miniMessage().serialize(channel.getFormat())
                        .replace("{channel_prefix}", channel.getChannelPrefix())
                        .replace("{prefix}", player.hasPermission(channel.getSpeakPermissions()) ? "[VIP] " : "")
                        .replace("{player_displayname}", displayName.toString())
                        .replace("{message}", message.toString());

                return MiniMessage.miniMessage().deserialize(formattedMessage);
            }
        }

        // Fallback if no channel or the player cannot receive messages
        return Component.text("[Default] " + player.getName() + ": " + message);
    }
}
