package me.flame.communication.managers;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.actions.Action;
import me.flame.communication.actions.ActionsRegistry;
import me.flame.communication.actions.types.*;
import me.flame.communication.data.DataRegistry;
import me.flame.communication.data.GroupedDataRegistry;
import me.flame.communication.data.MessageDataRegistry;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ActionsManager {
    private final Map<String, ActionParser> actionsRegistry = new HashMap<>();

    public ActionsManager() {
        this.createActionParser("SOUND", ActionsManager::createSoundAction);
        this.createActionParser("TITLE", ActionsManager::createTitleAction);
        this.createActionParser("ACTION_BAR", ActionsManager::createActionBarAction);
        this.createActionParser("SEND_MESSAGE", ActionsManager::createSendMessageAction);
        this.createActionParser("EXECUTE_COMMAND", ActionsManager::createExecuteCommandAction);
    }

    public void createActionParser(String actionType, ActionParser action) {
        this.actionsRegistry.put(actionType, action);
    }

    public void createCensorActions(Player player) {
        this.parseActions(EnhancedCommunication.get()
                .getChatFilterConfig()
                .getCensorActions(), player);
    }

    public void createFilterActions(Player player) {
        this.parseActions(EnhancedCommunication.get()
                .getChatFilterConfig()
                .getFilterActions(), player);
    }

    public void createChatCooldownActions(Player dataRegistry) {
        this.parseActions(EnhancedCommunication.get()
                .getPrimaryConfig()
                .getChatCooldownsActions(), dataRegistry);
    }

    public void createMentionActions(GroupedDataRegistry dataRegistry) {
        this.parseActions(EnhancedCommunication.get()
                .getPrimaryConfig()
                .getMentionActions(), dataRegistry);
    }

    public void createCensorActions(GroupedDataRegistry dataRegistry) {
        this.parseActions(EnhancedCommunication.get()
                .getChatFilterConfig()
                .getCensorActions(), dataRegistry);
    }

    public void createFilterActions(GroupedDataRegistry dataRegistry) {
        this.parseActions(EnhancedCommunication.get()
                .getChatFilterConfig()
                .getFilterActions(), dataRegistry);
    }

    public void createChatCooldownActions(GroupedDataRegistry dataRegistry) {
        this.parseActions(EnhancedCommunication.get()
                .getPrimaryConfig()
                .getChatCooldownsActions(), dataRegistry);
    }

    public void parseActions(@NotNull List<String> configuredActions, Player player) {
        for (String action : configuredActions) {
            String[] steps = action.split(":");

            Optional.ofNullable(actionsRegistry.get(steps[0]))
                    .map((converter) -> converter.apply(steps, player))
                    .orElseThrow(() -> new IllegalArgumentException(
                            "User input an invalid action in the configuration files: " + steps[0]
                    ))
                    .execute(player);
        }
    }

    public void parseActions(@NotNull List<String> configuredActions, GroupedDataRegistry dataRegistry) {
        for (String action : configuredActions) {
            String[] steps = action.split(":");

            Action processedAction = Optional.ofNullable(actionsRegistry.get(steps[0]))
                    .map((converter) -> converter.apply(steps, dataRegistry.getPlayer()))
                    .orElseThrow(() -> new IllegalArgumentException(
                            "User input an invalid action in the configuration files: " + steps[0]
                    ));

            for (Player player : dataRegistry) {
                processedAction.execute(player);
            }
        }
    }

    @NotNull
    private static SoundAction createSoundAction(final String[] steps, final Player player) {
        DataRegistry dataRegistry = DataRegistry.create();
        dataRegistry.addRegistration("volume", Float.valueOf(steps[2]));
        dataRegistry.addRegistration("pitch", Float.valueOf(steps[3]));
        return new SoundAction(dataRegistry);
    }

    @NotNull
    private static ExecuteCommandAction createExecuteCommandAction(final String[] steps, final Player player) {

        return new ExecuteCommandAction(MessageDataRegistry.create(player, steps[1], steps));
    }

    @NotNull
    private static SendMessageAction createSendMessageAction(final String[] steps, final Player player) {
        return new SendMessageAction(MessageDataRegistry.create(player, steps[1], steps));
    }

    @NotNull
    private static ActionBarAction createActionBarAction(final String[] steps, final Player player) {
        return new ActionBarAction(MessageDataRegistry.create(player, steps[1], steps));
    }

    @NotNull
    private static TitleAction createTitleAction(final String[] steps, final Player player) {
        MessageDataRegistry dataRegistry = MessageDataRegistry.create(player, steps[1], steps);
        dataRegistry.addRegistration("title", steps[0]);
        dataRegistry.addRegistration("subtitle", steps[1]);
        dataRegistry.addRegistration("fadeIn", Long.valueOf(steps[2]));
        dataRegistry.addRegistration("fadeOut", Long.valueOf(steps[3]));
        dataRegistry.addRegistration("stay", Long.valueOf(steps[4]));
        return new TitleAction(dataRegistry);
    }

    public void reload() {
    }

    public interface ActionParser {
        Action apply(String[] steps, Player player);
    }
}
