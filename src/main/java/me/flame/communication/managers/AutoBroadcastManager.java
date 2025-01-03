package me.flame.communication.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import me.flame.communication.broadcasts.Broadcast;
import me.flame.communication.utils.Reloadable;

@SuppressWarnings("unused")
public interface AutoBroadcastManager extends Reloadable {
    /**
     * Adds a new broadcast to the manager with a specified broadcast configuration.
     *
     * @param broadcast the broadcast to be added
     */
    void addAutoBroadcast(Broadcast broadcast);

    /**
     * Removes a broadcast from the manager.
     *
     * <p>This method stops the scheduled task associated with the broadcast and removes it from the internal
     * registry. The broadcast will no longer be announced to the viewers.
     *
     * @param broadcast the broadcast to be removed
     */
    void removeAutoBroadcast(Broadcast broadcast);

    /**
     * Broadcasts the given broadcast to all the viewers that are registered to receive it.
     *
     * <p>This method is used internally by the {@link AutoBroadcastManager} to announce the broadcast to the
     * viewers. The scheduled task associated with the broadcast will call this method when the interval specified
     * in the configuration has passed.
     *
     * @param broadcast the broadcast to be broadcasted
     */
    void broadcast(Broadcast broadcast);

    /**
     * Gets the section associated with the given broadcast section name.
     *
     * @param broadcastSection the name of the section
     * @return the section associated with the given name
     */
    Section getSection(String broadcastSection);
}
