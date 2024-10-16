package me.flame.communication.providers;

import me.flame.communication.providers.data.GroupChatMetaData;
import me.flame.communication.providers.data.ChatMetaData;
import net.milkbowl.vault.chat.Chat;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static me.flame.communication.providers.EmptyChatProvider.EMPTY;

public class VaultChatProvider implements ChatProvider {
    private final Chat chatProvider;

    VaultChatProvider(final Chat chatProvider) {
        this.chatProvider = chatProvider;
    }

    @Override
    public String getFormat(String message, @NotNull String groupFormat, @NotNull Player player) {
        final ChatMetaData metaData = getMetaData(player, player.getWorld());
        return EMPTY.getFormat(message, groupFormat, player)
                .replace("{prefix}", metaData.prefix() != null ? metaData.prefix() : "")
                .replace("{suffix}", metaData.suffix() != null ? metaData.suffix() : "")
                .replace("{prefixes}", metaData.group().prefixes())
                .replace("{suffixes}", metaData.group().suffixes());
    }

    @Contract("_, _ -> new")
    @NotNull
    private ChatMetaData getMetaData(final Player player, final World world) {
        return new ChatMetaData(
                this.chatProvider.getPlayerPrefix(player),
                this.chatProvider.getPlayerSuffix(player),
                this.chatProvider.getPrimaryGroup(player),
                this.getCachedGroupMetaData(player, world)
        );
    }

    @NotNull
    private GroupChatMetaData getCachedGroupMetaData(final @NotNull Player player, final World world) {
        String[] groups = chatProvider.getPlayerGroups(player);
        StringBuilder prefixes = new StringBuilder(groups.length << 2);
        StringBuilder suffixes = new StringBuilder(groups.length << 2);

        for (String group : groups) {
            String prefix = chatProvider.getGroupPrefix(world, group);
            if (prefix != null && !prefix.isEmpty()) {
                prefixes.append(prefix);
                prefixes.append(' ');
                continue;
            }

            String suffix = chatProvider.getGroupSuffix(world, group);
            if (suffix != null && !suffix.isEmpty()) {
                suffixes.append(suffix);
                suffixes.append(' ');
            }
        }

        return new GroupChatMetaData(prefixes.toString(), suffixes.toString());
    }
}