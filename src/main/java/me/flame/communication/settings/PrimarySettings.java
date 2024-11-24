package me.flame.communication.settings;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.utils.Reloadable;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class PrimarySettings implements Reloadable {
    private final YamlDocument config;

    private final Map<String, Object> cachedValues = new ConcurrentHashMap<>(10);

    public PrimarySettings() {
        try {
            this.config = YamlDocument.create(
                    new File(EnhancedCommunication.get().getDataFolder(), "config.yml"),
                    Objects.requireNonNull(EnhancedCommunication.get().getResource("config.yml")),
                    UpdaterSettings.builder().setAutoSave(true).setVersioning(new BasicVersioning("config-version")).build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reload() {
        try {
            config.reload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.cachedValues.clear();
    }
    public String getGroupFormat() {
        return this.get("chat-format.group-format");
    }

    public String getChatProvider() {
        return this.get("chat-format.chat-provider");
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String section) {
        T cachedValue = (T) this.cachedValues.get(section);
        if (cachedValue == null) {
            T value = (T) config.get(section);
            if (value == null) return null;

            this.cachedValues.put(section, value);
            cachedValue = value;
        }
        return cachedValue;
    }

    public Long getLong(String section) {
        Long cachedValue = (Long) this.cachedValues.get(section);
        if (cachedValue == null) {
            Long value = config.getLong(section);
            if (value == null) return null;

            this.cachedValues.put(section, value);
            return value;
        }
        return cachedValue;

    }

    @SuppressWarnings("unchecked")
    public List<String> getStringList(String section) {
        List<String> list = (List<String>) this.cachedValues.get(section);
        if (list == null) {
            List<String> value = this.config.getStringList(section);
            if (value == null) return null;

            this.cachedValues.put(section, value);
            return value;
        }
        return list;
    }

    public boolean isMentionsEnabled() {
        return this.get("mentions.enabled");
    }

    public String getMentionSymbol() {
        return this.get("mentions.mention-symbol");
    }

    public String getMentionColor() {
        return this.get("mentions.mention-color");
    }

    public List<String> getMentionActions() {
        return this.getStringList("mentions.on-mention-actions");
    }

    public boolean isChatCooldownsEnabled() {
        return this.get("chat-cooldown.enabled");
    }

    public List<String> getChatCooldownsActions() {
        return this.getStringList("chat-cooldown.on-chat-cooldown-actions");
    }

    public Long getChatCooldown() {
        return this.getLong("chat-cooldown.cooldown");
    }

    public boolean isMessagingEnabled() {
        return this.get("messaging.enabled");
    }

    public String getMessagingSenderFormat() {
        return this.get("messaging.message-sender-format");
    }

    public String getMessagingRecipientFormat() {
        return this.get("messaging.message-recipient-format");
    }

    public boolean isCountingMessageOtherAsConversationEnd() {
        return this.get("messaging.count-message-other-as-conversation-end");
    }

    public boolean isChatFormatPlaceholderApiEnabled() {
        return this.get("chat-format.placeholderapi");
    }

    public List<String> getWordReplacements() {
        return this.getStringList("word-replacements");
    }

    public boolean isAutoBroadcastsEnabled() {
        return this.get("enable-broadcasts");
    }
}
