package me.flame.communication.broadcasts;

import java.util.concurrent.ScheduledFuture;

public record BroadcastTask(ScheduledFuture<?> task, Broadcast broadcast) {
    /**
     * Cancel the broadcast task.
     */
    public void cancel() {
        task.cancel(false);
    }
}
