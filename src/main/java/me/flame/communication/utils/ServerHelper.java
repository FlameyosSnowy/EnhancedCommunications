package me.flame.communication.utils;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
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

    /**
     * Schedules a task to run asynchronously after a certain delay
     *
     * @param runnable the task to be executed
     * @param delay the initial delay before the task is first executed, in seconds
     */
    public static void runDelayedAsync(Runnable runnable, long delay) {
        SERVICE.schedule(runnable, delay * 50, TimeUnit.MILLISECONDS);
    }
}
