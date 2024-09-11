package me.flame.communication.providers;

import me.flame.communication.data.DataRegistry;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.LuckPerms;

import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LuckPermsChatProvider implements ChatProvider {
    private final LuckPerms chatProvider;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    LuckPermsChatProvider(final LuckPerms chatProvider) {
        this.chatProvider = chatProvider;
    }

    @Override
    public String getFormat(String message, String groupFormat, Player player) {
        final DataRegistry dataRegistry = DataRegistry.create();
        final GenericCachedMetaData metaData = this.getMetaData(message, groupFormat, player, dataRegistry);
        return groupFormat.replace("{prefix}", metaData.prefix() != null ? metaData.prefix() : "")
                .replace("{suffix}", metaData.suffix() != null ? metaData.suffix() : "")
                .replace("{prefixes}", String.join(" ", metaData.groupMetaData().prefixes()))
                .replace("{suffixes}", String.join(" ", metaData.groupMetaData().suffixes()))
                .replace("{world}", player.getWorld().getName())
                .replace("{name}", player.getName())
                .replace("{message}", message)
                .replace("{displayname}", this.miniMessage.serialize(player.displayName()));
    }

    @Override
    public GenericCachedMetaData getMetaData(final String message, final String groupFormat, final Player player, DataRegistry dataRegistry) {
        final CachedMetaData metaData = chatProvider.getPlayerAdapter(Player.class).getMetaData(player);
        dataRegistry.addRegistration("metaData", metaData);

        return new GenericCachedMetaData(
                metaData.getPrefix(),
                metaData.getSuffix(),
                metaData.getPrimaryGroup(),
                this.getCachedGroupMetaData(player, player.getWorld(), dataRegistry)
        );
    }

    @NotNull
    @Override
    public CachedGroupMetaData getCachedGroupMetaData(@NotNull final Player player, final World world, DataRegistry dataRegistry) {
        CachedMetaData cachedMetaData = dataRegistry.getRegistration("metaData");
        return new CachedGroupMetaData(
                cachedMetaData.getPrefixes().values(),
                cachedMetaData.getSuffixes().values()
        );
    }
}