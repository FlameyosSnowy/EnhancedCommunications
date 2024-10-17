package me.flame.communication.managers.impl;

import com.google.common.collect.Iterators;
import me.flame.communication.EnhancedCommunication;
import me.flame.communication.data.GroupedDataRegistry;

import me.flame.communication.data.MessageDataRegistry;

import me.flame.communication.managers.ActionsManager;
import me.flame.communication.managers.MentionsManager;
import me.flame.communication.settings.PrimarySettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import panda.std.Option;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

public class MentionsManagerImpl implements MentionsManager {
    private final PrimarySettings settings = EnhancedCommunication.get().getPrimaryConfig();

    private static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9_]+"); // Adjust as needed

    @Override
    public Option<GroupedDataRegistry> changeMentionsLook(final Player player, final String message) {
        if (!this.settings.isMentionsEnabled()) return Option.none();

        ActionsManager actionsManager = EnhancedCommunication.get().getChatManager().getActionsManager();

        StringBuilder currentToken = new StringBuilder(8);
        StringBuilder newMessage = new StringBuilder(message.length());
        Set<Player> players = new HashSet<>();

        for (int index = 0; index < message.length(); index++) {
            char c = message.charAt(index);
            if (Character.isWhitespace(c)) {
                String processedToken = this.processToken(currentToken.toString(), players);
                newMessage.append(processedToken).append(' ');
                currentToken.setLength(0);
            } else {
                currentToken.append(c);
            }
        }

        String processedToken = this.processToken(currentToken.toString(), players);
        newMessage.append(processedToken);

        GroupedDataRegistry mentionedUsers = MessageDataRegistry.fromSet(player, newMessage.toString(), null, players);

        actionsManager.executeMentionActions(mentionedUsers);
        return Option.of(mentionedUsers);
    }

    private String processToken(@NotNull String token, Set<Player> mentionedUsers) {
        String symbol = this.settings.getMentionSymbol();
        String username = resolvePlayerUsername(token, symbol);

        if (username == null) return token;

        Player player = Bukkit.getPlayer(username);
        if (player != null) {
            mentionedUsers.add(player);
            return this.changeMentionLook(username, symbol);
        } else {
            return token;
        }
    }

    private static String resolvePlayerUsername(final @NotNull String token, @NotNull final String symbol) {
        String username = null;
        if (!symbol.isEmpty() && token.startsWith(symbol)) {
            username = token.substring(1);
            if (USERNAME_PATTERN.matcher(token).matches()) username = token;
        }
        return username;
    }

    public String changeMentionLook(@NotNull final String username, @NotNull String symbol) {
        String mentionColor = this.settings.getMentionColor();
        StringBuilder builder = new StringBuilder(mentionColor.length() + username.length() + (!symbol.isEmpty() ? 1 : 0));
        builder.append(mentionColor);

        if (!symbol.isEmpty()) {
            builder.append(symbol);
        }

        builder.append(username);
        return builder.toString();
    }
}
