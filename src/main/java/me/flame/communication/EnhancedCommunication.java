package me.flame.communication;

import dev.velix.imperat.BukkitImperat;
import me.flame.communication.commands.MessageCommand;
import me.flame.communication.commands.ReloadCommand;
import me.flame.communication.commands.ReplyCommand;
import me.flame.communication.listeners.LoginListener;
import me.flame.communication.listeners.PrimaryChatListener;
import me.flame.communication.managers.ChatManager;
import me.flame.communication.managers.ConversationManager;
import me.flame.communication.managers.impl.ChatManagerImpl;

import me.flame.communication.managers.impl.ConversationManagerImpl;
import me.flame.communication.settings.MessagesSettings;
import me.flame.communication.settings.PrimarySettings;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EnhancedCommunication extends JavaPlugin {
    private static EnhancedCommunication INSTANCE;
    public static final Logger LOGGER = LoggerFactory.getLogger(EnhancedCommunication.class);

    private BukkitImperat commandManager;

    private PrimarySettings primaryConfig;
    private MessagesSettings messagesConfig;

    private ChatManager chatManager;
    private ConversationManager conversationManager;

    @Override
    public void onEnable() {
        INSTANCE = this;

        this.primaryConfig = new PrimarySettings();
        this.messagesConfig = new MessagesSettings();


        this.chatManager = new ChatManagerImpl(this);
        this.conversationManager = new ConversationManagerImpl();

        this.commandManager = BukkitImperat.create(this);

        if (this.primaryConfig.isMessagingEnabled()) {
            this.commandManager.registerCommand(new MessageCommand());
            this.commandManager.registerCommand(new ReplyCommand());
        }
        this.commandManager.registerCommand(new ReloadCommand());

        this.commandManager.registerDependencyResolver(ConversationManager.class, () -> conversationManager);
        this.commandManager.registerDependencyResolver(ChatManager.class, () -> chatManager);
        this.commandManager.registerDependencyResolver(PrimarySettings.class, () -> primaryConfig);
        this.commandManager.registerDependencyResolver(MessagesSettings.class, () -> messagesConfig);

        this.getServer().getPluginManager().registerEvents(new PrimaryChatListener(this), this);
        this.getServer().getPluginManager().registerEvents(new LoginListener(this), this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        INSTANCE = null;
        this.chatManager = null;
        this.primaryConfig = null;
        this.messagesConfig = null;
        this.commandManager = null;
        this.conversationManager = null;
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
    public ChatManager getChatManager() {
        return chatManager;
    }

    @NotNull
    public ConversationManager getConversationManager() {
        return conversationManager;
    }

    public void setChatManager(final ChatManager chatManager) {
        this.chatManager = chatManager;
    }

    public void setConversationManager(final ConversationManager conversationManager) {
        this.conversationManager = conversationManager;
    }
}