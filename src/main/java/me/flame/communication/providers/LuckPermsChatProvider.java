package me.flame.communication.providers;

import me.flame.communication.messages.SerializedMessage;
import net.luckperms.api.LuckPerms;

import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.flame.communication.actions.Action.MINI_MESSAGE;

public class LuckPermsChatProvider implements ChatProvider {
    private final LuckPerms chatProvider;

    LuckPermsChatProvider(final LuckPerms chatProvider) {
        this.chatProvider = chatProvider;
    }

    @Override
    public SerializedMessage getFormat(SerializedMessage message, @NotNull String groupFormat, Player player) {
        World world = player.getWorld();
        final CachedMetaData metaData = chatProvider.getPlayerAdapter(Player.class).getMetaData(player);
        message.setRawFormat(groupFormat);
        message.setSerializedFormat(groupFormat
                .replace("{world}", world.getName())
                .replace("{name}", player.getName())
                .replace("{message}", message.getMessage())
                .replace("{displayname}", MINI_MESSAGE.serialize(player.displayName()))
                .replace("{prefix}", metaData.getPrefix() != null ? metaData.getPrefix() : "")
                .replace("{suffix}", metaData.getSuffix() != null ? metaData.getSuffix() : "")
                .replace("{prefixes}", String.join(" ", metaData.getPrefixes().values()))
                .replace("{suffixes}", String.join(" ", metaData.getSuffixes().values())));
        return message;
    }
}