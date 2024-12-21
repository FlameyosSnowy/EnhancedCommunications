package me.flame.communication.managers.impl;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.data.GroupedDataRegistry;

import me.flame.communication.data.MessageDataRegistry;

import me.flame.communication.managers.ActionsManager;
import me.flame.communication.managers.MentionsManager;
import me.flame.communication.messages.SerializedMessage;
import me.flame.communication.settings.PrimarySettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MentionsManagerImpl implements MentionsManager {
    private final PrimarySettings settings = EnhancedCommunication.get().getPrimaryConfig();

    private static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9_]+");
    private static final Pattern EXCLUDED_SYMBOLS = Pattern.compile("([^\\w_]+)?(\\w+)([^\\w_]+)?");

    @Override
    public void changeMentionsLook(final Player player, final SerializedMessage data) {
        if (!this.settings.isMentionsEnabled()) return;

        String message = data.getMessage();
        ActionsManager actionsManager = EnhancedCommunication.get().getChatManager().getActionsManager();

        Set<Player> players = new HashSet<>();

        data.setMessage(this.processMessage(message, players));
        GroupedDataRegistry<SerializedMessage> mentionedUsers = MessageDataRegistry.fromSet(player, data, null, players);

        actionsManager.executeMentionActions(mentionedUsers);
    }

    @NotNull
    private String processMessage(@NotNull final String message, final Set<Player> players) {
        StringBuilder newMessage = new StringBuilder(message.length());
        String[] words = message.split("\\s+");

        for (String word : words) {
            Matcher matcher = EXCLUDED_SYMBOLS.matcher(word);

            if (matcher.matches()) {
                // Handle prefix
                if (matcher.group(1) != null) {
                    newMessage.append(matcher.group(1));
                }

                // Process username
                if (matcher.group(2) != null) {
                    String processedToken = this.processToken(matcher.group(2), players);
                    newMessage.append(processedToken);
                }

                // Handle suffix
                if (matcher.group(3) != null) {
                    newMessage.append(matcher.group(3));
                }
            } else {
                newMessage.append(word); // Append the word as-is if it doesn't match
            }

            newMessage.append(' '); // Add space between words
        }

        return newMessage.toString().trim();
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

    @Nullable
    private static String resolvePlayerUsername(final @NotNull String token, @NotNull final String symbol) {
        String username = parseUsername(token, symbol);
        if (username == null) return null;

        return USERNAME_PATTERN.matcher(username).matches() ? username : null;
    }

    @Nullable
    private static String parseUsername(final @NotNull String token, final @NotNull String symbol) {
        String username;
        if (!symbol.isEmpty()) {
            if (token.startsWith(symbol)) username = token.substring(1);
            else return null;
        } else {
            username = token;
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

        builder.append(username).append(this.settings.getMentionColorEnd());
        return builder.toString();
    }
}