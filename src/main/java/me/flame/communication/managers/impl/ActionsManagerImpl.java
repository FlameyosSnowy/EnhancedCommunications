package me.flame.communication.managers.impl;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.actions.Action;
import me.flame.communication.actions.types.*;
import me.flame.communication.data.DataRegistry;
import me.flame.communication.data.GroupedDataRegistry;
import me.flame.communication.data.MessageDataRegistry;
import me.flame.communication.managers.ActionsManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import panda.std.Pair;

import java.util.*;

public class ActionsManagerImpl implements ActionsManager {
    private final Map<String, ActionsManager.ActionParser> actionsRegistry = new HashMap<>();

    public ActionsManagerImpl() {
        this.createActionParser("SOUND", ActionsManager::createSoundAction);
        this.createActionParser("TITLE", ActionsManager::createTitleAction);
        this.createActionParser("ACTION_BAR", ActionsManager::createActionBarAction);
        this.createActionParser("SEND_MESSAGE", ActionsManager::createSendMessageAction);
        this.createActionParser("EXECUTE_COMMAND", ActionsManager::createExecuteCommandAction);
    }

    public void createActionParser(String actionType, ActionsManager.ActionParser action) {
        this.actionsRegistry.put(actionType, action);
    }

    @Override
    public void executeChatCooldownActions(Player dataRegistry) {
        this.parseActions(EnhancedCommunication.get()
                .getPrimaryConfig()
                .getChatCooldownsActions(), dataRegistry);
    }

    @Override
    public void executeMentionActions(GroupedDataRegistry dataRegistry) {
        this.parseActions(EnhancedCommunication.get()
                .getPrimaryConfig()
                .getMentionActions(), dataRegistry);
    }

    @Override
    public void executeChatCooldownActions(GroupedDataRegistry dataRegistry) {
        this.parseActions(EnhancedCommunication.get()
                .getPrimaryConfig()
                .getChatCooldownsActions(), dataRegistry);
    }

    @Override
    public void parseActions(@NotNull List<String> configuredActions, Player player) {
        List<Action> actions = configuredActions.stream()
                .map((action) -> {
                    String[] steps = action.split(":");
                    return Objects.requireNonNull(this.actionsRegistry.get(steps[0]), "User input an invalid action in the configuration files: " + steps[0])
                            .apply(steps, player);
                })
                .toList();
        for (Action action : actions) action.execute(player);
    }

    @Override
    public void parseActions(@NotNull List<String> configuredActions, @NotNull GroupedDataRegistry dataRegistry) {
        List<Action> actions = configuredActions.stream()
                .map((action) -> {
                    String[] steps = action.split(":");
                    return Objects.requireNonNull(this.actionsRegistry.get(steps[0]), "User input an invalid action in the configuration files: " + steps[0])
                            .apply(steps, dataRegistry.getPlayer());
                })
                .toList();

        for (Player player : dataRegistry) for (Action action : actions) action.execute(player);
    }
}
