package me.flame.communication.managers;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.filter.ChatFilter;
import me.flame.communication.filter.LevenshteinChatFilter;
import me.flame.communication.filter.SimpleChatFilter;

import static me.flame.communication.EnhancedCommunication.LOGGER;

public class ChatModerationManager {
    private final EnhancedCommunication plugin = EnhancedCommunication.get();
    private ChatFilter chatFilter, chatCensor;

    public ChatModerationManager() {
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

    public boolean
}
