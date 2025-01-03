package me.flame.communication.managers.impl;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.managers.*;
import me.flame.communication.renderers.DefaultChatRenderer;
import me.flame.communication.renderers.ProcessedChatRenderer;
import me.flame.communication.utils.Reloadable;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ChatManagerImpl implements Reloadable, ChatManager {
    private MentionsManager mentionsManager;
    private ActionsManager actionsManager;
    private ChatCooldownManager cooldownManager;
    private MessageModifierManager messageModifierManager;
    private ChatFormatManager chatFormatManager;
    private WordReplacementManager wordReplacementManager;
    private AutoBroadcastManager autoBroadcastManager;

    private ProcessedChatRenderer processedChatRenderer = DefaultChatRenderer::new;

    public ChatManagerImpl(@NotNull final EnhancedCommunication communication) {
        // Mandatory managers
        this.actionsManager = new ActionsManagerImpl();
        this.messageModifierManager = new MessageModifierManagerImpl();

        this.mentionsManager = communication.getPrimaryConfig().isMentionsEnabled() ? new MentionsManagerImpl() : null;
        this.cooldownManager = communication.getPrimaryConfig().isChatCooldownsEnabled() ? new ChatCooldownManagerImpl() : null;
        this.chatFormatManager = !communication.getPrimaryConfig().getGroupFormat().isEmpty() ? new ChatFormatManagerImpl() : null;
        this.wordReplacementManager = !communication.getPrimaryConfig().getWordReplacements().isEmpty() ? new WordReplacementManagerImpl() : null;
        this.autoBroadcastManager = communication.getPrimaryConfig().isAutoBroadcastsEnabled() ? new AutoBroadcastManagerImpl() : null;
    }

    public @NotNull MentionsManager getMentionsManager() {
        return mentionsManager;
    }

    public @NotNull ActionsManager getActionsManager() {
        return actionsManager;
    }

    public @NotNull ChatCooldownManager getCooldownManager() {
        return cooldownManager;
    }

    @Override
    public @NotNull ChatFormatManager getChatFormatManager() {
        return chatFormatManager;
    }

    @NotNull
    @Override
    public AutoBroadcastManager getAutoBroadcastManager() {
        return autoBroadcastManager;
    }

    @NotNull
    @Override
    public ProcessedChatRenderer getDefaultChatRenderer() {
        return processedChatRenderer;
    }

    @NotNull
    @Override
    public MessageModifierManager getMessageModifierManager() {
        return messageModifierManager;
    }

    @Override
    public void setMessageModifierManager(final MessageModifierManager messageModifierManager) {
        this.messageModifierManager = messageModifierManager;
    }

    @Override
    public void setMentionsManager(final MentionsManager mentionsManager) {
        this.mentionsManager = mentionsManager;
    }

    @Override
    public void setActionsManager(final ActionsManager actionsManager) {
        this.actionsManager = actionsManager;
    }

    @Override
    public void setCooldownManager(final ChatCooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @Override
    public void setChatFormatManager(final ChatFormatManager chatFormatManager) {
        this.chatFormatManager = chatFormatManager;
    }

    @Override
    public void setDefaultChatRenderer(final ProcessedChatRenderer chatRenderer) {
        this.processedChatRenderer = chatRenderer;
    }

    @Override
    public void setAutoBroadcastManager(final AutoBroadcastManager autoBroadcastManager) {
        this.autoBroadcastManager = autoBroadcastManager;
    }

    @NotNull
    @Override
    public WordReplacementManager getWordReplacementManager() {
        return wordReplacementManager;
    }

    @Override
    public void setWordReplacementManager(final WordReplacementManager wordReplacementManager) {
        this.wordReplacementManager = wordReplacementManager;
    }

    @Override
    public void reload() {
        this.cooldownManager = initializeAndReload(this.cooldownManager, ChatCooldownManagerImpl::new);
        this.messageModifierManager = initializeAndReload(this.messageModifierManager, MessageModifierManagerImpl::new);
        this.chatFormatManager = initializeAndReload(this.chatFormatManager, ChatFormatManagerImpl::new);
        this.wordReplacementManager = initializeAndReload(this.wordReplacementManager, WordReplacementManagerImpl::new);
        this.autoBroadcastManager = initializeAndReload(this.autoBroadcastManager, AutoBroadcastManagerImpl::new);
    }

    private <T extends Reloadable> T initializeAndReload(T manager, Supplier<T> initializer) {
        if (manager == null) {
            return initializer.get();
        } else {
            manager.reload();
            return manager;
        }
    }
}
