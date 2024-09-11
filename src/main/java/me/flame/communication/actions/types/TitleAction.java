package me.flame.communication.actions.types;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.actions.Action;
import me.flame.communication.data.GroupedDataRegistry;

import me.flame.communication.data.RawDataRegistry;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Objects;

public class TitleAction implements Action {
    private final RawDataRegistry dataRegistry;

    public TitleAction(RawDataRegistry dataRegistry) {
        this.dataRegistry = dataRegistry;
    }

    @Override
    public int expectedArgs() {
        return 5;
    }

    @Override
    public void execute(@NotNull Player involvedPlayer) {
        String title = Objects.requireNonNull(dataRegistry.getRegistration("title"));
        String subTitle = Objects.requireNonNull(dataRegistry.getRegistration("subTitle"));
        Long fadeIn = Objects.requireNonNull(dataRegistry.getRegistration("fadeIn"));
        Long fadeOut = Objects.requireNonNull(dataRegistry.getRegistration("fadeOut"));
        Long stay = Objects.requireNonNull(dataRegistry.getRegistration("stay"));

        String displayName = MINI_MESSAGE.serialize(dataRegistry.getPlayer().displayName());
        title = title.replace("%player%", displayName);
        subTitle = subTitle.replace("%player%", displayName);

        involvedPlayer.showTitle(Title.title(
                MINI_MESSAGE.deserialize(title),
                MINI_MESSAGE.deserialize(subTitle),
                Title.Times.times(Duration.ofMillis(fadeIn), Duration.ofMillis(fadeOut), Duration.ofMillis(stay))
        ));
    }
}
