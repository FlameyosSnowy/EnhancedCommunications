package me.flame.communication.managers;

import io.papermc.paper.chat.ChatRenderer;
import me.flame.communication.data.RawDataRegistry;
import me.flame.communication.renderers.ProcessedChatRenderer;
import me.flame.communication.utils.Reloadable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import panda.std.Option;

public interface ChatManager extends Reloadable {
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
    ProcessedChatRenderer getDefaultChatRenderer();

    @NotNull
    MessageModifierManager getMessageModifierManager();

    void setMessageModifierManager(final MessageModifierManager messageModifierManager);

    void setMentionsManager(final MentionsManager mentionsManager);

    void setActionsManager(final ActionsManager actionsManager);

    void setCooldownManager(final ChatCooldownManager cooldownManager);

    void setChatFormatManager(final ChatFormatManager chatFormatManager);

    void setDefaultChatRenderer(ProcessedChatRenderer chatRenderer);
}
