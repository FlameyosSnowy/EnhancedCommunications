package me.flame.communication.managers;

import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.renderers.ProcessedChatRenderer;
import me.flame.communication.utils.Reloadable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import panda.std.Option;

@SuppressWarnings("unused")
public interface ChatManager extends Reloadable {
    /**
     * Process a chat message for a player.
     *
     * @param player the player who sent the message
     * @param message the message sent by the player
     * @return a processed {@link RawDataRegistry} if the message was
     *         allowed to be sent, otherwise an empty option
     */
    @NotNull
    Option<RawDataRegistry> processChat(Player player, String message);

    @NotNull
    MentionsManager getMentionsManager();

    @NotNull
    ActionsManager getActionsManager();

    @NotNull
    ChatCooldownManager getCooldownManager();

    @NotNull
    ChatFormatManager getChatFormatManager();

    @NotNull
    AutoBroadcastManager getAutoBroadcastManager();

    @NotNull
    ProcessedChatRenderer getDefaultChatRenderer();

    @NotNull
    MessageModifierManager getMessageModifierManager();

    @NotNull
    WordReplacementManager getWordReplacementManager();

    void setMessageModifierManager(final MessageModifierManager messageModifierManager);

    void setMentionsManager(final MentionsManager mentionsManager);

    void setActionsManager(final ActionsManager actionsManager);

    void setCooldownManager(final ChatCooldownManager cooldownManager);

    void setChatFormatManager(final ChatFormatManager chatFormatManager);

    void setDefaultChatRenderer(final ProcessedChatRenderer chatRenderer);

    void setAutoBroadcastManager(final AutoBroadcastManager autoBroadcastManager);

    void setWordReplacementManager(WordReplacementManager wordReplacementManager);
}
