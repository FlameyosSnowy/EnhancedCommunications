package me.flame.communication.utils;

import me.flame.communication.EnhancedCommunication;
import org.bukkit.Bukkit;

public class ServerHelper {
    public static final boolean IS_FOLIA = isFolia();

    private static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void runDelayed(Runnable runnable, long delay) {
        if (IS_FOLIA) {
            Bukkit.getGlobalRegionScheduler().runDelayed(EnhancedCommunication.get(), (task) -> runnable.run(), delay);
        } else {
            Bukkit.getScheduler().runTaskLater(EnhancedCommunication.get(), runnable, delay);
        }
    }
}
