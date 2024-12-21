package me.flame.communication.providers;

import me.flame.communication.messages.SerializedMessage;
import me.flame.communication.providers.data.GroupChatMetaData;
import me.flame.communication.providers.data.ChatMetaData;
import net.milkbowl.vault.chat.Chat;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static me.flame.communication.actions.Action.MINI_MESSAGE;

public class VaultChatProvider implements ChatProvider {
    private final Chat chatProvider;

    VaultChatProvider(final Chat chatProvider) {
        this.chatProvider = chatProvider;
    }

    @Override
    public SerializedMessage getFormat(SerializedMessage message, @NotNull String groupFormat, @NotNull Player player) {
        World world = player.getWorld();
        final ChatMetaData metaData = getMetaData(player, world);

        message.setRawFormat(groupFormat);
        message.setSerializedFormat(groupFormat
                .replace("{world}", world.getName())
                .replace("{name}", player.getName())
                .replace("{message}", message.getMessage())
                .replace("{displayname}", MINI_MESSAGE.serialize(player.displayName()))
                .replace("{prefix}", metaData.prefix() != null ? metaData.prefix() : "")
                .replace("{suffix}", metaData.suffix() != null ? metaData.suffix() : "")
                .replace("{prefixes}", metaData.group().prefixes())
                .replace("{suffixes}", metaData.group().suffixes()));
        return message;
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