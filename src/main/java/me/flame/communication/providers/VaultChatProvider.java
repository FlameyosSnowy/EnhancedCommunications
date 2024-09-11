package me.flame.communication.providers;

import me.flame.communication.data.DataRegistry;
import me.flame.communication.data.GroupedDataRegistry;
import me.flame.communication.data.MessageDataRegistry;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.milkbowl.vault.chat.Chat;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VaultChatProvider implements ChatProvider {
    private final Chat chatProvider;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    VaultChatProvider(final Chat chatProvider) {
        this.chatProvider = chatProvider;
    }

    @Override
    public String getFormat(String message, String groupFormat, @NotNull Player player) {
        World world = player.getWorld();

        final String prefix = chatProvider.getPlayerPrefix(player);
        final String suffix = chatProvider.getPlayerSuffix(player);
        final CachedGroupMetaData cachedGroupMetaData = getCachedGroupMetaData(player, world, DataRegistry.empty());

        return groupFormat.replace("{prefix}", prefix != null ? prefix : "")
                .replace("{suffix}", suffix != null ? suffix : "")
                .replace("{prefixes}", String.join(" ", cachedGroupMetaData.prefixes()))
                .replace("{suffixes}", String.join(" ", cachedGroupMetaData.suffixes()))
                .replace("{world}", world.getName())
                .replace("{name}", player.getName())
                .replace("{message}", message)
                .replace("{displayname}", this.miniMessage.serialize(player.displayName()));
    }

    @Override
    public GenericCachedMetaData getMetaData(final String message, final String groupFormat, final Player player, DataRegistry dataRegistry) {
        return new GenericCachedMetaData(
                this.chatProvider.getPlayerPrefix(player),
                this.chatProvider.getPlayerSuffix(player),
                this.chatProvider.getPrimaryGroup(player),
                this.getCachedGroupMetaData(player, player.getWorld(), dataRegistry)
        );
    }

    @NotNull
    @Override
    public CachedGroupMetaData getCachedGroupMetaData(final @NotNull Player player, final World world, DataRegistry dataRegistry) {
        String[] groups = chatProvider.getPlayerGroups(player);
        List<String> prefixes = new ArrayList<>(groups.length);
        List<String> suffixes = new ArrayList<>(groups.length);

        for (String group : groups) {
            String prefix = chatProvider.getGroupPrefix(world, group);
            String suffix = chatProvider.getGroupSuffix(world, group);

            if (prefix != null && !prefix.isEmpty()) prefixes.add(prefix);
            if (suffix != null && !suffix.isEmpty()) suffixes.add(suffix);
        }

        return new CachedGroupMetaData(prefixes, suffixes);
    }
}