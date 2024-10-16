package me.flame.communication.providers;

import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.World;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public class EmptyChatProvider implements ChatProvider {
    static final EmptyChatProvider EMPTY = new EmptyChatProvider();

    @Override
    public String getFormat(final String message, @NotNull final String groupFormat, @NotNull final Player player) {
        World world = player.getWorld();
        MiniMessage miniMessage = MiniMessage.miniMessage();

        return groupFormat.replace("{world}", world.getName())
                          .replace("{name}", player.getName())
                          .replace("{message}", message)
                          .replace("{displayname}", miniMessage.serialize(player.displayName()));
    }
}
