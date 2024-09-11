package me.flame.communication.managers;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.actions.ActionsRegistry;
import me.flame.communication.data.MessageDataRegistry;
import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.filter.LevenshteinChatFilter;
import me.flame.communication.filter.ChatFilter;
import me.flame.communication.filter.SimpleChatFilter;
import me.flame.communication.providers.ChatProvider;
import me.flame.communication.utils.Reloadable;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import panda.std.Blank;
import panda.std.Result;

import java.time.Instant;

import static me.flame.communication.EnhancedCommunication.LOGGER;

public class ChatManager implements Reloadable {
    private final MentionsManager mentionsManager;
    private final ActionsManager actionsManager;
    private final ChatCooldownManager cooldownManager;
    private final MessageModifierManager messageModifierManager;

    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private ChatProvider chatProvider;
    private ChatFilter chatFilter, chatCensor;

    private final EnhancedCommunication plugin;
    private PlayerChannelManager channelManager;

    public ChatManager(@NotNull final EnhancedCommunication communication) {
        this.plugin = communication;

        LOGGER.info("Registering mentions manager.");
        this.mentionsManager = new MentionsManager();
        if (!communication.getPrimaryConfig().isMentionsEnabled()) {
            LOGGER.info("Mentions is not enabled.");
        }

        LOGGER.info("Registering actions manager.");
        this.actionsManager = new ActionsManager();

        LOGGER.info("Registering message modification manager.");
        this.messageModifierManager = new MessageModifierManager();

        this.channelManager = new PlayerChannelManager();

        LOGGER.info("Registering cooldowns manager.");
        this.cooldownManager = new ChatCooldownManager();

        if (!communication.getPrimaryConfig().isChatCooldownsEnabled()) {
            LOGGER.info("Chat cooldowns are not enabled.");
        }

        this.chatProvider = ChatProvider.getChosenChatProvider(this.plugin.getPrimaryConfig().getChatProvider().toLowerCase());

        LOGGER.info("Registering chat filter.");
        this.chatFilter = this.plugin.getChatFilterConfig().isChatFilterEnabled() &&
                this.plugin.getChatFilterConfig().isAdvancedChatFilterEnabled()
                ? new LevenshteinChatFilter()
                : new SimpleChatFilter();
        for (String filteredWord : this.plugin.getChatFilterConfig().getFilteredWords()) {
            this.chatFilter.prohibitWord(filteredWord);
        }

        LOGGER.info("Registering chat filter.");
        this.chatCensor = this.plugin.getChatFilterConfig().isChatCensorEnabled() && this.plugin.getChatFilterConfig().isAdvancedChatFilterEnabled()
                ? new LevenshteinChatFilter()
                : new SimpleChatFilter();
        for (String filteredWord : this.plugin.getChatFilterConfig().getCensoredWords()) {
            this.chatCensor.prohibitWord(filteredWord);
        }
    }

    public Result<RawDataRegistry, Blank> processChat(Player player, String message) {
        if (this.containsBlockedBehaviors(player, message)) return Result.error(Blank.BLANK);

        RawDataRegistry dataRegistry = MessageDataRegistry.createRaw(player, message);

        this.messageModifierManager.editMessage(dataRegistry);
        this.insertCooldownIfAllowed(player);

        return Result.ok(dataRegistry);
    }

    private void insertCooldownIfAllowed(final Player player) {
        if (this.cooldownManager.isChatCooldownsEnabled()) {
            this.cooldownManager.insertCooldown(player, Instant.now().plusMillis(this.plugin.getPrimaryConfig().getChatCooldown()));
        }
    }

    private boolean containsBlockedBehaviors(final Player player, final String message) {
        if (this.cooldownManager.hasCooldown(player)) {
            this.actionsManager.createChatCooldownActions(player);
            return true;
        }

        if (this.chatFilter.isMessageBlocked(message)) {
            this.actionsManager.createFilterActions(player);
            return true;
        }

        if (this.chatCensor.isMessageBlocked(message)) {
            this.actionsManager.createCensorActions(player);
            return true;
        }
        return false;
    }

    public MentionsManager getMentionsManager() {
        return mentionsManager;
    }

    public ActionsManager getActionsManager() {
        return actionsManager;
    }

    public ChatCooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public ChatFilter getChatFilter() {
        return chatFilter;
    }

    @NotNull
    public ChatProvider getChatProvider() {
        return chatProvider;
    }

    @Override
    public void reload() {
        this.cooldownManager.reload();
        this.chatProvider = ChatProvider.getChosenChatProvider(this.plugin
                .getPrimaryConfig()
                .getChatProvider()
                .toLowerCase());
        this.chatFilter = this.plugin.getChatFilterConfig().isChatFilterEnabled() &&
                        this.plugin.getChatFilterConfig().isAdvancedChatFilterEnabled()
                ? new LevenshteinChatFilter()
                : new SimpleChatFilter();

        this.chatCensor = this.plugin.getChatFilterConfig().isChatFilterEnabled() &&
                        this.plugin.getChatFilterConfig().isAdvancedChatFilterEnabled()
                ? new LevenshteinChatFilter()
                : new SimpleChatFilter();

        this.actionsManager.reload();
        this.messageModifierManager.reload();

        for (String filteredWord : this.plugin.getChatFilterConfig().getFilterActions()) {
            this.chatFilter.prohibitWord(filteredWord);
        }

        for (String filteredWord : this.plugin.getChatFilterConfig().getCensoredWords()) {
            this.chatCensor.prohibitWord(filteredWord);
        }
    }

    public PlayerChannelManager getChannelManager() {
        return channelManager;
    }
}
