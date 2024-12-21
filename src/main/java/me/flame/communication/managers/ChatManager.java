package me.flame.communication.managers;

import me.flame.communication.renderers.ProcessedChatRenderer;
import me.flame.communication.utils.Reloadable;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface ChatManager extends Reloadable {
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
