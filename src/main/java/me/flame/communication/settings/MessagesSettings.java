package me.flame.communication.settings;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.flame.communication.EnhancedCommunication;
import me.flame.communication.utils.MessageData;
import me.flame.communication.utils.Reloadable;

import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MessagesSettings implements Reloadable {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private final YamlDocument config;

    private final Map<String, Object> cachedValues = new ConcurrentHashMap<>(10);

    public MessagesSettings() {
        try {
            this.config = YamlDocument.create(
                    new File(EnhancedCommunication.get().getDataFolder(), "messages.yml"),
                    Objects.requireNonNull(EnhancedCommunication.get().getResource("messages.yml")),
                    UpdaterSettings.builder().setAutoSave(true).setVersioning(new BasicVersioning("config-version")).build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reload() {
        try {
            this.config.reload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.cachedValues.clear();
    }

    public void send(Player player, String route) {
        String prefix = this.get("server.prefix");
        if (!prefix.isEmpty()) prefix += " ";

        player.sendMessage(this.miniMessage.deserialize(prefix).append(this.miniMessage.deserialize(this.get(route))));
    }

    @SuppressWarnings("unused")
    public void send(Player player, MessageData route) {
        String prefix = this.get("server.prefix");
        if (!prefix.isEmpty()) prefix += " ";

        player.sendMessage(this.miniMessage.deserialize(prefix).append(this.miniMessage.deserialize(route.content())));
    }

    public String get(String section) {
        String cachedValue = (String) this.cachedValues.get(section);
        if (cachedValue == null) {
            String value = config.getString(section);
            if (value == null) return null;

            this.cachedValues.put(section, value);
            cachedValue = value;
        }
        return cachedValue;
    }
}