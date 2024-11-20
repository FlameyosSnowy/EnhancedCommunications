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
}
