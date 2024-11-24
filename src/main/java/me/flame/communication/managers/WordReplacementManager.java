package me.flame.communication.managers;

import me.flame.communication.utils.Reloadable;

public interface WordReplacementManager extends Reloadable {
    /**
     * Replaces words in the given message based on the word replacements defined
     * in the primary configuration.
     *
     * @param message the message to replace words in
     * @return the message with all words replaced
     */
    String replaceWords(String message);

    /**
     * Adds a new word mapper to the word replacement configuration.
     * This mapper will replace occurrences of the specified key
     * with the given value in messages processed by this manager.
     *
     * @param key the word to be replaced
     * @param value the word to replace with
     */
    void addWordMapper(String key, String value);

    /**
     * Removes the word mapper with the given key from the word replacement configuration.
     * After calling this method, any words that were replaced by the removed mapper will no longer be replaced.
     *
     * @param key the key of the word mapper to remove
     */
    void removeWordMapper(String key);

    /**
     * Clears all word mappers from the word replacement configuration.
     * After calling this method, no words will be replaced until new mappers are added.
     */
    void clearWordMappers();
}
