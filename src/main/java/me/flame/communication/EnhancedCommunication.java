package me.flame.communication;

import me.flame.communication.listeners.LoginListener;
import me.flame.communication.listeners.PrimaryChatListener;
import me.flame.communication.managers.ChatManager;

import me.flame.communication.managers.ConversationManager;
import me.flame.communication.settings.ChatFilterSettings;
import me.flame.communication.settings.MessagesSettings;
import me.flame.communication.settings.PrimarySettings;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class EnhancedCommunication extends JavaPlugin {
    private static EnhancedCommunication INSTANCE;
    public static final Logger LOGGER = LoggerFactory.getLogger(EnhancedCommunication.class);

    private PrimarySettings primaryConfig;
    private ChatFilterSettings chatFilterConfig;
    private MessagesSettings messagesConfig;

    private ChatManager chatManager;
    private ConversationManager conversationManager;

    @Override
    public void onEnable() {
        INSTANCE = this;

        this.primaryConfig = new PrimarySettings();
        this.chatFilterConfig = new ChatFilterSettings();
        this.messagesConfig = new MessagesSettings();

        this.chatManager = new ChatManager(this);
        this.conversationManager = new ConversationManager();

        Bukkit.getPluginManager().registerEvents(new PrimaryChatListener(chatManager), this);
        Bukkit.getPluginManager().registerEvents(new LoginListener(this), this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        INSTANCE = null;
        chatManager = null;
    }

    @NotNull
    public static Logger getPluginLogger() {
        return EnhancedCommunication.LOGGER;
    }

    @NotNull
    public static EnhancedCommunication get() {
        return EnhancedCommunication.INSTANCE;
    }

    @NotNull
    public PrimarySettings getPrimaryConfig() {
        return this.primaryConfig;
    }

    @NotNull
    public MessagesSettings getMessagesConfig() {
        return this.messagesConfig;
    }

    @NotNull
    public ChatFilterSettings getChatFilterConfig() {
        return this.chatFilterConfig;
    }

    @NotNull
    public ChatManager getChatManager() {
        return chatManager;
    }

    @NotNull
    public ConversationManager getConversationManager() {
        return conversationManager;
    }
}