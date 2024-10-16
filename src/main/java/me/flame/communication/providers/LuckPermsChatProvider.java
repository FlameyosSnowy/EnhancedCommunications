package me.flame.communication.providers;

import me.flame.communication.data.DataRegistry;
import me.flame.communication.providers.data.GroupChatMetaData;
import me.flame.communication.providers.data.ChatMetaData;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.LuckPerms;

import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.flame.communication.providers.EmptyChatProvider.EMPTY;

public class LuckPermsChatProvider implements ChatProvider {
    private final LuckPerms chatProvider;

    LuckPermsChatProvider(final LuckPerms chatProvider) {
        this.chatProvider = chatProvider;
    }

    @Override
    public String getFormat(String message, @NotNull String groupFormat, Player player) {
        final CachedMetaData metaData = chatProvider.getPlayerAdapter(Player.class).getMetaData(player);
        return EMPTY.getFormat(message, groupFormat, player)
                .replace("{prefix}", metaData.getPrefix() != null ? metaData.getPrefix() : "")
                .replace("{suffix}", metaData.getSuffix() != null ? metaData.getSuffix() : "")
                .replace("{prefixes}", String.join(" ", metaData.getPrefixes().values()))
                .replace("{suffixes}", String.join(" ", metaData.getSuffixes().values()));
    }
}