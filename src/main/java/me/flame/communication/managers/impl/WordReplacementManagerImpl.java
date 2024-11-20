package me.flame.communication.managers.impl;

import me.flame.communication.EnhancedCommunication;
import me.flame.communication.managers.WordReplacementManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordReplacementManagerImpl implements WordReplacementManager {
    private final Map<String, String> wordsToReplace;

    public WordReplacementManagerImpl() {
        this.wordsToReplace = new HashMap<>();
        this.reload();
    }

    @Override
    public String replaceWords(final String message) {
        if (wordsToReplace.isEmpty()) return message;

        String newMessage = message;
        for (Map.Entry<String, String> mappers : this.wordsToReplace.entrySet()) {
            newMessage = newMessage.replace(mappers.getKey(), mappers.getValue());
        }

        return newMessage;
    }

    @Override
    public void reload() {
        if (!wordsToReplace.isEmpty()) wordsToReplace.clear();

        List<String> mappers = EnhancedCommunication.get().getPrimaryConfig().getWordReplacements();
        if (mappers.isEmpty()) return;

        for (String mapper : mappers) {
            String[] processedMapper = mapper.split(":");

            if (processedMapper.length != 2) throw new IllegalArgumentException("Found wrong word replacement configuration: " + mapper + " returned" + Arrays.toString(processedMapper) + "\nList: " + mappers);

            this.wordsToReplace.put(processedMapper[0], processedMapper[1]);
        }
    }
}
