package me.flame.communication.managers.impl;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.managers.WordReplacementManager;
import me.flame.communication.messages.SerializedMessage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordReplacementManagerImpl implements WordReplacementManager {
    private final Map<String, String> wordsToReplace;

    public WordReplacementManagerImpl() {
        this.wordsToReplace = new HashMap<>();
        this.loadWordMappersFromConfig();
    }

    @Override
    public void replaceWords(final SerializedMessage data) {
        if (wordsToReplace.isEmpty()) return;

        for (Map.Entry<String, String> mappers : this.wordsToReplace.entrySet()) {
            data.setMessage(data.getMessage().replace(mappers.getKey(), mappers.getValue()));
        }
    }

    @Override
    public void addWordMapper(final String key, final String value) {
        this.wordsToReplace.put(key, value);
    }

    @Override
    public void removeWordMapper(final String key) {
        this.wordsToReplace.remove(key);
    }

    @Override
    public void clearWordMappers() {
        this.wordsToReplace.clear();
    }

    @Override
    public void reload() {
        this.clearWordMappers();
        this.loadWordMappersFromConfig();
    }

    private void loadWordMappersFromConfig() {
        List<String> mappers = EnhancedCommunication.get().getPrimaryConfig().getWordReplacements();
        if (mappers.isEmpty()) return;

        for (String mapper : mappers) {
            String[] processedMapper = mapper.split(":");

            if (processedMapper.length != 2) throw new IllegalArgumentException("Found wrong word replacement configuration: " + mapper + " returned" + Arrays.toString(processedMapper) + "\nList: " + mappers);

            this.wordsToReplace.put(processedMapper[0], processedMapper[1]);
        }
    }
}
