package me.flame.communication.providers;

import me.flame.communication.EnhancedCommunication;

import me.flame.communication.messages.SerializedMessage;
import net.luckperms.api.LuckPerms;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import org.jetbrains.annotations.NotNull;

import org.slf4j.Logger;

import java.util.Set;

public interface ChatProvider {
    Set<String> POSSIBLE_PERMISSION_PROVIDERS = Set.of("Empty", "Vault", "LuckPerms");

    /**
     * This method is used to format a message according to a player's chat metadata.
     * This method is used for all chat messages, including those sent by the messaging system.
     * The groupFormat parameter is the group format loaded from the configuration file.
     * The method should return the formatted message.
     *
     * @param message the message that needs to be formatted
     * @param groupFormat the group format that should be used to format the message
     * @param player the player that sent the message
     * @return the formatted message
     */
    SerializedMessage getFormat(SerializedMessage message, String groupFormat, Player player);

    /**
     * Gets the chosen chat provider that is set in the configuration file.
     * This method will return the chosen chat provider, or the empty chat provider if the chosen provider is invalid.
     * If the chosen provider is "Vault", this method will check if Vault is enabled and if a permissions plugin is detected.
     * If the chosen provider is "LuckPerms", this method will check if LuckPerms is enabled.
     * If the chosen provider is "Empty", this method will return the empty chat provider.
     * In all other cases, this method will log an error and return the empty chat provider.
     *
     * @param chosenChatProvider the chosen chat provider
     * @return the chosen chat provider
     */
    @SuppressWarnings("LoggingSimilarMessage")
    @NotNull
    static ChatProvider getChosenChatProvider(String chosenChatProvider) {
        Logger logger = EnhancedCommunication.LOGGER;
        return switch (chosenChatProvider.toLowerCase()) {
            case "vault" -> {
                if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
                    logger.error("Vault has not been enabled.");
                    logger.error("You cannot use any features of vault chat formatting in the mean time.");
                    yield EmptyChatProvider.EMPTY;
                }
                RegisteredServiceProvider<Chat> registeredServiceProvider = Bukkit.getServicesManager().getRegistration(Chat.class);
                if (registeredServiceProvider == null) {
                    logger.error("Vault has not detected any permissions plugin.");
                    logger.error("You cannot use any features of vault chat formatting.");
                    yield EmptyChatProvider.EMPTY;
                }

                Chat chat = registeredServiceProvider.getProvider();
                yield new VaultChatProvider(chat);
            }
            case "luckperms" -> {
                if (!Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
                    logger.error("LuckPerms has not been enabled.");
                    logger.error("You cannot use any of the chat formatting features from LuckPerms");
                    yield EmptyChatProvider.EMPTY;
                }
                LuckPerms luckPerms = Bukkit.getServicesManager().load(LuckPerms.class);
                yield new LuckPermsChatProvider(luckPerms);
            }
            case "empty" -> EmptyChatProvider.EMPTY;
            default -> {
                logger.error("Invalid chat provider supplied, must choose:");
                logger.error(POSSIBLE_PERMISSION_PROVIDERS.toString());
                yield EmptyChatProvider.EMPTY;
            }
        };
    }
}
