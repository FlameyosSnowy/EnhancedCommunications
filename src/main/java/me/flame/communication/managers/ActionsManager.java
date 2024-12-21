package me.flame.communication.managers;

import me.flame.communication.actions.Action;
import me.flame.communication.actions.types.*;

import me.flame.communication.data.GroupedDataRegistry;
import me.flame.communication.data.MessageDataRegistry;
import me.flame.communication.messages.SerializedMessage;
import me.flame.communication.utils.SoundData;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;

import static me.flame.communication.actions.Action.MINI_MESSAGE;

public interface ActionsManager {
    /**
     * Registers a new action parser for the specified action type.
     *
     * @param actionType the type of action to be parsed
     * @param action the action parser implementation to handle parsing for the specified action type
     */
    void createActionParser(String actionType, ActionParser action);

    /**
     * Executes the actions registered for the chat cooldown event,
     * with the given {@link Player} as the data registry.
     *
     * @param dataRegistry the data registry to be used for the actions
     */
    void executeChatCooldownActions(Player dataRegistry);

    /**
     * Executes the actions registered for the mention event,
     * with the given {@link GroupedDataRegistry} as the data registry.
     *
     * @param dataRegistry the data registry to be used for the actions
     */
    void executeMentionActions(GroupedDataRegistry<SerializedMessage> dataRegistry);

    /**
     * Parses the given list of configured actions with the given {@link Player}
     * as the data registry.
     *
     * @param configuredActions the list of configured actions to be parsed
     * @param player the data registry to be used for the actions
     */
    void parseActions(@NotNull List<String> configuredActions, Player player);

    /**
     * Parses the given list of configured actions with the specified {@link GroupedDataRegistry}
     * as the data registry.
     *
     * @param configuredActions the list of configured actions to be parsed
     * @param dataRegistry the data registry to be used for the actions
     */
    void parseActions(@NotNull List<String> configuredActions, GroupedDataRegistry<?> dataRegistry);

    /**
     * Creates a new {@link SoundAction} from the given {@link String}
     * array of steps and the given {@link Player}.
     * <p>
     * The first element of the {@code steps} array should be the name
     * of a {@link Sound}, and the second and third elements of the
     * array should be the volume and pitch of the sound, respectively.
     * <p>
     * This method will throw an {@link IllegalArgumentException} if
     * the given array does not have length 3, or if the first element
     * of the array is not a valid {@link Sound}.
     *
     * @param steps the steps of the action
     * @param ignoredPlayer the data registry to be used for the action
     * @return the created action
     */
    @Contract("_, _ -> new")
    @NotNull
    static SoundAction createSoundAction(@NotNull final String[] steps, final Player ignoredPlayer) {
        return new SoundAction(MessageDataRegistry.create(ignoredPlayer, new SoundData(Sound.valueOf(steps[1]), Float.parseFloat(steps[2]), Float.parseFloat(steps[3])), steps));
    }

    /**
     * Creates a new {@link ExecuteCommandAction} from the given {@link String}
     * array of steps and the given {@link Player}.
     * <p>
     * The first element of the {@code steps} array should be the name
     * of the action, and the second element should be the command
     * to be executed.
     * <p>
     * This method will throw an {@link IllegalArgumentException} if
     * the given array does not have length 2, or if the first element
     * of the array is not "EXECUTE_COMMAND".
     *
     * @param steps the steps of the action
     * @param player the data registry to be used for the action
     * @return the created action
     */
    @NotNull
    static ExecuteCommandAction createExecuteCommandAction(final String[] steps, final Player player) {
        return new ExecuteCommandAction(MessageDataRegistry.create(player, steps[1], steps));
    }

    /**
     * Creates a new {@link SendMessageAction} from the given {@link String}
     * array of steps and the given {@link Player}.
     * <p>
     * The first element of the {@code steps} array should be the name
     * of the action, and the second element should be the message
     * to be sent.
     * <p>
     * This method will throw an {@link IllegalArgumentException} if
     * the given array does not have length 2, or if the first element
     * of the array is not "SEND_MESSAGE".
     *
     * @param steps the steps of the action
     * @param ignoredPlayer the data registry to be used for the action
     * @return the created action
     */
    @Contract("_, _ -> new")
    @NotNull
    static SendMessageAction createSendMessageAction(@NotNull final String[] steps, final Player ignoredPlayer) {
        return new SendMessageAction(MessageDataRegistry.create(ignoredPlayer, MINI_MESSAGE.deserialize(steps[1]), steps));
    }

    /**
     * Creates a new {@link TitleAction} from the given {@link String}
     * array of steps and the given {@link Player}.
     * <p>
     * The first element of the {@code steps} array should be the name
     * of the action, the second element should be the title,
     * the third element should be the subtitle, the fourth element
     * should be the fade-in time, the fifth element should be the
     * stay time, and the sixth element should be the fade-out time.
     * <p>
     * This method will throw an {@link IllegalArgumentException} if
     * the given array does not have length 6, or if the first element
     * of the array is not "TITLE".
     *
     * @param steps the steps of the action
     * @param player the data registry to be used for the action
     * @return the created action
     */
    @Contract("_, _ -> new")
    @NotNull
    static TitleAction createTitleAction(@NotNull final String[] steps, final @NotNull Player player) {
        String displayName = MINI_MESSAGE.serialize(player.displayName());
        MessageDataRegistry<Title> registry = MessageDataRegistry.create(player, Title.title(
                MINI_MESSAGE.deserialize(steps[1].replace("%player%", displayName)),
                MINI_MESSAGE.deserialize(steps[2].replace("%player%", displayName)),
                Title.Times.times(
                        Duration.ofMillis(Long.parseLong(steps[3])),
                        Duration.ofMillis(Long.parseLong(steps[4])),
                        Duration.ofMillis(Long.parseLong(steps[5]))
                )
        ), steps);
        return new TitleAction(registry);
    }

    @NotNull
    static ActionBarAction createActionBarAction(final String[] steps, final Player player) {
        return new ActionBarAction(MessageDataRegistry.create(player, steps[1], steps));
    }

    interface ActionParser {
        Action apply(String[] steps, Player player);
    }
}
