package me.flame.communication.settings;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.utils.Reloadable;
import me.flame.communication.utils.SectionData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatFilterSettings implements Reloadable {
    private final CommentedConfigurationNode config;

    private final YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
            .path(Path.of(EnhancedCommunication.get().getDataFolder().getPath(), "messages.yml"))
            .indent(2)
            .build();
    ;

    private final Map<SectionData, Object> cachedValues = new HashMap<>(10);

    public ChatFilterSettings() {
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

    public boolean isChatFilterEnabled() {
        return this.get(Boolean.class, "chat-filter", "enabled");
    }

    public boolean isAdvancedChatFilterEnabled() {
        return this.get(Boolean.class, "advanced-chat-filter", "enabled");
    }

    public List<String> getCensorActions() {
        return this.getStringList("chat-censor", "on-word-censor");
    }

    public List<String> getFilterActions() {
        return this.getStringList("chat-filter", "on-word-filter");
    }

    public boolean isChatCensorEnabled() {
        return this.get(Boolean.class, "chat-censor", "enabled");
    }

    public List<String> getFilteredWords() {
        return this.getStringList("chat-filter", "banned-words");
    }

    public List<String> getCensoredWords() {
        return this.getStringList("chat-filter", "banned-words");
    }

}
