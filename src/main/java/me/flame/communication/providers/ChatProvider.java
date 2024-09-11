package me.flame.communication.providers;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.data.DataRegistry;
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
    Set<String> POSSIBLE_PERMISSION_PROVIDERS = Set.of("Vault", "LuckPerms");

    String getFormat(String message, String groupFormat, Player player);

    GenericCachedMetaData getMetaData(String message, String groupFormat, Player player, DataRegistry dataRegistry);

    @NotNull CachedGroupMetaData getCachedGroupMetaData(@NotNull Player player, World world, DataRegistry dataRegistry);

    @SuppressWarnings("LoggingSimilarMessage")
    @NotNull
    static ChatProvider getChosenChatProvider(String chosenChatProvider) {
        Logger logger = EnhancedCommunication.LOGGER;
        if (!EnhancedCommunication.get().getPrimaryConfig().isChatProviderEnabled()) {
            return new EmptyChatProvider();
        }
        switch (chosenChatProvider) {
            case "vault" -> {
                if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
                    logger.error("Vault has not been enabled.");
                    logger.error("You cannot use any features of vault chat formatting in the mean time.");
                    return new EmptyChatProvider();
                }
                RegisteredServiceProvider<Chat> registeredServiceProvider = Bukkit.getServicesManager().getRegistration(Chat.class);
                if (registeredServiceProvider == null) {
                    logger.error("Vault has not detected any permissions plugin.");
                    logger.error("You cannot use any features of vault chat formatting.");
                    return new EmptyChatProvider();
                }

                Chat chat = registeredServiceProvider.getProvider();
                return new VaultChatProvider(chat);
            }
            case "luckperms" -> {
                if (!Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
                    logger.error("LuckPerms has not been enabled.");
                    logger.error("You cannot use any of the chat formatting features from LuckPerms");
                    return new EmptyChatProvider();
                }
                LuckPerms luckPerms = Bukkit.getServicesManager().load(LuckPerms.class);
                return new LuckPermsChatProvider(luckPerms);
            }
            default -> {
                logger.error("Invalid chat provider supplied, must choose:");
                logger.error(POSSIBLE_PERMISSION_PROVIDERS.toString());
                return new EmptyChatProvider();
            }
        }
    }
}
