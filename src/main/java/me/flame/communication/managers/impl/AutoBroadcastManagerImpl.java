package me.flame.communication.managers.impl;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;

import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;

import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.broadcasts.BroadcastTask;
import me.flame.communication.broadcasts.BroadcastViewers;
import me.flame.communication.events.PreBroadcastAnnounceEvent;
import me.flame.communication.managers.AutoBroadcastManager;
import me.flame.communication.broadcasts.Broadcast;
import me.flame.communication.utils.ServerHelper;
import org.bukkit.Bukkit;
import org.bukkit.World;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

public class AutoBroadcastManagerImpl implements AutoBroadcastManager {
    private final YamlDocument config;

    private final Map<String, BroadcastTask> broadcasts = new HashMap<>(5);

    public AutoBroadcastManagerImpl() {
        try {
            this.config = YamlDocument.create(
                    new File(EnhancedCommunication.get().getDataFolder(), "broadcasts.yml"),
                    Objects.requireNonNull(EnhancedCommunication.get().getResource("broadcasts.yml")),
                    UpdaterSettings.builder()
                            .setAutoSave(true)
                            .setVersioning(new BasicVersioning("config-version"))
                            .build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.addBroadcasts();
    }

    @Override
    public void addAutoBroadcast(@NotNull final Broadcast broadcast) {
        String uniqueId = broadcast.getUniqueId();
        long interval = broadcast.getInterval();

        ScheduledFuture<?> task = ServerHelper.runScheduledAsync(() -> this.broadcast(broadcast), interval, interval);
        BroadcastTask broadcastTask = new BroadcastTask(task, broadcast);

        this.broadcasts.put(uniqueId, broadcastTask);
    }

    @Override
    public void removeAutoBroadcast(@NotNull final Broadcast broadcast) {
        String uniqueId = broadcast.getUniqueId();
        BroadcastTask removedBroadcast = this.broadcasts.remove(uniqueId);
        removedBroadcast.cancel();
    }

    @Override
    public void broadcast(@NotNull final Broadcast broadcast) {
        BroadcastViewers viewers = broadcast.getViewers();
        PreBroadcastAnnounceEvent event = new PreBroadcastAnnounceEvent(!Bukkit.isPrimaryThread(), viewers, broadcast);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) return;
        if (viewers.isGlobal()) {
            Bukkit.broadcast(broadcast.getLore());
            return;
        }
        for (World world : viewers.getWorlds()) world.sendMessage(broadcast.getLore());
    }

    @Override
    public Section getSection(final String broadcastSection) {
        return this.config.getSection(broadcastSection);
    }

    @Override
    public void reload() {
        this.broadcasts.clear();
        this.addBroadcasts();
    }

    private void addBroadcasts() {
        for (Object broadcast : this.config.getKeys()) this.addAutoBroadcast(Broadcast.create(this, broadcast.toString()));
    }
}