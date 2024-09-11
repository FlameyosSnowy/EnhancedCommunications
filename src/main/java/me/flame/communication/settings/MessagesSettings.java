package me.flame.communication.settings;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.utils.MessageRouteData;
import me.flame.communication.utils.Reloadable;
import me.flame.communication.utils.SectionData;
import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.entity.Player;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class MessagesSettings implements Reloadable {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private final CommentedConfigurationNode config;

    private final YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
            .path(Path.of(EnhancedCommunication.get().getDataFolder().getPath(), "messages.yml"))
            .indent(2)
            .build();
    ;

    private final Map<SectionData, Object> cachedValues = new HashMap<>(10);

    public MessagesSettings() {
        try {
            this.config = loader.load(ConfigurationOptions.defaults());
        } catch (ConfigurateException e) {
            throw new RuntimeException(e);
        }
    }

    public void reload() {
        try {
            loader.save(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.cachedValues.clear();
    }

    public void send(Player player, String route) {
        String prefix = this.get("server-prefix");
        if (!prefix.isEmpty()) prefix += " ";

        player.sendMessage(prefix + this.miniMessage.deserialize(this.get(route)));
    }

    public void send(Player player, MessageRouteData route) {
        String prefix = this.get("server-prefix");
        if (!prefix.isEmpty()) prefix += " ";

        player.sendMessage(prefix + this.miniMessage.deserialize(route.content()));
    }

    public String get(Object... section) {
        SectionData sectionData = SectionData.create(section);
        String cachedValue = (String) this.cachedValues.get(sectionData);
        if (cachedValue == null) {
            String value = config.node(section).getString();
            this.cachedValues.put(sectionData, value);
            cachedValue = value;
        }
        return cachedValue;
    }
}
