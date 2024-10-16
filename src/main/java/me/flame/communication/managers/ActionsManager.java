package me.flame.communication.managers;

import me.flame.communication.actions.Action;
import me.flame.communication.actions.types.*;
import me.flame.communication.data.GroupedDataRegistry;
import me.flame.communication.data.MessageDataRegistry;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;

import static me.flame.communication.actions.Action.MINI_MESSAGE;

public interface ActionsManager {
    void createActionParser(String actionType, ActionParser action);

    void executeChatCooldownActions(Player dataRegistry);

    void executeMentionActions(GroupedDataRegistry dataRegistry);

    void executeChatCooldownActions(GroupedDataRegistry dataRegistry);

    void parseActions(@NotNull List<String> configuredActions, Player player);

    void parseActions(@NotNull List<String> configuredActions, GroupedDataRegistry dataRegistry);

    @Contract("_, _ -> new")
    @NotNull
    static SoundAction createSoundAction(@NotNull final String[] steps, final Player player) {
        return new SoundAction(Sound.valueOf(steps[1]), Float.parseFloat(steps[2]), Float.parseFloat(steps[3]));
    }

    @NotNull
    static ExecuteCommandAction createExecuteCommandAction(final String[] steps, final Player player) {
        return new ExecuteCommandAction(MessageDataRegistry.create(player, steps[1], steps));
    }

    @Contract("_, _ -> new")
    @NotNull
    static SendMessageAction createSendMessageAction(@NotNull final String[] steps, final Player player) {
        return new SendMessageAction(steps[1]);
    }

    @NotNull
    static ActionBarAction createActionBarAction(final String[] steps, final Player player) {
        return new ActionBarAction(MessageDataRegistry.create(player, steps[1], steps));
    }

    @Contract("_, _ -> new")
    @NotNull
    static TitleAction createTitleAction(@NotNull final String[] steps, final @NotNull Player player) {
        return new TitleAction(Title.title(
                MINI_MESSAGE.deserialize(steps[1].replace("%player%", MINI_MESSAGE.serialize(player.displayName()))),
                MINI_MESSAGE.deserialize(steps[2].replace("%player%", MINI_MESSAGE.serialize(player.displayName()))),
                Title.Times.times(
                        Duration.ofMillis(Long.parseLong(steps[3])),
                        Duration.ofMillis(Long.parseLong(steps[4])),
                        Duration.ofMillis(Long.parseLong(steps[5]))
                )
        ));
    }

    interface ActionParser {
        Action apply(String[] steps, Player player);
    }
}
