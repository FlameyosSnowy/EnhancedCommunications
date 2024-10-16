package me.flame.communication.providers;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.data.DataRegistry;
import me.flame.communication.providers.data.GroupChatMetaData;
import me.flame.communication.providers.data.ChatMetaData;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Set;

public interface ChatProvider {
    Set<String> POSSIBLE_PERMISSION_PROVIDERS = Set.of("Empty", "Vault", "LuckPerms");

    String getFormat(String message, String groupFormat, Player player);

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
