package me.flame.communication.settings;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.flame.communication.EnhancedCommunication;
import me.flame.communication.utils.Reloadable;
import me.flame.communication.utils.SectionData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimarySettings implements Reloadable {
    private final CommentedConfigurationNode config;

    private final YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
            .path(Path.of(EnhancedCommunication.get().getDataFolder().getPath(), "config.yml"))
            .indent(2)
            .build();
;

    private final Map<SectionData, Object> cachedValues = new HashMap<>(10);

    public PrimarySettings() {
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

    public String getGroupFormat(String group) {
        return this.get(String.class, "group-formats", group);
    }

    public String getGroupFormat() {
        return this.getGroupFormat("chat-format");
    }

    public String getChatProvider() {
        return this.get(String.class, "chat-provider");
    }

    public <T> T get(@NotNull Class<T> clazz, Object... section) {
        SectionData sectionData = SectionData.create(section);
        T cachedValue = clazz.cast(this.cachedValues.get(sectionData));
        if (cachedValue == null) {
            T value;
            try {
                value = config.node(section).get(clazz);
            } catch (SerializationException e) {
                throw new RuntimeException(e);
            }
            this.cachedValues.put(sectionData, value);
            cachedValue = value;
        }
        return cachedValue;

    }

    @SuppressWarnings("unchecked")
    public List<String> getStringList(Object... section) {
        SectionData sectionData = SectionData.create(section);
        Object list = this.cachedValues.get(sectionData);
        if (list == null) {
            List<String> value;
            try {
                value = config.node(section).getList(String.class);
            } catch (SerializationException e) {
                throw new RuntimeException(e);
            }
            this.cachedValues.put(sectionData, value);
            list = value;
        }
        return (List<String>) list;
    }

    public boolean isMentionsEnabled() {
        return this.get(Boolean.class, "mentions", "enabled");
    }

    public char getMentionSymbol() {
        return this.get(Character.class, "mentions", "mention-symbol");
    }

    public boolean getMentionColor() {
        return this.get(Boolean.class, "mentions", "mention-color");
    }

    public List<String> getMentionActions() {
        return this.getStringList("mentions", "on-mention-actions");
    }

    public boolean isChatCooldownsEnabled() {
        return this.get(Boolean.class, "chat-cooldown", "enabled");
    }

    public List<String> getChatCooldownsActions() {
        return this.getStringList("chat-cooldown", "on-chat-cooldown-actions");
    }

    public long getChatCooldown() {
        Double cooldown = this.get(Double.class, "chat-cooldowns", "cooldown");
        return (long) Math.floor(cooldown * 1000);
    }

    public boolean isMessagingEnabled() {
        return this.get(Boolean.class, "messaging", "enabled");
    }

    public String getMessagingSenderFormat() {
        return this.get(String.class, "messaging", "message-sender-format");
    }

    public String getMessagingRecipientFormat() {
        return this.get(String.class, "messaging", "message-recipient-format");
    }

    public boolean isChatProviderEnabled() {
        return false;
    }
}
