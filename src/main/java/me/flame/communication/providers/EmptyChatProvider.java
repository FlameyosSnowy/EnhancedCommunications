package me.flame.communication.providers;

import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.World;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class EmptyChatProvider implements ChatProvider {
    private static final GenericCachedMetaData EMPTY_META_DATA =
            new GenericCachedMetaData("", "", "", Collections.emptySet(), Collections.emptySet());

    @Override
    public String getFormat(final String message, @NotNull final String groupFormat, @NotNull final Player player) {
        World world = player.getWorld();
        MiniMessage miniMessage = MiniMessage.miniMessage();

        return groupFormat.replace("{world}", world.getName())
                          .replace("{name}", player.getName())
                          .replace("{message}", message)
                          .replace("{displayname}", miniMessage.serialize(player.displayName()));
    }

    @Override
    public GenericCachedMetaData getMetaData(final String message, final String groupFormat, final Player player) {
        return EMPTY_META_DATA;
    }
}
