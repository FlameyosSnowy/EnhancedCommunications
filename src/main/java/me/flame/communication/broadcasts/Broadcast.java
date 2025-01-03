package me.flame.communication.broadcasts;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import me.flame.communication.managers.AutoBroadcastManager;
import me.flame.communication.utils.StringUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

import java.util.List;
import java.util.function.Predicate;

public interface Broadcast {
    MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private static BroadcastViewers getViewers(@NotNull final Section broadcast) {
        String worlds = broadcast.getString("in-world");
        if (Objects.requireNonNull(worlds, "Worlds should not be null").equalsIgnoreCase("global")) {
            return BroadcastViewers.global();
        }

        String[] processedWorlds = worlds.split(":");
        if (processedWorlds.length == 1) {
            return BroadcastViewers.create(Bukkit.getWorld(worlds));
        }

        return BroadcastViewers.create(Arrays.stream(processedWorlds).map(Bukkit::getWorld).toList());
    }

    /**
     * Creates a new broadcast with the given section name.
     *
     * @param broadcastSection the name of the section.
     * @return a new broadcast instance.
     * @throws IllegalArgumentException if the section does not exist.
     */
    @NotNull
    @Contract("_, _ -> new")
    static Broadcast create(@NotNull AutoBroadcastManager broadcastManager, @NotNull String broadcastSection) {
        final Section broadcast = broadcastManager.getSection(broadcastSection);

        if (broadcast == null) {
            throw new IllegalArgumentException("Unknown section named: " + broadcastSection);
        }

        List<Component> lines = broadcast.getStringList("lore").stream()
                .filter(Predicate.not(String::isEmpty))
                .map((lore) -> MINI_MESSAGE.deserialize(centerLoreIfNeeded(lore)))
                .toList();

        Component lore = Component.join(JoinConfiguration.newlines(), lines);

        BroadcastViewers viewers = Broadcast.getViewers(broadcast);

        String uniqueId = Objects.requireNonNull(broadcast.getNameAsString());

        long interval = broadcast.getLong("interval");
        return new BroadcastImpl(lore, uniqueId, viewers, interval);
    }

    @NotNull
    private static String centerLoreIfNeeded(@NotNull final String lore) {
        if (!lore.startsWith("<center>")) return lore;
        return StringUtils.center(lore.replace("<center>", ""));
    }

    /**
     * Gets the unique identifier of the broadcast.
     *
     * @return the unique identifier.
     */
    String getUniqueId();

    /**
     * Retrieves the lore associated with this broadcast.
     *
     * @return a list of components representing the lore.
     */
    Component getLore();

    /**
     * Gets the viewers of the broadcast, which may be either a list of worlds (if the broadcast is global)
     * or a list of players (if the broadcast is private).
     *
     * @return the viewers of the broadcast.
     */
    BroadcastViewers getViewers();

    /**
     * Gets the interval at which the broadcast is repeated.
     *
     * @return the interval in milliseconds.
     */
    long getInterval();
}
