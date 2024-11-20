package me.flame.communication.utils;

import me.flame.communication.EnhancedCommunication;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ServerHelper {
    public static final boolean IS_FOLIA = isFolia();

    private ServerHelper() {}

    private static final ScheduledExecutorService SERVICE = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    private static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Runs a task asynchronously after a certain delay in ticks.
     * <p>
     * Uses {@link org.bukkit.scheduler.BukkitScheduler#runTaskLater(Plugin, Runnable, long)}
     * if the server is not running Folia.
     * <p>
     * otherwise uses {@link io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler#runDelayed(Plugin, Consumer, long)}.
     *
     * @param runnable the task to run
     * @param delay the delay in ticks
     */
    public static void runDelayed(Runnable runnable, long delay) {
        if (IS_FOLIA) {
            Bukkit.getGlobalRegionScheduler().runDelayed(EnhancedCommunication.get(), (task) -> runnable.run(), delay);
        } else {
            Bukkit.getScheduler().runTaskLater(EnhancedCommunication.get(), runnable, delay);
        }
    }

    /**
     * Schedules a task to run asynchronously at a fixed rate with an initial delay.
     * <p>
     * This method uses a scheduled executor service to execute the given task periodically.
     *
     * @param runnable the task to be executed
     * @param delay the initial delay before the task is first executed, in seconds
     * @param period the period between successive executions, in seconds
     */
    @NotNull
    public static ScheduledFuture<?> runScheduledAsync(Runnable runnable, long delay, long period) {
        return SERVICE.scheduleAtFixedRate(runnable, delay, period, TimeUnit.SECONDS);
    }
}
