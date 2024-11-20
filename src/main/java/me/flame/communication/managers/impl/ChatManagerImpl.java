package me.flame.communication.managers.impl;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.data.MessageDataRegistry;
import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.managers.*;
import me.flame.communication.renderers.DefaultChatRenderer;
import me.flame.communication.renderers.ProcessedChatRenderer;
import me.flame.communication.utils.Reloadable;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import panda.std.Option;

import java.time.Instant;

import static me.flame.communication.EnhancedCommunication.LOGGER;

public class ChatManagerImpl implements Reloadable, ChatManager {
    private MentionsManager mentionsManager;
    private ActionsManager actionsManager;
    private ChatCooldownManager cooldownManager;
    private MessageModifierManager messageModifierManager;
    private ChatFormatManager chatFormatManager;
    private WordReplacementManager wordReplacementManager;
    private AutoBroadcastManager autoBroadcastManager;

    private ProcessedChatRenderer processedChatRenderer = DefaultChatRenderer::new;

    private final EnhancedCommunication plugin;

    public ChatManagerImpl(@NotNull final EnhancedCommunication communication) {
        this.plugin = communication;

        LOGGER.info("Registering mentions manager.");
        this.mentionsManager = new MentionsManagerImpl();
        if (!communication.getPrimaryConfig().isMentionsEnabled()) {
            LOGGER.info("Mentions is not enabled.");
        }

        LOGGER.info("Registering actions manager.");
        this.actionsManager = new ActionsManagerImpl();

        LOGGER.info("Registering actions manager.");
        this.autoBroadcastManager = new AutoBroadcastManagerImpl();

        this.wordReplacementManager = new WordReplacementManagerImpl();

        LOGGER.info("Registering chat format manager.");
        this.chatFormatManager = new ChatFormatManagerImpl();

        LOGGER.info("Registering message modification manager.");
        this.messageModifierManager = new MessageModifierManagerImpl();

        LOGGER.info("Registering cooldowns manager.");
        this.cooldownManager = new ChatCooldownManagerImpl();

        if (!communication.getPrimaryConfig().isChatCooldownsEnabled()) {
            LOGGER.info("Chat cooldowns are not enabled.");
        }
    }

    public @NotNull Option<RawDataRegistry> processChat(Player player, String message) {
        if (this.containsBlockedBehaviors(player)) return Option.none();

        RawDataRegistry dataRegistry = MessageDataRegistry.createRaw(player, message);

        this.messageModifierManager.editMessage(dataRegistry);
        this.insertCooldownIfAllowed(player);

        return Option.of(dataRegistry);
    }

    private boolean containsBlockedBehaviors(final Player player) {
        if (!this.cooldownManager.hasCooldown(player)) return false;
        this.actionsManager.executeChatCooldownActions(player);
        return true;
    }

    private void insertCooldownIfAllowed(final Player player) {
        if (!this.cooldownManager.isChatCooldownsEnabled()) return;
        this.cooldownManager.insertCooldown(player, Instant.now().plusSeconds(this.plugin.getPrimaryConfig().getChatCooldown()));
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
        this.cooldownManager.reload();
        this.chatFormatManager.reload();
        this.messageModifierManager.reload();
        this.wordReplacementManager.reload();
        this.autoBroadcastManager.reload();
    }
}
