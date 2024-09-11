package me.flame.communication.filter;

import java.util.HashSet;
import java.util.Set;

public class SimpleChatFilter implements ChatFilter {
    private final Set<String> bannedWords;

    public SimpleChatFilter() {
        this.bannedWords = new HashSet<>(0);
    }

    @Override
    public void prohibitWord(final String word) {
        bannedWords.add(word);
    }

    @Override
    public void allowWord(final String word) {
        bannedWords.remove(word);
    }

    @Override
    public boolean isMessageBlocked(final String message) {
        return bannedWords.contains(message);
    }
}
